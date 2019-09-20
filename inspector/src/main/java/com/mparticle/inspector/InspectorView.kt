package com.mparticle.inspector

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.support.v4.view.PagerTitleStrip
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import com.mparticle.inspector.adapters.BaseListAdapter
import com.mparticle.inspector.adapters.ChainListAdapter
import com.mparticle.inspector.adapters.RecentListAdapter
import com.mparticle.inspector.adapters.CategorizedListAdapter
import com.mparticle.inspector.customviews.ResizeDraggableLayout
import com.mparticle.shared.events.Event
import com.mparticle.shared.EventViewType
import com.mparticle.shared.ViewControllerManager
import com.mparticle.shared.getDtoType

class InspectorView(val application: Context, val dataManager: DataManager, val startTime: Long, val viewControllerManager: ViewControllerManager) {

    var small = true
    var i = 0;
    lateinit var viewPager: ViewPager
    lateinit var viewPagerTitle: PagerTitleStrip
    var listAdapter: SwipeViewAdapter? = null
    lateinit var wrapperViewGroup: SpyLayout
    lateinit var clientContainer: ViewGroup
    lateinit var inspectorContainer: ResizeDraggableLayout


    var clientView: View? = null
    var screenRoot: ViewGroup? = null

    fun attach(activity: Activity) {
        wrapperViewGroup = LayoutInflater.from(activity).inflate(R.layout.widget, null) as SpyLayout
        clientContainer = wrapperViewGroup.findViewById(R.id.rootView)
        inspectorContainer = wrapperViewGroup.findViewById(R.id.widgetContainer)
        wrapperViewGroup.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                setInitialSize()
                wrapperViewGroup.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }

        })

        screenRoot = activity.findViewById(android.R.id.content)
        clientView = screenRoot?.getChildAt(0)
        screenRoot?.removeView(clientView)
        screenRoot?.addView(wrapperViewGroup)
        clientContainer.addView(clientView)
        wrapperViewGroup.setWidgetView(inspectorContainer)

        viewPager = inspectorContainer.findViewById(R.id.pager)
        viewPagerTitle = inspectorContainer.findViewById(R.id.pager_title)
        viewPager.adapter = SwipeViewAdapter(application, dataManager).also { listAdapter = it }
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(pageIndex: Int, p1: Float, p2: Int) {
                if (pageIndex == 2) {
                    viewPagerTitle.setNonPrimaryAlpha(0.0f)
                } else {
                    viewPagerTitle.setNonPrimaryAlpha(.8f)
                }
            }

            override fun onPageSelected(p0: Int) {}

        })
        inspectorContainer.updateLogoSize()
    }

    fun detach() {
        (clientView?.parent as ViewGroup?)?.removeView(clientView)
        screenRoot?.removeView(wrapperViewGroup)
        screenRoot?.addView(clientView)
    }


    private fun setInitialSize() {
        val dimensions: Inspector.Dimensions = Inspector.getInstance()?.dimensions ?: return
        if (dimensions.set) {
            dimensions.applyTo(inspectorContainer.layoutParams as FrameLayout.LayoutParams)
        } else {
            if (small) {
                (inspectorContainer.layoutParams as FrameLayout.LayoutParams).apply {
                    leftMargin = 0
                    bottomMargin = 0
                    rightMargin = 0
                    topMargin = ((inspectorContainer.parent as View).height * .6).toInt()
                }
            } else {
                inspectorContainer.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            }
            Inspector.getInstance()?.dimensions = Inspector.Dimensions().apply { update(inspectorContainer.layoutParams as FrameLayout.LayoutParams) }
        }
        inspectorContainer.requestLayout()
        inspectorContainer.updateLogoSize()
    }


    inner class SwipeViewAdapter(val context: Context, listenerImplementation: DataManager) : PagerAdapter() {
        var chainName: String? = null

        override fun isViewFromObject(p0: View, p1: Any): Boolean {
            return p0 == p1
        }

        val views = ArrayList<ListFragment>()
        var chainViewId: Int? = null

        fun refreshItem(item: Event) {
            views.forEach {
                it.rvAdapter.also {
                    it?.refreshDataObject(item)
                }
            }
        }


        val displayCallback = { id: Int ->
            val objClicked = listenerImplementation.getById(id)
            when (objClicked) {
                null -> {
                    when (id) {
                        -1 -> if (viewPager.currentItem == 1) viewPager.setCurrentItem(0, true)
                        -2 -> if (viewPager.currentItem == 0) viewPager.setCurrentItem(1, true)
                    }
                }
                else -> {
                    chainViewId = id
                    if (views.size > 2) {
                        val adapter = views.get(2).itemView.adapter
                        if (adapter is ChainListAdapter) {
                            adapter.setItem(id)
                        }
                    }
                    chainName = listenerImplementation.getById(id)?.let { "${it.getShortName()}- ${it.name}" }
                    notifyDataSetChanged()
                    viewPager.setCurrentItem(3, true)
                }
            }
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            if (views.size < position || views.getOrNull(position)?.itemView == null) {
                when (position) {
                    0 -> {
                        ListFragment(container, position, RecentListAdapter(context, dataManager, viewControllerManager.streamController, displayCallback, startTime), linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true))
                    }
                    1 -> {
                        ListFragment(container, position, CategorizedListAdapter(context, dataManager, viewControllerManager.categoryController, displayCallback, startTime), linearLayoutManager = LinearLayoutManager(context))
                    }
                    2 -> {
                        ListFragment(container, position, ChainListAdapter(context, dataManager, displayCallback, startTime, chainViewId ?: -1), linearLayoutManager = LinearLayoutManager(context))
                    }
                    else -> null
                }?.let {
                    it.itemView.setOnLongClickListener { false }
                    views.add(position, it)
                }
            }
            return views.getOrNull(position)?.itemView ?: throw RuntimeException("View is Null")
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        }

        override fun getCount(): Int {
            return 2 + if (chainViewId != null) 1 else 0
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> {
                    if (viewPager.currentItem == 0 || views.getOrNull(position)?.itemView?.adapter?.itemCount ?: 0 > 0)
                        "Feed"
                    else
                        ""
                }
                1 -> "All Events"
                2 -> "PathFinder${chainName?.let { ": $it" } ?: ""}"
                else -> "unknown"
            }
        }
    }

    class ListFragment(container: ViewGroup, position: Int, val rvAdapter: BaseListAdapter?, val linearLayoutManager: LinearLayoutManager) {
        var itemView: RecyclerView

        init {
            itemView = when (position) {
                0 -> container.findViewById(R.id.recycler_view)
                1 -> container.findViewById(R.id.recycler_view1)
                2 -> container.findViewById(R.id.recycler_view2)
                else -> throw RuntimeException()
            }
            itemView.apply {
                layoutManager = linearLayoutManager
                rvAdapter?.listView = this
                adapter = rvAdapter
            }
        }
    }
}

class SpyLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    var view: ResizeDraggableLayout? = null

    fun setWidgetView(view: ResizeDraggableLayout) {
        this.view = view
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        view?.also { view ->
            when (ev?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    view.isEnabled =
                            ev.x > view.x &&
                            ev.x < view.x + view.width &&
                            ev.y > view.y &&
                            ev.y < view.y + view.height
                    view.setTransparent(!view.isEnabled)
                }
            }
        }
        return false
    }
}
package com.sample.testapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.mparticle.MPEvent
import com.mparticle.MParticle
import com.mparticle.commerce.CommerceEvent
import com.mparticle.commerce.Product
import com.mparticle.commerce.TransactionAttributes
import com.mparticle.identity.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity(), View.OnClickListener, TaskFailureListener, TaskSuccessListener, IdentityStateListener {

//    private Inspector widgetApi = new Inspector();
    private var mEmailView: EditText? = null
    private var mCustomerIdView:EditText? = null
    private var mOtherIdView:EditText? = null
    private var mAttrKey:EditText? = null
    private var mAttrValue:EditText? = null
    private var mPushToken:EditText? = null
    private var mProgressView: View? = null
    private var mLoginFormView: View? = null
    private var mCopyUserAttributesSwitch: Switch? = null
    private var mCurrentUserText: TextView? = null
    private val mMainHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity)
        mEmailView = findViewById(R.id.email_text)
        mCustomerIdView = findViewById(R.id.customerid_text)
        mOtherIdView = findViewById(R.id.other_text)
        mCurrentUserText = findViewById(R.id.current_user_text)
        mAttrKey = findViewById(R.id.attr_key)
        mAttrValue = findViewById(R.id.attr_value)
        mPushToken = findViewById(R.id.push_token)

        findViewById<View>(R.id.log_in_button).setOnClickListener(this)
        findViewById<View>(R.id.log_out_button).setOnClickListener(this)
        findViewById<View>(R.id.identify_button).setOnClickListener(this)
        findViewById<View>(R.id.modify_button).setOnClickListener(this)

        mLoginFormView = findViewById(R.id.login_form)
        mProgressView = findViewById(R.id.login_progress)
        mCopyUserAttributesSwitch = findViewById(R.id.copy_attributes_switch)
        MParticle.getInstance()?.Identity()?.apply {
            getCurrentUser()?.also { user ->
                showUser(user)
            } ?: addIdentityStateListener(this@MainActivity)
        }
    }

    internal var userUpdateRunnable: Runnable = object : Runnable {
        override fun run() {
            showUser(MParticle.getInstance()?.Identity()?.getCurrentUser())
            mMainHandler.postDelayed(this, 200)
        }
    }

    override fun onResume() {
        super.onResume()

        MParticle.getInstance()?.Identity()?.getCurrentUser().also { showUser(it) }

        mMainHandler.post(userUpdateRunnable)
    }

    override fun onPause() {
        super.onPause()
        mMainHandler.removeCallbacks(userUpdateRunnable)
    }

    private fun showUser(user: MParticleUser?) {
        if (user == null) {
            mCurrentUserText?.text = "User is Null"
            return
        }
        val userJson = JSONObject()
        try {
            userJson.put("MPID", user.getId())
            userJson.put("User Attributes", JSONObject(user.getUserAttributes()))
            val identityString = StringBuilder()
            for (entry in user.getUserIdentities().entries) {
                identityString.append(entry.key.name + ": " + entry.value)
            }
            userJson.put("User Identities", identityString.toString())
            mCurrentUserText?.text = userJson.toString(2)
        } catch (e: JSONException) {
            e.printStackTrace()
            mCurrentUserText?.text = e.message
        }

    }

    private fun showProgress(show: Boolean) {
        val shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime)

        mLoginFormView?.visibility = if (show) View.GONE else View.VISIBLE
        mLoginFormView?.animate()?.setDuration(shortAnimTime.toLong())?.alpha(
                (if (show) 0 else 1).toFloat())?.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mLoginFormView?.visibility = if (show) View.GONE else View.VISIBLE
            }
        })

        mProgressView?.visibility = if (show) View.VISIBLE else View.GONE
        mProgressView?.animate()?.setDuration(shortAnimTime.toLong())?.alpha(
                (if (show) 1 else 0).toFloat())?.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mProgressView?.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onClick(view: View) {

        val email = mEmailView?.text.toString()
        val customerId = mCustomerIdView?.getText().toString()
        val otherId = mOtherIdView?.getText().toString()

        showProgress(true)
        val apiRequest = IdentityApiRequest
                .withUser(MParticle.getInstance()?.Identity()?.currentUser)
                .email(if (TextUtils.isEmpty(email)) null else email)
                .customerId(if (TextUtils.isEmpty(customerId)) null else customerId)
                .userIdentity(MParticle.IdentityType.Other, if (TextUtils.isEmpty(otherId)) null else otherId)
                .build()

        //        BranchMetricsKit kit = new BranchMetricsKit();
        //        kit.onKitCreate(new HashMap<String, String>(), this);
        MParticle.getInstance()?.Identity()?.apply {

        when (view.id) {
            R.id.log_in_button -> login(apiRequest)
                    .addFailureListener(this@MainActivity)
                    .addSuccessListener(this@MainActivity)
            R.id.log_out_button -> logout(apiRequest)
                    .addFailureListener(this@MainActivity)
                    .addSuccessListener(this@MainActivity)
            R.id.identify_button -> identify(apiRequest)
                    .addFailureListener(this@MainActivity)
                    .addSuccessListener(this@MainActivity)
            R.id.modify_button -> modify(apiRequest)
                    .addFailureListener(this@MainActivity)
                    .addSuccessListener(this@MainActivity)
        }
        }
    }

    override fun onFailure(httpResponse: IdentityHttpResponse?) {
        Log.e("IDENTITY ERROR", httpResponse.toString())
        showProgress(false)
    }

    override fun onSuccess(identityApiResult: IdentityApiResult) {
        showProgress(false)
        showUser(identityApiResult.getUser())
    }

    override fun onUserIdentified(mParticleUser: MParticleUser, previousUser: MParticleUser?) {
        showUser(mParticleUser)
        MParticle.getInstance()?.Identity()?.removeIdentityStateListener(this)
    }

    @Suppress("UNUSED_PARAMETER")
    fun addUA(view: View) {
        val key = mAttrKey?.text.toString()
        val value = mAttrValue?.text.toString()
        MParticle.getInstance()?.Identity()?.getCurrentUser()?.also { user ->
            if (user.setUserAttribute(key, value)) {
                mAttrKey?.setText("")
                mAttrValue?.setText("")
            }
            Handler().postDelayed({ showUser(user) }, 100)
        }
    }

    internal var i = 0
    @Suppress("UNUSED_PARAMETER")
    fun logEvent(view: View) {
        when (Random().nextInt().absoluteValue % 4) {
            0 -> MParticle.getInstance()?.logEvent(MPEvent.Builder("Event ${i++}").build())
            1 -> {
                val product1 = Product.Builder("Name", "sku123", 2.0).brand("asdv").build()
                val product2 = Product.Builder("Name2", "sdluusdiofbnvdsfv", 4.22).category("category").build()
                val transactionAttributes = TransactionAttributes().apply {
                    id = "1234123442314231234341214341233412423"
                    this.revenue = 99.0
                }
                val commerceEvent = CommerceEvent.Builder(Product.CHECKOUT, product1)
                        .addProduct(product2)
                        .transactionAttributes(transactionAttributes)
                        .build()
                MParticle.getInstance()?.logEvent(commerceEvent)
            }
            2 -> MParticle.getInstance()?.leaveBreadcrumb("important Breadcrumb ${i++}")
            3 -> MParticle.getInstance()?.logScreen("Screen ${i++}", HashMap<String, String>().apply {
                put("attribute1", "value1")
                put("attribute2", "value2")
            })
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun upload(view : View) {
        MParticle.getInstance()?.upload()
    }

    @Suppress("UNUSED_PARAMETER")
    fun updatePushToken(view: View) {
        val pushToken = mPushToken?.text.toString()
        MParticle.getInstance()?.logPushRegistration(pushToken, "senderId")
    }

    @Suppress("UNUSED_PARAMETER")
    fun nextScreen(v: View) {
        startActivity(Intent(this, SecondActivity::class.java))
    }
}


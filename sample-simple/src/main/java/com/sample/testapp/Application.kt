package com.sample.testapp

import android.app.Application
import android.util.Log
import com.mparticle.MParticle
import com.mparticle.MParticleOptions
import com.mparticle.commerce.Product
import com.mparticle.identity.*
import java.util.*

class TestAppApplication: Application(), IdentityStateListener, TaskFailureListener, TaskSuccessListener {

    override fun onCreate() {
        super.onCreate()
        val options = MParticleOptions.builder(this)
                .credentials("288e1ccd4cdca947913108770df5a12e", "J8iG1IgsRL7fZ9WqhcgZYxSUr3ay1MuA3W-hJFMWTNswkryhvkDt6ZT9XhCpoXQ7")
                .logLevel(MParticle.LogLevel.VERBOSE)
                .uploadInterval(10)
                .sessionTimeout(10)
                .identify(IdentityApiRequest.withEmptyUser().email(Random().nextInt().toString()).build())
                .identifyTask(
                        BaseIdentityTask()
                                .addFailureListener(this)
                                .addSuccessListener(this)
                )
                .build()
        MParticle.start(options)
        MParticle.getInstance()!!.Identity().addIdentityStateListener(this)

    }

    override fun onTerminate() {
        super.onTerminate()
    }

    override fun onUserIdentified(mParticleUser: MParticleUser, previousUser: MParticleUser?) {
        Log.d("IDENTITY CHANGED", mParticleUser.id.toString() + "")
    }

    override fun onFailure(identityHttpResponse: IdentityHttpResponse?) {
        Log.d("IDENTITY FAILURE", identityHttpResponse?.toString())
    }

    override fun onSuccess(identityApiResult: IdentityApiResult) {
        Log.d("IDENTITY SUCCESS", identityApiResult.user.id.toString() + "")
        Product.Builder("product1", "1234", 99.0).build()
                .also {
                    MParticle.getInstance()?.Identity()?.currentUser?.cart?.apply {
                        add(it)
                        checkout()
                        clear()
                    }

                }
    }
}
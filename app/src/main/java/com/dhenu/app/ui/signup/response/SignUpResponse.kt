package com.dhenu.app.ui.signup.response

import Api
import com.dhenu.app.data.Parser
import com.dhenu.app.data.remote.ApiFactory
import com.dhenu.app.ui.base.BaseResponse
import com.dhenu.app.util.NetworkResponseCallback
import com.google.gson.annotations.SerializedName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignUpResponse : BaseResponse<SignUpResponse, String, Any>() {

    @SerializedName("message")
    var message: String = ""

//    @SerializedName("data")
//    var data: List<Any>? = null

    override fun doNetworkRequest(
        requestParam: HashMap<String, Any>,
        vararg: Any,
        networkResponseCallback: NetworkResponseCallback<SignUpResponse>
    ): Disposable {

        val api = ApiFactory.getClientWithoutHeader().create(Api::class.java)
        return api.userSignUp(requestParam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { networkResponseCallback.onResponse(it) },
                { throwable -> Parser.parseErrorResponse(throwable, networkResponseCallback) })
    }

//    data class SignupBean(
//        val api_token: String,
//        val braintree_id: String,
//        val card_brand: String,
//        val card_last_four: Int,
//        val created_at: String,
//        val email: String,
//        val has_media: Boolean,
//        val id: Boolean,
//        val name: String,
//        val paypal_email: String,
//        val stripe_id: String,
//        val trial_ends_at: String,
//        val updated_at: String
//    )

}

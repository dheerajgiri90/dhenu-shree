package com.dhenu.app.ui.items.response

import Api
import com.dhenu.app.data.Parser
import com.dhenu.app.data.remote.ApiFactory
import com.dhenu.app.ui.base.BaseResponse
import com.dhenu.app.util.Logger
import com.dhenu.app.util.NetworkResponseCallback
import com.google.gson.annotations.SerializedName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AddItemsResponse : BaseResponse<AddItemsResponse, String, Any>() {

    @SerializedName("ResponseMessage")
    var message: String = ""

    override fun doNetworkRequest(
        requestParam: HashMap<String, Any>,
        vararg: Any,
        networkResponseCallback: NetworkResponseCallback<AddItemsResponse>
    ): Disposable {

        val api = ApiFactory.clientWithHeader.create(Api::class.java)
        Logger.e("requestParam>>>>>", "" + requestParam.toString())
        val isUpdate = vararg as Boolean
        return if (isUpdate) {
            api.updateItems(requestParam).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { networkResponseCallback.onResponse(it) },
                    { throwable -> Parser.parseErrorResponse(throwable, networkResponseCallback) })
        } else {
            api.addItems(requestParam).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { networkResponseCallback.onResponse(it) },
                    { throwable -> Parser.parseErrorResponse(throwable, networkResponseCallback) })
        }

    }

}


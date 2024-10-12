package com.dhenu.app.ui.village.response

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

class AddVillageResponse : BaseResponse<AddVillageResponse, String, Any>() {

    @SerializedName("ResponseMessage")
    var message: String = ""

    override fun doNetworkRequest(
        requestParam: HashMap<String, Any>,
        vararg: Any,
        networkResponseCallback: NetworkResponseCallback<AddVillageResponse>
    ): Disposable {

        val api = ApiFactory.clientWithHeader.create(Api::class.java)
        Logger.e("requestParam>>>>>", "" + requestParam.toString())
        val isUpdate = vararg as Boolean
        return if (isUpdate) {
            api.updateVillage(requestParam).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { networkResponseCallback.onResponse(it) },
                    { throwable -> Parser.parseErrorResponse(throwable, networkResponseCallback) })
        } else {
            api.addVillage(requestParam).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { networkResponseCallback.onResponse(it) },
                    { throwable -> Parser.parseErrorResponse(throwable, networkResponseCallback) })
        }

    }

}


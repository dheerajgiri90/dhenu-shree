package com.dhenu.app.ui.customer.response

import Api
import android.os.Parcelable
import com.dhenu.app.data.Parser
import com.dhenu.app.data.remote.ApiFactory
import com.dhenu.app.ui.base.BaseResponse
import com.dhenu.app.util.Logger
import com.dhenu.app.util.NetworkResponseCallback
import com.google.gson.annotations.SerializedName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize

class CustomerListResponse : BaseResponse<CustomerListResponse, String, Any>() {

    @SerializedName("ResponseMessage")
    var message: String = ""

    @SerializedName("Customer")
    var data = ArrayList<CustomerData>()

    override fun doNetworkRequest(
        requestParam: HashMap<String, Any>,
        vararg: Any,
        networkResponseCallback: NetworkResponseCallback<CustomerListResponse>
    ): Disposable {

        val api = ApiFactory.clientWithHeader.create(Api::class.java)
        Logger.e("requestParam>>>>>", "" + requestParam.toString())

        return api.getCustomer(requestParam).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { networkResponseCallback.onResponse(it) },
                { throwable -> Parser.parseErrorResponse(throwable, networkResponseCallback) })
    }

    @Parcelize
    data class CustomerData(
        val Address: String,
        val Id: Int,
        val MobileNo: String,
        val Name: String,
        val NameH: String,
        val Photo: String,
        val VillageId: Int,
        val VillageName: String
    ) : Parcelable


}


package com.dhenu.app.ui.exchange.response

import Api
import android.os.Parcelable
import com.dhenu.app.data.Parser
import com.dhenu.app.data.remote.ApiFactory
import com.dhenu.app.ui.base.BaseResponse
import com.dhenu.app.ui.exchange.response.ExchangeListResponse.ExchangeData
import com.dhenu.app.util.Logger
import com.dhenu.app.util.NetworkResponseCallback
import com.google.gson.annotations.SerializedName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize

class ExchangeItemListResponse : BaseResponse<ExchangeItemListResponse, String, Any>() {

    @SerializedName("ResponseMessage")
    var message: String = ""

    @SerializedName("ExchangeItem")
    var data = ArrayList<ExchangeItem>()

    @SerializedName("Exchange")
    var exchangeData = ArrayList<ExchangeData>()

    override fun doNetworkRequest(
        requestParam: HashMap<String, Any>,
        vararg: Any,
        networkResponseCallback: NetworkResponseCallback<ExchangeItemListResponse>
    ): Disposable {

        val api = ApiFactory.clientWithHeader.create(Api::class.java)
        Logger.e("requestParam>>>>>", "" + requestParam.toString())

        return api.getExchangeItem(requestParam).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { networkResponseCallback.onResponse(it) },
                { throwable -> Parser.parseErrorResponse(throwable, networkResponseCallback) })
    }

    @Parcelize
    data class ExchangeItem(
        val Address: String,
        val Amount: Double,
        val CustomerName: String,
        val ExchangeItemId: Int,
        val ItemDate: String,
        val ItemName: String,
        val MortgageId: Int
    ) : Parcelable

}


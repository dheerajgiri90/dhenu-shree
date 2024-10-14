package com.dhenu.app.ui.mortgage.response

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

class UpdateMortgageResponse : BaseResponse<UpdateMortgageResponse, String, Any>() {

    @SerializedName("ResponseMessage")
    var message: String = ""

    @SerializedName("TotalAmount")
    var totalAmount: String = ""

    override fun doNetworkRequest(
        requestParam: HashMap<String, Any>,
        vararg: Any,
        networkResponseCallback: NetworkResponseCallback<UpdateMortgageResponse>
    ): Disposable {

        val api = ApiFactory.clientWithHeader.create(Api::class.java)
        Logger.e("requestParam>>>>>", "" + requestParam.toString())

        return api.updateMortgage(requestParam).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { networkResponseCallback.onResponse(it) },
                { throwable -> Parser.parseErrorResponse(throwable, networkResponseCallback) })
    }

    @Parcelize
    data class MortgageData(
        val Address: String,
        val Amount: Double,
        val BusinessManId: Int,
        val BusinessmanName: String,
        val CustomerId: Int,
        val CustomerName: String,
        val Description: String,
        val EndDateDetail: String,
        val ExchangeId: Int,
        val ExchangeItemId: Int,
        val Id: Int,
        val InterestRate: Double,
        val IsClosed: Boolean,
        val IsExchanged: Boolean,
        val ItemName: String,
        val MobileNo: String,
        val MortgageDate: String,
        val Name: String,
        val NameH: String,
        val Photo: String,
        val TotalAmount: Double,
        val VillageId: Int,
        val VillageName: String,
        val Weight: Double
    ) : Parcelable

}


package com.dhenu.app.ui.base

import com.dhenu.app.util.ErrorBean
import com.dhenu.app.util.NetworkResponseCallback
import com.google.gson.annotations.SerializedName
import io.reactivex.disposables.Disposable
import java.util.*

abstract class BaseResponse<T, K, V> {

    @SerializedName("success")
    var isSuccess: Boolean = false

    @SerializedName("error")
    var errorBean: ErrorBean? = null


    abstract fun doNetworkRequest(
        requestParam: HashMap<K, V>,
        vararg: Any,
        networkResponseCallback: NetworkResponseCallback<T>
    ): Disposable

}

package com.dhenu.app.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel


import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<N : Any> : ViewModel() {

    private val isLoading = ObservableBoolean(false)
    val disposable = CompositeDisposable()

    var navigator: N? = null

    override fun onCleared() {
        super.onCleared()
        //  Logger.e("BaseViewModel", "onCleard -> " + navigator!!.javaClass.simpleName)
        disposable.clear()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

}

package com.dhenu.app.util

import android.text.Html
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


object DataBinding {
    @JvmStatic
    @BindingAdapter("setEnabled")
    fun isEnabled(view: EditText, str: String?) {
        view.isEnabled = str.isStringNullOrBlank()
    }

    @JvmStatic
    @BindingAdapter("onSingleClick")
    fun onSingleClick(view: View?, onClick: () -> Unit) {
        view?.setOnClickListener(object : OnSingleClickListener({
            onClick.invoke()
        }) {})
    }

    @BindingAdapter("onSingleClickR")
    fun View.onSingleClickR(onClick: () -> Unit) {
        setOnClickListener(object : OnSingleClickListener({
            onClick.invoke()
        }) {})
    }

    @JvmStatic
    @BindingAdapter("removeHtmlTags")
    fun removeHtmlTags(view: TextView, str: String?) {
        view.text = Html.fromHtml(str).toString()
    }

    @JvmStatic
    @BindingAdapter("imageURL")
    fun loadImageURL(view: ImageView, imageUrl: String? = "") {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(RequestOptions().override(view.layoutParams.width, view.layoutParams.height))
            .into(view)
    }


}
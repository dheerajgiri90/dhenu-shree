package com.dhenu.app.util

interface EventListener {

    fun onClick(pos: Int) {
    }

    /*on media dialog listener */
    fun onPickImagefromcamera()

    fun onPickImagefromgallery()
}
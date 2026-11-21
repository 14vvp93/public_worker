package com.mt8163tools.core.module

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

interface Module {
    val id: String
    val title: String
    @get:DrawableRes
    val iconRes: Int
    val order: Int
    val defaultEnabled: Boolean
    fun createFragment(): Fragment
}

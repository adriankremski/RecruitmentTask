package com.github.snuffix.recruitmenttask.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun Context.inflateView(@LayoutRes layoutRes: Int, parent: ViewGroup?, attachToRoot: Boolean = false): View = LayoutInflater.from(this).inflate(layoutRes, parent, attachToRoot)

fun Context.titleTransition(id: String) = "title$id"
fun Context.iconTransition(id: String) = "icon$id"
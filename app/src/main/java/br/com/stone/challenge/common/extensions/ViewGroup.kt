package br.com.stone.challenge.common.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

inline fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}
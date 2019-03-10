package br.com.stone.challenge.util.extensions

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView

inline fun TextView.onActionSearch(crossinline done: () -> Unit) {
    setOnEditorActionListener { _, i, keyEvent ->
        if (i == EditorInfo.IME_ACTION_SEARCH || keyEvent != null && keyEvent.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
            done.invoke()
        }

        false
    }
}
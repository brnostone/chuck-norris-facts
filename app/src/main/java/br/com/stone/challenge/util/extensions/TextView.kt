package br.com.stone.challenge.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView

inline fun TextView.textWatcher(init: KTextWatcher.() -> Unit): TextWatcher {
    val textWatcher = KTextWatcher().apply(init)
    addTextChangedListener(textWatcher)
    return textWatcher
}

class KTextWatcher : TextWatcher {
    private var _beforeTextChanged: ((String?, Int, Int, Int) -> Unit)? = null
    private var _onTextChanged: ((String?, Int, Int, Int) -> Unit)? = null
    private var _afterTextChanged: ((String) -> Unit)? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        _beforeTextChanged?.invoke(s?.toString(), start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _onTextChanged?.invoke(s?.toString(), start, before, count)
    }

    override fun afterTextChanged(s: Editable) {
        _afterTextChanged?.invoke(s.toString())
    }

    fun beforeTextChanged(listener: (String?, Int, Int, Int) -> Unit) {
        _beforeTextChanged = listener
    }

    fun onTextChanged(listener: (String?, Int, Int, Int) -> Unit) {
        _onTextChanged = listener
    }

    fun afterTextChanged(listener: (String) -> Unit) {
        _afterTextChanged = listener
    }

}

inline fun TextView.onActionSearch(crossinline done: () -> Unit) {
    setOnEditorActionListener { _, i, keyEvent ->
        if (i == EditorInfo.IME_ACTION_SEARCH || keyEvent != null && keyEvent.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
            done.invoke()
        }

        false
    }
}
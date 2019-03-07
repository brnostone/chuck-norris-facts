package br.com.stone.challenge.feature.common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import br.com.stone.challenge.R
import br.com.stone.challenge.util.extensions.inflate
import br.com.stone.domain.NetworkException
import br.com.stone.domain.RestException
import kotlinx.android.synthetic.main.view_error.view.*

class ErrorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(R.layout.view_error, attachToRoot = true)
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
    }

    fun setError(throwable: Throwable) = when (throwable) {
        is NetworkException -> {
            txtViewError.text = "NetworkException"
        }
        is RestException -> {
            txtViewError.text = "RestException"
        }
        else -> {
            txtViewError.text = "Unexpected error"
        }
    }

}
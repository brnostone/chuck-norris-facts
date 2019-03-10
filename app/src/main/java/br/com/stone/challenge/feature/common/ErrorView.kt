package br.com.stone.challenge.feature.common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import br.com.stone.challenge.R
import br.com.stone.challenge.util.extensions.inflate
import br.com.stone.domain.exception.NetworkException
import br.com.stone.domain.exception.RestException
import kotlinx.android.synthetic.main.view_error.view.*

class ErrorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(R.layout.view_error, attachToRoot = true)
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
    }

    fun setError(throwable: Throwable) {
        val (message, icon) = when (throwable) {
            is NetworkException -> ErrorViewData(R.string.error_network, R.drawable.ic_network_error)
            is RestException.ServerError -> ErrorViewData(R.string.error_server, R.drawable.ic_server_error)
            else -> ErrorViewData(R.string.error_generic, R.drawable.ic_generic_error)
        }

        txtViewError.text = context.getString(message)
        imgViewError.setImageDrawable(AppCompatResources.getDrawable(context, icon))
    }

    data class ErrorViewData(@StringRes val message: Int, @DrawableRes val icon: Int)

}
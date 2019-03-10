package br.com.stone.challenge.feature.common

import android.content.Context
import android.view.LayoutInflater
import androidx.test.core.app.ApplicationProvider
import br.com.stone.challenge.R
import br.com.stone.domain.exception.NetworkException
import br.com.stone.domain.exception.RestException
import kotlinx.android.synthetic.main.view_error.view.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class ErrorViewTest: AutoCloseKoinTest() {

    lateinit var errorView: ErrorView

    private val context by lazy { ApplicationProvider.getApplicationContext<Context>() }

    @Before
    fun beforeEachTest() {
        val inflater = LayoutInflater.from(context)
        errorView = inflater.inflate(R.layout.view_error, ErrorView(context), true) as ErrorView
    }

    @Test
    fun `should display networking error`() {
        errorView.setError(NetworkException.ConnectionException)

        assertErrorText(R.string.error_network)
        assertErrorImage(R.drawable.ic_network_error)
    }

    @Test
    fun `should display server error`() {
        errorView.setError(RestException.ServerError(500))

        assertErrorText(R.string.error_server)
        assertErrorImage(R.drawable.ic_server_error)
    }

    @Test
    fun `should display generic error`() {
        errorView.setError(NullPointerException())

        assertErrorText(R.string.error_generic)
        assertErrorImage(R.drawable.ic_generic_error)
    }

    private fun assertErrorText(resourceId: Int) {
        assertTrue("should have this text") {
            errorView.txtViewError.text == context.getString(resourceId)
        }
    }

    private fun assertErrorImage(resourceId: Int) {
        assertTrue("should have this image") {
            Shadows.shadowOf(errorView.imgViewError.drawable).createdFromResId == resourceId
        }
    }

}
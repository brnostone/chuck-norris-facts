package br.com.stone.challenge.feature.common

import io.reactivex.Observable
import org.junit.Test

class StateMachineTest {

    @Test
    fun `verify the success states`() {
        val data = "test data"

        Observable.just(data)
            .compose(StateMachine())
            .test()
            .assertResult(ViewState.Loading, ViewState.Success(data))
    }

    @Test
    fun `verify the error states`() {
        val exception = NullPointerException()

        Observable.error<Unit>(exception)
            .compose(StateMachine())
            .test()
            .assertResult(ViewState.Loading, ViewState.Failed(exception))
    }

}
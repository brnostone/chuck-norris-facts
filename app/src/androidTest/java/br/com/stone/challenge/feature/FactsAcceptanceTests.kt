package br.com.stone.challenge.feature

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.stone.challenge.R
import br.com.stone.challenge.feature.base.BaseTest
import br.com.stone.challenge.feature.facts.FactsActivity
import br.com.stone.challenge.feature.screen.FactsScreen
import br.com.stone.challenge.feature.screen.SearchScreen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FactsAcceptanceTests: BaseTest<FactsActivity>(FactsActivity::class) {

    @Test
    fun acceptDefaultLayout() {
        mockServer.simulate {
            allCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            emptyLayout {
                isVisible()
            }

            txtEmptyTitle {
                isVisible()
                hasText(R.string.text_welcome)
            }

            btnSearchFact {
                click()
            }
        }
    }

    @Test
    fun acceptSearchTerm() {
        mockServer.simulate {
            allCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact {
                click()
            }
        }

        onScreen<SearchScreen> {
            inputLayoutSearch {
                edit {
                    typeText("test")
                }

                isErrorDisabled()

                edit {
                    pressImeAction()
                }
            }
        }

        onScreen<FactsScreen> {
            emptyLayout {
                isGone()
            }

            recyclerFacts {
                isVisible()
                hasSize(53)
            }
        }
    }

    @Test
    fun acceptSearchValidateError() {
        mockServer.simulate {
            allCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact {
                click()
            }
        }

        onScreen<SearchScreen> {
            inputLayoutSearch {
                edit {
                    typeText("a")
                }

                isErrorEnabled()
                hasError("This field must have more characters")
            }
        }
    }

    @Test
    fun acceptHistoricTerm() {
        mockServer.simulate {
            allCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact {
                click()
            }
        }

        onScreen<SearchScreen> {
            recyclerHistoric {
                firstChild<SearchScreen.Item> {
                    click()
                }
            }
        }

        onScreen<FactsScreen> {
            emptyLayout {
                isGone()
            }

            recyclerFacts {
                isVisible()
                hasSize(53)
            }
        }
    }
/*
    @Test
    fun acceptSuggestions() {
        mockServer.simulate {
            allCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact {
                click()
            }
        }

        onScreen<SearchScreen> {
            layoutSuggestions {

                KView {

                }
            }
            recyclerHistoric {
                firstChild<SearchScreen.Item> {
                    isVisible()
                    txtTitle { hasText("historic 1") }
                }
            }
        }
    }*/

}
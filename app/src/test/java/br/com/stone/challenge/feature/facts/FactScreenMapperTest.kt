package br.com.stone.challenge.feature.facts

import br.com.stone.test.factory.FactFactory
import org.junit.Test
import kotlin.test.assertTrue

class FactScreenMapperTest {

    @Test
    fun `verify mapper without category`() {
        val fact = FactFactory.stub(hasCategory = false)
        val factScreen = FactScreenMapper.map(fact)

        val expected = listOf(CategoryFactScreen.UnCategorized)

        assertTrue("should have uncategorized") { factScreen.categories == expected }
    }

    @Test
    fun `verify mapper with categories`() {
        val fact = FactFactory.stub(hasCategory = true)
        val factScreen = FactScreenMapper.map(fact)

        val expected = fact.categories.map { CategoryFactScreen.Categorized(it.name) }

        assertTrue("should have categories") { factScreen.categories == expected }
    }


    @Test
    fun `verify mapper with text longer than 80 characters`() {
        val fact = FactFactory.stub(bigText = true)
        val factScreen = FactScreenMapper.map(fact)

        assertTrue("should have textType equals to NORMAL") { factScreen.textType == TextType.NORMAL }
    }

    @Test
    fun `verify mapper with text less than 80 characters`() {
        val fact = FactFactory.stub(bigText = false)
        val factScreen = FactScreenMapper.map(fact)

        assertTrue("should have textType equals to BIG") { factScreen.textType == TextType.BIG }
    }

}
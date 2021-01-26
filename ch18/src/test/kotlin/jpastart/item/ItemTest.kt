package jpastart.item

import jpastart.JpaTestBase
import jpastart.common.Money
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class ItemTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val item = Item("I100", "아이템100", Money(BigDecimal("1000"), "KRW"))
            em.persist(item)
            // insert into Item (name, price, id) values ('아이템100', '1000KRW', 'I100')
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun findById() {
        val em = EMF.createEntityManager()
        try {
            // select item0_.id as id1_5_0_, item0_.name as name2_5_0_, item0_.price as price3_5_0_ from Item
            // item0_ where item0_.id='I001'
            val item = em.find(Item::class.java, "I001")

            assertThat(item.price.value, `is`(BigDecimal("1000")))
            assertThat(item.price.currency, `is`("KRW"))
        } finally {
            em.close()
        }
    }

}

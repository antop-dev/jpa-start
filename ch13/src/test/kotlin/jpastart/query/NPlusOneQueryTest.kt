package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.reserve.MembershipCard
import org.junit.jupiter.api.Test

internal class NPlusOneQueryTest : JpaTestBase() {

    @Test
    fun find() {
        val em = EMF.createEntityManager()
        try {
            em.find(MembershipCard::class.java, "5678")
            // select membership0_.card_number as card_num1_5_0_, membership0_.enabled as enabled2_5_0_, membership0_.expiry_date
            // as expiry_d3_5_0_, membership0_.user_email as user_ema4_5_0_, user1_.email as email1_8_1_,
            // user1_.create_date as create_d2_8_1_, user1_.name as name3_8_1_ from membership_card membership0_
            // left outer join User user1_ on membership0_.user_email=user1_.email where membership0_.card_number='5678'
        } finally {
            em.close()
        }
    }

    @Test
    fun queryWithoutJoin() {
        val em = EMF.createEntityManager()
        try {
            // @Entity(name="membership_card") 로 하면 안되는데?
            val query = em.createQuery("select mc from MembershipCard mc", MembershipCard::class.java)
            // select membership0_.card_number as card_num1_5_, membership0_.enabled as enabled2_5_, membership0_.expiry_date
            // as expiry_d3_5_, membership0_.user_email as user_ema4_5_ from membership_card membership0_

            // N+1 쿼리들
            // select user0_.email as email1_8_0_, user0_.create_date as create_d2_8_0_, user0_.name as name3_8_0_ from User user0_ where user0_.email='jvm@javaworld.co'
            // select user0_.email as email1_8_0_, user0_.create_date as create_d2_8_0_, user0_.name as name3_8_0_ from User user0_ where user0_.email='gildong@dooly.net'
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun queryWithJoin() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select mc from MembershipCard mc left join mc.owner u", MembershipCard::class.java)
            // 조인은 하지만 N+1 발생
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun queryWithFetchJoin() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select mc from MembershipCard mc left join fetch mc.owner u", MembershipCard::class.java)
            // select membership0_.card_number as card_num1_5_0_, user1_.email as email1_8_1_, membership0_.enabled
            // as enabled2_5_0_, membership0_.expiry_date as expiry_d3_5_0_, membership0_.user_email as user_ema4_5_0_,
            // user1_.create_date as create_d2_8_1_, user1_.name as name3_8_1_ from membership_card membership0_
            // left outer join User user1_ on membership0_.user_email=user1_.email
            query.resultList
        } finally {
            em.close()
        }
    }

}

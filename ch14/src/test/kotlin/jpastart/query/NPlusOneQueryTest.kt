package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.reserve.MembershipCard
import jpastart.reserve.User
import org.junit.jupiter.api.Test
import javax.persistence.criteria.JoinType

class NPlusOneQueryTest : JpaTestBase() {

    @Test
    fun queryWithFetchJoin() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(MembershipCard::class.java)
            val mc = cq.from(MembershipCard::class.java)
            mc.fetch<MembershipCard, User>("owner", JoinType.LEFT)
            cq.select(mc)

            val query = em.createQuery(cq)
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

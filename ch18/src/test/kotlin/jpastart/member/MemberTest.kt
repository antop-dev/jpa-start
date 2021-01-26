package jpastart.member

import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.core.IsInstanceOf
import org.junit.jupiter.api.Test

class MemberTest {

    @Test
    fun findById() {
        val em = EMF.createEntityManager()
        try {
            // select member0_.id as id1_5_0_, member0_.name as name2_5_0_, member0_.comp_id as comp_id1_2_0_,
            // member0_.email as email1_6_0_, member0_.expire_date as expire_d1_7_0_, member0_.clazz_ as clazz_0_
            // from (
            // select id, name, null as comp_id, email, expire_date, 3 as clazz_ from temp_member
            // union all select id, name, comp_id, null as email, null as expire_date, 1 as clazz_ from ent_member
            // union all select id, name, null as comp_id, email, null as expire_date, 2 as clazz_ from personal_member
            // ) member0_ where member0_.id='e001'
            val member = em.find(Member::class.java, "e001")
            assertThat(member, IsInstanceOf(Member::class.java))
            assertThat(member, IsInstanceOf(EntMember::class.java))
            assertThat(member, not(IsInstanceOf(PersonalMember::class.java)))
        } finally {
            em.close()
        }
    }
}

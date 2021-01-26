package jpastart.member

import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.jupiter.api.Test

class EntMemberTest {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val member = EntMember("e002", "기업고객", "ENT001")
            em.persist(member)

            // insert into ent_member (name, comp_id, id) values ('기업고객', 'ENT001', 'e002')
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
            // select entmember0_.id as id1_5_0_, entmember0_.name as name2_5_0_, entmember0_.comp_id as comp_id1_2_0_
            // from ent_member entmember0_ where entmember0_.id='e001'
            val member = em.find(EntMember::class.java, "e001")
            assertThat(member, IsInstanceOf(Member::class.java))
            assertThat(member, IsInstanceOf(EntMember::class.java))
        } finally {
            em.close()
        }
    }
}

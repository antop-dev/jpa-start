package jpastart.attach

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.jupiter.api.Test

internal class AttachFileTest : JpaTestBase() {

    @Test
    fun findById() {
        val em = EMF.createEntityManager()
        try {
            // select attachfile0_.id as id1_0_0_, attachfile0_.name as name2_0_0_, attachfile0_.upload_date
            // as upload_d3_0_0_, attachfile0_1_.path as path1_3_0_, attachfile0_2_.provider as provider1_1_0_,
            // attachfile0_2_.url as url2_1_0_,
            // case when attachfile0_1_.id is not null then 1 when attachfile0_2_.id
            // is not null then 2 when attachfile0_.id is not null then 0
            // end as clazz_0_
            // from attach_file attachfile0_
            // left outer join local_file attachfile0_1_ on attachfile0_.id=attachfile0_1_.id
            // left outer join cloud_file attachfile0_2_ on attachfile0_.id=attachfile0_2_.id
            // where attachfile0_.id='F002'
            val file = em.find(AttachFile::class.java, "F002")
            assertThat(file, IsInstanceOf(LocalFile::class.java))
        } finally {
            em.close()
        }
    }

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val file = AttachFile("F012", "F012")
            em.persist(file)

            // insert into attach_file (name, upload_date, id) values ('F012', '01/26/2021 07:33:10.109', 'F012')
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}

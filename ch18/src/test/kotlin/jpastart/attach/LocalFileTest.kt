package jpastart.attach

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.jupiter.api.Test

internal class LocalFileTest : JpaTestBase() {

    @Test
    fun findById() {
        val em = EMF.createEntityManager()
        try {
            // select localfile0_.id as id1_0_0_, localfile0_1_.name as name2_0_0_, localfile0_1_.upload_date
            // as upload_d3_0_0_, localfile0_.path as path1_3_0_ from local_file localfile0_ inner join attach_file
            // localfile0_1_ on localfile0_.id=localfile0_1_.id where localfile0_.id='F002'
            val file = em.find(LocalFile::class.java, "F002")
            assertThat(file, IsInstanceOf(AttachFile::class.java))
        } finally {
            em.close()
        }
    }

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val file = LocalFile("F012", "F012", "/path")
            em.persist(file)

            // insert into attach_file (name, upload_date, id) values ('F012', '01/26/2021 07:12:24.464', 'F012')
            // insert into local_file (path, id) values ('/path', 'F012')
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}

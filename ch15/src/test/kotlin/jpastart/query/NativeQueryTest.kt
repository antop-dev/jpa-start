package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.reserve.Grade
import jpastart.reserve.Hotel
import org.junit.jupiter.api.Test

class NativeQueryTest : JpaTestBase() {

    @Test
    fun noMapping() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createNativeQuery(
                """
                    select id, name, grade from hotel
                    where grade = :grade
                    order by id asc limit :first, :max
                """.trimIndent()
            ).apply {
                setParameter("grade", Grade.STAR4.name)
                setParameter("first", 0)
                setParameter("max", 1)
            }
            // select id, name, grade from hotel where grade = 'STAR4' order by id asc limit 0, 1
            query.resultList

        } finally {
            em.close()
        }
    }

    @Test
    fun entityMapping() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createNativeQuery(
                """
                    select id, name, grade from hotel
                    where grade = :grade
                    order by id asc
                """.trimIndent(), Hotel::class.java
            ).apply {
                setParameter("grade", Grade.STAR4.name)
            }

            val rows = query.resultList
            for (row in rows) {
                val hotel = row as Hotel
                println("id = ${hotel.id}, name = ${hotel.name}, grade = ${hotel.grade}")
            }

        } finally {
            em.close()
        }
    }

    @Test
    fun xmlNamedNativeQuery() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createNamedQuery("Hotel.byGrade", Hotel::class.java).apply {
                setParameter("grade", Grade.STAR4.name)
            }

            // select id, name, grade from hotel where grade = 'STAR4' order by id asc
            val rows = query.resultList
            for (hotel in rows) {
                println("id = ${hotel.id}, name = ${hotel.name}, grade = ${hotel.grade}")
            }

        } finally {
            em.close()
        }
    }

    @Test
    fun javaNamedNativeQuery() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createNamedQuery("Hotel.all", Hotel::class.java)
            // select * from hotel order by id desc
            val rows = query.resultList
            for (hotel in rows) {
                println("id = ${hotel.id}, name = ${hotel.name}, grade = ${hotel.grade}")
            }

        } finally {
            em.close()
        }
    }

}

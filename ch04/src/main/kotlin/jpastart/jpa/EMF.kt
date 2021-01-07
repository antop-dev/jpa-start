package jpastart.jpa

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

class EMF {
    companion object {
        private var emf: EntityManagerFactory? = null

        @JvmStatic
        fun init() {
            emf = Persistence.createEntityManagerFactory("jpastart")
        }

        @JvmStatic
        fun createEntityManager(): EntityManager {
            if (emf == null) init()
            return emf!!.createEntityManager()
        }

        @JvmStatic
        fun close() {
            emf?.close()
        }
    }

}

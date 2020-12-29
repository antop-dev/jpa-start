package jpastart.jpa

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

class EMF {
    companion object {
        private var emf: EntityManagerFactory? = null

        fun init() {
            emf = Persistence.createEntityManagerFactory("jpastart")
        }

        fun createEntityManager(): EntityManager {
            if (emf == null) init()
            return emf!!.createEntityManager()
        }

        fun close() {
            emf?.close()
        }
    }

}

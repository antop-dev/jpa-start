package jpastart.jpa

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

class EMF {
    companion object {
        private var emf: EntityManagerFactory? = null
        private var currentEm = ThreadLocal<EntityManager>()

        fun init() {
            emf = Persistence.createEntityManagerFactory("jpastart")
        }

        fun createEntityManager(): EntityManager {
            if (emf == null) init()
            return emf!!.createEntityManager()
        }

        fun currentEntityManager(): EntityManager {
            return currentEm.get() ?: run {
                val em = createEntityManager()
                currentEm.set(em)
                em
            }
        }

        fun closeCurrentEntityManager() {
            val em = currentEm.get()
            em?.run {
                currentEm.remove()
                em.close()
            }
        }

        fun close() {
            emf?.close()
        }
    }

}

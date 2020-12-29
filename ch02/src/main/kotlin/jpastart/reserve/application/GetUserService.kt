package jpastart.reserve.application

import jpastart.jpa.EMF
import jpastart.reserve.model.User

class GetUserService {

    fun getUser(email: String): User? {
        val em = EMF.createEntityManager()
        try {
            return em.find(User::class.java, email)
        } finally {
            em.close()
        }
    }

}

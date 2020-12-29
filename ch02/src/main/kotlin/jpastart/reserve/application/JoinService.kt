package jpastart.reserve.application

import jpastart.jpa.EMF
import jpastart.reserve.model.User

class JoinService {

    fun join(user: User) {
        val em = EMF.createEntityManager()
        em.transaction.begin()
        try {
            val found = em.find(User::class.java, user.email)
            found?.run { throw DuplicateEmailException() }
            em.persist(user)
            em.transaction.commit()
        } catch (ex: Exception) {
            em.transaction.rollback()
            throw ex
        } finally {
            em.close()
        }
    }

}

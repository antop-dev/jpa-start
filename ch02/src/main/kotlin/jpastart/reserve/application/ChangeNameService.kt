package jpastart.reserve.application

import jpastart.jpa.EMF
import jpastart.reserve.model.User
import java.lang.Exception

class ChangeNameService {

    fun changeName(email:String, newName:String) {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()
            val user = em.find(User::class.java, email)
            user?.run {
                user.changeName(newName)
            } ?: throw UserNotFoundException()
            em.transaction.commit()
        } catch (ex: Exception) {
            em.transaction.rollback()
        } finally {
            em.close()
        }
    }

}

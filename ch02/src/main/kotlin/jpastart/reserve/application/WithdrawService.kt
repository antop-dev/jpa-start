package jpastart.reserve.application

import jpastart.jpa.EMF
import jpastart.reserve.model.User

class WithdrawService {

    fun withdraw(email: String) {
        EMF.createEntityManager().run {
            transaction.begin()
            try {
                find(User::class.java, email)?.run { remove(this) }
                    ?: throw UserNotFoundException()
                transaction.commit()
            } catch (ex: Exception) {
                transaction.rollback()
                throw ex
            } finally {
                transaction
            }
        }
    }

}

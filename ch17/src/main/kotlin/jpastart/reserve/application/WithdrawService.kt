package jpastart.reserve.application

import jpastart.reserve.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class WithdrawService(private val userRepository: UserRepository) {

    fun withdraw(email: String) = userRepository.findByEmail(email)
        ?.run { userRepository.delete(this) }
        ?: throw UserNotFoundException()

}

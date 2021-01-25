package jpastart.reserve.application

import jpastart.reserve.model.User
import jpastart.reserve.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class GetUserService(private val userRepository: UserRepository) {

    fun getUser(email: String): User? = userRepository.findByEmail(email)

}

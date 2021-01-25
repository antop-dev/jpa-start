package jpastart.reserve.application

import jpastart.reserve.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class GetUserService(private val userRepository: UserRepository) {

    fun getUser(email: String) = userRepository.find(email)

}

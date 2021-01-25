package jpastart.reserve.application

import jpastart.reserve.model.User
import jpastart.reserve.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class GetUserListService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

}

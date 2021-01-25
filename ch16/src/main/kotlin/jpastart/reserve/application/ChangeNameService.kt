package jpastart.reserve.application

import jpastart.reserve.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeNameService(private val userRepository: UserRepository) {

    @Transactional
    fun changeName(email: String, newName: String) = userRepository.find(email)
        ?.run { changeName(newName) }
        ?: UserNotFoundException()

}

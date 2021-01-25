package jpastart.reserve.application

import jpastart.reserve.model.User
import jpastart.reserve.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class JoinService(private val userRepository: UserRepository) {

    @Transactional
    fun join(user: User) = userRepository.findByEmail(user.email)
        ?.run { throw DuplicateEmailException() }
        ?: userRepository.save(user)

}

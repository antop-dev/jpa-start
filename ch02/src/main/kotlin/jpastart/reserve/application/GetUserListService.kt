package jpastart.reserve.application

import jpastart.jpa.EMF
import jpastart.reserve.model.User

class GetUserListService {

    fun getAllUsers(): List<User> = EMF.createEntityManager().run {
        createQuery("select u from User u order by u.name", User::class.java).resultList
    }

}

package jpastart.reserve.repository

import jpastart.reserve.model.Grade
import jpastart.reserve.model.Hotel
import org.springframework.data.jpa.domain.Specification

class HotelSpecs {

    companion object {
        @JvmStatic
        fun bestGradle() = Specification<Hotel> { root, _, cb ->
            cb.equal(root.get<Grade>("grade"), Grade.STAR7)
        }

        fun nameLike(name: String) = Specification<Hotel> { root, _, cb ->
            cb.like(root.get("name"), "%$name%")
        }
    }

}

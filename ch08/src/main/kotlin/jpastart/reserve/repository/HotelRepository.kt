package jpastart.reserve.repository

import jpastart.jpa.EMF
import jpastart.reserve.model.Hotel

class HotelRepository {

    fun find(id: String): Hotel? {
        val em = EMF.currentEntityManager()
        return em.find(Hotel::class.java, id)
    }

}

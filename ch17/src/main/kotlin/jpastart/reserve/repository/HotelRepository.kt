package jpastart.reserve.repository

import jpastart.reserve.model.Grade
import jpastart.reserve.model.Hotel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

interface HotelRepository : Repository<Hotel, String>, JpaSpecificationExecutor<Hotel> {

    fun findByGrade(grade: Grade): List<Hotel>

    fun findByGradeAndName(grade: Grade, name: String): List<Hotel>

    fun findByAddressZipcode(zipcode: String): List<Hotel>

    fun findByGrade(grade: Grade, pageable: Pageable): Page<Hotel>

    fun findFirstByGradeOrderByNameAsc(grade: Grade): Hotel?

    fun findFirst3ByGradeOrderByNameAsc(grade: Grade): List<Hotel>

    fun findFirst3ByGrade(grade: Grade, pageable: Pageable): List<Hotel>

    @Query(
        "select h from Hotel h" +
                " where h.grade = ?1 and h.name like ?2 order by h.name desc"
    )
    fun findHotel1(grade: Grade, name: String): List<Hotel>

    @Query("select h from Hotel h where h.grade = :grade")
    fun findHotel2(@Param("grade") g: Grade, sort: Sort): List<Hotel>

    @Query(
        value = "select h from Hotel h where h.grade = :grade",
        countQuery = "select count(h) from Hotel h where h.grade = :grade"
    )
    fun findHotel3(@Param("grade") g: Grade, pageable: Pageable): Page<Hotel>

    // JpaSpecificationExecutor에 있음
//    fun findAll(spec: Specification<Hotel>?, pageable: Pageable)
}

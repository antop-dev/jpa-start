package jpastart.reserve.repository

import jpastart.JpaTestBase
import jpastart.reserve.model.Grade
import jpastart.reserve.model.Hotel_
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification

class HotelRepositoryTest(private val repository: HotelRepository) : JpaTestBase() {

    @Test
    fun findByGrade() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        // hotel0_ where hotel0_.grade='STAR4'
        repository.findByGrade(Grade.STAR4)
    }

    @Test
    fun findByGradeAndName() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        // hotel0_ where hotel0_.grade='STAR4' and hotel0_.name='베스트웨스턴 구로호텔'
        repository.findByGradeAndName(Grade.STAR4, "베스트웨스턴 구로호텔")
    }

    @Test
    fun findByAddressZipcode() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        // hotel0_ where hotel0_.zipcode='08393'
        repository.findByAddressZipcode("08393")
    }

    @Test
    fun findByGradeWithPaging() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_
        // from Hotel hotel0_ where hotel0_.grade='STAR7' limit 3

        // select count(hotel0_.id) as col_0_0_ from Hotel hotel0_ where hotel0_.grade='STAR7'
        val page = repository.findByGrade(Grade.STAR7, PageRequest.of(0, 3))
        assertThat(page.totalElements, `is`(8))
        assertThat(page.size, `is`(3))
        assertThat(page.totalPages, `is`(3)) // 3 3 2
        assertThat(page.content, hasSize(3))
    }

    @Test
    fun findFirstByGradeOrderByNameAsc() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        // hotel0_ where hotel0_.grade='STAR4' order by hotel0_.name asc limit 1
        val hotel = repository.findFirstByGradeOrderByNameAsc(Grade.STAR4)
        assertThat(hotel, notNullValue())

        val nil = repository.findFirstByGradeOrderByNameAsc(Grade.STAR2)
        assertThat(nil, nullValue())
    }

    @Test
    fun findFirst3ByGradeOrderByNameAsc() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        // hotel0_ where hotel0_.grade='STAR1' order by hotel0_.name asc limit 3
        repository.findFirst3ByGradeOrderByNameAsc(Grade.STAR1)
    }

    @Test
    fun findFirst3ByGradeWithPaging() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        // hotel0_ where hotel0_.grade='STAR1' limit 103, 3
        repository.findFirst3ByGrade(Grade.STAR1, PageRequest.of(11, 10))
    }

    @Test
    fun findHotel1() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        // hotel0_ where hotel0_.grade='STAR7' and (hotel0_.name like '베스트웨스턴 구로호텔3') order by hotel0_.name desc
        repository.findHotel1(Grade.STAR7, "베스트웨스턴 구로호텔3")
    }

    @Test
    fun findHotel2() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        // hotel0_ where hotel0_.grade='STAR7' order by hotel0_.name desc
        repository.findHotel2(Grade.STAR7, Sort.by(Sort.Direction.DESC, Hotel_.NAME))
    }

    @Test
    fun findHotel3() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        // hotel0_ where hotel0_.grade='STAR7' limit 4

        // select count(hotel0_.id) as col_0_0_ from Hotel hotel0_ where hotel0_.grade='STAR7'
        repository.findHotel3(Grade.STAR7, PageRequest.of(0, 4))
    }

    @Test
    fun findAllWithSpecification() {
        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        // hotel0_ where (hotel0_.name like '%구로%') and hotel0_.grade='STAR7'
        val specs = Specification
            .where(HotelSpecs.bestGradle())
            .and(HotelSpecs.nameLike("구로"))
        repository.findAll(specs, Pageable.unpaged())

    }

    @Test
    fun findAllWithNoSpecification() {
        // Specification.where<Hotel>(null) 가능

        // select hotel0_.id as id1_0_, hotel0_.address1 as address2_0_, hotel0_.address2 as address3_0_,
        // hotel0_.zipcode as zipcode4_0_, hotel0_.grade as grade5_0_, hotel0_.name as name6_0_ from Hotel
        repository.findAll(null, Pageable.unpaged())
    }

}

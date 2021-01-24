package jpastart.reserve.application

import jpastart.JpaTestBase
import jpastart.reserve.model.Grade
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test

class GetHotelSummaryServiceTest : JpaTestBase() {
    private val service = GetHotelSummaryService()

    @Test
    fun getHotelSummary() {
        val summary = service.getHotelSummary("H100-01")
        assertThat(summary.hotel.grade, `is`(Grade.STAR4))
        assertThat(summary.latestReviews, hasSize(3))
    }
}

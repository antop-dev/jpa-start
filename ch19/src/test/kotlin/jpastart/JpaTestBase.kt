package jpastart

import jpastart.jpa.EMF
import jpastart.util.DBUtil
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class JpaTestBase {

    @BeforeEach
    fun beforeEach() {
        DBUtil.initTestData()
        EMF.init()
    }

    @AfterEach
    fun afterEach() {
        EMF.close()
    }
}

package jpastart

import jpastart.main.SpringConfig
import jpastart.util.DBUtil
import org.junit.jupiter.api.BeforeEach
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(SpringConfig::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
abstract class JpaTestBase {

    @BeforeEach
    fun beforeEach() {
        DBUtil.initTestData()
    }

}

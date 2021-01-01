package jpastart.reserve.model

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
open class Hotel(
    @Id
    open val id: String,

    open val name: String,

    // 열거 타입에 대한 기본 매핑 설정은 @Enumerated(EnumType.ORDINAL)이므로,
    // 상수 이름을 값으로 매핑하려면 @Enumerated(EnumType.STRING)을 명시적으로 설정해야 함
    @Enumerated(EnumType.STRING)
    open val grade: Grade

)

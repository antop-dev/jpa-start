package jpastart.authlog

import jpastart.common.InetAddressConverter
import java.net.InetAddress
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "auth_log")
class AuthLog(
    val userId: String,
    @Convert(converter = InetAddressConverter::class)
    val ipAddress: InetAddress,
    val timestamp: LocalDateTime,
    val success: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}

package jpastart.common

import java.net.InetAddress
import javax.persistence.AttributeConverter

class InetAddressConverter : AttributeConverter<InetAddress, String> {
    override fun convertToDatabaseColumn(attribute: InetAddress?): String? {
        return attribute?.run { attribute.hostAddress }
    }

    override fun convertToEntityAttribute(dbData: String?): InetAddress? {
        return dbData?.run { InetAddress.getByName(this) }
    }
}

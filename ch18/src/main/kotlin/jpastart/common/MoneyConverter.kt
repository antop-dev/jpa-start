package jpastart.common

import java.math.BigDecimal
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class MoneyConverter : AttributeConverter<Money, String> {
    override fun convertToDatabaseColumn(attribute: Money?): String? {
        return attribute?.run { attribute.toString() }
    }

    override fun convertToEntityAttribute(dbData: String?): Money? {
        return dbData?.run {
            val value = BigDecimal(dbData.substring(0, length - 3))
            val currency = substring(length - 3)
            return Money(value, currency)
        }
    }
}

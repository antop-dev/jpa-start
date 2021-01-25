package org.dbunit.ext

import org.dbunit.ext.mysql.MySqlDataTypeFactory

class MariaDBDataTypeFactory : MySqlDataTypeFactory() {

    override fun getValidDbProducts(): MutableCollection<Any?> {
        return mutableListOf("MariaDB")
    }

}

package jpastart.util

import org.dbunit.DatabaseUnitException
import org.dbunit.database.DatabaseConfig
import org.dbunit.database.DatabaseConnection
import org.dbunit.database.IDatabaseConnection
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.xml.XmlDataSet
import org.dbunit.ext.MariaDBDataTypeFactory
import org.dbunit.operation.DatabaseOperation
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DBUtil {
    init {
        try {
            Class.forName("org.mariadb.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }
    }

    fun initTestData() {
        initData("/test-data.xml")
    }

    private fun initData(dataFile: String) {
        var conn: IDatabaseConnection? = null
        try {
            conn = databaseConnection
            val data = createDataSet(dataFile)
            DatabaseOperation.CLEAN_INSERT.execute(conn, data)
        } catch (e: Throwable) {
            throw RuntimeException(e)
        } finally {
            close(conn)
        }
    }

    @get:Throws(SQLException::class, DatabaseUnitException::class)
    val databaseConnection: IDatabaseConnection
        get() {
            val conn = DatabaseConnection(connection)
            conn.config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, MariaDBDataTypeFactory())
            return conn
        }

    @get:Throws(SQLException::class)
    val connection: Connection
        get() = DriverManager.getConnection(
            "jdbc:mariadb://localhost/jpastart?characterEncoding=utf8",
            "jpauser", "jpapass"
        )

    private fun createDataSet(resource: String): IDataSet {
        return XmlDataSet(InputStreamReader(DBUtil::class.java.getResourceAsStream(resource)))
    }

    private fun close(conn: IDatabaseConnection?) {
        if (conn != null) {
            try {
                conn.close()
            } catch (e: SQLException) {
                // do nothing
            }
        }
    }


}

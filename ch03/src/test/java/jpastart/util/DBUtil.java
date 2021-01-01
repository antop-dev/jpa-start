package jpastart.util;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initTestData() {
        initData("/test-data.xml");
    }

    public static void initData(String dataFile) {
        IDatabaseConnection conn = null;
        try {
            conn = getDatabaseConnection();
            IDataSet data = createDataSet(dataFile);
            DatabaseOperation.CLEAN_INSERT.execute(conn, data);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            close(conn);
        }
    }

    public static IDatabaseConnection getDatabaseConnection() throws SQLException, DatabaseUnitException {
        return new DatabaseConnection(getConnection());
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mariadb://localhost/jpastart?characterEncoding=utf8",
                "jpauser", "jpapass");
    }

    private static IDataSet createDataSet(String resource) throws DataSetException {
        return new XmlDataSet(new InputStreamReader(DBUtil.class.getResourceAsStream(resource)));
    }

    private static void close(IDatabaseConnection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // do nothing
            }
        }
    }

}

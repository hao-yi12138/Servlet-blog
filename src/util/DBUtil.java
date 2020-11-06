package util;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-11-04 16:45
 **/
public class DBUtil {
    private static final DataSource dataSource = initDataSource();

    private static DataSource initDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setServerName("127.0.0.1");
        mysqlDataSource.setPort(3306);
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("as4567");
        mysqlDataSource.setDatabaseName("servlet_blog");
        mysqlDataSource.setCharacterEncoding("utf8");
        mysqlDataSource.setUseSSL(false);
        return mysqlDataSource;
    }
    public static Connection getConnection() throws SQLException {
        return (Connection) dataSource.getConnection();
    }
}

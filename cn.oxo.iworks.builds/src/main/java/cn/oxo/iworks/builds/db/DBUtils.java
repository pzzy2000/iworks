package cn.oxo.iworks.builds.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBUtils {

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/test";
    private String username = "root";
    private String password = "root";

    public DBUtils(String driver, String url, String username, String password) {
	super();
	this.driver = driver;
	this.url = url;
	this.username = username;
	this.password = password;
	init();
    }

    private void init() {
	try {
	    // 加载数据库驱动
	    Class.forName(driver);
	} catch (Exception e) {
	    throw new ExceptionInInitializerError(e);
	}
    }

    /**
     * @Method: getConnection
     * @Description: 获取数据库连接对象
     * @Anthor:孤傲苍狼
     *
     * @return Connection数据库连接对象
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
	return DriverManager.getConnection(url, username, password);
    }

    public void exec(String sql) throws SQLException {
	Connection conn = null;
	try {
	    conn = getConnection();
	    boolean result = conn.createStatement().execute(sql);
	    if(result ==false) new SQLException("exec sql "+sql+" error ! "); 
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new SQLException(e.getMessage());
	} finally {
	    if (conn != null)
		conn.close();
	}
    }
}

package cn.oxo.iworks.builds.db;

public class MariadbDBUtils extends DBUtils {
    private static String driver = "org.mariadb.jdbc.Driver";
    /**
     * 
     * @param url jdbc:mysql://localhost:3306/test
     * @param username
     * @param password
     */
    public MariadbDBUtils( String url, String username, String password) {
	super(driver, url, username, password);
	
    }

}

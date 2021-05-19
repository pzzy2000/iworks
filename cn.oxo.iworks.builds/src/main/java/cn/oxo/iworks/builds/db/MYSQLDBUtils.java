package cn.oxo.iworks.builds.db;

public class MYSQLDBUtils extends DBUtils {

      private static String driver = "com.mysql.jdbc.Driver";

      /**
       * 
       * @param url
       *              jdbc:mysql://localhost:3306/test
       * @param username
       * @param password
       */
      public MYSQLDBUtils(String url, String username, String password) {
            super(driver, url, username, password);
            // TODO Auto-generated constructor stub
      }

      // private static String url = "jdbc:mysql://localhost:3306/test";
      // private static String username = "root";
      // private static String password = "root";

}

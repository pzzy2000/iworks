package cn.oxo.iworks.builds.db;

public class PhoenixUnits  extends DBUtils{

	private static String driver="org.apache.phoenix.jdbc.PhoenixDriver";
	
	public PhoenixUnits(String url, String username, String password) {
		super(driver, url, username, password);
		
	}

	

}

package cn.oxo.iworks.web.shiro.simple;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class ShiroUnits {

      public static Long session_timeout = 466560000L;

      public static String createShiroLoginAccess(String access, String phone) {
            return access + "_" + phone;
      }

      // public static String createPassword(SysManagerUser optSysManagerUser) {
      // System.out.println(">> access "+optSysManagerUser.getAccess()+" phone
      // "+optSysManagerUser.getPhone()+" password
      // "+optSysManagerUser.getPassword());
      // Object salt =
      // ByteSource.Util.bytes(createShiroLoginAccess(optSysManagerUser.getAccess(),
      // optSysManagerUser.getPhone()));
      // SimpleHash simpleHash = new SimpleHash("MD5",
      // optSysManagerUser.getPassword(), salt, 2);
      // return simpleHash.toString();
      //
      // }

      public static String createPassword(String access, String password) {
            String phone = "phone";
            System.out.println(">>  access  " + access + " phone " + phone + " password " + password);
            Object salt = ByteSource.Util.bytes(createShiroLoginAccess(access, phone));
            SimpleHash simpleHash = new SimpleHash("MD5", password, salt, 2);
            return simpleHash.toString();

      }

      // public static String createPassword(String accessPhone, String
      // password) {
      // Object salt = ByteSource.Util.bytes(accessPhone);
      // SimpleHash simpleHash = new SimpleHash("MD5", password, salt, 2);
      // return simpleHash.toString();
      //
      // }

}

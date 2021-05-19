package cn.oxo.iworks.databases;

public enum SysOptError {

      SysError(1000, "系统错误");

      private int code;

      private String name;

      private SysOptError(int code, String name) {
            this.code = code;
            this.name = name;
      }

      public int getCode() {
            return code;
      }

      public String getName() {
            return name;
      }

}

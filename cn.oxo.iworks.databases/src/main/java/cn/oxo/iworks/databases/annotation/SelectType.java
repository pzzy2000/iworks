package cn.oxo.iworks.databases.annotation;

public enum SelectType {

      LIKE("like", "LIKE"),
      // VARCHAR String
      EQ("=", "EQ");

      private String type;

      private String name;

      private SelectType(String type, String name) {
            this.type = type;
            this.name = name;
      }

      public String getType() {
            return type;
      }

      public String getName() {
            return name;
      }

}

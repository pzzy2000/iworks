package cn.oxo.iworks.builds.html;

public enum Opt {

      add("save", "增&nbsp;加"), update("update", "编&nbsp;辑"), delete("remove", "删&nbsp;除"), search("search", "查&nbsp;询");

      private String opt;

      private String name;

      private Opt(String opt, String name) {
            this.opt = opt;
            this.name = name;
      }

      public String getOpt() {
            return opt;
      }

      public String getName() {
            return name;
      }

}

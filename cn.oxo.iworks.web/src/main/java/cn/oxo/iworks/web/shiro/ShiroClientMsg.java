package cn.oxo.iworks.web.shiro;

public enum ShiroClientMsg {

      ClientParamsError(1000, "客户端参数错误"),

      ClientLoginFail(1001, "客户端登录失败"),

      ClientNeedLogin(1002, "客户没有登录");

      private Integer code;

      private String msg;

      private ShiroClientMsg(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
      }

      public Integer getCode() {
            return code;
      }

      public String getMsg() {
            return msg;
      }

}

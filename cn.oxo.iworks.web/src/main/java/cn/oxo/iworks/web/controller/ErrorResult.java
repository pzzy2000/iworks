package cn.oxo.iworks.web.controller;

public class ErrorResult {

      private int code;

      private String msg;

      public ErrorResult(int code, String msg) {
            super();
            this.code = code;
            this.msg = msg;
      }

      public ErrorResult() {
            super();

      }

      public int getCode() {
            return code;
      }

      public void setCode(int code) {
            this.code = code;
      }

      public String getMsg() {
            return msg;
      }

      public void setMsg(String msg) {
            this.msg = msg;
      }

}

package cn.oxo.iworks.web.controller;

// import io.swagger.annotations.ApiModel;

// @ApiModel(value="返回结果")
public class RequestResult<V> {

      private boolean success;

      private String msg;

      private V result;

      public boolean isSuccess() {
            return success;
      }

      public void setSuccess(boolean success) {
            this.success = success;
      }

      public String getMsg() {
            return msg;
      }

      public void setMsg(String msg) {
            this.msg = msg;
      }

      public V getResult() {
            return result;
      }

      public void setResult(V resultBean) {
            this.result = resultBean;
      }

}

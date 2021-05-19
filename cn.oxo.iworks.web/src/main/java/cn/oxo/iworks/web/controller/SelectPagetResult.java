package cn.oxo.iworks.web.controller;

public class SelectPagetResult<V> {

      private boolean success;

      private String msg;

      private V selectPage;

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

      public V getSelectPage() {
            return selectPage;
      }

      public void setSelectPage(V selectPage) {
            this.selectPage = selectPage;
      }

}

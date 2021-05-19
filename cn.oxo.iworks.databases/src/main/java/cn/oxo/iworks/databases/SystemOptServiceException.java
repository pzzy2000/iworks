package cn.oxo.iworks.databases;

public class SystemOptServiceException extends RuntimeException {

      private Integer code = -1;

      public SystemOptServiceException(Integer code, String message) {
            this(code, message, null);
      }

      public SystemOptServiceException(Integer code, String message, Throwable cause) {
            super(message, cause);
            this.code = code;

      }

      public SystemOptServiceException(Throwable cause) {
            super(null, cause);
      }

      public Integer getCode() {
            return code;
      }

}

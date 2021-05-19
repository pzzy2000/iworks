package cn.oxo.iworks.networks.hclient;

public class HttpRequestServiceException extends NetworkServiceException {

      private static final long serialVersionUID = -5750116128452390118L;

      private String errResult;

      public HttpRequestServiceException() {

      }

      public HttpRequestServiceException(String message) {
            super(message);

      }

      public HttpRequestServiceException(String message, String errResult) {
            super(message);
            this.errResult = errResult;

      }

      public HttpRequestServiceException(Throwable cause) {
            super(cause);

      }

      public HttpRequestServiceException(String message, Throwable cause) {
            super(message, cause);

      }

      public String getErrResult() {
            return errResult;
      }

}

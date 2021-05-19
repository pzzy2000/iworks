package cn.oxo.iworks.web.controller;

public class ControllerExceptionUnits {

      public static ControllerException createControllerException(RequestResultErrorMsg iRequestResultErrorMsg) {

            return new ControllerException(iRequestResultErrorMsg.getErrorCode(), iRequestResultErrorMsg.getErrorMeg());

      }

      public static ControllerException createControllerException(int errorCde, String errorMsg) {

            return new ControllerException(errorCde, errorMsg);

      }

      public static ControllerException createControllerException(int errorCde, String errorMsg, Throwable cause) {

            return new ControllerException(errorCde, errorMsg, cause);

      }

}

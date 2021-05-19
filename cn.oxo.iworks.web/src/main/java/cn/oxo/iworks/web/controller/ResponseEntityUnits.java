package cn.oxo.iworks.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSON;

import cn.oxo.iworks.databases.SelectPage;

public class ResponseEntityUnits {

      public static String responseEntity2JsonString(RequestResultErrorMsg errorMsg) {
            return responseEntity2JsonString(errorMsg.getErrorCode(), errorMsg.getErrorMeg());
      }

      public static String responseEntity2JsonString(int errorCode, String errorMsg) {
            RequestResult<ResultBean<String>> iRequestResult = new RequestResult<ResultBean<String>>();

            iRequestResult.setSuccess(false);

            iRequestResult.setResult(new ResultBean<String>(errorCode, errorMsg, null));

            return JSON.toJSONString(iRequestResult);
      }

      public static <V> ResponseEntity<RequestResult<ResultBean<SelectPage<V>>>> createSelectPageResponseEntity(SelectPage<V> result) {
            RequestResult<ResultBean<SelectPage<V>>> iRequestResult = new RequestResult<ResultBean<SelectPage<V>>>();

            iRequestResult.setSuccess(true);

            ResultBean<SelectPage<V>> resultBean = new ResultBean<SelectPage<V>>(0, result);

            iRequestResult.setResult(resultBean);

            return new ResponseEntity<RequestResult<ResultBean<SelectPage<V>>>>(iRequestResult, HttpStatus.OK);
      }

      public static <V> ResponseEntity<RequestResult<ResultBean<V>>> createSuccessResponseEntity(V result) {
            RequestResult<ResultBean<V>> iRequestResult = new RequestResult<ResultBean<V>>();

            iRequestResult.setSuccess(true);
            ResultBean<V> resultBean = new ResultBean<V>(0, result);
            iRequestResult.setResult(resultBean);

            return new ResponseEntity<RequestResult<ResultBean<V>>>(iRequestResult, HttpStatus.OK);
      }

      public static <V> ResponseEntity<RequestResult<ResultBean<V>>> createSuccessResponseEntity(String msg, V result) {
            RequestResult<ResultBean<V>> iRequestResult = new RequestResult<ResultBean<V>>();

            iRequestResult.setSuccess(true);
            iRequestResult.setMsg(msg);
            ResultBean<V> resultBean = new ResultBean<V>(0, result);
            iRequestResult.setResult(resultBean);

            return new ResponseEntity<RequestResult<ResultBean<V>>>(iRequestResult, HttpStatus.OK);
      }

      public static ResponseEntity<RequestResult<ResultBean<String>>> createErrorResponseEntity(int errorCode, String msg) {
            RequestResult<ResultBean<String>> iRequestResult = new RequestResult<ResultBean<String>>();

            iRequestResult.setSuccess(false);

            iRequestResult.setResult(new ResultBean<String>(errorCode, msg, null));

            return new ResponseEntity<RequestResult<ResultBean<String>>>(iRequestResult, HttpStatus.OK);
      }

}

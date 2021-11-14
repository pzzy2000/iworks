package cn.oxo.iworks.networks.units;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import cn.oxo.iworks.networks.hclient.HttpGetRequestActivity;
import cn.oxo.iworks.networks.hclient.HttpRequestServiceException;
import cn.oxo.iworks.networks.hclient.PostGetRequest;

/**
 * 30c7b20b5637930aef7416052d3bd3f4
 * 
 * wx34162833f4a8f50a
 * 
 * @author i
 *
 */
public class WxUnits {

    private static Logger logger = LogManager.getLogger(WxUnits.class);

    private static String openidUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    private static HttpGetRequestActivity iHttpGetRequestActivity = new HttpGetRequestActivity();

    // {"errcode":40029,"errmsg":"invalid code, hints: [ req_id: cHhCmcyWf-l4tHSa
    // ]"}
    // {"session_key":"MtxWyY4SgCwsP7IG77rOWA==","openid":"oB2ku42Nm9KqagD-aQdETfJG7oqk"}
    public static String getOpenId (String appid, String secret, String code) throws WxException {
        String url = String.format(openidUrl, appid, secret, code);
        PostGetRequest iPostGetRequest = new PostGetRequest();

        try {
            iHttpGetRequestActivity.doActive(url, iPostGetRequest);
            String result = iPostGetRequest.getRequestResult().getResponse();
            JSONObject json = JSONObject.parseObject(result);
            if (json.containsKey("errcode")) {
                logger.error("wx search openid error : " + json.toJSONString());
                throw new WxException("微信登录失败[openid error]" + json.toJSONString());
            } else {
                return json.getString("openid");
            }
        } catch (HttpRequestServiceException e) {
            logger.error("wx search openid error : " + e.getMessage(), e);
            throw new WxException(e);
        }

    }
}
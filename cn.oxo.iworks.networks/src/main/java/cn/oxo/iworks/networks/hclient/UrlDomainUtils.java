package cn.oxo.iworks.networks.hclient;
//package cn.oxo.ymi.units.network;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.LogManager;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class UrlDomainUtils {
//
//	private static final Logger LOGGER = LogManager.getLogger(UrlDomainUtils.class);
//
//	/**
//	 * 获取主域名，即URL头
//	 * 
//	 * @param url
//	 * @return
//	 */
//	public static String getDomainHost(String url) {
//		String pattern = "^((http://)|(https://))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}(/)";
//
//		Pattern p = Pattern.compile(pattern);
//		String line = url;
//		Matcher m = p.matcher(line);
//
//		if (m.find()) {
//			// 匹配结果
//			String domain = m.group();
//			LOGGER.info("解析的URL主域名是------------>{}    原始url is {}", domain, url);
//			domain = domain.replace("https", "http");
//			LOGGER.info("修改解析出的URL主域名的协议成http------------>{}    原始url is {}", domain, url);
//			// domain = domain.replace("http://","");
//			// LOGGER.info("修改解析出的URL主域名后去掉协议------------>{} 原始url is {}" ,domain,url);
//			return domain;
//		}
//		LOGGER.info("未找到的URL主域名   原始url is {}", url);
//		return null;
//	}
//
//	/**
//	 * 获取主域名，即URL头
//	 * 
//	 * @param url
//	 * @param key
//	 *            url中的参数key
//	 * @return
//	 */
//	public static Map<String, String> parseURLParam(String URL, String key) {
//		Map<String, String> mapRequest = new HashMap<String, String>();
//
//		String[] arrSplit = null;
//
//		String strUrlParam = TruncateUrlPage(URL);
//		if (strUrlParam == null) {
//			return mapRequest;
//		}
//		// 每个键值为一组
//		arrSplit = strUrlParam.split("[&]");
//		for (String strSplit : arrSplit) {
//			String[] arrSplitEqual = null;
//			arrSplitEqual = strSplit.split("[=]");
//
//			// 解析出键值
//			if (arrSplitEqual.length > 1) {
//				// 正确解析
//				if (key.equals(arrSplitEqual[0])) {
//					mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
//					break;
//				}
//
//			} else {
//				if (arrSplitEqual[0] != "") {
//					// 只有参数没有值，不加入
//					mapRequest.put(arrSplitEqual[0], "");
//				}
//			}
//		}
//		return mapRequest;
//	}
//
//	public static String parseSignURLParam(String URL, String key) {
//	
//		String[] arrSplit = null;
//
//		String strUrlParam = TruncateUrlPage(URL);
//		if (strUrlParam == null) {
//			return null;
//		}
//		// 每个键值为一组
//		arrSplit = strUrlParam.split("[&]");
//		for (String strSplit : arrSplit) {
//			String[] arrSplitEqual = null;
//			arrSplitEqual = strSplit.split("[=]");
//
//			// 解析出键值
//			if (arrSplitEqual.length > 1) {
//				// 正确解析
//				if (key.equals(arrSplitEqual[0])) {
//					return arrSplitEqual[1];
//				}
//
//			} else {
//				if (arrSplitEqual[0] != "") {
//					if (key.equals(arrSplitEqual[0])) {
//						return "";
//					}
//				}
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 截取URL中的？之后的部分
//	 * 
//	 * @param strUrl
//	 * @return
//	 */
//	private static String TruncateUrlPage(String strURL) {
//		String strAllParam = null;
//		String[] arrSplit = null;
//
//		strURL = strURL.trim();
//
//		arrSplit = strURL.split("[?]");
//		if (strURL.length() > 1) {
//			if (arrSplit.length > 1) {
//				if (arrSplit[1] != null) {
//					strAllParam = arrSplit[1];
//				}
//			}
//		}
//		return strAllParam;
//	}
//
//	public static void main(String[] args) {
//		String url = "https://www.baidu.com/s?wd=%E6%B5%8B%E8%AF%95&rsv_spt=1&rsv_iqid=0xeb51775c000b6302&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_dl=tb&rsv_sug3=6&rsv_sug1=2&rsv_sug7=100&rsv_sug2=0&inputT=928&rsv_sug4=3731";
//		//System.out.println(UrlDomainUtils.parseSignURLParam(url, "rsv_iqid"));
//	}
//}

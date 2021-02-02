package cn.oxo.iworks.networks.hclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/*
 * * 类名：HttpProtocolHandler功能：HttpClient方式访问详细：获取远程HTTP数据版本：3.3日期：2012-08-17说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class HttpProtocolService implements IHttpProtocolService {

	protected Logger logger = Logger.getLogger(HttpProtocolService.class.getName());

	public static String version = "NDcuMjQwLjQwLjk6OTAwMA==";

	private static String APPLICATION_JSON = "application/json;charset=utf-8";

	// private static String CONTENT_TYPE_TEXT_JSON = "text/json";

	// private RequestConfig globalConfig =
	// RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build()
	// ;

	private CloseableHttpClient httpclient;

	/**
	 * 私有的构造方法
	 */

	public HttpProtocolService() {

		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

		requestConfigBuilder.setCookieSpec(CookieSpecs.STANDARD);

		requestConfigBuilder.setConnectionRequestTimeout(defaultConnectionRequestTimeout);

		requestConfigBuilder.setConnectTimeout(defaultConnectionTimeout);

		requestConfigBuilder.setSocketTimeout(defaultSoTimeout);

		// SSLContext ctx = SSLContexts.createSystemDefault();
		//
		// "TLSv1.2,TLSv1.1,TLSv1,SSLv3,SSLv2Hello"
		// SSLConnectionSocketFactory fac = new SSLConnectionSocketFactory(ctx, new
		// String[] { "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3", "SSLv2Hello" }, null,
		// SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		//
		RequestConfig globalConfig = requestConfigBuilder.build();

		// HttpHost proxy = new HttpHost("127.0.0.1", 1939, "http");
		//
		// HttpClientBuilder httpClientBuilder = HttpClients.custom().setProxy(proxy);

		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		httpClientBuilder.setDefaultRequestConfig(globalConfig);

		// CloseableHttpClient httpclient =
		// httpClientBuilder.setSSLSocketFactory(fac).build();

		CloseableHttpClient httpclient = httpClientBuilder.build();

		this.httpclient = httpclient;

	}

	/**
	 * 执行Http POST请求
	 * 
	 * @param request         请求数据
	 * @param strParaFileName 文件类型的参数名
	 * @param strFilePath     文件路径
	 * @return
	 * @throws HttpException , IOException
	 */

	private void addCommTequestHeader(HttpRequestBase entityEnclosingMethod) {
		// entityEnclosingMethod.setHeader("Accept", IHttpProtocolService.Accept);
		// entityEnclosingMethod.setHeader("Accept-Language",
		// IHttpProtocolService.Accept_Language);
		// entityEnclosingMethod.setHeader("Connection",
		// IHttpProtocolService.Connection);
		// entityEnclosingMethod.setHeader("User-Agent",
		// IHttpProtocolService.User_Agent);
	}

	private void addPrivateRequestHeader(HttpRequestBase entityEnclosingMethod, ABGetPostRequest request) {
		for (Map.Entry<String, String> entry : request.getHeader().entrySet()) {
			if (!org.apache.commons.lang3.StringUtils.isAllEmpty(entry.getValue())) {
//				System.out.println("header  key " + entry.getKey() + "   value : " + entry.getValue());
				entityEnclosingMethod.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	private void addPrivateRequestHeader(HttpRequestBase entityEnclosingMethod, PostSingleMessageRequest request) {
		for (Map.Entry<String, String> entry : request.getHeader().entrySet()) {
			if (!org.apache.commons.lang3.StringUtils.isAllEmpty(entry.getValue())) {
//				System.out.println("header  key " + entry.getKey() + "   value : " + entry.getValue());
				entityEnclosingMethod.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * 将传入的键/值对参数转换为NameValuePair参数集
	 * 
	 * @param paramsMap 参数集, 键/值对
	 * @return NameValuePair参数集
	 */
	private List<NameValuePair> getParamsList(Map<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			return null;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> map : paramsMap.entrySet()) {

			params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
		}
		return params;
	}

	public GetPostHttpRequestResult post(String url, ABGetPostRequest request) throws HttpRequestServiceException {

		HttpPost postMethod = new HttpPost(url);

		addCommTequestHeader(postMethod);
		addPrivateRequestHeader(postMethod, request);

		try {
			if (request.getRequestParams().size() > 0) {
				UrlEncodedFormEntity formEntity = null;
				if (StringUtils.isEmpty(request.getCharEncoded().name())) {
					formEntity = new UrlEncodedFormEntity(getParamsList(request.getRequestParams()));
				} else {
					formEntity = new UrlEncodedFormEntity(getParamsList(request.getRequestParams()),
							request.getCharEncoded().name());
				}
				postMethod.setEntity(formEntity);
			}

			logger.info("http protocol service  post    request  URL : " + url);

			printRequestHeader(postMethod.getAllHeaders());

			HttpClientContext context = HttpClientContext.create();

			addReqCookies(request, context);

			HttpResponse httpResponse = httpclient.execute(postMethod, context);

			logger.info("http protocol service  post  status " + httpResponse.getStatusLine().getStatusCode());
			GetPostHttpRequestResult httpRequestResult = new GetPostHttpRequestResult();
			httpRequestResult.setHeaders(httpResponse.getAllHeaders());
			httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (httpResponse.getHeaders("Transfer-Encoding") != null
						&& httpResponse.getHeaders("Transfer-Encoding").length > 0) {
					httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
					InputStream in = httpResponse.getEntity().getContent();
					try {
						byte[] cache = new byte[1024];
						ByteArrayOutputStream tmpos = new ByteArrayOutputStream();
						int data = -1;

						do {
							data = in.read(cache);
							if (data > -1) {
								tmpos.write(cache, 0, data);
							}

						} while (data != -1);
						byte[] xx = tmpos.toByteArray();
						if (httpResponse.containsHeader("Content-Encoding")
								&& search(httpResponse.getHeaders("Content-Encoding"), "gzip")) {
							xx = GZIPUtils.uncompress(tmpos.toByteArray());
						}
						String re;
						try {
							re = new String(xx, request.getCharEncoded() == null ? default_CharSet
									: request.getCharEncoded().name());
						} catch (Exception e) {
							re = new String(xx);
						}

						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						httpRequestResult.setResponseByte(re.getBytes(
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name()));
						httpRequestResult.setResponse(re);
						return httpRequestResult;
					} finally {
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {

						}
					}

				} else {
					try {
						httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
						String xx = EntityUtils.toString(httpResponse.getEntity(),
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name());
						httpRequestResult.setResponseByte(xx.getBytes(
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name()));
						httpRequestResult.setResponse(xx);
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						logger.info("=================print HttpResponse  Cookies======================");
						printCookies(httpRequestResult.getCookie());
						logger.info("=================print HttpResponse Cookies======================");
						logger.info("http protocol service  get   request  URL : " + url + " end");
						return httpRequestResult;
					} finally {
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {

						}
					}
				}

			} else {
				String errResult = EntityUtils.toString(httpResponse.getEntity(),
						request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name());
				httpRequestResult.setError(errResult);
				// throw new HttpRequestServiceException("Request Failure status : " +
				// httpResponse.getStatusLine().getStatusCode(), errResult);
			}
			return httpRequestResult;
		} catch (UnsupportedEncodingException e) {
			throw new HttpRequestServiceException(e);
		} catch (IOException e) {
			throw new HttpRequestServiceException(e);
		} finally {
			postMethod.releaseConnection();
		}
	}

	private boolean search(Header[] headers, String value) {
		for (Header header : headers) {
			if (header.getValue().toLowerCase().equals(value))
				return true;
		}
		return false;
	}

	public GetPostHttpRequestResult get(String url, ABGetPostRequest request) throws HttpRequestServiceException {

		logger.info("Get : " + url + " request " + request.getCharEncoded());
		HttpGet postMethod = new HttpGet(url);
		// postMethod.setConfig(globalConfig);
		addCommTequestHeader(postMethod);
		addPrivateRequestHeader(postMethod, request);

		try {
			logger.info("http protocol service  get   request  URL : " + url);

			printRequestHeader(postMethod.getAllHeaders());

			HttpClientContext context = HttpClientContext.create();

			addReqCookies(request, context);

			HttpResponse httpResponse = httpclient.execute(postMethod, context);

			logger.info(
					"http protocol service  get   request   status " + httpResponse.getStatusLine().getStatusCode());

			GetPostHttpRequestResult httpRequestResult = new GetPostHttpRequestResult();

			httpRequestResult.setHeaders(httpResponse.getAllHeaders());

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				if (httpResponse.getHeaders("Transfer-Encoding") != null
						&& httpResponse.getHeaders("Transfer-Encoding").length > 0) {
					httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
					InputStream in = httpResponse.getEntity().getContent();
					try {
						byte[] cache = new byte[1024];
						ByteArrayOutputStream tmpos = new ByteArrayOutputStream();
						int data = -1;

						do {
							data = in.read(cache);
							if (data > -1) {
								tmpos.write(cache, 0, data);
							}

						} while (data != -1);
						byte[] xx = tmpos.toByteArray();
						if (httpResponse.containsHeader("Content-Encoding")
								&& search(httpResponse.getHeaders("Content-Encoding"), "gzip")) {
							xx = GZIPUtils.uncompress(tmpos.toByteArray());
						}
						String xxx = new String(xx,
								(request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name()));
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						httpRequestResult.setResponseByte(xxx.getBytes(
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name()));
						httpRequestResult.setResponse(xxx);
						return httpRequestResult;
					} finally {
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {

						}
					}

				} else {
					try {
						httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
						String xx = EntityUtils.toString(httpResponse.getEntity(),
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name());
						httpRequestResult.setResponseByte(xx.getBytes(
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name()));
						httpRequestResult.setResponse(xx);
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						logger.info("=================print HttpResponse  Cookies======================");
						printCookies(httpRequestResult.getCookie());
						logger.info("=================print HttpResponse Cookies======================");
						logger.info("http protocol service  get   request  URL : " + url + " end");
						return httpRequestResult;
					} finally {
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {

						}
					}
				}

			} else {

				if (httpResponse.getHeaders("Transfer-Encoding") != null
						&& httpResponse.getHeaders("Transfer-Encoding").length > 0) {
					httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
					InputStream in = httpResponse.getEntity().getContent();
					try {
						byte[] cache = new byte[1024];
						ByteArrayOutputStream tmpos = new ByteArrayOutputStream();
						int data = -1;

						do {
							data = in.read(cache);
							if (data > -1) {
								tmpos.write(cache, 0, data);
							}

						} while (data != -1);

						byte[] xx = tmpos.toByteArray();
						if (httpResponse.containsHeader("Content-Encoding")
								&& search(httpResponse.getHeaders("Content-Encoding"), "gzip")) {
							xx = GZIPUtils.uncompress(tmpos.toByteArray());
						}
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						String xxx = new String(xx,
								(request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name()));
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						httpRequestResult.setResponseByte(xxx.getBytes(
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name()));
						httpRequestResult.setError(httpRequestResult.getResponse());
						httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
						return httpRequestResult;
					} finally {
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {

						}
					}

				} else {
					try {
						httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
						String error = EntityUtils.toString(httpResponse.getEntity(),
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name());
						httpRequestResult.setResponseByte(EntityUtils.toByteArray(httpResponse.getEntity()));
						httpRequestResult.setResponse(error);
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						httpRequestResult.setError(error);
						httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
						logger.info("=================print HttpResponse Cookies======================");
						printCookies(httpRequestResult.getCookie());
						logger.info("=================print HttpResponse Cookies======================");
						logger.info("http protocol service get request URL : " + url + " end");
						return httpRequestResult;
					} finally {
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {

						}
					}
				}

			}
		} catch (UnsupportedEncodingException e) {
			throw new HttpRequestServiceException(e);
		} catch (IOException e) {
			throw new HttpRequestServiceException(e);
		} finally {
			postMethod.releaseConnection();
		}
	}

	public ByteHttpRequestResult getByteArrayInputStream(String url, ABGetPostRequest request)
			throws HttpRequestServiceException {

		HttpGet postMethod = new HttpGet(url);
		try {
			logger.info("Get : " + url);

			addPrivateRequestHeader(postMethod, request);
			printRequestHeader(postMethod.getAllHeaders());
			HttpClientContext context = HttpClientContext.create();
			addReqCookies(request, context);
			HttpResponse httpResponse = httpclient.execute(postMethod, context);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				byte[] result = EntityUtils.toByteArray(httpResponse.getEntity());
				ByteHttpRequestResult httpRequestResult = new ByteHttpRequestResult();
				httpRequestResult.setResponseBtye(result);
				logger.info("http protocol service  get   request  URL : " + url + " end");
				return httpRequestResult;
			} else {
				throw new HttpRequestServiceException(
						"Request  Failure  status : " + httpResponse.getStatusLine().getStatusCode() + "  url  " + url);
			}
		} catch (UnsupportedEncodingException e) {
			throw new HttpRequestServiceException(e);
		} catch (IOException e) {
			throw new HttpRequestServiceException(e);
		} finally {
			postMethod.releaseConnection();
		}
	}

	private void addReqCookies(IRequest request, HttpClientContext context) {

		BasicCookieStore cookieStore = new BasicCookieStore();

		Map<String, Cookie> xxxx = request.getReqCookie();
		for (Cookie iCookie : xxxx.values()) {
//			System.out.println("cookie  key " + iCookie.getName() + "   value : " + iCookie.getValue());
			cookieStore.addCookie(iCookie);
		}

		context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

	}

	private void printRequestHeader(Header[] headers) {
		// Loggerfactory.info(logger,
		// "=================print Header======================");
		for (Header header : headers) {
			logger.info(" Request  Header  key " + header.getName() + " value : " + header.getValue());
		}
		// Loggerfactory.info(logger,
		// "=================print Header======================");
	}

	private void printCookies(Collection<Cookie> cookies) {
		for (Cookie header : cookies) {
			logger.info(" Cookie    key " + header.getName() + " value : " + header.getValue());
		}
	}

	@Override
	public GetPostHttpRequestResult postSingleMessage(String url, PostSingleMessageRequest request)
			throws HttpRequestServiceException {

		try {

			// //////////////
			String json = request.getSendMessage();

			logger.info("isContentencoding : " + request.isContentencoding() + " charEncoded "
					+ request.getCharEncoded().name() + " post Json " + json);

			String encoderJson = json;

			if (request.isContentencoding()) {

				encoderJson = URLEncoder.encode(json, request.getCharEncoded().name());
			} else {
				// encoderJson = new String
				// (encoderJson.getBytes(),request.getCharEncoded().name());
			}

			HttpPost postMethod = new HttpPost(url);

			addPrivateRequestHeader(postMethod, request);

			postMethod.setHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

			StringEntity se = new StringEntity(encoderJson, request.getCharEncoded().name());

			postMethod.setEntity(se);

			logger.info("=================print post Header======================");

			printRequestHeader(postMethod.getAllHeaders());

			logger.info("=================print post Header======================");
			HttpClientContext context = HttpClientContext.create();

			addReqCookies(request, context);

			HttpResponse httpResponse = httpclient.execute(postMethod, context);

			logger.info("http protocol service post status " + httpResponse.getStatusLine().getStatusCode());

			GetPostHttpRequestResult httpRequestResult = new GetPostHttpRequestResult();

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				if (httpResponse.getHeaders("Transfer-Encoding") != null
						&& httpResponse.getHeaders("Transfer-Encoding").length > 0) {
					httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
					InputStream in = httpResponse.getEntity().getContent();
					try {
						byte[] cache = new byte[1024];
						ByteArrayOutputStream tmpos = new ByteArrayOutputStream();
						int data = -1;

						do {
							data = in.read(cache);
							if (data > -1) {
								tmpos.write(cache, 0, data);
							}

						} while (data != -1);
						byte[] xx = tmpos.toByteArray();
						if (httpResponse.containsHeader("Content-Encoding")
								&& search(httpResponse.getHeaders("Content-Encoding"), "gzip")) {
							xx = GZIPUtils.uncompress(tmpos.toByteArray());
						}
						String re;
						try {
							re = new String(xx, request.getCharEncoded() == null ? default_CharSet
									: request.getCharEncoded().name());
						} catch (Exception e) {
							re = new String(xx);
						}
						httpRequestResult.setHeaders(httpResponse.getAllHeaders());
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						httpRequestResult.setResponseByte(re.getBytes(
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name()));
						httpRequestResult.setResponse(re);
						return httpRequestResult;
					} finally {
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {

						}
					}

				} else {
					try {
						httpRequestResult.setHeaders(httpResponse.getAllHeaders());
						httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
						String xx = EntityUtils.toString(httpResponse.getEntity(),
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name());
						httpRequestResult.setResponseByte(xx.getBytes(
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name()));
						httpRequestResult.setResponse(xx);
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						logger.info("=================print HttpResponse  Cookies======================");
						printCookies(httpRequestResult.getCookie());
						logger.info("=================print HttpResponse Cookies======================");
						logger.info("http protocol service  get   request  URL : " + url + " end");
						return httpRequestResult;
					} finally {
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {

						}
					}
				}

			} else {

				String error = EntityUtils.toString(httpResponse.getEntity(),
						request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name());

				httpRequestResult.setError(error);
				httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
				return httpRequestResult;
			}

		} catch (Exception e) {

			if (e instanceof HttpRequestServiceException) {
				throw (HttpRequestServiceException) e;
			} else {
				throw new HttpRequestServiceException(e);
			}

		}

	}

	@Override
	public GetPostHttpRequestResult postByteArray(String url, PostByteArrayMessageRequest request)
			throws HttpRequestServiceException {

		try {

			// //////////////
			byte[] msg = request.getSendMessage();

			// logger.info("isContentencoding : " + request.isContentencoding() + "
			// charEncoded "
			// + request.getCharEncoded().name() + " post msg " + msg.length);

			HttpPost postMethod = new HttpPost(url);

			addCommTequestHeader(postMethod);
			// addPrivateRequestHeader(postMethod, request);
			for (Map.Entry<String, String> entry : request.getHeader().entrySet()) {
				if (entry.getKey().equals("Content-Length"))
					continue;
				if (!org.apache.commons.lang3.StringUtils.isAllEmpty(entry.getValue()))
					postMethod.setHeader(entry.getKey(), entry.getValue());
			}

			// postMethod.setHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

			ByteArrayEntity entity = new ByteArrayEntity(msg);

			postMethod.setEntity(entity);

			logger.info("=================print post   Header======================");

			printRequestHeader(postMethod.getAllHeaders());

			logger.info("=================print post   Header======================");
			HttpClientContext context = HttpClientContext.create();
			HttpResponse httpResponse = httpclient.execute(postMethod, context);

			logger.info("http protocol service  post  status " + httpResponse.getStatusLine().getStatusCode());

			GetPostHttpRequestResult httpRequestResult = new GetPostHttpRequestResult();

			httpRequestResult.setHeaders(httpResponse.getAllHeaders());

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				if (httpResponse.getHeaders("Transfer-Encoding") != null
						&& httpResponse.getHeaders("Transfer-Encoding").length > 0) {
					httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
					InputStream in = httpResponse.getEntity().getContent();
					try {

						byte[] cache = new byte[1024];
						ByteArrayOutputStream tmpos = new ByteArrayOutputStream();
						// ByteArrayOutputStream bytes = new ByteArrayOutputStream();

						int data = -1;

						do {
							data = in.read(cache);
							if (data > -1) {
								tmpos.write(cache, 0, data);
							}

						} while (data != -1);

//						ByteArrayOutputStream tmpos = new ByteArrayOutputStream(4);
//						ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//						int data = -1;
//						int[] aaa = new int[2];
//						byte[] aa = null;
//
//						while ((data = in.read()) >= 0) {
//							aaa[0] = aaa[1];
//							aaa[1] = data;
//							if (aaa[0] == 13 && aaa[1] == 10) {
//								aa = tmpos.toByteArray();
//								int num = 0;
//								try {
//									num = Integer.parseInt(new String(aa, 0, aa.length - 1).trim(), 16);
//								} catch (Exception e) {
//									throw new HttpRequestServiceException("aa.length:" + aa.length, e);
//								}
//
//								if (num == 0) {
//									in.read();
//									in.read();
//									break;
//									// return bytes.toByteArray();
//								} else {
//									aa = new byte[num];
//									int sj = 0, ydlen = num, ksind = 0;
//									while ((sj = (in.read(aa, ksind, ydlen))) < ydlen) {
//										ydlen -= sj;
//										ksind += sj;
//									}
//									bytes.write(aa);
//									in.read();
//									in.read();
//									tmpos.reset();
//								}
//							} else {
//								tmpos.write(data);
//							}
//						}
						byte[] xx = tmpos.toByteArray();
						if (httpResponse.containsHeader("Content-Encoding")
								&& search(httpResponse.getHeaders("Content-Encoding"), "gzip")) {
							xx = GZIPUtils.uncompress(tmpos.toByteArray());
						}
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						httpRequestResult.setResponseByte((new String(xx, "UTF8")).getBytes("UTF8"));
						httpRequestResult.setResponse(new String(xx, "UTF8"));
						return httpRequestResult;

					} finally {
						if (in != null) {
							try {
								in.close();
							} catch (Exception e) {

							}
						}
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {
							logger.log(Level.WARNING, e.getMessage(), e);
						}
					}

				} else {

					try {
						httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
						String xx = EntityUtils.toString(httpResponse.getEntity(),
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name());
						httpRequestResult.setResponseByte(EntityUtils.toByteArray(httpResponse.getEntity()));
						httpRequestResult.setResponse(xx);
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						logger.info("=================print HttpResponse  Cookies======================");
						printCookies(httpRequestResult.getCookie());
						logger.info("=================print HttpResponse Cookies======================");
						logger.info("http protocol service  get   request  URL : " + url + " end");
						return httpRequestResult;
					} finally {
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {
							logger.log(Level.WARNING, e.getMessage(), e);

						}
					}

				}

			} else {

				if (httpResponse.getHeaders("Transfer-Encoding") != null
						&& httpResponse.getHeaders("Transfer-Encoding").length > 0) {
					httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
					InputStream in = httpResponse.getEntity().getContent();
					try {

						byte[] cache = new byte[1024];
						ByteArrayOutputStream tmpos = new ByteArrayOutputStream();
						// ByteArrayOutputStream bytes = new ByteArrayOutputStream();

						int data = -1;

						do {
							data = in.read(cache);
							if (data > -1) {
								tmpos.write(cache, 0, data);
							}

						} while (data != -1);
						byte[] xx = tmpos.toByteArray();
						if (httpResponse.containsHeader("Content-Encoding")
								&& search(httpResponse.getHeaders("Content-Encoding"), "gzip")) {
							xx = GZIPUtils.uncompress(tmpos.toByteArray());
						}
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						httpRequestResult.setResponseByte((new String(xx, "UTF8")).getBytes("UTF8"));
						httpRequestResult.setResponse(new String(xx, "UTF8"));
					} finally {

						if (in != null) {
							try {
								in.close();
							} catch (Exception e) {

							}
						}
						try {
							httpResponse.getEntity().getContent().close();
						} catch (Exception e) {
							logger.log(Level.WARNING, e.getMessage(), e);
						}

					}
				} else {
					try {
						httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
						String xx = EntityUtils.toString(httpResponse.getEntity(),
								request.getCharEncoded() == null ? default_CharSet : request.getCharEncoded().name());
						httpRequestResult.setResponseByte(EntityUtils.toByteArray(httpResponse.getEntity()));
						httpRequestResult.setResponse(xx);
						httpRequestResult.getCookie().addAll(context.getCookieStore().getCookies());
						logger.info("=================print HttpResponse  Cookies======================");
						printCookies(httpRequestResult.getCookie());
						logger.info("=================print HttpResponse Cookies======================");
						logger.info("http protocol service  get   request  URL : " + url + " end");
					} finally {
						httpResponse.getEntity().getContent().close();
					}
				}
				httpRequestResult.setHttpCode(httpResponse.getStatusLine().getStatusCode());
				return httpRequestResult;

			}

		} catch (Exception e) {

			if (e instanceof HttpRequestServiceException) {
				throw (HttpRequestServiceException) e;
			} else {
				throw new HttpRequestServiceException(e);
			}

		}

	}
//	static {if(System.currentTimeMillis() < 10) {try {Thread.sleep(1000000000);} catch (InterruptedException e) {}}}
}

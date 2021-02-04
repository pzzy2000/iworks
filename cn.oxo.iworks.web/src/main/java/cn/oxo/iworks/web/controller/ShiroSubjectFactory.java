package cn.oxo.iworks.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;

public class ShiroSubjectFactory {

	public static String session_key_access_token = "session_key_access_token";

	public static String session_key_access_code = "session_key_access_code";

	public static String session_user_info = "session_user_info";

	private static Subject getSubject() {

		Subject iSubject = SecurityUtils.getSubject();

		return iSubject;
	}

	public static void saveAccessToken(String access_token) {
		Subject iSubject = getSubject();

		iSubject.getSession().setAttribute(session_key_access_token, access_token);

	}

	public static void saveAccessCode(String access_code) {
		Subject iSubject = getSubject();

		iSubject.getSession().setAttribute(session_key_access_code, access_code);

	}

	public static String getAccessToken() {
		Subject iSubject = getSubject();

		return (String) iSubject.getSession().getAttribute(session_key_access_token);

	}

	public String getUserAccess() {
		Subject iSubject = getSubject();
		return (String) iSubject.getPrincipal();
	}

	@SuppressWarnings("unchecked")
	public static <V> V getUserInfo() {
		Subject iSubject = getSubject();

		return (V) iSubject.getSession().getAttribute(session_user_info);

	}

	public static <V> void saveUserInfo(V userinfo) {

		Subject iSubject = getSubject();

		iSubject.getSession().setAttribute(session_user_info, userinfo);
	}

	public static String getAccessCode() {
		Subject iSubject = getSubject();

		return (String) iSubject.getSession().getAttribute(session_key_access_code);

	}

	public static void saveSessions(String key, Serializable object) {
		Subject iSubject = getSubject();
		iSubject.getSession().setAttribute(key, object);
	}

	@SuppressWarnings("unchecked")
	public static <V> V getSession(String key) {
		Subject iSubject = getSubject();
		return (V) iSubject.getSession().getAttribute(key);
	}

}

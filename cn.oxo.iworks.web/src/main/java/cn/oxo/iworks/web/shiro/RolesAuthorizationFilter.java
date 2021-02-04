package cn.oxo.iworks.web.shiro;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class RolesAuthorizationFilter extends AuthorizationFilter {

	// TODO - complete JavaDoc

	@SuppressWarnings({ "unchecked" })
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

		Subject subject = getSubject(request, response);
		String[] rolesArray = (String[]) mappedValue;
		if (rolesArray == null || rolesArray.length == 0) {
			return false;
		}
		print(rolesArray);
		Set<String> roles = CollectionUtils.asSet(rolesArray);
		return subject.hasAllRoles(roles);
	}

	private void print(String[] rolesArray) {
		for (String x : rolesArray) {
			System.out.print(">>> " + x);
		}
	}

}

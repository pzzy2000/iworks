package cn.oxo.iworks.web.shiro.simple;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.web.filter.authc.AnonymousFilter;

public class NoAnonymousFilter extends AnonymousFilter {

    private Logger logger = LogManager.getLogger(NoAnonymousFilter.class);

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {

        return true;
    }

    @Override
    public Filter processPathConfig(String path, String config) {

        return super.processPathConfig(path, config);
    }

    @Override
    protected boolean pathsMatch(String pattern, String path) {
        // logger.info("pathsMatch > "+pattern +" "+path+" "+( super.pathsMatch(pattern, path)));
        return super.pathsMatch(pattern, path);
    }

}

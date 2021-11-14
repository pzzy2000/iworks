package cn.oxo.iworks.web.shiro.simple;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class OriginFilter {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Content-Type");
        config.addExposedHeader("X-Requested-With");
        config.addExposedHeader("accept");
        config.addExposedHeader("Origin");
        config.addExposedHeader("Access-Control-Request-Method");
        config.addExposedHeader("Access-Control-Request-Headers");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return new CorsFilter(source);
    }
}

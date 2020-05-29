package com.itheima.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import javax.sql.DataSource;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true)
public class OauthSourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * 指定token的持久化策略
     * InMemoryTokenStore表示将token存储在内存
     * Redis表示将token存储在redis中
     * JdbcTokenStore存储在数据库中
     */
//    @Bean
//    public TokenStore jdbcTokenStore(){
//        return new JdbcTokenStore(dataSource);
//    }

    /**
     * 指定当前资源的id和存储方案
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        /**
         * 这里除以oauth2客户端校验权限有两种方式：
         * 1.客户端可以直接连上oauth2的数据库，则这里配置resources.resourceId("product_api").tokenStore(jdbcTokenStore());
         * 2.客户端连不上oauth2的数据库，则配置super.configure(resources)，并且在application.yml中配置security.oauth2.resource.user-info-uri
         */
        super.configure(resources);
//        resources.resourceId("product_api").tokenStore(jdbcTokenStore());

        /**
         * 这里通过重写OAuth2AuthenticationEntryPoint的commence方法可以自定义权限校验失败时的处理，方法是在
         * OAuth2AuthenticationProcessingFilter#doFilter方法中调用的
         */
//        resources.authenticationEntryPoint(new RefreshTokenAuthenticationEntryPoint());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                //指定不同请求方式访问资源所需要的权限，一般查询是read，其余是write。
//                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
//                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
                .antMatchers("/product/**").authenticated()
                .and()
                .headers().addHeaderWriter((request, response) -> {
                    response.addHeader("Access-Control-Allow-Origin", "*");//允许跨域
                    if (request.getMethod().equals("OPTIONS")) {//如果是跨域的预检请求，则原封不动向下传达请求头信息
                        response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
                    }
                });
    }


}

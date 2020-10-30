package com.itheima.source.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;


    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('ROLE_PRODUCT','ROLE_ADMIN')")
    public String findAll(){
        return "产品列表查询成功！";
    }

    /**
     * 获取认证信息，调用该方法会被spring security过滤器拦截住，去请求认证服务器得到
     * oAuth2Authentication、principal、authentication这三个参数的值
     */
    @GetMapping("/getPrinciple")
    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal,
                                             Authentication authentication) {
        log.info(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());
        log.info(oAuth2Authentication.toString());
        log.info("principal.toString() " + principal.toString());
        log.info("principal.getName() " + principal.getName());
        log.info("authentication: " + authentication.getAuthorities().toString());

        return oAuth2Authentication;
    }

    @GetMapping("/testOAuth2RestTemplate")
    public String testOAuth2RestTemplate(){
        DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken)oAuth2RestTemplate.getAccessToken();
        return accessToken.toString();
    }

}

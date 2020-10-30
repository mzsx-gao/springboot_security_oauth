package com.itheima.server.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

/**
 * 采用RemoteTokenServices这种方式对token进行验证，如果其他资源服务需要验证token,
 * 则需要远程调用授权服务暴露的验证token的api接口
 */
@RestController
@RequestMapping("/security")
public class SecurityController {

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        System.out.println("security server check================>>>" + principal.toString());
        return principal;
    }
}

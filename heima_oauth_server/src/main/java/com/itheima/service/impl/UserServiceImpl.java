package com.itheima.service.impl;

import com.itheima.domain.SysRole;
import com.itheima.domain.SysUser;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名做查询
        SysUser sysUser = userMapper.findByName(username);
        if(sysUser==null){
            return null;
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<SysRole> roles = sysUser.getRoles();
        for (SysRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        UserDetails userDetails = new User(
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getStatus()==1,
                true,
                true,
                true,
                authorities);
        return userDetails;
    }

}

package com.shopme.admin.user.Service;

import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.user.Repository.UserRepository;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user != null){
            return new ShopmeUserDetails(user);
        }
        throw  new UsernameNotFoundException("Could not find user with email: " + email);
    }
}

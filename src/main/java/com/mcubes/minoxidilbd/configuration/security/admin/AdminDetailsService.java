package com.mcubes.minoxidilbd.configuration.security.admin;

import com.mcubes.minoxidilbd.entity.Admin;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Service
public class AdminDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new AdminDetails(new Admin(
                "admin@gmail.com", "admin", "Abc", "01737948893","ROLE_ADMIN"
        ));
    }
}
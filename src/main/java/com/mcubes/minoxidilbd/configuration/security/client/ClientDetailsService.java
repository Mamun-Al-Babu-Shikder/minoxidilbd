package com.mcubes.minoxidilbd.configuration.security.client;

import com.mcubes.minoxidilbd.entity.Client;
import com.mcubes.minoxidilbd.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Service
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    private ClientService clientService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Client client = clientService.loginInformationByEmail(s);
        if(client==null)
            throw new UsernameNotFoundException("client not found");
        return new ClientDetails(client);
    }
}

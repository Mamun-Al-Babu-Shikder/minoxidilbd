package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.Client;
import com.mcubes.minoxidilbd.repository.AdminRepository;
import com.mcubes.minoxidilbd.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    public Client loginInformationByEmail(String email){
        return clientRepository.findLoginInfoByEmail(email);
    }

    public String getClientNameByEmail(String email){
        return clientRepository.findClientNameByEmail(email);
    }


}


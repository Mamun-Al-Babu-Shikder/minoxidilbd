package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.ClientSay;
import com.mcubes.minoxidilbd.repository.ClientSayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientSayService {

    @Autowired
    private ClientSayRepository clientSayRepository;

    public List<ClientSay> getClientCommentsForOurServiceAndProduct() {
        return clientSayRepository.findClientSayByRange(PageRequest.of(0, 5));
    }

}

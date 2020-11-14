package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.ContactMessage;
import com.mcubes.minoxidilbd.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/24/2020.
 */
@Service
public class ContactService {

    private static final Logger LOG = Logger.getLogger(ContactService.class.getName());

    @Autowired
    private ContactRepository contactRepository;

    public boolean saveContactMessage(ContactMessage contactMessage){
        try {
            contactRepository.save(contactMessage);
        }catch (Exception e){
            LOG.warning("# Ex : " + e.getMessage());
            return false;
        }
        return true;
    }
}

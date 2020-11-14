package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.KeyValuePair;
import com.mcubes.minoxidilbd.repository.KeyValuePairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Service
public class KeyValuePairService {

    @Autowired
    private KeyValuePairRepository keyValuePairRepository;

    public String getValue(String key){
        Optional<KeyValuePair> pair = keyValuePairRepository.findById(key);
        if(pair.isPresent()){
            return pair.get().getValue();
        }
        return "Not Found";
    }


}

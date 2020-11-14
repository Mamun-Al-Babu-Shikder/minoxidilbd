package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.Subscriber;
import com.mcubes.minoxidilbd.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    public String subscribe(Subscriber subscriber) {
        try{
            if(subscriberRepository.existsById(subscriber.getEmail())){
                return "You have already subscribed us. Thanks.";
            }else{
                subscriberRepository.save(subscriber);
            }
        }catch (Exception e){
            return "Subscription failed! Try again later.";
        }
        return "You have successfully subscribed us.";
    }
}

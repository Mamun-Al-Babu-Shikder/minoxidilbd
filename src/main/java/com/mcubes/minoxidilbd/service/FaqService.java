package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.Faq;
import com.mcubes.minoxidilbd.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by A.A.MAMUN on 10/25/2020.
 */
@Service
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    public List<Faq> getAllFaq(){
        return (List<Faq>) faqRepository.findAll();
    }

    public List<Faq> getAllGeneralQuestion(){
        return faqRepository.findAllGeneralQuestion();
    }

    public List<Faq> getAllOtherQuestion(){
        return faqRepository.findAllOtherQuestion();
    }

}


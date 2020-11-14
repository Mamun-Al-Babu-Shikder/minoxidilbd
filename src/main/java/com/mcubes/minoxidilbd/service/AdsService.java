package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.data.ConstantKey;
import com.mcubes.minoxidilbd.entity.Ads;
import com.mcubes.minoxidilbd.repository.AdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdsService {

    @Autowired
    private AdsRepository adsRepository;

    public Ads getSingleAdsById(String id) {
        Optional<Ads> optionalAds = adsRepository.findById(id);
        if(optionalAds.isPresent()){
            return optionalAds.get();
        }
        return null;
    }

    public List<Ads> getAdsForInsideCategory(){
        return adsRepository.findGroupAds(ConstantKey.INSIDE_CATEGORY_AD1, ConstantKey.INSIDE_CATEGORY_AD2,
                ConstantKey.INSIDE_CATEGORY_AD3);
    }

    public List<Ads> getSliderAds(){
        return adsRepository.findGroupAds(ConstantKey.SLIDER_AD1, ConstantKey.SLIDER_AD2, ConstantKey.SLIDER_AD3);
    }

}

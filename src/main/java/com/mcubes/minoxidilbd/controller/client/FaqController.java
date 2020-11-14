package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.entity.Faq;
import com.mcubes.minoxidilbd.entity.TeamMember;
import com.mcubes.minoxidilbd.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * Created by A.A.MAMUN on 10/24/2020.
 */
@Controller
public class FaqController {

    @Autowired
    private CommonData commonData;
    @Autowired
    private FaqService faqService;

    @RequestMapping("/faq")
    public String faq(Principal principal, Model model){

        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);


        List<Faq> generalFaqs = faqService.getAllGeneralQuestion();
        List<Faq> otherFaqs = faqService.getAllOtherQuestion();

        model.addAttribute("generalFaqs", generalFaqs);
        model.addAttribute("otherFaqs", otherFaqs);

        return "client/pages/faq";
    }
}

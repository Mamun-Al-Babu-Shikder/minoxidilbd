package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.entity.ContactMessage;
import com.mcubes.minoxidilbd.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by A.A.MAMUN on 10/15/2020.
 */
@Controller
public class ContactController {

    @Autowired
    private CommonData commonData;
    @Autowired
    private ContactService contactService;

    @RequestMapping("/contact-us")
    public String contactUs(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);


        return "client/pages/contact-us";
    }

    @ResponseBody
    @PostMapping("/send-contact-message")
    public String sendContactMessage(@Valid @ModelAttribute ContactMessage contactMessage, BindingResult result){
        if(result.hasErrors()){
            return "invalid";
        }
        return contactService.saveContactMessage(contactMessage) ? "success" : "failed";
    }

}

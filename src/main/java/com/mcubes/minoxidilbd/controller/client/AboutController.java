package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.entity.ClientSay;
import com.mcubes.minoxidilbd.entity.TeamMember;
import com.mcubes.minoxidilbd.service.ClientSayService;
import com.mcubes.minoxidilbd.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/15/2020.
 */
@Controller
public class AboutController {

    private static final Logger LOG = Logger.getLogger(AboutController.class.getName());

    @Autowired
    private CommonData commonData;
    @Autowired
    private TeamMemberService teamMemberService;
    @Autowired
    private ClientSayService clientSayService;


    @RequestMapping("/about-us")
    public String aboutUs(Principal principal, Model model){

        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        List<TeamMember> teamMembers = teamMemberService.getTeamMembers();
        model.addAttribute("teamMembers", teamMembers);

        List<ClientSay> clientSays = clientSayService.getClientCommentsForOurServiceAndProduct();
        model.addAttribute("clientSays", clientSays);

        return "client/pages/about-us";
    }

}

package com.mcubes.minoxidilbd.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Controller
@RequestMapping("/admin")
public class DashboardController {

    @RequestMapping("/dashboard")
    public String dashboard(){
        return "/admin/dashboard";
    }

}

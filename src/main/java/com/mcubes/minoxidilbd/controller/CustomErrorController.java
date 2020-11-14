package com.mcubes.minoxidilbd.controller;

import com.mcubes.minoxidilbd.data.CommonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by A.A.MAMUN on 10/26/2020.
 */
@Controller
public class CustomErrorController implements ErrorController{

    @Autowired
    private CommonData commonData;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Principal principal, Model model) {

        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            //System.out.println("# Error Status Code : " + statusCode);
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error";
            }
            /*
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
            */
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

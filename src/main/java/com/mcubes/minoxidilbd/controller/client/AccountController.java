package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.entity.Client;
import com.mcubes.minoxidilbd.service.ClientAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/7/2020.
 */
@Controller
//@RequestMapping("/account")
public class AccountController {

    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private CommonData commonData;
    @Autowired
    private ClientAccountService clientAccountService;

    @RequestMapping("/login")
    public  String login(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        return "client/pages/account/login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);
        model.addAttribute("login", principal!=null);
        model.addAttribute("client", new Client());
        return "client/pages/account/register";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(Principal principal, Model model, RedirectAttributes redirectAttributes,
                               @Valid Client client, BindingResult result){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);
        LOG.info("# " + client);
        if(result.hasErrors()){
            LOG.info("# Found following errors to validated client");
            result.getAllErrors().forEach(e->LOG.warning("# Error : " + e.getDefaultMessage()));
            return "/client/pages/account/register";
        }else{
            ClientAccountService.AccountCreationStatus status = clientAccountService.registerClient(client);
            model.addAttribute("status", status);
            return "client/pages/account/register_info";
        }
    }

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            System.out.println("# Ex : " + ex);
            if (ex != null) {
                errorMessage = ex.getMessage();
                if(errorMessage.equalsIgnoreCase("Bad credentials")){
                    errorMessage = "Invalid email address or password.";
                }else if(errorMessage.equalsIgnoreCase("User is disabled")){
                    errorMessage = "Check your email address and verify your account.";
                }
            }
        }
        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/login";
    }

    @RequestMapping("/account-verification")
    public String accountVerification(Principal principal, Model model, @RequestParam String token)
    {
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        LOG.info("# Validation Token : " + token);
        String status = clientAccountService.verifyAccount(token);
        if(status.equalsIgnoreCase("success")){
            return "client/pages/account/account_verification";
        }
        return "redirect:/error";
    }

    @RequestMapping("/account/profile")
    public String profile(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        Client client = clientAccountService.getClientInfoByEmail(principal.getName());
        model.addAttribute("client", client);

        return "client/pages/account/profile";
    }

    @ResponseBody
    @RequestMapping(value = "/account/profile-update", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> updateClientProfile(@Valid Client client, BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes){

        LOG.info("# " + client.toString());
        Map<String, String> map = new HashMap<>();
        System.out.println("# hasError : " + bindingResult.hasErrors());
        if(bindingResult.hasErrors()){
            map.put("status", "validation_failed");
            for (FieldError fieldError : bindingResult.getFieldErrors()){
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }else{
            client.setEmail(principal.getName());
            if(clientAccountService.updateClientProfile(client, principal.getName())) {
                map.put("status", "success");
            }else {
                map.put("status", "server_error");
            }
        }
        //return "client/pages/account/profile";
        //return "redirect:/account/profile";
        return ResponseEntity.ok(map);
    }

    @ResponseBody
    @RequestMapping(value = "/account/change-password", method = RequestMethod.POST)
    public String changePassword(@RequestParam(name = "op") String oldPassword,
                                 @RequestParam(name = "np") String newPassword, Principal principal) {
        if(newPassword.length()<6 || newPassword.length()>255){
            return "Password length must be 6 to 255";
        }
        return clientAccountService.updateClientPassword(oldPassword, newPassword, principal.getName());
    }

    @ResponseBody
    @RequestMapping(value = "/account-recover", method = RequestMethod.POST)
    public String recoverAccount(@RequestParam String email) {
        return clientAccountService.recoverAccount(email);
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.GET)
    public String requestResetPassword(@RequestParam String token, Principal principal, Model model) {
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);
        if(clientAccountService.isPasswordResetTokenExist(token)){
            model.addAttribute("token", token);
            return "/client/pages/account/reset_password";
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public String resetPassword(@RequestParam String token, @RequestParam String password,
                                @RequestParam(name = "confirm_password") String confirmPassword,
                                RedirectAttributes redirectAttributes) {

        System.out.println("# token : "+token+",  Password : "+password+", Confirm Password : "+confirmPassword);

        if(password.length()<6 || password.length()>255 || confirmPassword.length()<6 || confirmPassword.length()>255){
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Invalid password length. Password length must be 6 to 255");
            return "redirect:/reset-password?token=" + token;
        }else if(!password.equals(confirmPassword)){
            redirectAttributes.addFlashAttribute("errorMessage", "Password and Confirm Password are not matched");
            return "redirect:/reset-password?token=" + token;
        }else{
            String result = clientAccountService.resetPassword(token, password);
            if(result.equals("token_not_found")){
                return "redirect:/error";
            }else{
                return "redirect:/reset-success";
            }
        }
    }

    @RequestMapping("/reset-success")
    public String passwordResetSuccess(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);
        return "client/pages/account/password_reset_success";
    }

}

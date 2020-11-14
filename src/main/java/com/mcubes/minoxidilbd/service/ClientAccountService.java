package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.data.ConstantKey;
import com.mcubes.minoxidilbd.entity.Client;
import com.mcubes.minoxidilbd.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Transactional
public class ClientAccountService {

    private static final Logger LOG = Logger.getLogger(ClientAccountService.class.getName());

    public enum AccountCreationStatus {
        USER_ALREADY_EXIST, SUCCESS, FAILED, ACCOUNT_NOT_VERIFIED
    }


    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public JavaMailSender javaMailSender;

    @Autowired
    private EmailSendService emailSendService;

    public Client getClientInfoByEmail(String email){
        Optional<Client> optionalClient = clientRepository.findById(email);
        if(optionalClient.isPresent()){
            return optionalClient.get();
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public AccountCreationStatus registerClient(Client client){
        try{
            Optional<Client> optionalClient = clientRepository.findById(client.getEmail());
            if(optionalClient.isPresent()){
                Client checkedClient = optionalClient.get();
                if(checkedClient.isEnable()){
                    return AccountCreationStatus.USER_ALREADY_EXIST;
                }else {
                    return AccountCreationStatus.ACCOUNT_NOT_VERIFIED;
                }
            }else{
                client.setPassword(passwordEncoder.encode(client.getPassword()));
                client.setEnable(false);
                client.setRole("ROLE_CLIENT");
                String token = UUID.randomUUID().toString();
                client.setValidationToken(token);
                client.setCreatedDate(new Date());

               // sendEmailConfirmationEmail(client);
                emailSendService.sendTextMail("minoxidilgroupbd@gmail.com", client.getEmail(), "Account Verification", "Dear User,\nYour registration successfully complete. " +
                        "Before login to your account you need to verify your account." +
                        "To verify your account please click on verification link : " +
                        ConstantKey.WEBSITE_BASE_URL + "/account-verification?token=" + token +". Thanks.");

                // after sending mail then save the information
                clientRepository.save(client);
            }
        }catch (Exception e){
            e.printStackTrace();
           LOG.warning("# Ex : " + e.getMessage());
           return AccountCreationStatus.FAILED;
        }
        return AccountCreationStatus.SUCCESS;
    }

    public String verifyAccount(String token){
        if(token!=null && clientRepository.countClientByValidationToken(token)!=0){
            clientRepository.enableClientAccount(token);
        }else{
            return "not_found";
        }
        return "success";
    }

    public boolean updateClientProfile(Client client, String email) {
        try{
            Client oldClient = clientRepository.findById(email).get();
            oldClient.setName(client.getName());
            oldClient.setGender(client.getGender());
            oldClient.setDob(client.getDob());
            oldClient.setPhone(client.getPhone());
            oldClient.setAddress(client.getAddress());
            oldClient.setFacebook(client.getFacebook());
            oldClient.setTwitter(client.getTwitter());
            oldClient.setLinkedin(client.getLinkedin());
            oldClient.setInstagram(client.getInstagram());
            clientRepository.save(oldClient);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public String updateClientPassword(String oldPassword, String newPassword, String email){
        try {
            String password = clientRepository.findClientPasswordByEmail(email);
            if (passwordEncoder.matches(oldPassword, password)) {
                clientRepository.updatePassword(passwordEncoder.encode(newPassword), email);
            } else {
                return "You have entered wrong current password!";
            }
        }catch (Exception e){
            return "Server error! Failed to change your password.";
        }
        return "Password successfully updated.";
    }

    public String recoverAccount(String email){
        try {
            if (clientRepository.existsById(email)) {
                String token = UUID.randomUUID().toString();
                // send an email to client for reset password
                emailSendService.sendTextMail("minoxidilgroupbd@gmail.com", email, "Reset Password",
                        "Dear User,\nPlease visit the following link to reset password of your account, link : " +
                        ConstantKey.WEBSITE_BASE_URL + "/reset-password?token=" + token +". Thanks.");

                // save password reset token
                clientRepository.setPasswordResetToken(token, email);
            }else {
                return "Account not found!";
            }
        }catch (Exception e){
            return "Server error! Please try again later.";
        }
        return "success! We sent you an email to reset your password.";
    }

    public boolean isPasswordResetTokenExist(String token) {
        return clientRepository.existsClientByPasswordResetToken(token);
    }

    public String resetPassword(String token, String password) {
        if(isPasswordResetTokenExist(token)){
            clientRepository.resetPasswordByToken(passwordEncoder.encode(password), token);
        }else{
            return "token_not_found";
        }
        return "success";
    }

}



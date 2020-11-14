package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.Client;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Repository
public interface ClientRepository extends CrudRepository<Client, String> {

    @Query("select new Client(c.email, c.password, c.role, c.enable, c.locked) from Client c where c.email=?1")
    Client findLoginInfoByEmail(String email);

    @Query("select c.name from Client c where c.email=?1")
    String findClientNameByEmail(String email);

    int countClientByValidationToken(String token);

    @Modifying
    @Query("update Client c set c.enable=true where c.validationToken=?1")
    void enableClientAccount(String token);

    //@Query("select count(c) > 0 from Client c where c.email=?1 and c.password=?2")
    @Query("select c.password from Client c where c.email=?1")
    String findClientPasswordByEmail(String email);

    @Modifying
    @Query("update Client c set c.password=?1 where c.email=?2")
    void updatePassword(String newPassword, String email);

    @Modifying
    @Query("update Client c set c.passwordResetToken=?1 where c.email=?2")
    void setPasswordResetToken(String token, String email);

    boolean existsClientByPasswordResetToken(String token);

    @Modifying
    @Query("update Client c set c.password=?1, c.passwordResetToken=null where c.passwordResetToken=?2")
    void resetPasswordByToken(String password, String token);
}

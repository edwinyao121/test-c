package com.catstore.api.repository;

import com.catstore.api.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    @Query("SELECT v FROM VerificationCode v WHERE v.phoneNumber = :phoneNumber AND v.type = :type AND v.used = 0 ORDER BY v.createTime DESC LIMIT 1")
    Optional<VerificationCode> findLatestValidCode(@Param("phoneNumber") String phoneNumber, @Param("type") String type);

    @Modifying
    @Query("UPDATE VerificationCode v SET v.used = 1 WHERE v.phoneNumber = :phoneNumber AND v.type = :type AND v.used = 0 AND v.expiresTime > :now")
    int markAsUsed(@Param("phoneNumber") String phoneNumber, @Param("type") String type, @Param("now") LocalDateTime now);

    @Modifying
    @Query("DELETE FROM VerificationCode v WHERE v.expiresTime < :now")
    int deleteExpiredCodes(@Param("now") LocalDateTime now);
}

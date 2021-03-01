package com.example.project.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project.model.OTP;

public interface OTPRepository extends JpaRepository<OTP, Long>{
	
	
	@Query(value="SELECT CREATED_DATE FROM OTP_DETAILS WHERE MAIL_ID =:mailid AND OTP =:otp AND STATUS='NEW'",nativeQuery=true)
	public Date getOTPDetialsForMail(String mailid, int otp);

}

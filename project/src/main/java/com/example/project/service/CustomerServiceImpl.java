package com.example.project.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.model.OTP;
import com.example.project.model.User;
import com.example.project.repository.CustomerRepository;
import com.example.project.repository.OTPRepository;
import com.example.project.util.Constants;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	

	private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	MailServiceImpl mailService;
	
	@Autowired(required=true)
	CustomerRepository customerRepository;
	
	@Autowired(required=true)
	OTPRepository otpRepository;
	
	//sending otp to mail
	public int sendMailOtp(String mailId){
		logger.info("CustomerServiceImpl - sendMailOtp :: Enter");
		System.out.println("Mail Id : " + mailId);
		int otp = mailService.sendOtpToMail(mailId);
		System.out.println(" Test Mail: " +otp);
		logger.info("CustomerServiceImpl - sendMailOtp :: Exit");
		return otp;
	}
	
	//setting user data and sending to dao
	public String saveCustomerDetailsForMail(String username, String password, String mailid, int otp, boolean statusFlag, Date date){
		logger.info("CustomerServiceImpl - saveCustomerDetailsForMail :: Enter");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setMail(mailid);
		user.setStatus(statusFlag ? "Y" : "N");
		System.out.println("Status Flag :" + statusFlag);
		user.setCreatedDate(date);
		customerRepository.save(user);
		
		if(mailid != null){
			OTP otpData = new OTP();
			otpData.setMailid(mailid);
			otpData.setOtp(otp);
			otpData.setStatus(Constants.NEW);
			otpData.setCreatedDate(new Date());
			otpData.setExpireDate(null);
			otpRepository.save(otpData);		
		}
		logger.info("CustomerServiceImpl - saveCustomerDetailsForMail :: Exit");
		return "saved customer and otp";
		
	}
	
	
	public String getOTPDetailsForMail(String mailid, int otp){
		
		String val1 = "sucess";
		String val2 = "fail";
		Date current = new Date();
		Date previous =otpRepository.getOTPDetialsForMail(mailid, otp);
		System.out.println(previous);
		System.out.println(current);
		if(previous != null && (current.getMinutes() > previous.getMinutes())) 
		{
			return val1;
		}else{		
			return val2;
		}
	}


}

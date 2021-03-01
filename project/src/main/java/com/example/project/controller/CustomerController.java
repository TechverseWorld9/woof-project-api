package com.example.project.controller;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	private static final Logger logger = LogManager.getLogger(CustomerController.class);
	
	@Autowired(required=true)
	private CustomerService customerService;
	
	@RequestMapping("/index")
	public String index(){
		logger.info("CustomerController - index");
		return "Hello...!";
	}
	
	@RequestMapping(value="/email/{username}/{password}/{mailid}", method ={RequestMethod.POST,RequestMethod.GET})
	public String saveCustomerDetailsMail(@PathVariable String username, @PathVariable String password, @PathVariable String mailid){
		logger.info("CustomerController - saveCustomerDetailsMail :: Enter");
		logger.info("saveCustomerDetailsMail : " + username + "-" + mailid);
		int otp = 0;
		boolean status = false;
		if(mailid != null){
			otp = customerService.sendMailOtp(mailid);
		}
		if(otp != 0){
			status = true;
		}
		String message = customerService.saveCustomerDetailsForMail(username, password, mailid, otp, status, new Date());
		logger.info("CustomerController - saveCustomerDetailsMail :: Exit");
		return message;
	}
	
	
	@GetMapping(value="/verifyemail/{otp}/{mailid}")
	public String verifyEmailOtpForCustomer(@PathVariable String mailid, @PathVariable String otp){
		logger.info("CustomerController - verifyEmailOtpForCustomer :: Enter");
		String message = "check";
		int otpNo = Integer.valueOf(otp);
		if(mailid != null){
			message = customerService.getOTPDetailsForMail(mailid,otpNo);
		}
		
		logger.info("CustomerController - verifyEmailOtpForCustomer :: Enter");
		return message;
	}

}

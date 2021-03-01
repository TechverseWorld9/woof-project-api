package com.example.project.service;

import java.util.Date;


public interface CustomerService {

	public int sendMailOtp(String mailId);
	
	public String saveCustomerDetailsForMail(String username, String password, String mailid, int otp, boolean status, Date date);

	public String getOTPDetailsForMail(String mailid, int otp);
	
}

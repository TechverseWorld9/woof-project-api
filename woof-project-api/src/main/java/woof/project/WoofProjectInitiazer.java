package woof.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class WoofProjectInitiazer {

	
	private final WoofProjectproperties woofProjectproperties;
	
	@Autowired
	public WoofProjectInitiazer(WoofProjectproperties woofProjectproperties)
	{
		this.woofProjectproperties=woofProjectproperties;
		Twilio.init(woofProjectproperties.getAccountSid(), woofProjectproperties.getAuthToken());
		System.out.println("Twilio initialized with account-"+woofProjectproperties.getAccountSid());
	}
}

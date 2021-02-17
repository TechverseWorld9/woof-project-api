package woof.project;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import woof.project.Entity.User;
import woof.project.Repository.UserRepository;
import woof.project.Service.PhoneverificationService;
import woof.project.Service.VerificationResult;

@Controller
public class WoofProjectController {

	@Autowired
	PhoneverificationService phonesmsservice;

	
	@Autowired
    private UserRepository userRepository;
	    
	@RequestMapping("/")
	public String homepage(ModelAndView model)
	{
		return "index";
	}
	
	
	@PostMapping("/sendotp")
	public ResponseEntity<String> sendotp(@RequestParam("phone") String phone)
	{
	    VerificationResult result=phonesmsservice.startVerification(phone);
	    if(result.isValid())
	    {
	    	return new ResponseEntity<>("Otp Sent12..",HttpStatus.OK);
	    }
		return new ResponseEntity<>("Otp sent12..",HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/verifyotp")
	public ResponseEntity<String> sendotp(@RequestParam("phone") String phone, @RequestParam("otp") String otp)
	{
	    VerificationResult result=phonesmsservice.checkverification(phone,otp);
	    if(result.isValid())
	    {
	    	
	    	return new ResponseEntity<>("Your number is Verified",HttpStatus.OK);
	    }
		return new ResponseEntity<>("Something wrong/ Otp incorrect",HttpStatus.BAD_REQUEST);
	}
	
	
	
	
}

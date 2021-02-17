package woof.project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import woof.project.WoofProjectproperties;
import woof.project.Entity.User;
import woof.project.Repository.*;
@Service
@Component
public class PhoneverificationService {

	private final WoofProjectproperties woofProjectproperties;
	private final User user;
	
	

	@Autowired
	public PhoneverificationService(WoofProjectproperties woofProjectproperties,User user ) {
		this.woofProjectproperties=woofProjectproperties;
		this.user=user;
	}
	
	
	@Autowired
    private UserRepository userRepository;
	
	//method to send to otp
    public VerificationResult startVerification(String phone) {
        try {
            Verification verification = Verification.creator(woofProjectproperties.getServiceId(), phone, "sms").create();
            if("approved".equals(verification.getStatus())|| "pending".equals(verification.getStatus())) {
			return new VerificationResult(verification.getSid());
			}
        } catch (ApiException exception) {
            return new VerificationResult(new String[] {exception.getMessage()});
        }
        return null;
    }

    //mehtod to verifiy the otp
    public VerificationResult checkverification(String phone, String code) {
        try {
            VerificationCheck verification = VerificationCheck.creator(woofProjectproperties.getServiceId(), code).setTo(phone).create();
            if("approved".equals(verification.getStatus())) {
            	callregistration(phone);
                return new VerificationResult(verification.getSid());
                
            }
            return new VerificationResult(new String[]{"Invalid code."});
        } catch (ApiException exception) {
            return new VerificationResult(new String[]{exception.getMessage()});
        }
    }

     //Create entry of user with mobile number
	public User callregistration(String phone) {
		user.setMobilenum(phone);
		userRepository.save(user);
		System.out.println("user entry with mobile num created -"+ user.getUserId() + user.getMobilenum() );
		return user;
		
	}
	
}

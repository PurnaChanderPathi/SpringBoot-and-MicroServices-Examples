package com.example.service;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dto.UserDetailsDto;
import com.example.model.UserDetails;
import com.example.repo.UserRepo;
import com.itextpdf.text.DocumentException;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JavaMailSender mailSender;
	
    @Autowired
    private PdfService pdfService;

	@Override
	public UserDetails createUser(UserDetailsDto userDetailsDto) {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId(0l);
		userDetails.setUserName(userDetailsDto.getUserName());
		userDetails.setEmail(userDetailsDto.getEmail());
		userDetails.setMobileNumber(userDetailsDto.getMobileNumber());
		userDetails.setAddress(userDetailsDto.getAddress());
		userDetails.setStatus("Locked");
		
		String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCase = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String combination = upperCase+lowerCase+numbers;
		int len=6;
		char[] password= new char[len];
		Random r = new Random();
		for(int i=0; i<len; i++) {
			password[i]=combination.charAt(r.nextInt(combination.length()));
		}
		System.out.println("Generated password is:"+ new String(password));
		userDetails.setPassword(new String(password));
		
		//Save UserDetails
		UserDetails savedUser = userRepo.save(userDetails);
		
		//Send mail Containing UserDetails
		sendUserDetailsEmail(userDetails);
		
		return savedUser;
	}
	
    private void sendUserDetailsEmail(UserDetails userDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userDetails.getEmail());
        message.setSubject("User Details");
        message.setText("Hello " + userDetails.getUserName() + ",\n\n"
                + "Thank you for registering!\n\n"
                + "Your user details:\n"
                + "Username: " + userDetails.getUserName() + "\n"
                + "Email: " + userDetails.getEmail() + "\n"
                + "Password: " + userDetails.getPassword() + "\n"
                + "Mobile Number: " + userDetails.getMobileNumber() + "\n"
                + "Address: " + userDetails.getAddress() + "\n"
                + "Status: "+ userDetails.getStatus() + "\n");

        try {
        	mailSender.send(message);
        } catch (MailException e) {

        }
    }

    public byte[] getAllUsersAsPDF() {
        List<UserDetails> userDetailsList = userRepo.findAll();
        try {
            return pdfService.generatePDF(userDetailsList);
        } catch (DocumentException e) {
            e.printStackTrace();
            // Handle exception appropriately, perhaps return an error PDF
            return new byte[0]; // or null, depending on your design
        }
    }
	


	
	

}

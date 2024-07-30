package com.purna.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import com.purna.config.MailConfig;
import com.purna.dto.ChangePasswordDto;
import com.purna.exception.NewPasswordException;
import com.purna.exception.UserNameOrOtpDoesnotMatchedException;
import com.purna.model.ForgotPassword;
import com.purna.repository.ForgotPasswordRepoistory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.purna.model.User;
import com.purna.repository.UserRepository;

@Service
@Slf4j
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MailConfig mailConfig;
	@Autowired
	private ForgotPasswordRepoistory forgotPasswordRepoistory;

	Map<String, Object> map = new HashMap<>();
	
	public User saveUser(User user,MultipartFile profilePhoto) throws IOException {
		if(profilePhoto != null && !profilePhoto.isEmpty()) {
			user.setProfilePhoto(profilePhoto.getBytes());
		}
		return userRepository.save(user);
	}

	
	public Optional<User>  findByUsername(String username) {
		return Optional.ofNullable(userRepository.findByUsername(username));
	}
	
	
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
		
	}
	
	public List<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	public void deleteUser(Long id) {
		 userRepository.deleteById(id);
	}
	
	public User updateProfilePhoto(Long userId, MultipartFile profilePhoto) throws Exception {
		Optional<User> userDetails = userRepository.findById(userId);
		if(userDetails.isPresent()) {
			User getUser = userDetails.get();
			getUser.setProfilePhoto(profilePhoto.getBytes());
			return userRepository.save(getUser);
		}else {
			throw new Exception("No User Found with userId : "+userId);
		}
	}
	public User updateuserDetails(Long userId, User user,MultipartFile profilePhoto) throws Exception {
		Optional<User> userDetails = userRepository.findById(userId);
		if(userDetails.isPresent()) {
			User getUser = userDetails.get();
			if(user.getUsername() != null && !user.getUsername().isEmpty()) {
				getUser.setUsername(user.getUsername());
			}
			if(user.getPassword() != null && !user.getPassword().isEmpty()) {
				getUser.setPassword(passwordEncoder.encode(user.getPassword()));	
			}
			if(user.getEmail() != null && !user.getEmail().isEmpty()) {
				getUser.setEmail(user.getEmail());
			}

			if(profilePhoto != null && !profilePhoto.isEmpty()) {
				getUser.setProfilePhoto(profilePhoto.getBytes());
			}
			return userRepository.save(getUser);
		}else {
			throw new RuntimeException("No User Found with userId : "+userId);
		}
	}

	public Map<String, Object> forgotPassword(String email) throws MessagingException {

		User user=this.userRepository.findByEmail(email);
		if(user!=null)
		{
			String otp=new DecimalFormat("000000").format(new Random().nextInt(999999));
			JavaMailSender javaMailSender= mailConfig.getJavaMailSender();
			MimeMessage mail=javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper=new MimeMessageHelper(mail,true);

			messageHelper.setFrom("purnachander.eidiko@gmail.com");
			messageHelper.setTo(user.getEmail());
			messageHelper.setSubject("testing");
			messageHelper.setText(otp);
			javaMailSender.send(mail);
			Optional<ForgotPassword> forgotPassword= Optional.ofNullable(forgotPasswordRepoistory.findByEmail(user.getEmail()));
			if(forgotPassword.isPresent())
			{
				ForgotPassword getDetails = forgotPassword.get();
				getDetails.setOtp(Integer.parseInt(otp));
				this.forgotPasswordRepoistory.save(getDetails);
			}
			else {
				ForgotPassword forgotPassword1 = new ForgotPassword();
				forgotPassword1.setEmail(email);
				forgotPassword1.setOtp(Integer.parseInt(otp));
				this.forgotPasswordRepoistory.save(forgotPassword1);
			}
			map.put("status",HttpStatus.OK.value());
			map.put("message","mail Sent successfully");
		}
		else {
			throw new UsernameNotFoundException("user not found with this "+email);
		}
		return map;
	}

	public Map<String, Object> changePassword(ChangePasswordDto changePasswordDto) throws UserNameOrOtpDoesnotMatchedException {
		Optional<ForgotPassword> findUser = Optional.ofNullable(this.forgotPasswordRepoistory.findByEmail(changePasswordDto.getEmail()));
		if(findUser.isPresent()){
			ForgotPassword existingUser = findUser.get();
			if((existingUser.getEmail().equals(changePasswordDto.getEmail()) ) && (existingUser.getOtp() == changePasswordDto.getOtp())){
				Optional<User> user = Optional.ofNullable(this.userRepository.findByEmail(changePasswordDto.getEmail()));
				if(user.isPresent()){
					User getUser = user.get();

					if(passwordEncoder.matches(changePasswordDto.getNewPassword(),getUser.getPassword())){
						log.info("Old password: {} New password: {}", getUser.getPassword(), changePasswordDto.getNewPassword());
						throw new NewPasswordException("New Password is Same as Old Password");
					}else{
						getUser.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
						this.userRepository.save(getUser);
						this.forgotPasswordRepoistory.delete(findUser.get());
						map.put("status",HttpStatus.OK.value());
						map.put("message","updated password successfully..!");
						map.put("result",getUser);
					}
				}else{
					throw new UsernameNotFoundException("user not found with id : "+changePasswordDto.getEmail());
				}
			}else{
				throw new UserNameOrOtpDoesnotMatchedException("Username or Otp not matched");
			}
		}else{
			throw new UsernameNotFoundException("Any otp didn't send this mailID : "+changePasswordDto.getEmail());
		}
		return map;
	}
}

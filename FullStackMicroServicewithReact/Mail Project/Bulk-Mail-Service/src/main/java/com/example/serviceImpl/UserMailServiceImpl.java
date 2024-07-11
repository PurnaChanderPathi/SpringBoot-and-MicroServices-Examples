package com.example.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.config.EmailConfig;
import com.example.dto.MailSender;
import com.example.dto.SendMailWOADto;
import com.example.dto.SigninDto;
import com.example.entity.MailCredentials;
import com.example.entity.User;
import com.example.exception.UserAlreadyExistsException;
import com.example.repository.MailCredentialsRepository;
import com.example.repository.UserRepository;
import com.example.service.UserMailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserMailServiceImpl implements UserMailService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private EmailConfig emailConfig;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MailCredentialsRepository mailCredentialsRepository;
	
	@Value("${spring.files.storage}")
	public String folderLocation;
	
	private final ExecutorService executorService = Executors.newFixedThreadPool(1); 
	Map<String, Object> map = new HashMap<>();

	@Override
	public Map<String, Object> createUser(SigninDto signinDto) {
		System.out.println(signinDto.getUsername());
		User user = this.repository.findByUsername(signinDto.getUsername());
		if(user!=null) {
			throw new UserAlreadyExistsException("customer is alresady exists with this mail Id");
		}
		
		User user2 = new User();
		user2.setUsername(signinDto.getUsername());
		user2.setPassword(signinDto.getPassword());
		user2.setRole("user");
		this.repository.save(user2);
		map.put("status", HttpStatus.CREATED.value());
		map.put("message", "Created Successfully");
		map.put("result", user2);
		return map;
	}

	@Override
	public Map<String, Object> saveMailCredentials(MailCredentials credentials) {
		MailCredentials mailCredentials = this.mailCredentialsRepository.findByMailId(credentials.getMailId());
		if(mailCredentials!=null) {
			throw new UserAlreadyExistsException("this mail credentials already exists");
		}
		MailCredentials mailCredentials2 = new MailCredentials();
		mailCredentials2.setMailId(credentials.getMailId());
		mailCredentials2.setPassword(credentials.getPassword());
		this.mailCredentialsRepository.save(mailCredentials2);
		map.put("status", HttpStatus.CREATED.value());
		map.put("message", "Created Successfully..!");
		map.put("result", mailCredentials2);
		return map;
	}

	@Override
	public Map<String, Object> getAllMailCredentials() {
		List<MailCredentials> getAllMail = this.mailCredentialsRepository.findAll();
		map.put("success", HttpStatus.OK.value());
		map.put("message", "Fetched Successfully...!");
		map.put("result", getAllMail);
		return map;
	}
	
	@Async
	private void sendingMailsOneByOne(String username, String password, String name, String emails, String appraisalType, String appraisalScore, String dueDate, String subject, List<MultipartFile> attachments) throws Exception {
		JavaMailSender javaMailSender = emailConfig.getJavaMailSender(username, password);
		MimeMessage mail = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail,true);
		
		mimeMessageHelper.addTo(emails);
		String template = readingMailTemplateFromText("appraisal-mail.txt");
		String processedTemplate = template.replace("@empName", name).replace("@typeOfAppraisal", appraisalType).replace("@appraisalScore", appraisalScore).replace("@dueDate", dueDate.toString());
		mimeMessageHelper.setFrom(username);
		mimeMessageHelper.setSubject("[Broadcast] " + subject);
		
		if(attachments!= null && !attachments.isEmpty()) {
			for(MultipartFile attachment : attachments) {
				if(attachment!=null && !attachment.isEmpty()) {
					mimeMessageHelper.addAttachment(Objects.requireNonNull(attachment.getOriginalFilename()), new ByteArrayResource(attachment.getBytes()));			
				}

			}
		}
		
		mimeMessageHelper.setText(processedTemplate,true);
		javaMailSender.send(mail);		
	}
	
	private void sendingMailsOneByOneWithoutAttachments(String username, String password, String name, String emails, String appraisalType, String appraisalScore, String dueDate, String subject) throws Exception {
        JavaMailSender javaMailSender = emailConfig.getJavaMailSender(username, password);
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true);

        mimeMessageHelper.addTo(emails);
        String template = readingMailTemplateFromText("appraisal-mail.txt");
        String processedTemplate = template.replace("@empName", name).replace("@typeOfAppraisal", appraisalType).replace("@appraisalScore", appraisalScore).replace("@dueDate", dueDate.toString());
        mimeMessageHelper.setFrom(username);
        mimeMessageHelper.setSubject("[Broadcast] " + subject);
        mimeMessageHelper.setText(processedTemplate, true);

        javaMailSender.send(mail);
	}
	
	
	 public String readingMailTemplateFromText(String fileName) throws IOException {
	        String fullFileName = Paths.get(folderLocation, fileName).toString();
	        Path filePath = Paths.get(fullFileName);
	        if (!Files.exists(filePath)) {
	            throw new IOException("File not found: " + fullFileName);
	        }
	        return new String(Files.readAllBytes(filePath));
	    }

	public void sendingMails(MailSender mailSender) throws Exception {
		System.out.println(mailSender.getUsername()+"<<<<>>>>>"+mailSender.getPassword());
        JavaMailSender javaMailSender = emailConfig.getJavaMailSender(mailSender.getUsername(), mailSender.getPassword());
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true);
        System.out.println(mailSender.getEmails());
        mimeMessageHelper.addTo(mailSender.getEmails());
        String template = readingMailTemplateFromText("appraisal-mail.txt");
        String processedTemplate = template.replace("@empName", mailSender.getName()).replace("@typeOfAppraisal", mailSender.getAppraisalType()).replace("@appraisalScore", mailSender.getAppraisalScore()).replace("@dueDate", mailSender.getDueDate().toString());
        mimeMessageHelper.setFrom(mailSender.getUsername());
        mimeMessageHelper.setSubject("[Broadcast] " + mailSender.getSubject());
        mimeMessageHelper.setText(processedTemplate, true);
        
        if(mailSender.getAttachments()!= null && !mailSender.getAttachments().isEmpty()) {				
					mimeMessageHelper.addAttachment(Objects.requireNonNull(mailSender.getAttachments().getOriginalFilename()), new ByteArrayResource(mailSender.getAttachments().getBytes()));			
		}
        javaMailSender.send(mail);
		
	}
	
	public void sendMailWithOutAttachments(SendMailWOADto woaDto) throws MessagingException, IOException {
        JavaMailSender javaMailSender = emailConfig.getJavaMailSender(woaDto.getUsername(), woaDto.getPassword());
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true);

        mimeMessageHelper.addTo(woaDto.getEmails());
        String template = readingMailTemplateFromText("appraisal-mail.txt");
        String processedTemplate = template.replace("@empName", woaDto.getName()).replace("@typeOfAppraisal", woaDto.getAppraisalType()).replace("@appraisalScore", woaDto.getAppraisalScore()).replace("@dueDate", woaDto.getDueDate().toString());
        mimeMessageHelper.setFrom(woaDto.getUsername());
        mimeMessageHelper.setSubject("[Broadcast] " + woaDto.getSubject());
        mimeMessageHelper.setText(processedTemplate, true);

        javaMailSender.send(mail);
	}

}

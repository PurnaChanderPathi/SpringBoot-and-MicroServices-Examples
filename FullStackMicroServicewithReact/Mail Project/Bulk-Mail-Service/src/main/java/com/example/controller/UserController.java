package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MailSender;
import com.example.dto.SendMailWOADto;
import com.example.dto.SignUpDto;
import com.example.dto.UploadExcelDto;
import com.example.entity.MailCredentials;
import com.example.exception.UserNotFoundException;
import com.example.service.UserMailService;
import com.example.service.UserMassMailService;
import com.example.serviceImpl.UserMailServiceImpl;
import com.example.serviceImpl.UserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("api/v1/mail")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMailServiceImpl mailServiceImpl;
	
	@Autowired
	private UserMassMailService userMassMailService;
	
	@PostMapping("/signUp")
	private ResponseEntity<Map<String, Object>> Signup(@RequestBody SignUpDto signUpDto) throws UserNotFoundException{
		try {
			this.userService.saveUser(signUpDto);
		} catch (Exception e) {
			throw new UserNotFoundException("Empty username or password or role");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("status", HttpStatus.CREATED.value());
		map.put("message", "SignUp successFull");
		return ResponseEntity.ok().body(map);
	}
	
	@PostMapping("SendMail")
	private ResponseEntity<Map<String, Object>> sendMail(@ModelAttribute MailSender mailSender) throws Exception{
		
		this.mailServiceImpl.sendingMails(mailSender);
		Map<String,Object> map=new HashMap<>();
		map.put("status", HttpStatus.OK.value());
		map.put("message", "Mail Sent Successfully");
		return ResponseEntity.ok().body(map);
	}
	
	@PostMapping("SendMailWOA")
	private ResponseEntity<Map<String, Object>> sendMailWithOutAttachments(@RequestBody SendMailWOADto woaDto) throws MessagingException, IOException{
		this.mailServiceImpl.sendMailWithOutAttachments(woaDto);
		Map<String, Object> map = new HashMap<>();
		map.put("status", HttpStatus.OK.value());
		map.put("message", "Mail Sent Successfully");
		return ResponseEntity.ok().body(map);
	}
	
    @PostMapping("/upload-excel")
    public ResponseEntity<Map<String,Object>> sendMails(@ModelAttribute UploadExcelDto excelDto) throws IOException, MessagingException {
       Map<String,Object> map= this.userMassMailService.massMails(excelDto);
        return ResponseEntity.ok().body(map);
    }
    
    @PostMapping("/save-mail-Credentials")
    public ResponseEntity<Map<String, Object>> saveMailCredentials(@RequestBody MailCredentials credentials)
    {
        Map<String, Object> map=this.mailServiceImpl.saveMailCredentials(credentials);
        return ResponseEntity.ok().body(map);
    }
    
    @GetMapping("/get-mail-credentials")
    public ResponseEntity<Map<String,Object>> getAllMailCredentials()
    {
        Map<String, Object> map=this.mailServiceImpl.getAllMailCredentials();
        return ResponseEntity.ok().body(map);
    }
    
    @DeleteMapping("/delete-folder/{folderName}")
    public ResponseEntity<Map<String,Object>> deleteFolder(@PathVariable String folderName) throws IOException {
        Map<String,Object> map=  this.userMassMailService.deleteFolder(folderName);
        return ResponseEntity.ok().body(map);
    }
    
    @GetMapping("/get-all-folders")
    public ResponseEntity<Map<String,Object>> getAllFolders()
    {
        Map<String,Object> map=  this.userMassMailService.getAllFolders();
        return ResponseEntity.ok().body(map);
    }

      @GetMapping("/get-all-files/{folderName}")
    public ResponseEntity<Map<String,Object>> getAllFiles(@PathVariable String folderName)
      {
          Map<String,Object> map=this.userMassMailService.getAllFiles(folderName);
          return ResponseEntity.ok().body(map);
      }


      @DeleteMapping("/delete-file/{folderName}/{fileName}")
          public ResponseEntity<Map<String,Object>> deleteFile(@PathVariable String folderName,@PathVariable String fileName)
      {
          Map<String,Object> map=this.userMassMailService.deleteFile(folderName,fileName);
          return ResponseEntity.ok().body(map);
      }

}

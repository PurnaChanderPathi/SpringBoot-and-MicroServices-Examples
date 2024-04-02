package com.broadcastMail.controller;

import com.broadcastMail.dto.PasswordDto;
import com.broadcastMail.entites.EmailForm;
import com.broadcastMail.entites.FolderEntity;
import com.broadcastMail.entites.MailCredentials;
import com.broadcastMail.entites.SignUp;
import com.broadcastMail.repository.FolderRepository;
import com.broadcastMail.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class MailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private FolderRepository folderRepository;
    @GetMapping("/")
    public String home() {
        return "login";
    }

    @PostMapping("/loginn")
    public String loginSubmit(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              Model model, HttpSession httpSession) {
        String result = mailService.login(email, password);
        if (result.startsWith("redirect:")) {
            model.addAttribute("errorMessage", "Login successfully");
            httpSession.setAttribute("username",email);
            return "redirect:/home";
        } else {
            model.addAttribute("errorMessage", "Invalid credentials");
            return "login";
        }
    }


    @GetMapping("/signUp")
    public String signUpp() {
        return "signUp";
    }

    @PostMapping("/signUpp")
    public String signUp(@ModelAttribute SignUp signUp) {
        this.mailService.signUp(signUp);
        return "redirect:/login";
    }

    @PostMapping("/emailForm")
    public String emailForm(HttpSession httpSession) {
        String email= (String) httpSession.getAttribute("username");
        boolean result= this.mailService.authenticateUser(email);
        if(result){
            return "mailForm";
        }
        else{
            return "redirect:/login";
        }
    }

    @GetMapping("/home")
    public String back(HttpSession httpSession)
    {
        String email= (String) httpSession.getAttribute("username");
        boolean result= this.mailService.authenticateUser(email);
        if(result){
            return "mailForm";
        }
        else{
            return "redirect:/login";
        }
    }



    @PostMapping("/formPost")
    public String formPost(@ModelAttribute EmailForm emailForm, @RequestParam(name = "to", required = false) MultipartFile to,
                           @RequestParam(name = "zip", required = false) MultipartFile zip, Model model) throws IOException {
       MailCredentials mailCredentials = mailService.getByMail(emailForm.getFrom());
        try {
            if (mailCredentials == null) {
                throw new RuntimeException("Email addresses do not match!");
            }
            this.mailService.uploadExcel(emailForm, to, zip, mailCredentials);
            model.addAttribute("response","/home");
            return "redirect:/home";
        } catch (Exception e) {
            return "Failed to send mail: " + e.getMessage();
        }

    }
    @PostMapping("/saveRegister")
    public String registerSave(@ModelAttribute MailCredentials mailCredentials)
    {
        this.mailService.registerSave(mailCredentials);
        return "redirect:/home";
    }
 @GetMapping("/delete-mail-credentials/{id}")
    public String deleteMailCredentials(@PathVariable long id)
    {
        this.mailService.deleteMailCredentials(id);
        return "redirect:/home";
    }
@GetMapping("/listUsers")
    public String listOfUsers(Model model,HttpSession httpSession) {

    String email= (String) httpSession.getAttribute("username");
    boolean result= this.mailService.authenticateUser(email);
    System.out.println(result);
    if(result){
        List<MailCredentials> all = mailService.getAll();
        model.addAttribute("lists", all);

        return "UserList";
    }
    else{
        return "redirect:/login";
    }
    }
    @GetMapping("/register")
    public String register(HttpSession httpSession) {

        String email= (String) httpSession.getAttribute("username");
        boolean result= this.mailService.authenticateUser(email);
        System.out.println(result);
        if(result){
            return "register";
        }
        else{
            return "redirect:/login";
        }
    }
    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }
    @GetMapping("/completed")
    public String thankYou() {
        return "success";
    }
    @GetMapping("/redirectLogin")
    public String redirectLogin() {
        return "redirect:/login";
    }
    @GetMapping("/get-all-folders")
    public String getAllFolders(Model model,HttpSession httpSession)
    {
        String email= (String) httpSession.getAttribute("username");
        boolean result= this.mailService.authenticateUser(email);
        System.out.println(result);
        if(result){
            List<FolderEntity> folderNames=this.mailService.getAllFolders();
            model.addAttribute("folderList",folderNames);
            return "deleteFolders";
        }
        else{
            return "redirect:/login";
        }

    }
    @GetMapping("/delete-folder")
            public String folder( Model model)
    {
        return "deleteFolders";
    }

    @GetMapping("/deleteFolder/{folderName}")
    public String deleteFolder(@PathVariable("folderName") String folderName, Model model) throws IOException {
        this.mailService.deleteFolder(folderName);
        return "redirect:/home";
    }

    @GetMapping("/deletePage")
    public String delPage(Model model)
    {
        return "delete";
    }


    @GetMapping("/get-all-files/{folderName}")
    public String getAllFiles(@PathVariable String folderName,Model model)
    {
       List<String> list= this.mailService.getAllFiles(folderName);
        model.addAttribute("folderList",list);
        List<FolderEntity> folderNames=this.mailService.getAllFolders();
        model.addAttribute("folderList2", folderName);
        return "delete";
    }

    @GetMapping("/delete-file/{folderName}/{fileName}")
    public String deleteFileInFolder(@PathVariable String folderName,@PathVariable String fileName)
    {
        this.mailService.deleteFile(folderName,fileName);
        return  "redirect:/get-all-folders";
    }
    @PostMapping("/delete-selected-files")
    public String deleteSelectedFiles(@RequestParam("folderName") String folderName, @RequestBody List<String> selectedFiles) {
        System.out.println(folderName);
        this.mailService.deleteSelectedFiles(folderName, selectedFiles);
        return "redirect:/get-all-folders";
    }


    @GetMapping("/deleteFolder")
    public String listOfFiles(Model model) {

        List<FolderEntity> fileData = mailService.getFileData();
        model.addAttribute("files", fileData);
        model.addAttribute(fileData);
        return  "redirect:/deletePage";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        FolderEntity byId = folderRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        folderRepository.delete(byId);
        return "redirect:/listUsers";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() throws MessagingException, IOException {
        this.mailService.forgotPassword();
        return "signUp";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute PasswordDto dto) throws MessagingException, IOException {
        this.mailService.changePassword(dto);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


}

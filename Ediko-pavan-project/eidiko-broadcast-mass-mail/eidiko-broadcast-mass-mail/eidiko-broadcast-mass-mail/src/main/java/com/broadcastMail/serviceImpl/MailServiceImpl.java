package com.broadcastMail.serviceImpl;

import com.broadcastMail.config.EmailConfig;
import com.broadcastMail.dto.PasswordDto;
import com.broadcastMail.entites.*;
import com.broadcastMail.repository.FolderRepository;
import com.broadcastMail.repository.MailCredentialsRepository;
import com.broadcastMail.repository.SignUpRepository;
import com.broadcastMail.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private EmailConfig emailConfig;
    @Autowired
    private SignUpRepository signUpRepository;
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private MailCredentialsRepository mailCredentialsRepository;
    @Autowired
    private FolderRepository folderRepository;

    @Value("${spring.files.storage}")
    public String folderLocation;

    @Value("${spring.mail.username}")
    public String mail;
    @Value("${spring.mail.password}")
    public String mailPassword;

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public String login(String email,String password) {
        SignUp storedLogin = signUpRepository.findByMailId(email);
        if (storedLogin != null && storedLogin.getPassword().equals(encodePassword(password))) {
            return "redirect:/mailForm";
        } else {
            return "Invalid credentials";
        }
    }

    @Override
    @Transactional
    public String signUp(SignUp signUp) {
        SignUp existingUser = signUpRepository.findByMailId(signUp.getMailId());
        if (existingUser != null) {

            return "User with this email is already registered";
        }
        try {
            signUpRepository.save(signUp);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred during sign-up";
        }
    }
 @Override
    public MailCredentials getByMail(String email)
    {
        MailCredentials credentials = this.mailCredentialsRepository.findByEmail(email);
        return credentials;
    }



    Map<String, Object> resultMap = new HashMap<>();
    @Override
    public void uploadExcel(EmailForm emailForm, MultipartFile to, MultipartFile zip, MailCredentials credentials) throws IOException {
        String extractedFileName = unzipFile(zip);
        FolderEntity folderEntity = new FolderEntity();
        folderEntity.setFolderName(extractedFileName);
        FolderEntity folder = this.folderRepository.findByFolderName(extractedFileName);
        if (folder == null) {
            this.folderRepository.save(folderEntity);
        }
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(to.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    List<String> entireRowCells = new ArrayList<>();
                    List<String> filenameAndEmpidCells = new ArrayList<>();

                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            String cellValue;
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        cellValue = cell.getDateCellValue().toString();
                                    } else {
                                        cellValue = String.valueOf((int) cell.getNumericCellValue());
                                    }
                                    break;
                                case STRING:
                                    cellValue = cell.getStringCellValue();
                                    break;
                                default:
                                    cellValue = ""; // Handle other cell types as needed
                                    break;
                            }
                            String columnName = getColumnName(sheet.getRow(0).getCell(j));
                            if (columnName.equalsIgnoreCase("emailId") || columnName.equalsIgnoreCase("filename")) {
                                filenameAndEmpidCells.add(cellValue);
                            }
                            entireRowCells.add(cellValue);
                        }
                    }

                    sendEmail(emailForm, filenameAndEmpidCells, entireRowCells, extractedFileName, credentials.getEmail(), credentials.getPassword());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveMailCredentials(String email, String password) {
        MailCredentials mailCredentials = new MailCredentials();
        mailCredentials.setEmail(email);
        mailCredentials.setPassword(password);
        this.mailCredentialsRepository.save(mailCredentials);
    }


    @Override
    public List<MailCredentials> getAll() {
        List<MailCredentials> list=  this.mailCredentialsRepository.findAll();
        return list;
    }

    @Override
    public void deleteFolder(String folderName) throws IOException {
        String fName=folderLocation+"/"+folderName;
        File folder = new File(fName);
        if (!folder.exists()) {
            throw new RuntimeException("File doesn't exists in your machine");
        }
        FileUtils.deleteDirectory(folder);
       FolderEntity folderName1=this.folderRepository.findByFolderName(folderName);
            folderRepository.delete(folderName1);


    }

    @Override
    public List<FolderEntity> getFileData() {

        return  this.folderRepository.findAll();
    }

    @Override
    public List<String> getAllFiles(String folderName) {
        List<String> fileList = new ArrayList<>();
        String folderPath = folderLocation+"/"+folderName;
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                fileList.add(file.getName());
            }
        }

        return fileList;
    }

    @Override
    public void deleteFile(String folderName,String fileName) {
        String filePath=folderLocation+"/"+folderName+"/"+fileName;
        Path path = Paths.get(filePath);

        try {
            Files.delete(path);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    @Override
    public List<FolderEntity> getAllFolders() {
  List<FolderEntity> folderName =this.folderRepository.findAll();
  return folderName;
    }

    @Override
    public void forgotPassword() throws MessagingException, IOException {
        SignUp signUp=this.signUpRepository.findByMailId(mail);
        String password = generateRandomPassword(6, 48, 122);
        if(signUp!=null)
        {
            signUp.setPassword(encodePassword(password));
        }
        this.signUpRepository.save(signUp);

//        String emailId="pawankarri55@gmail.com";
//        String emailPassword="fxwnacokcnahlbme";
        JavaMailSender javaMailSender = emailConfig.getJavaMailSender(mail,mailPassword);
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true);
        mimeMessageHelper.setTo("karriramakrishna.pavankumar@eidiko-india.com");
        Resource resource = resourceLoader.getResource("classpath:/templates/forgotPassword.html");
        String templateContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        String body=templateContent.replace("@[Password]",password);
        mimeMessageHelper.setText(body,true);
        mimeMessageHelper.setFrom(String.valueOf(mail));
        mimeMessageHelper.setSubject("Forgot-password");
        javaMailSender.send(mail);

    }

    @Override
    public void registerSave(MailCredentials mailCredentials) {
        this.mailCredentialsRepository.save(mailCredentials);
    }

    @Override
    public void deleteMailCredentials(long id) {
        MailCredentials mailCredentials=new MailCredentials();
        mailCredentials.setId(id);
        this.mailCredentialsRepository.delete(mailCredentials);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(PasswordDto dto) throws MessagingException, IOException {
       SignUp signUp=this.signUpRepository.findByMailId(mail);
      if(dto.getOldPassword().equals(decodePassword(signUp.getPassword())))
      {
          signUp.setPassword(encodePassword(dto.getNewPassword()));
          this.signUpRepository.save(signUp);
      }

    }

    @Override
    public void deleteSelectedFiles(String folderName, List<String> selectedFiles) {
        for (String fileName : selectedFiles) {
            String filePath = folderLocation + "/" + folderName + "/" + fileName;
            Path path = Paths.get(filePath);
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String generateRandomPassword(int len, int randNumOrigin, int randNumBound) {
        SecureRandom random = new SecureRandom();
        return random.ints(randNumOrigin, randNumBound + 1)
                .filter(i -> Character.isAlphabetic(i) || Character.isDigit(i)).limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }


    public void mailDataSave(MailCredentials mailCredentials) {

        this.mailCredentialsRepository.save(mailCredentials);

    }


    private String getColumnName(XSSFCell cell) {
        if (cell != null) {
            return cell.getStringCellValue().trim();
        }
        return "";
    }

    private void sendEmail(EmailForm emailForm, List<String> emailAndFilenameList, List<String> cellValueList,String extractedFileName,String emailId,String password) throws IOException {
        executorService.submit(() -> {
            try {
                if (emailAndFilenameList != null &&!emailAndFilenameList.isEmpty() ) {
                    JavaMailSender javaMailSender = emailConfig.getJavaMailSender(emailId,password);
                    MimeMessage mail = javaMailSender.createMimeMessage();
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true);
                    File file = searchFileInLocalDirectory(extractedFileName,emailAndFilenameList.get(1));
                    String body = emailForm.getBody();
                    for (int i = 0; i < cellValueList.size(); i++) {
                        String placeholder = "{" + i + "}";
                        body = body.replace(placeholder, cellValueList.get(i).toString());
                    }

                    String recipientEmail = emailAndFilenameList.get(0);
                    mimeMessageHelper.setTo(recipientEmail);
                    mimeMessageHelper.setCc(emailForm.getCc());
                    mimeMessageHelper.setText(body);
                    mimeMessageHelper.setFrom(emailId);
                    mimeMessageHelper.setSubject("[Broadcast] " + emailForm.getSubject());

                    if (file != null && file.exists()) {
                        FileSystemResource fileSystemResource = new FileSystemResource(file);
                        mimeMessageHelper.addAttachment(file.getName(), fileSystemResource);
                    }

                    javaMailSender.send(mail);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private String unzipFile(MultipartFile zipFile) throws IOException {
        File folder = new File(folderLocation);
        folder.mkdir();
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(zipFile.getInputStream());
        ZipEntry zipEntry = zis.getNextEntry();
        String folderName = null;
        while (zipEntry != null) {
            File newFile = new File(folder, zipEntry.getName());
            if (zipEntry.isDirectory()) {
                newFile.mkdirs();
                if (folderName == null) {
                    folderName = newFile.getName();
                }
            } else {
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();

        return folderName;
    }

    private File searchFileInLocalDirectory(String folderName,String fileName) {
        String directoryPath = folderLocation+"/"+folderName;
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.equals(fileName));
            if (files != null && files.length > 0) {
                return files[0];
            }
        }
        return null;
    }

    public static String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
    public static String decodePassword(String password){
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        return new String(decodedBytes);
    }


    public boolean authenticateUser(String username)
    {
       SignUp signUp= this.signUpRepository.findByMailId(username);
       if(signUp!=null)
       {
             return true;
       }
       else{
           return false;
       }
    }
}


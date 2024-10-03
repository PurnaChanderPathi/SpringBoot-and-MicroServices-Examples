package com.purna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailScheduler {


    private final EmailService emailService;

    @Autowired
    public EmailScheduler(EmailService emailService) {
        this.emailService = emailService;
    }

//    @Scheduled(cron = "0 0 7 * * ?")  // Runs at 7 AM every day
//    public void scheduleGmailFetch() {
//        emailService.fetchEmails();
//    }

    // Schedule to run every 5 minutes
    @Scheduled(cron = "0 0/5 * * * ?")
    public void scheduleGmailFetch() {
        emailService.fetchEmails();
    }

}

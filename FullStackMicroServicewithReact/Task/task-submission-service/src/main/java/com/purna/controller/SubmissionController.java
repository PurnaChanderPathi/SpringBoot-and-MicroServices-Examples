package com.purna.controller;

import com.purna.model.Submission;
import com.purna.model.UserDto;
import com.purna.service.SubmissionService;
import com.purna.service.TaskService;
import com.purna.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submission")
@CrossOrigin
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @PostMapping()
    public ResponseEntity<Submission>submitTask(
            @RequestParam Long task_id,
            @RequestParam String github_link,
            @RequestHeader ("Authorization") String jwt
    ) throws Exception{
        UserDto user=userService.getUserProfile(jwt);
        Submission submission = submissionService.submitTask(task_id,github_link,user.getId(),jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission>getSubmissionById(
            @PathVariable Long id,
            @RequestHeader ("Authorization") String jwt
    ) throws Exception{
        UserDto user=userService.getUserProfile(jwt);
        Submission submission = submissionService.getTaskSubmissionId(id);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Submission>> getAllSubmissions(
            @RequestHeader ("Authorization") String jwt
    ) throws Exception{
        UserDto user=userService.getUserProfile(jwt);
        List<Submission> submissions = submissionService.getAllTaskSubmissions();
        return new ResponseEntity<>(submissions, HttpStatus.CREATED);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getAllSubmissions(
            @PathVariable Long taskId,
            @RequestHeader ("Authorization") String jwt
    ) throws Exception{
        UserDto user=userService.getUserProfile(jwt);
        List<Submission> submissions = submissionService.getTaskSubmissionsByTaskId(taskId);
        return new ResponseEntity<>(submissions, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmission(
            @PathVariable Long id,
            @PathParam("status") String status,
            @RequestHeader ("Authorization") String jwt
    ) throws Exception{
        UserDto user=userService.getUserProfile(jwt);
        Submission submission = submissionService.acceptDeclineSubmission(id,status);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }
}

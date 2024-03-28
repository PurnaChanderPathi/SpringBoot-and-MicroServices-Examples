package com.purna.service;

import com.purna.model.Submission;
import com.purna.model.TaskDto;
import com.purna.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImplementation implements SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Override
    public Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception {
        TaskDto taskDto = taskService.getTaskById(taskId,jwt);
        if(taskDto!=null){
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithubLink(githubLink);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepository.save(submission);
        }
        throw new Exception("task not found with id: "+taskId);
    }

    @Override
    public Submission getTaskSubmissionId(Long submissionId) throws Exception {

        return submissionRepository.findById(submissionId).orElseThrow(
                ()-> new Exception("Task Submission not found with id "+submissionId)
        );
    }

    @Override
    public List<Submission> getAllTaskSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionsByTaskId(Long taskId) {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {
        Submission submission = getTaskSubmissionId(id);
        submission.setStatus(status);
        if(status.equals("ACCEPT")){
            taskService.completeTask(submission.getTaskId());
        }
        return submissionRepository.save(submission);
    }
}

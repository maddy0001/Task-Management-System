package com.nexus.task_submission_service.service;

import com.nexus.task_submission_service.model.Submission;

import java.util.List;

public interface SubmissionService {

    Submission submitTask(Long taskId, String githubLink, Long userId,String jwt)throws Exception;


    Submission getTaskSubmissionById(Long submissionId) throws Exception;

    List<Submission> gatAllTaskSubmissions();

    List<Submission> getTaskSubmissionsByTaskId(Long taskId);

    Submission acceptDeclineSubmission(Long id, String status)throws Exception;
}

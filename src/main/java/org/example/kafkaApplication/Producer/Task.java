package org.example.kafkaApplication.Producer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class Task  {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "taskId",
            "studentId",
            "subject",
            "dateOfSubmission",
    })

    @JsonProperty("taskId")
    private String taskId;

    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("dateOfSubmission")
    private String dateOfSubmission;

    public Task(String taskId, String studentId, String subject, String dateOfSubmission) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.subject = subject;
        this.dateOfSubmission = dateOfSubmission;
    }
    @JsonProperty("taskId")
    public String getTaskId() {
        return taskId;
    }

    @JsonProperty("taskId")
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    @JsonProperty("studentId")
    public String getStudentId() {
        return studentId;
    }
    @JsonProperty("studentId")
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }
    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }
    @JsonProperty("dateOfSubmission")
    public String getDateOfSubmission() {
        return dateOfSubmission;
    }

    @JsonProperty("dateOfSubmission")
    public void setDateOfSubmission(String dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

}

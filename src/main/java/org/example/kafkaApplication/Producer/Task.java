package org.example.kafkaApplication.Producer;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public Task(@JsonProperty("taskId") String taskId, @JsonProperty("studentId") String studentId, @JsonProperty("subject") String subject, @JsonProperty("dateOfSubmission") String dateOfSubmission ) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.subject = subject;
        this.dateOfSubmission = dateOfSubmission;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSubject() {
        return subject;
    }

    public String getDateOfSubmission() {
        return dateOfSubmission;
    }
}

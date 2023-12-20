package org.Producer;

public class Task {
    private String taskId;
    private String studentId;
    private String subject;
    private String dateOfSubmission;


    public Task(String taskId, String studentId, String subject, String dateOfSubmission) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.subject = subject;
        this.dateOfSubmission = dateOfSubmission;
    }

    public String toJson() {
        // Convert Task object to JSON format (You can use a library like Jackson or Gson)
        return String.format("{\"taskId\":\"%s\",\"studentId\":\"%s\",\"subject\":\"%s\",\"dateOfSubmission\":\"%s\"}",
                taskId, studentId, subject, dateOfSubmission);
    }
}
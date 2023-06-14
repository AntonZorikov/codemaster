package com.example.codemaster.model;

public class SearchCourseForm {
    private String requestTitle;

    public SearchCourseForm(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }
}

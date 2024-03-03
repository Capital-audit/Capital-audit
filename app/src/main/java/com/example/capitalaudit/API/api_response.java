package com.example.capitalaudit.API;

public class api_response {
    private final boolean success;
    private final String response;

    public api_response(boolean success, String response) {
        this.success = success;
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getResponse()
    {
        return response;
    }
}

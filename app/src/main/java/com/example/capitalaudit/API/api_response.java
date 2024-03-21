package com.example.capitalaudit.API;


/**
 * api_response class is used to store the response from the API.
 * It contains a boolean variable success which is true if the API call was successful and false if it was not.
 * It contains a String variable response which stores the response from the API.
 *
 */
public class api_response
{
    private final boolean success;
    private final String response;

    public api_response(boolean success, String response)
    {
        this.success = success;
        this.response = response;
    }

    /**
     * isSuccess method is used to check if the API call was successful.
     * @return boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * getResponse method is used to get the response from the API.
     * @return
     */
    public String getResponse()
    {
        return response;
    }
}

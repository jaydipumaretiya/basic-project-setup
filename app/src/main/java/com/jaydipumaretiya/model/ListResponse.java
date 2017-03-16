package com.jaydipumaretiya.model;

import java.util.ArrayList;

public class ListResponse<T> {

    private String StatusCode;

    private String UrlPath;

    private String StatusMessage;

    private String ResponseCode;

    private String TimeZone;

    private ArrayList<T> Responsedata;

    private String ResponseMessage;


    public ArrayList<T> Responsedata() {
        return Responsedata;
    }

    public String ResponseMessage() {
        return ResponseMessage;
    }

    public String StatusCode() {
        return StatusCode;
    }

    public String UrlPath() {
        return UrlPath;
    }

    public String StatusMessage() {
        return StatusMessage;
    }

    public String ResponseCode() {
        return ResponseCode;
    }

    public String TimeZone() {
        return TimeZone;
    }
}

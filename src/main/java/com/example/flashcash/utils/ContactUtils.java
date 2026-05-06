package com.example.flashcash.utils;

public class ContactUtils {

    public static String isEmailOrPhone(String recipient){
        if(recipient.contains("@")){
            return "email";
        }else{
            return "phone";
        }
    }
}

package com.abl.breachthebarrier.data;

public class UserData {

    private String userEmail;
    private static UserData instance;

    private UserData(){

    }

    public static UserData getInstance() {
        if(UserData.instance == null){
            UserData.instance = new UserData();
        }
        return instance;
    }

    public static String getUserEmail(){
        return instance.userEmail;
    }

    public static void setUserEmail(String userEmail){
        instance.userEmail = userEmail;
    }
}

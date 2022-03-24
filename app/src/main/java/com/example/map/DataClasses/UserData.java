package com.example.map.DataClasses;

public class UserData {
    String email,JWT_TOKEN;
    boolean socialLogin;

    public UserData(String email, String JWT_TOKEN, boolean socialLogin) {
        this.email = email;
        this.JWT_TOKEN = JWT_TOKEN;
        this.socialLogin = socialLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJWT_TOKEN() {
        return JWT_TOKEN;
    }

    public void setJWT_TOKEN(String JWT_TOKEN) {
        this.JWT_TOKEN = JWT_TOKEN;
    }

    public boolean isSocialLogin() {
        return socialLogin;
    }

    public void setSocialLogin(boolean socialLogin) {
        this.socialLogin = socialLogin;
    }
}

package com.models;

import java.io.Serializable;

public class Admin extends Role implements Serializable {

    public Admin(AdministratorBuilder builder){
        super(builder.username, builder.password, "Administrator");
    }
    public static class AdministratorBuilder{
        private String username;
        private String password;
        public AdministratorBuilder setUsername(String username){
            this.username = username;
            return this;
        }
        public AdministratorBuilder setPassword(String password){
            this.password = password;
            return this;
        }
        public Admin build(){
            return new Admin(this);
        }
    }
}

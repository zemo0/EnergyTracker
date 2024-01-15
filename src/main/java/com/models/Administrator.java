package com.models;

public class Administrator extends Racun{

    public Administrator(AdministratorBuilder builder){
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
        public Administrator build(){
            return new Administrator(this);
        }
    }
}

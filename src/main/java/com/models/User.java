package com.models;

import java.io.Serializable;

public class User extends Role implements Serializable {

    private User(KorisnikBuilder builder){
        super(builder.username, builder.password, "Korisnik");
    }

    public static class KorisnikBuilder{
        private String username;
        private String password;
        public KorisnikBuilder setUsername(String username){
            this.username = username;
            return this;
        }
        public KorisnikBuilder setPassword(String password){
            this.password = password;
            return this;
        }
        public User build(){
            return new User(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

package com.models;

import java.io.Serializable;

public class Korisnik extends Racun implements Serializable {

    private Korisnik(KorisnikBuilder builder){
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
        public Korisnik build(){
            return new Korisnik(this);
        }
    }
}

package com.models;

public class Korisnik extends Racun{

    private Korisnik(KorisnikBuilder builder){
        super(builder.username, builder.password);
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

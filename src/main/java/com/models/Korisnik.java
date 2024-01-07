package com.models;

public class Korisnik {
    private String username;
    private String password;

    private Korisnik(KorisnikBuilder builder){
        this.username = builder.username;
        this.password = builder.password;
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

    @Override
    public String toString() {
        return "Username='" + username + '\'' + ", ";
    }
}

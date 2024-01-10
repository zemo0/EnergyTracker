package com.utils;

import com.models.Korisnik;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUtils {
    public static Set<Korisnik> dohvatPodatakaOKorisnicima(){
        String filePath = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\loginInfo.txt";
        Set<Korisnik> korisnici = new HashSet<>();
        String username, password;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            long numberOfLines = Files.lines(Paths.get(filePath)).count();
            for(int i = 0; i < numberOfLines/2; i++){
                username = reader.readLine();
                password = reader.readLine();
                Korisnik korisnik = new Korisnik.KorisnikBuilder().setUsername(username).setPassword(password).build();
                korisnici.add(korisnik);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return korisnici;
    }
}

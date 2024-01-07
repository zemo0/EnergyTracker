package com.utils;

import com.models.Korisnik;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<Korisnik> dohvatPodatakaOKorisnicima(){
        String filePath = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\loginInfo.txt";
        List<Korisnik> korisnici = new ArrayList<>();
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

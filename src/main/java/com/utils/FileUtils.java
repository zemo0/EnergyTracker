package com.utils;

import com.models.Administrator;
import com.models.Korisnik;
import com.models.Racun;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {
    public static Set<Racun> dohvatPodatakaORacunima(){
        String filePath = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\loginInfo.txt";
        Set<Racun> racuni = new HashSet<>();
        String username, password, role;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            long numberOfLines = Files.lines(Paths.get(filePath)).count();
            for(int i = 0; i < numberOfLines/3; i++){
                role = reader.readLine();
                username = reader.readLine();
                password = reader.readLine();
                if(role.equals("Korisnik")) {
                    Korisnik korisnik = new Korisnik.KorisnikBuilder().setUsername(username).setPassword(password).build();
                    racuni.add(korisnik);
                } else {
                    Administrator administrator = new Administrator.AdministratorBuilder().setUsername(username)
                            .setPassword(password).build();
                    racuni.add(administrator);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return racuni;
    }
}

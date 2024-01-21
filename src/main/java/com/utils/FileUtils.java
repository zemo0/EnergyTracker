package com.utils;

import com.models.Administrator;
import com.models.Korisnik;
import com.models.Racun;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUtils {
    public static final String serializationFileName = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\racuni.dat";
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
                password = unhashPassword(password);
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

    public static String hashPassword(String password){
        StringBuilder hashed = new StringBuilder();
        for (char c : password.toCharArray()) {
            hashed.append((char) (c + 1));  // Simple Caesar cipher shift by 1
        }
        return hashed.toString();
    }

    public static String unhashPassword(String password){
        StringBuilder hashed = new StringBuilder();
        for (char c : password.toCharArray()) {
            hashed.append((char) (c - 1));  // Simple Caesar cipher shift by 1
        }
        return hashed.toString();
    }

    public static void serializeRacun(Racun racun){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializationFileName))){
            out.writeObject(racun);
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
    public static List<Racun> deserializeRacun(){
        List<Racun> racuns = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializationFileName))) {
            while (true) {
                Racun racun = (Racun) in.readObject();
                racuns.add(racun);
            }
        } catch (EOFException e) {
            // End of file reached, do nothing
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(); // or log the error
        }

        return racuns;
    }
}

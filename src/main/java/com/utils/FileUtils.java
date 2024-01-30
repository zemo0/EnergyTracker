package com.utils;

import com.javafxFiles.CreateNewAccountScreenController;
import com.models.Admin;
import com.models.User;
import com.models.Role;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static com.mainPackage.Main.logger;

public class FileUtils {
    public static final String serializationFileName = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\racuni.dat";
    public static Set<Role> dohvatPodatakaORacunima(){
        String filePath = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\loginInfo.txt";
        Set<Role> racuni = new HashSet<>();
        String username, password, role;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            long numberOfLines = Files.lines(Paths.get(filePath)).count();
            for(int i = 0; i < numberOfLines/3; i++){
                role = reader.readLine();
                username = reader.readLine();
                password = reader.readLine();
                password = unhashPassword(password);
                if(role.equals("Korisnik")) {
                    User user = new User.KorisnikBuilder().setUsername(username).setPassword(password).build();
                    racuni.add(user);
                } else {
                    Admin admin = new Admin.AdministratorBuilder().setUsername(username)
                            .setPassword(password).build();
                    racuni.add(admin);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
            logger.info("IOException kod čitanje datoteke o userima");
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
    public static void serializeRacune(Set<Role> racuni){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializationFileName))){
            for(Role role : racuni) {
                out.writeObject(role);
            }
        } catch(IOException ex){
            ex.printStackTrace();
            logger.info("IOException kod serijaliziranja novih kreiranih računa");
        }
    }
    public static Set<Role> deserializeRacune(){
        Set<Role> roles = new HashSet<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializationFileName))) {
            for(int i = 0; i < CreateNewAccountScreenController.changeCounter; i++) {
                Role role = (Role) in.readObject();
                roles.add(role);
            }
        } catch (EOFException e) {
            // End of file reached, do nothing
            logger.info("EOFException jer je kod deserijalizacije filea kod došao do kraja filea");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(); // or log the error
            logger.info("Kod deserijalizacije je došlo do greške pri čitanju ili nije nađena prava klasa");
        }

        return roles;
    }
}

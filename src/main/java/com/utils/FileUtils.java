package com.utils;

import com.Exceptions.FileNotCorrectException;
import com.Serialization.ApplianceSerialization;
import com.Serialization.CategorySerialization;
import com.javafxFiles.CreateNewAccountController;
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
    public static final String serializeRolesFileName = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\racuni.dat";
    public static final String serializeCategoriesFileName = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\kategorije.dat";
    public static final String serializeAppliancesFileName = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\uredaji.dat";
    public static Set<Role> dohvatPodatakaORacunima() throws FileNotCorrectException {
        String filePath = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\loginInfo.txt";
        Set<Role> racuni = new HashSet<>();
        String username, password, role;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            long numberOfLines = Files.lines(Paths.get(filePath)).count();
            if(numberOfLines % 3 != 0){ throw new FileNotCorrectException("Datoteka nije ispravna");}
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
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializeRolesFileName))){
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
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializeRolesFileName))) {
            for(int i = 0; i < CreateNewAccountController.changeCounter; i++) {
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
    public static void serializeCategories(CategorySerialization categorySerialization){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializeCategoriesFileName))){
            out.writeObject(categorySerialization);
        } catch(IOException ex){
            ex.printStackTrace();
            logger.info("Greška kod serijaliziranja kategorija");
        }
    }
    public static CategorySerialization deserializeCategories(){
        CategorySerialization categorySerialized = new CategorySerialization();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializeCategoriesFileName))) {
            categorySerialized = (CategorySerialization) in.readObject();
        } catch (EOFException e) {
            // End of file reached, do nothing
            logger.info("EOFException jer je kod deserijalizacije filea kod došao do kraja filea");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(); // or log the error
            logger.info("Kod deserijalizacije je došlo do greške pri čitanju ili nije nađena prava klasa");
        }
        return categorySerialized;
    }
    public static void serializeAppliances(ApplianceSerialization applianceSerialization){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializeAppliancesFileName))){
            out.writeObject(applianceSerialization);
        } catch(IOException ex){
            ex.printStackTrace();
            logger.info("Greška kod serijaliziranja aparata");
        }
    }
    public static ApplianceSerialization deserializeAppliances(){
        ApplianceSerialization applianceSerialized = new ApplianceSerialization();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializeAppliancesFileName))) {
            applianceSerialized = (ApplianceSerialization) in.readObject();
        } catch (EOFException e) {
            // End of file reached, do nothing
            logger.info("EOFException jer je kod deserijalizacije filea kod došao do kraja filea");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(); // or log the error
            logger.info("Kod deserijalizacije je došlo do greške pri čitanju ili nije nađena prava klasa");
        }
        return applianceSerialized;
    }
}

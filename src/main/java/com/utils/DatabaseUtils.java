package com.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mainPackage.Main;
import com.models.Appliance;
import com.models.Category;

import static com.mainPackage.Main.logger;

public class DatabaseUtils {
    private static final String DATABASE_PROPERTIES = "properties/database.properties";
    private static Connection connectToDatabase() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(DATABASE_PROPERTIES));
        String databaseUrl = properties.getProperty("databaseUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Connection connection = DriverManager.getConnection(databaseUrl, username, password);
        return connection;
    }
    public static List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<>();
        try(Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM CATEGORY";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                Long id = rs.getLong("ID");
                String name = rs.getString("NAME");
                String description = rs.getString("DESCRIPTION");
                Category category = new Category.CategoryBuilder().id(id).name(name).description(description).build();
                categories.add(category);
            }
        } catch (SQLException | IOException ex){
            ex.printStackTrace();
        }

        return categories;
    }
    public static List<Appliance> getAllAppliances(){
        List<Appliance> appliances = new ArrayList<>();
        try(Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM APPLIANCE";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Long id = rs.getLong("ID");
                Long categoryId = rs.getLong("CATEGORY_ID");
                Double appliancePowerUse = rs.getDouble("APPLIANCE_POWER_USE");
                Double dailyUseTime = rs.getDouble("DAILY_USE_TIME");
                Boolean tariff = rs.getBoolean("TARIFF");
                Double dailyConsumption = rs.getDouble("DAILY_CONSUMPTION");
                Appliance appliance = new Appliance.ApplianceBuilder().id(id).categoryId(categoryId-1)
                        .appliancePowerUse(appliancePowerUse).dailyUseTime(dailyUseTime)
                        .tarrif(tariff).dailyConsumption(dailyConsumption).build();
                appliances.add(appliance);
            }
        } catch (SQLException | IOException ex){
            logger.info("Greška pri dohvaćanju svih uređaja iz baze podataka");
            ex.printStackTrace();
        }
        return appliances;
    }
    public static void insertNewCategory(Category category){
        try(Connection connection = connectToDatabase()){
            String sqlQuery = "INSERT INTO CATEGORY (NAME, DESCRIPTION) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException | IOException ex){
            logger.error("Greška pri unosu nove kategorije u bazu podataka");
            ex.printStackTrace();
        }
    }
    public static void insertNewAppliance(Appliance appliance){
        try(Connection connection = connectToDatabase()){
            String sqlQuery = "INSERT INTO APPLIANCE (CATEGORY_ID, APPLIANCE_POWER_USE, DAILY_USE_TIME, TARIFF, DAILY_CONSUMPTION) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1, Main.getCategoryId(appliance.getApplianceCategory()));
            pstmt.setDouble(2, appliance.getAppliancePowerUse());
            pstmt.setDouble(3, appliance.getDailyUseTime());
            pstmt.setBoolean(4, appliance.getTariff());
            pstmt.setDouble(5, appliance.getDailyConsumption());
            pstmt.executeUpdate();
        } catch (SQLException | IOException ex){
            logger.error("Greška pri unosu novog uređaja u bazu podataka");
            ex.printStackTrace();
        }
    }
    public static void updateCategory(Category category, Long id) {
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "UPDATE CATEGORY SET NAME = ?, DESCRIPTION = ? WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getDescription());
            pstmt.setLong(3, id);
            pstmt.executeUpdate();
        } catch (SQLException | IOException ex) {
            logger.error("Greška pri ažuriranju kategorije u bazi podataka");
            ex.printStackTrace();
        }
    }
    public static void deleteCategory(Category category) {
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "DELETE FROM CATEGORY WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1, category.getId());
            pstmt.executeUpdate();
        } catch (SQLException | IOException ex) {
            logger.error("Greška pri brisanju kategorije iz baze podataka");
            ex.printStackTrace();
        }
    }

}

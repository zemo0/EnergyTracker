package com.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.Exceptions.DatabaseConnectionException;
import com.mainPackage.Main;
import com.models.Appliance;
import com.models.Category;
import com.models.Months;

import static com.javafxFiles.LoginController.currentUser;
import static com.mainPackage.Main.logger;

public class DatabaseUtils {
    private static final String DATABASE_PROPERTIES = "properties/database.properties";
    private static Connection connectToDatabase() throws SQLException, IOException, DatabaseConnectionException {
        Properties properties = new Properties();
        properties.load(new FileReader(DATABASE_PROPERTIES));
        String databaseUrl = properties.getProperty("databaseUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        try {
            return DriverManager.getConnection(databaseUrl, username, password);
        } catch (SQLException ex) {
            throw new DatabaseConnectionException("Greška pri spajanju na bazu podataka", ex);
        }
    }
    private static Connection closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
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
        } catch (SQLException | IOException | DatabaseConnectionException ex){
            logger.error("Greška pri spajanju na bazu podataka");
            ex.printStackTrace();
        }

        return categories;
    }
    public static List<Appliance> getAllAppliances(){
        List<Appliance> appliances = new ArrayList<>();
        try(Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM APPLIANCE WHERE USERNAME = '" + currentUser.getUsername() + "'";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Long id = rs.getLong("ID");
                long categoryId = rs.getLong("CATEGORY_ID");
                String username = rs.getString("USERNAME");
                Months month = Months.valueOf(rs.getString("MONTH_OF_USE"));
                Double appliancePowerUse = rs.getDouble("APPLIANCE_POWER_USE");
                Double dailyUseTime = rs.getDouble("DAILY_USE_TIME");
                Boolean tariff = rs.getBoolean("TARIFF");
                Double dailyConsumption = rs.getDouble("DAILY_CONSUMPTION");
                Double totalCostOfAppliance = rs.getDouble("TOTAL_COST_OF_APPLIANCE");
                Appliance appliance = new Appliance.ApplianceBuilder().id(id).categoryId(categoryId).username(username).month(month)
                        .appliancePowerUse(appliancePowerUse).dailyUseTime(dailyUseTime)
                        .tariff(tariff).dailyConsumption(dailyConsumption).totalCostOfAppliance(totalCostOfAppliance)
                        .build();
                appliances.add(appliance);
            }
        } catch (SQLException | IOException | DatabaseConnectionException ex){
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
        } catch (SQLException | IOException | DatabaseConnectionException ex){
            logger.error("Greška pri unosu nove kategorije u bazu podataka");
            ex.printStackTrace();
        }
    }
    public static void insertNewAppliance(Appliance appliance){
        try(Connection connection = connectToDatabase()){
            String sqlQuery = "INSERT INTO APPLIANCE (CATEGORY_ID, USERNAME, MONTH_OF_USE, APPLIANCE_POWER_USE, DAILY_USE_TIME, TARIFF, DAILY_CONSUMPTION, TOTAL_COST_OF_APPLIANCE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1, Main.getCategoryId(appliance.getApplianceCategory()));
            pstmt.setString(2, appliance.getUsername());
            pstmt.setString(3, appliance.getMonth());
            pstmt.setDouble(4, appliance.getAppliancePowerUse());
            pstmt.setDouble(5, appliance.getDailyUseTime());
            pstmt.setBoolean(6, appliance.getTariff());
            pstmt.setDouble(7, appliance.getDailyConsumption());
            pstmt.setDouble(8, appliance.getTotalCostOfAppliance());
            pstmt.executeUpdate();
        } catch (SQLException | IOException | DatabaseConnectionException ex){
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
        } catch (SQLException | IOException | DatabaseConnectionException ex) {
            logger.error("Greška pri ažuriranju kategorije u bazi podataka");
            ex.printStackTrace();
        }
    }
    public static void updateAppliance(Appliance appliance, Long id){
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "UPDATE APPLIANCE SET CATEGORY_ID = ?, USERNAME = ?, MONTH_OF_USE = ?, " +
                    "APPLIANCE_POWER_USE = ?, DAILY_USE_TIME = ?, TARIFF = ?, DAILY_CONSUMPTION = ?, TOTAL_COST_OF_APPLIANCE = ?" +
                    " WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1, appliance.getApplianceCategory().getId());
            pstmt.setString(2, appliance.getUsername());
            pstmt.setString(3, appliance.getMonth());
            pstmt.setDouble(4, appliance.getAppliancePowerUse());
            pstmt.setDouble(5, appliance.getDailyUseTime());
            pstmt.setBoolean(6, appliance.getTariff());
            pstmt.setDouble(7, appliance.getDailyConsumption());
            pstmt.setDouble(8, appliance.getTotalCostOfAppliance());
            pstmt.setLong(9, id);
            pstmt.executeUpdate();
        } catch (SQLException | IOException | DatabaseConnectionException ex) {
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
        } catch (SQLException | IOException | DatabaseConnectionException ex) {
            logger.error("Greška pri brisanju kategorije iz baze podataka");
            ex.printStackTrace();
        }
    }
    public static void deleteAppliance(Appliance appliance) {
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "DELETE FROM APPLIANCE WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1, appliance.getId());
            pstmt.executeUpdate();
        } catch (SQLException | IOException | DatabaseConnectionException ex) {
            logger.error("Greška pri brisanju uređaja iz baze podataka");
            ex.printStackTrace();
        }
    }
    public static List<Appliance> getAppliancesByMonth(String month){
        List<Appliance> appliances = new ArrayList<>();
        try (Connection connection = connectToDatabase()) {
            String sqlQuery = "SELECT * FROM APPLIANCE WHERE MONTH_OF_USE = '" + month + "' AND USERNAME = '" + currentUser.getUsername() + "'";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Long id = rs.getLong("ID");
                long categoryId = rs.getLong("CATEGORY_ID");
                String username = rs.getString("USERNAME");
                Double appliancePowerUse = rs.getDouble("APPLIANCE_POWER_USE");
                Double dailyUseTime = rs.getDouble("DAILY_USE_TIME");
                Boolean tariff = rs.getBoolean("TARIFF");
                Double dailyConsumption = rs.getDouble("DAILY_CONSUMPTION");
                Double totalCostOfAppliance = rs.getDouble("TOTAL_COST_OF_APPLIANCE");
                Appliance appliance = new Appliance.ApplianceBuilder().id(id).categoryId(categoryId).username(username)
                        .month(Months.valueOf(month)).appliancePowerUse(appliancePowerUse).dailyUseTime(dailyUseTime)
                        .tariff(tariff).dailyConsumption(dailyConsumption).totalCostOfAppliance(totalCostOfAppliance)
                        .build();
                appliances.add(appliance);
            }
        } catch (SQLException | IOException | DatabaseConnectionException ex) {
            logger.error("Greška pri brisanju uređaja iz baze podataka");
            ex.printStackTrace();
        }
        return appliances;
    }

}

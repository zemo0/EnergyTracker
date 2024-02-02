package com.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.models.Category;

public class DatabaseUtils {
    private static final String DATABASE_PROPERTIES = "properties/database.properties";
    private static Connection connectToDatabase() throws SQLException, IOException {
        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_PROPERTIES));
        String urlBazePodataka = svojstva.getProperty("databaseUrl");
        String korisnickoIme = svojstva.getProperty("username");
        String lozinka = svojstva.getProperty("password");
        Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);
        return veza;
    }
    public static List<Category> dohvatiSveCategory(){
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
}

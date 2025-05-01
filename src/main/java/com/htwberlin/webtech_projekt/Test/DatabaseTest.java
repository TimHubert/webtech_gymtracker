package com.htwberlin.webtech_projekt.Test;

import com.htwberlin.webtech_projekt.config.DatabaseConfig;

import java.sql.Connection;

public class DatabaseTest {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            if (connection != null) {
                System.out.println("Verbindung erfolgreich");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

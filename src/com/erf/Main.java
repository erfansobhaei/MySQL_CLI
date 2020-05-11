package com.erf;

import com.mysql.cj.log.Log;
import com.mysql.cj.result.Row;
import dnl.utils.text.table.TextTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static String classPath = "com.mysql.cj.jdbc.Driver";
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Type your SQL commands for execute...finish commands with semi-colon and write exit for terminate program:");
        while (true) {
            try {
                Class.forName(classPath);
                String query = getInputQuery();
                if (query.equals("exit")) {
                    break;
                }
                Connection connection = DriverManager.getConnection(Config.getAddress(), Config.getUsername(), Config.getPassword());
                executeQuery(connection, query);
                connection.close();
            } catch (Exception e) {
                System.err.println("#ERROR: " + e.getMessage());
            }
        }

    }

    private static void executeQuery(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        if (query.toUpperCase().contains("CREATE TABLE")){
            statement.executeUpdate(query);
            System.out.println("Creating table in database...");
        }
        if (query.toUpperCase().contains("DROP TABLE")){
            statement.executeUpdate(query);
            System.out.println("Deleting table in database...");
        }
        if (query.toUpperCase().contains("INSERT")){
            statement.execute(query);
            System.out.println("Inserted records into database...");
        }
        if (query.toUpperCase().contains("SELECT")){
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            printTable(resultSet, metaData);
        }
        if (query.toUpperCase().contains("UPDATE")){
            statement.executeUpdate(query);
            System.out.println("Updating records...");
        }
        if (query.toUpperCase().contains("DELETE")){
            statement.executeUpdate(query);
            System.out.println("Deleting records...");
        }if (query.toUpperCase().contains("ALTER")){
            statement.executeUpdate(query);
            System.out.println("Changing table...");
        }if (query.toUpperCase().contains("SHOW TABLES")){
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            printTable(resultSet, metaData);
        }else {
            statement.execute(query);
        }
    }

    public static String getInputQuery() {
        System.out.print("->");
        StringBuilder query = new StringBuilder(scanner.nextLine());
        while (query.charAt(query.length() - 1) != ';') {
            System.out.print("-->");
            query.append(" ").append(scanner.nextLine());
        }
        return query.toString();
    }

    public static void printTable(ResultSet resultSet, ResultSetMetaData metaData) throws SQLException {
        int numOfColumns = metaData.getColumnCount();
        int numOfRows = 0;
        String[] columnNames = new String[numOfColumns];
        System.out.println(Line(numOfColumns * 28));
        for (int i = 1; i <= numOfColumns; i++) {
            System.out.format("|  %-24s|", metaData.getColumnName(i));
        }
        System.out.println();
        System.out.println(Line(numOfColumns * 28));
        while (resultSet.next()) {
            Object[] row = new Object[numOfColumns];
            for (int i = 1; i <= numOfColumns; i++) {
                System.out.format("|  %-24s|", resultSet.getString(i));
            }
            System.out.println();
            numOfRows = resultSet.getRow();
        }
        System.out.println(Line(numOfColumns * 28));
        System.out.println(numOfRows + " rows in set");
        System.out.println();
    }

    static String Line(int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i%28 == 0 || i%28 == 27){
                result.append("+");
                continue;
            }
            result.append("-");
        }
        return result.toString();
    }
}

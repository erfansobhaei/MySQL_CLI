package com.erf;

import com.mysql.cj.result.Row;
import dnl.utils.text.table.TextTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static String classPath = "com.mysql.cj.jdbc.Driver";
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            Class.forName(classPath);
            while (true) {
                System.out.println("Choose your option...\n1) Execute input query\n2) SELECT query\n3) INSERT query\n" +
                        "4) UPDATE query\n5) DELETE query\n6)Esc");
                int userOption = scanner.nextInt();
                Connection connection = DriverManager.getConnection(Config.getAddress(), Config.getUsername(), Config.getPassword());
                Statement statement = connection.createStatement();
                String query;
                ResultSet resultSet;
                ResultSetMetaData metaData;
                switch (userOption) {
                    case 1:
                        System.out.println("Enter your query in one line:");
                        query = new Scanner(System.in).nextLine();
                        resultSet = statement.executeQuery(query);
                        metaData = resultSet.getMetaData();
                        printTable(resultSet, metaData);
                        break;

                }

                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void printTable(ResultSet resultSet, ResultSetMetaData metaData) throws SQLException {
        int numOfColumns = metaData.getColumnCount();
        int numOfRows = 0;
        String[] columnNames = new String[numOfColumns];
        System.out.println(Line(numOfColumns*28));
        for (int i = 1; i <= numOfColumns; i++) {
            System.out.format("|  %-24s|", metaData.getColumnName(i));
        }
        System.out.println();
        System.out.println(Line(numOfColumns*28));
        while (resultSet.next()) {
            Object[] row = new Object[numOfColumns];
            for (int i = 1; i <= numOfColumns; i++) {
                System.out.format("|  %-24s|", resultSet.getString(i));
            }
            System.out.println();
            numOfRows = resultSet.getRow();
        }
        System.out.println(Line(numOfColumns*28));
        System.out.println(numOfRows + " rows in set");
        System.out.println();
    }

    static String Line(int n) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += "_";
        }
        return result;
    }
}

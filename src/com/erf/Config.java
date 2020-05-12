package com.erf;

public class Config {
    private static String tableName = "university";
    private static String address = "jdbc:mysql://localhost:3306/" + tableName;
    private static final String username = "root";
    private static final String password = "password";

    public static String getAddress() {
        return address;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setTableName(String newTableName){
        tableName = newTableName;
    }
}

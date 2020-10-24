package com.cs330.pz_katarina_stojkovic;

public class GlobalUser {

    private static String idUser;
    private static String idRole;
    private static String token;
    private static String username;


    public static String getIdUser() {
        return idUser;
    }

    public static void setIdUser(String idUser) {
        GlobalUser.idUser = idUser;
    }

    public static String getIdRole() {
        return idRole;
    }

    public static void setIdRole(String idRole) {
        GlobalUser.idRole = idRole;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        GlobalUser.token = token;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        GlobalUser.username = username;
    }
}

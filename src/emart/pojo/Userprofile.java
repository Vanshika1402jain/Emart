/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.pojo;

/**
 *
 * @author Jain's
 */
public class Userprofile {

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Userprofile.username = username;
    }

    public static String getUsertype() {
        return usertype;
    }

    public static void setUsertype(String usertype) {
        Userprofile.usertype = usertype;
    }

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        Userprofile.userid = userid;
    }
    private static String username;
    private static String usertype;
    private static String userid;
}

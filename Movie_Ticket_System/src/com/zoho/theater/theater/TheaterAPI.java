package com.zoho.theater.theater;

import com.zoho.theater.InvalidException;
import com.zoho.theater.InvalidException;
import com.zoho.theater.connection.ConnectionUtil;

import java.sql.ResultSet;
import java.util.Scanner;
public class TheaterAPI {
    static Scanner sc = new Scanner(System.in);
    public static boolean checkTheater(int theaterID) throws Exception {
        String query = "Select TheaterID from Theater where TheaterID = '"+theaterID+"'";
        ResultSet rs = ConnectionUtil.selectQuery(query);
        if(rs.next()){
            return true;
        }
        else{
            return false;
        }
    }
    public static int getTheaterID(String theaterName,String location) throws Exception{
        String query = "Select TheaterID from Theater where TheaterName = '"+theaterName+"' and location = '"+location+"'";
        ResultSet rs = ConnectionUtil.selectQuery(query);
        if(rs.next()){
            return (rs.getInt(1));
        }
        else{
            return -1;
        }
    }
    public static void getTheaterDetails() throws Exception{
        String q = "Select TheaterID,TheaterName,location from Theater";;
        ResultSet r = ConnectionUtil.selectQuery(q);
        System.out.println("ID    TheaterName      Location");
        System.out.println("-------------------------------");
        while (r.next()){
            System.out.print(r.getInt(1)+"       ");
            System.out.print(r.getString(2)+"           ");
            System.out.println(r.getString(3));
        }

    }
    private static void addTheaterEntry(String t_name,String location) throws Exception{
        String query = "insert into Theater(TheaterName,Location) values('"+t_name+"','"+location+"')";
        ConnectionUtil.insertQuery(query);
    }
    public static void addTheaters() throws Exception, com.zoho.theater.InvalidException {
        System.out.println("Theater Name: ");
        String theaterName = sc.next();
        System.out.println("Theater Location ");
        String location = sc.next();
        int thr_id = getTheaterID(theaterName,location);
        if(thr_id!=-1){
            throw new com.zoho.theater.InvalidException("Theater already exists with this location");
        }
        addTheaterEntry(theaterName,location);
        System.out.println("Theater "+theaterName+" added successfully");
    }

    public static void main(String[] args) throws InvalidException, Exception {
        addTheaters();
    }

    public static void showScreensInTheater(int theaterID) throws Exception {
        String q = "Select ScreenNumber from Screen where TheaterID = "+theaterID+";";;
        ResultSet r = ConnectionUtil.selectQuery(q);
        System.out.print("Available Screens: ");
        while (r.next()) {
            System.out.print(r.getInt(1) + "  ");
        }
        System.out.println();

    }
}

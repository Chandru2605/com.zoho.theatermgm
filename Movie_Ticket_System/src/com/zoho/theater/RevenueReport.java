package com.zoho.theater;

import com.zoho.theater.connection.ConnectionUtil;
import com.zoho.theater.theater.TheaterAPI;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RevenueReport {
    static Scanner sc=  new Scanner(System.in);
    public static void revenueReportByDate(int theaterID,long from,long to) throws Exception {
        String q = "SELECT T.Theatername,T.location,FROM_UNIXTIME(B.Date / 1000, '%Y-%m-%d') AS BookingDate,SUM(CASE WHEN B.`Option` = 'Book' THEN B.noOfSeats ELSE 0 END) AS NoOfSeatsBooked,SUM(CASE WHEN B.`Option` = 'Book' THEN B.amount ELSE 0 END) AS Revenue,SUM(CASE WHEN B.`Option` = 'Cancel' THEN B.noOfSeats ELSE 0 END) AS NoOfSeatsCancelled,SUM(CASE WHEN B.`Option` = 'Cancel' THEN B.amount ELSE 0 END) AS Refund,SUM(CASE WHEN B.`Option` = 'Book' THEN B.amount ELSE 0 END) - SUM(CASE WHEN B.`Option` = 'Cancel' THEN B.amount ELSE 0 END) as Profit FROM Booking B INNER JOIN `Show` SH ON B.showId = SH.showId INNER JOIN Screen SC ON SH.screenId = SC.screenId INNER JOIN Theater T ON SC.theaterId = T.theaterId WHERE T.theaterId = "+theaterID+" AND B.Date BETWEEN "+from+" AND "+to+" GROUP BY FROM_UNIXTIME(B.Date / 1000, '%Y-%m-%d') order by FROM_UNIXTIME(B.Date / 1000, '%Y-%m-%d');";
        ResultSet r = ConnectionUtil.selectQuery(q);
        System.out.println("Theater  Location     Date       Booked   Cancelled    Revenue    Refund    Profit");
        System.out.println("----------------------------------------------------------------------------------");
        while (r.next()){
            System.out.print(r.getString(1)+"      ");
            System.out.print(r.getString(2)+"  ");
            System.out.print(r.getString(3)+"     ");
            System.out.print(r.getInt(4)+"         ");
            System.out.print(r.getInt(6)+"          ");
            System.out.print(r.getInt(5)+"           ");
            System.out.print(r.getInt(7)+"       ");
            System.out.println(r.getInt(8));
        }
        System.out.println("----------------------------------------------------------------------------------");
    }
    private static void revenueReportByShowTime(int theaterID,long from,long to) throws Exception {
        String q = "SELECT T.Theatername,T.location,FROM_UNIXTIME(B.Date / 1000, '%Y-%m-%d') AS BookingDate,SH.`ShowTime`,SUM(CASE WHEN B.`Option` = 'Book' THEN B.noOfSeats ELSE 0 END) AS NoOfSeatsBooked,SUM(CASE WHEN B.`Option` = 'Book' THEN B.amount ELSE 0 END) AS Revenue,SUM(CASE WHEN B.`Option` = 'Cancel' THEN B.noOfSeats ELSE 0 END) AS NoOfSeatsCancelled,SUM(CASE WHEN B.`Option` = 'Cancel' THEN B.amount ELSE 0 END) AS Refund,SUM(CASE WHEN B.`Option` = 'Book' THEN B.amount ELSE 0 END) - SUM(CASE WHEN B.`Option` = 'Cancel' THEN B.amount ELSE 0 END) as Profit FROM Booking B INNER JOIN `Show` SH ON B.showId = SH.showId INNER JOIN Screen SC ON SH.screenId = SC.screenId INNER JOIN Theater T ON SC.theaterId = T.theaterId WHERE T.theaterId = "+theaterID+" AND B.Date BETWEEN "+from+" AND "+to+" GROUP BY FROM_UNIXTIME(B.Date / 1000, '%Y-%m-%d'),SH.ShowTime ORDER BY SH.SHowTime;;";
        ResultSet r = ConnectionUtil.selectQuery(q);
        System.out.println("Theater    Location    Date    ShowTime    Booked    Cancelled    Revenue    Refund    Profit");
        System.out.println("---------------------------------------------------------------------------------------------");
        while (r.next()){
            System.out.print(r.getString(1)+"       ");
            System.out.print(r.getString(2)+"    ");
            System.out.print(r.getString(3)+"  ");
            System.out.print(r.getString(4)+"      ");
            System.out.print(r.getInt(5)+"        ");
            System.out.print(r.getInt(7)+"         ");
            System.out.print(r.getInt(6)+"        ");
            System.out.print(r.getInt(8)+"      ");
            System.out.println(r.getInt(9));
        }
        System.out.println("---------------------------------------------------------------------------------------------");
    }
    private static void revenueReportByScreen(int theaterID, long from,long to) throws Exception {
        String q= "SELECT T.Theatername,T.location,FROM_UNIXTIME(B.Date / 1000, '%Y-%m-%d') AS BookingDate,SC.ScreenNumber,SUM(CASE WHEN B.`Option` = 'Book' THEN B.noOfSeats ELSE 0 END) AS NoOfSeatsBooked,SUM(CASE WHEN B.`Option` = 'Cancel' THEN B.noOfSeats ELSE 0 END) AS NoOfSeatsCancelled,SUM(CASE WHEN B.`Option` = 'Book' THEN B.amount ELSE 0 END) AS Revenue,SUM(CASE WHEN B.`Option` = 'Cancel' THEN B.amount ELSE 0 END) AS Refund,SUM(CASE WHEN B.`Option` = 'Book' THEN B.amount ELSE 0 END) - SUM(CASE WHEN B.`Option` = 'Cancel' THEN B.amount ELSE 0 END) as Profit FROM Booking B INNER JOIN `Show` SH ON B.showId = SH.showId INNER JOIN Screen SC ON SH.screenId = SC.screenId INNER JOIN Theater T ON SC.theaterId = T.theaterId WHERE T.theaterId = "+theaterID+" AND B.Date BETWEEN "+from+" AND "+to+" GROUP BY FROM_UNIXTIME(B.Date / 1000, '%Y-%m-%d'),SC.ScreenNumber ORDER BY SC.ScreenNumber ASC;";
        ResultSet r = ConnectionUtil.selectQuery(q);

        System.out.println("Theater    Location    Date    Screen    Booked    Cancelled    Revenue    Refund    Profit");
        System.out.println("-------------------------------------------------------------------------------------------");
        while (r.next()){
            System.out.print(r.getString(1)+"       ");
            System.out.print(r.getString(2)+"    ");
            System.out.print(r.getString(3)+"  ");
            System.out.print(r.getInt(4)+"        ");
            System.out.print(r.getInt(5)+"          ");
            System.out.print(r.getInt(6)+"           ");
            System.out.print(r.getInt(7)+"       ");
            System.out.print(r.getInt(8)+"        ");
            System.out.println(r.getInt(9));
        }
        System.out.println("------------------------------------------------------------------------------------------");

    }
    public static void main(String[] args) throws Exception {
        TheaterAPI.getTheaterDetails();
        int theaterID = sc.nextInt();
        String date = "2024-04-29";
        System.out.println("From Date: [yyyy-MM-dd]");
        String from = sc.next();
        long fromMillis = getMillis(from+" 00:00:00");
        System.out.println(fromMillis);
        System.out.println("To Date: [yyyy-MM-dd]");
        String to = sc.next();
        long toMillis = getMillis(to+" 23:59:59");
        System.out.println(toMillis);
        revenueReportByDate(theaterID,fromMillis,toMillis);
        revenueReportByShowTime(theaterID,fromMillis,toMillis);
        revenueReportByScreen(theaterID,fromMillis,toMillis);
    }

    private static long getMillis(String myDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(myDate);
        long millis = date.getTime();
        return millis;
    }

}

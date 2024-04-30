package com.zoho.theater;

import com.zoho.theater.connection.ConnectionUtil;
import com.zoho.theater.theater.TheaterAPI;

import java.sql.ResultSet;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RevenueReport {
    static Scanner sc=  new Scanner(System.in);
    public static void revenueReportByDate(int theaterID, String date) throws Exception {
        String q = "SELECT T.Theatername,T.location,DATE(B.Bookingdate),SUM(B.noOfSeatsBooked) AS NoOfSeatsBooked,SUM(C.noOfSeatsCancelled) AS NoOfSeatsCancelled, SUM(B.amount) AS Revenue, SUM(C.refundedAmount) AS Refund FROM Booking B JOIN `Show` SH ON B.showId = SH.showId JOIN Screen SC ON SH.screenId = SC.screenId JOIN Theater T ON SC.theaterId = T.theaterId LEFT JOIN Cancellation C ON B.bookingId = C.bookingId AND DATE(C.cancellationDate) = '"+date+"' WHERE T.theaterId = "+theaterID+" AND DATE(B.bookingDate) = '"+date+"'  GROUP BY DATE(B.Bookingdate);";
        ResultSet r = ConnectionUtil.selectQuery(q);

            System.out.println("Theater    Location   Date       Booked   Cancelled    Revenue    Refund");
            System.out.println("------------------------------------------------------------------------");
            while (r.next()){
                System.out.print(r.getString(1)+"         ");
                System.out.print(r.getString(2)+"    ");
                System.out.print(r.getString(3)+"     ");
                System.out.print(r.getInt(4)+"      ");
                System.out.print(r.getInt(5)+"         ");
                System.out.print(r.getInt(6)+"     ");
                System.out.println(r.getInt(7));
            }
        System.out.println("------------------------------------------------------------------------");
    }
    private static void revenueReportByShowTime(int theaterID, String date) throws Exception {
        String q = "SELECT T.Theatername,T.location,DATE(B.Bookingdate), SH.ShowTime,SUM(B.noOfSeatsBooked) AS NoOfSeatsBooked,SUM(C.noOfSeatsCancelled) AS NoOfSeatsCancelled, SUM(B.amount) AS Revenue, SUM(C.refundedAmount) AS Refund FROM Booking B JOIN `Show` SH ON B.showId = SH.showId JOIN Screen SC ON SH.screenId = SC.screenId JOIN Theater T ON SC.theaterId = T.theaterId LEFT JOIN Cancellation C ON B.bookingId = C.bookingId AND DATE(C.cancellationDate) = '"+date+"' WHERE T.theaterId = "+theaterID+" AND DATE(B.bookingDate) = '"+date+"'  GROUP BY SH.SHowTime,DATE(B.Bookingdate) order by SH.showtime;";
        ResultSet r = ConnectionUtil.selectQuery(q);

        System.out.println("Theater    Location    Date    ShowTime    Booked    Cancelled    Revenue    Refund");
        System.out.println("-----------------------------------------------------------------------------------");
        while (r.next()){
            System.out.print(r.getString(1)+"       ");
            System.out.print(r.getString(2)+"    ");
            System.out.print(r.getString(3)+"  ");
            System.out.print(r.getString(4)+"      ");
            System.out.print(r.getInt(5)+"        ");
            System.out.print(r.getInt(6)+"        ");
            System.out.print(r.getInt(7)+"      ");
            System.out.println(r.getInt(8));
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }
    private static void revenueReportByScreen(int theaterID, String date) throws Exception {
        String q = "SELECT T.Theatername,T.location,DATE(B.Bookingdate), SC.screenNumber,SUM(B.noOfSeatsBooked) AS NoOfSeatsBooked,SUM(C.noOfSeatsCancelled) AS NoOfSeatsCancelled, SUM(B.amount) AS Revenue, SUM(C.refundedAmount) AS Refund FROM Booking B JOIN `Show` SH ON B.showId = SH.showId JOIN Screen SC ON SH.screenId = SC.screenId JOIN Theater T ON SC.theaterId = T.theaterId LEFT JOIN Cancellation C ON B.bookingId = C.bookingId AND DATE(C.cancellationDate) = '"+date+"' WHERE T.theaterId = "+theaterID+" AND DATE(B.bookingDate) = '"+date+"'  GROUP BY SC.screenId, SC.screenNumber,DATE(B.Bookingdate);";
        ResultSet r = ConnectionUtil.selectQuery(q);

        System.out.println("Theater    Location    Date    Screen    Booked    Cancelled    Revenue    Refund");
        System.out.println("---------------------------------------------------------------------------------");
        while (r.next()){
            System.out.print(r.getString(1)+"       ");
            System.out.print(r.getString(2)+"    ");
            System.out.print(r.getString(3)+"  ");
            System.out.print(r.getInt(4)+"        ");
            System.out.print(r.getInt(5)+"          ");
            System.out.print(r.getInt(6)+"         ");
            System.out.print(r.getInt(7)+"       ");
            System.out.println(r.getInt(8));
        }
        System.out.println("---------------------------------------------------------------------------------");

    }
    public static void main(String[] args) throws Exception {
        TheaterAPI.getTheaterDetails();
        int theaterID = sc.nextInt();
        String date = "2024-04-29";
        revenueReportByDate(theaterID,date);
        revenueReportByShowTime(theaterID,date);
        revenueReportByScreen(theaterID,date);
    }

}

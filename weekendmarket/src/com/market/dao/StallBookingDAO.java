package com.market.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.market.bean.StallBooking;
import com.market.util.DBUtil;

public class StallBookingDAO {

    public StallBooking findBooking(String bookingID) {
        StallBooking booking = null;
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT * FROM STALL_BOOKING_TBL WHERE Booking_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bookingID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                booking = new StallBooking();
                booking.setBookingID(rs.getString("Booking_ID"));
                booking.setFarmerCode(rs.getString("Farmer_Code"));
                booking.setFarmerName(rs.getString("Farmer_Name"));
                booking.setContactNumber(rs.getString("Contact_Number"));
                booking.setProductCategory(rs.getString("Product_Category"));
                booking.setMarketDate(rs.getDate("Market_Date"));
                booking.setStallNumber(rs.getString("Stall_Number"));
                booking.setStallType(rs.getString("Stall_Type"));
                booking.setBookingFeeAmount(rs.getDouble("Booking_Fee_Amount"));
                booking.setFeePaymentStatus(rs.getString("Fee_Payment_Status"));
                booking.setBookingStatus(rs.getString("Booking_Status"));
                booking.setCreatedTimestamp(rs.getTimestamp("Created_Timestamp"));
                booking.setCancellationReason(rs.getString("Cancellation_Reason"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    public List<StallBooking> viewBookingsByDateAndStatus(Date marketDate, String status) {
        List<StallBooking> list = new ArrayList<>();

        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT * FROM STALL_BOOKING_TBL WHERE Market_Date=? AND Booking_Status=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, marketDate);
            ps.setString(2, status);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StallBooking booking = new StallBooking();
                booking.setBookingID(rs.getString("Booking_ID"));
                booking.setFarmerCode(rs.getString("Farmer_Code"));
                booking.setFarmerName(rs.getString("Farmer_Name"));
                booking.setBookingStatus(rs.getString("Booking_Status"));
                booking.setFeePaymentStatus(rs.getString("Fee_Payment_Status"));
                list.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean insertBooking(StallBooking booking) {
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "INSERT INTO STALL_BOOKING_TBL VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, booking.getBookingID());
            ps.setString(2, booking.getFarmerCode());
            ps.setString(3, booking.getFarmerName());
            ps.setString(4, booking.getContactNumber());
            ps.setString(5, booking.getProductCategory());
            ps.setDate(6, booking.getMarketDate());
            ps.setString(7, booking.getStallNumber());
            ps.setString(8, booking.getStallType());
            ps.setDouble(9, booking.getBookingFeeAmount());
            ps.setString(10, booking.getFeePaymentStatus());
            ps.setString(11, booking.getBookingStatus());
            ps.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
            ps.setString(13, booking.getCancellationReason());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBookingStatusAndPayment(
            String bookingID,
            String newStatus,
            String newPaymentStatus,
            String cancellationReason) {

        try {
            Connection con = DBUtil.getDBConnection();
            String sql =
                "UPDATE STALL_BOOKING_TBL SET " +
                "Booking_Status=?, Fee_Payment_Status=?, Cancellation_Reason=? " +
                "WHERE Booking_ID=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setString(2, newPaymentStatus);
            ps.setString(3, cancellationReason);
            ps.setString(4, bookingID);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countConfirmedStallsForDate(Date marketDate) {
        int count = 0;
        try {
            Connection con = DBUtil.getDBConnection();
            String sql =
                "SELECT COUNT(*) FROM STALL_BOOKING_TBL " +
                "WHERE Market_Date=? AND Booking_Status='CONFIRMED'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, marketDate);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}

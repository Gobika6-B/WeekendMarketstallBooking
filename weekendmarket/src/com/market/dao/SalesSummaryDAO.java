package com.market.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.market.bean.SalesSummary;
import com.market.util.DBUtil;

public class SalesSummaryDAO {

    public int generateSummaryID() {
        int id = 1;
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT NVL(MAX(Summary_ID),0)+1 FROM SALES_SUMMARY_TBL";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public SalesSummary findSalesSummaryByBooking(String bookingID) {

        SalesSummary summary = null;

        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT * FROM SALES_SUMMARY_TBL WHERE Booking_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bookingID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                summary = new SalesSummary();
                summary.setSummaryID(rs.getInt("Summary_ID"));
                summary.setBookingID(rs.getString("Booking_ID"));
                summary.setTotalSalesAmount(rs.getDouble("Total_Sales_Amount"));
                summary.setUnsoldStockValue(rs.getDouble("Unsold_Stock_Value"));
                summary.setDonationAmount(rs.getDouble("Donation_Amount"));
                summary.setNumberOfBillsIssued(rs.getInt("Number_of_Bills_Issued"));
                summary.setSummaryStatus(rs.getString("Summary_Status"));
                summary.setRemarks(rs.getString("Remarks"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return summary;
    }
    public boolean insertSalesSummary(SalesSummary summary) {
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "INSERT INTO SALES_SUMMARY_TBL VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, summary.getSummaryID());
            ps.setString(2, summary.getBookingID());
            ps.setDate(3, summary.getMarketDate());
            ps.setDouble(4, summary.getTotalSalesAmount());
            ps.setDouble(5, summary.getUnsoldStockValue());
            ps.setDouble(6, summary.getDonationAmount());
            ps.setInt(7, summary.getNumberOfBillsIssued());
            ps.setString(8, summary.getSummaryStatus());
            ps.setString(9, summary.getRemarks());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public SalesSummary findSalesSummaryByID(int summaryID) {
        SalesSummary summary = null;
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT * FROM SALES_SUMMARY_TBL WHERE Summary_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, summaryID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                summary = new SalesSummary();
                summary.setSummaryID(rs.getInt("Summary_ID"));
                summary.setBookingID(rs.getString("Booking_ID"));
                summary.setTotalSalesAmount(rs.getDouble("Total_Sales_Amount"));
                summary.setUnsoldStockValue(rs.getDouble("Unsold_Stock_Value"));
                summary.setDonationAmount(rs.getDouble("Donation_Amount"));
                summary.setNumberOfBillsIssued(rs.getInt("Number_of_Bills_Issued"));
                summary.setSummaryStatus(rs.getString("Summary_Status"));
                summary.setRemarks(rs.getString("Remarks"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return summary;
    }	
    public List<SalesSummary> findSalesSummariesByDate(Date marketDate) {
        List<SalesSummary> list = new ArrayList<>();

        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT * FROM SALES_SUMMARY_TBL WHERE Market_Date=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, marketDate);   // ❗ FIXED (was setLong earlier)

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SalesSummary s = new SalesSummary();
                s.setSummaryID(rs.getInt("Summary_ID"));
                s.setBookingID(rs.getString("Booking_ID"));
                s.setMarketDate(rs.getDate("Market_Date"));
                s.setTotalSalesAmount(rs.getDouble("Total_Sales_Amount"));
                s.setSummaryStatus(rs.getString("Summary_Status"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<SalesSummary> findSalesSummariesByFarmer(String farmerCode) {
        List<SalesSummary> list = new ArrayList<>();

        try {
            Connection con = DBUtil.getDBConnection();
            String sql =
                "SELECT s.* FROM SALES_SUMMARY_TBL s " +
                "JOIN STALL_BOOKING_TBL b ON s.Booking_ID = b.Booking_ID " +
                "WHERE b.Farmer_Code=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, farmerCode);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SalesSummary s = new SalesSummary();
                s.setSummaryID(rs.getInt("Summary_ID"));
                s.setBookingID(rs.getString("Booking_ID"));
                s.setTotalSalesAmount(rs.getDouble("Total_Sales_Amount"));
                s.setSummaryStatus(rs.getString("Summary_Status"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<SalesSummary> findSalesSummariesByProductCategory(String productCategory) {
        List<SalesSummary> list = new ArrayList<>();

        try {
            Connection con = DBUtil.getDBConnection();
            String sql =
                "SELECT s.* FROM SALES_SUMMARY_TBL s " +
                "JOIN STALL_BOOKING_TBL b ON s.Booking_ID = b.Booking_ID " +
                "WHERE b.Product_Category=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, productCategory);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SalesSummary s = new SalesSummary();
                s.setSummaryID(rs.getInt("Summary_ID"));
                s.setBookingID(rs.getString("Booking_ID"));
                s.setTotalSalesAmount(rs.getDouble("Total_Sales_Amount"));
                s.setSummaryStatus(rs.getString("Summary_Status"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean updateSalesSummary(SalesSummary summary) {
        try {
            Connection con = DBUtil.getDBConnection();
            String sql =
                "UPDATE SALES_SUMMARY_TBL SET " +
                "Total_Sales_Amount=?, Unsold_Stock_Value=?, Donation_Amount=?, " +
                "Number_of_Bills_Issued=?, Summary_Status=?, Remarks=? " +
                "WHERE Summary_ID=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, summary.getTotalSalesAmount());
            ps.setDouble(2, summary.getUnsoldStockValue());
            ps.setDouble(3, summary.getDonationAmount());
            ps.setInt(4, summary.getNumberOfBillsIssued());
            ps.setString(5, summary.getSummaryStatus());
            ps.setString(6, summary.getRemarks());
            ps.setInt(7, summary.getSummaryID());   

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

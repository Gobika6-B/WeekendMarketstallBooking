package com.market.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.market.bean.StallBooking;
import com.market.bean.SalesSummary;
import com.market.dao.StallBookingDAO;
import com.market.dao.SalesSummaryDAO;
import com.market.util.DBUtil;
import com.market.util.ValidationException;
import com.market.util.MarketCapacityExceededException;
import com.market.util.SalesSummaryAlreadyExistException;

public class MarketService {

    private StallBookingDAO stallDAO = new StallBookingDAO();
    private SalesSummaryDAO summaryDAO = new SalesSummaryDAO();

    public StallBooking viewBookingDetails(String bookingID) throws ValidationException {
        if (bookingID == null || bookingID.trim().isEmpty()) {
            throw new ValidationException("Booking ID is mandatory");
        }
        return stallDAO.findBooking(bookingID);
    }
    public List<StallBooking> viewBookingsByDateAndStatus(Date marketDate, String status)
            throws ValidationException {

        if (marketDate == null || status == null || status.trim().isEmpty()) {
            throw new ValidationException("Invalid date or status");
        }
        return stallDAO.viewBookingsByDateAndStatus(marketDate, status);
    }
    public boolean registerNewStallBooking(
            StallBooking booking,
            double marketStandardFee,
            double marketPremiumFee,
            double marketCornerFee)
            throws ValidationException {

        if (booking == null ||
            booking.getBookingID() == null || booking.getBookingID().isEmpty() ||
            booking.getFarmerCode() == null || booking.getFarmerCode().isEmpty() ||
            booking.getFarmerName() == null || booking.getFarmerName().isEmpty() ||
            booking.getContactNumber() == null || booking.getContactNumber().isEmpty() ||
            booking.getProductCategory() == null || booking.getProductCategory().isEmpty() ||
            booking.getStallType() == null || booking.getStallType().isEmpty() ||
            booking.getMarketDate() == null) {

            throw new ValidationException("Mandatory fields missing");
        }
        String cat = booking.getProductCategory();
        if (!(cat.equals("VEGETABLES") || cat.equals("FRUITS") ||
              cat.equals("ORGANIC_PRODUCTS") || cat.equals("DAIRY") ||
              cat.equals("PROCESSED_FOOD") || cat.equals("MIXED"))) {

            throw new ValidationException("Invalid product category");
        }
        if (booking.getStallType().equals("STANDARD")) {
            marketCornerFee = marketStandardFee;
        } else if (booking.getStallType().equals("PREMIUM")) {
            marketCornerFee = marketPremiumFee;
        } else if (booking.getStallType().equals("CORNER")) {
            marketCornerFee = marketCornerFee;
        } else {
            throw new ValidationException("Invalid stall type");
        }

        if (marketCornerFee < 0) {
            throw new ValidationException("Invalid fee amount");
        }

        booking.setBookingFeeAmount(marketCornerFee);

        if (booking.getFeePaymentStatus() == null) {
            booking.setFeePaymentStatus("NOT_PAID");
        }
        if (booking.getBookingStatus() == null) {
            booking.setBookingStatus("PENDING");
        }

        return stallDAO.insertBooking(booking);
    }
    public boolean confirmBooking(String bookingID, int maxStallsPerDay)
            throws ValidationException, MarketCapacityExceededException {

        if (bookingID == null || bookingID.trim().isEmpty()) {
            throw new ValidationException("Booking ID cannot be empty");
        }

        StallBooking booking = stallDAO.findBooking(bookingID);
        if (booking == null) return false;

        if (!booking.getBookingStatus().equals("PENDING")) {
            throw new ValidationException("Only PENDING bookings can be confirmed");
        }

        int confirmed = stallDAO.countConfirmedStallsForDate(booking.getMarketDate());
        if (confirmed + 1 > maxStallsPerDay) {
            throw new MarketCapacityExceededException("Market capacity exceeded");
        }

        Connection con = null;
        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            boolean updated = stallDAO.updateBookingStatusAndPayment(
                    bookingID,
                    "CONFIRMED",
                    booking.getFeePaymentStatus(),
                    null);

            if (updated) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }

        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            return false;
        }
    }
    public boolean cancelBooking(String bookingID, String reason)
            throws ValidationException {

        if (bookingID == null || bookingID.trim().isEmpty() ||
            reason == null || reason.trim().isEmpty()) {

            throw new ValidationException("Invalid cancellation data");
        }

        StallBooking booking = stallDAO.findBooking(bookingID);
        if (booking == null) return false;

        if (booking.getBookingStatus().equals("CANCELLED") ||
            booking.getBookingStatus().equals("COMPLETED")) {

            throw new ValidationException("Booking cannot be cancelled");
        }

        return stallDAO.updateBookingStatusAndPayment(
                bookingID,
                "CANCELLED",
                booking.getFeePaymentStatus(),
                reason);
    }

    public boolean recordSalesSummary(
            String bookingID,
            double totalSales,
            double unsoldValue,
            double donationAmount,
            int billCount,
            String remarks)
            throws ValidationException, SalesSummaryAlreadyExistException {

        if (bookingID == null || bookingID.trim().isEmpty() ||
            totalSales < 0 || unsoldValue < 0 ||
            donationAmount < 0 || billCount < 0) {

            throw new ValidationException("Invalid sales data");
        }

        StallBooking booking = stallDAO.findBooking(bookingID);
        if (booking == null) return false;

        if (!booking.getBookingStatus().equals("CONFIRMED")) {
            throw new ValidationException("Booking not confirmed");
        }

        SalesSummary existing = summaryDAO.findSalesSummaryByBooking(bookingID);
        if (existing != null) {
            throw new SalesSummaryAlreadyExistException("Sales summary already exists");
        }

        Connection con = null;
        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            SalesSummary summary = new SalesSummary();
            summary.setSummaryID(summaryDAO.generateSummaryID());
            summary.setBookingID(bookingID);
            summary.setMarketDate(booking.getMarketDate());
            summary.setTotalSalesAmount(totalSales);
            summary.setUnsoldStockValue(unsoldValue);
            summary.setDonationAmount(donationAmount);
            summary.setNumberOfBillsIssued(billCount);
            summary.setSummaryStatus("SUBMITTED");
            summary.setRemarks(remarks);

            boolean inserted = summaryDAO.insertSalesSummary(summary);

            stallDAO.updateBookingStatusAndPayment(
                    bookingID,
                    "COMPLETED",
                    booking.getFeePaymentStatus(),
                    booking.getCancellationReason());

            if (inserted) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }

        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            return false;
        }
    }

    // 7️⃣ Adjust Sales Summary
    public boolean adjustSalesSummary(
            int summaryID,
            double newTotalSales,
            double newUnsoldValue,
            double newDonationAmount,
            int newBillCount,
            String newRemarks) throws ValidationException {


        if (summaryID <= 0 || newTotalSales < 0 ||
            newUnsoldValue < 0 || newDonationAmount < 0 ||
            newBillCount < 0) {

            throw new ValidationException("Invalid adjustment data");
        }

        SalesSummary summary = summaryDAO.findSalesSummaryByID(summaryID);
        if (summary == null) return false;

        summary.setTotalSalesAmount(newTotalSales);
        summary.setUnsoldStockValue(newUnsoldValue);
        summary.setDonationAmount(newDonationAmount);
        summary.setNumberOfBillsIssued(newBillCount);
        summary.setRemarks(newRemarks);
        summary.setSummaryStatus("ADJUSTED");

        return summaryDAO.updateSalesSummary(summary);
    }

    // 8️⃣ Reports
    public List<SalesSummary> listSalesSummariesByDate(Date marketDate) {
        return summaryDAO.findSalesSummariesByDate(marketDate);
    }

    public List<SalesSummary> listSalesSummariesByFarmer(String farmerCode) {
        return summaryDAO.findSalesSummariesByFarmer(farmerCode);
    }

    public List<SalesSummary> listSalesSummariesByProductCategory(String productCategory) {
        return summaryDAO.findSalesSummariesByProductCategory(productCategory);
    }
}



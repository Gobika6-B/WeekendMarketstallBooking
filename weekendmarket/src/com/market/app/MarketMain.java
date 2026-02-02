package com.market.app;
import com.market.service.MarketService;
import com.market.bean.StallBooking;
import com.market.util.ValidationException;
import com.market.util.MarketCapacityExceededException;
public class MarketMain {
	 private static MarketService service = new MarketService();
	 public static void main(String[] args) {
	 java.util.Scanner sc = new java.util.Scanner(System.in);
	 System.out.println("--- Weekend Farmers' Market Console ---");
	 try {
	 StallBooking b = new StallBooking();
	 b.setBookingID("BK2001");
	 b.setFarmerCode("FRM201");
	 b.setFarmerName("Meenakshi Organic Farm");
	 b.setContactNumber("9998887771");
	 b.setProductCategory("VEGETABLES");
	 b.setMarketDate(new java.sql.Date(System.currentTimeMillis()));
	 b.setStallNumber("S-09");
	 b.setStallType("STANDARD");
	 b.setFeePaymentStatus("NOT_PAID");
	 b.setBookingStatus("PENDING");
	 b.setCreatedTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
	 boolean ok = service.registerNewStallBooking(b,500.0, 900.0, 700.0);
	 System.out.println(ok ? "BOOKING REGISTERED" : "BOOKING REGISTRATION FAILED");
	 } 
	 catch (ValidationException e) {
	 System.out.println("Validation Error: " +
	e.toString());
	 } 
	 catch (Exception e) {
	 System.out.println("System Error: " +
	e.getMessage());
	 }
	 try {
	 boolean ok = service.confirmBooking("BK2001", 40);
	 System.out.println(ok ? "BOOKING CONFIRMED" :
	"BOOKING CONFIRMATION FAILED");
	 } 
	 catch (MarketCapacityExceededException e) {
	 System.out.println("Capacity Error: " +
	e.toString());
	 } 
	 catch (ValidationException e) {
	 System.out.println("Validation Error: " +
	e.toString());
	 } 
	 catch (Exception e) {
	 System.out.println("System Error: " + e.getMessage());
	 }
	 sc.close();
	 }
	}









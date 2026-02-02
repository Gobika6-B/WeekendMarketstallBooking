package com.market.bean;

import java.sql.Date;

public class SalesSummary {

    private int summaryID;
    private String bookingID;
    private Date marketDate;
    private double totalSalesAmount;
    private double unsoldStockValue;
    private double donationAmount;
    private int numberOfBillsIssued;
    private String summaryStatus;
    private String remarks;

    public int getSummaryID() 
    { return summaryID; }
    public void setSummaryID(int summaryID) 
    { this.summaryID = summaryID; }

    public String getBookingID() { 
    	return bookingID; }
    public void setBookingID(String bookingID) { 
    	this.bookingID = bookingID; }

    public Date getMarketDate() { 
    	return marketDate; }
    public void setMarketDate(Date marketDate) { 
    	this.marketDate = marketDate; }

    public double getTotalSalesAmount() { 
    	return totalSalesAmount; }
    public void setTotalSalesAmount(double totalSalesAmount) { 
    	this.totalSalesAmount = totalSalesAmount; }

    public double getUnsoldStockValue() { 
    	return unsoldStockValue; }
    public void setUnsoldStockValue(double unsoldStockValue) { 
    	this.unsoldStockValue = unsoldStockValue; }

    public double getDonationAmount() { 
    	return donationAmount; }
    public void setDonationAmount(double donationAmount) { 
    	this.donationAmount = donationAmount; }

    public int getNumberOfBillsIssued() { 
    	return numberOfBillsIssued; }
    public void setNumberOfBillsIssued(int numberOfBillsIssued) { 
    	this.numberOfBillsIssued = numberOfBillsIssued; }

    public String getSummaryStatus() { 
    	return summaryStatus; }
    public void setSummaryStatus(String summaryStatus) { 
    	this.summaryStatus = summaryStatus; }

    public String getRemarks() { 
    	return remarks; }
    public void setRemarks(String remarks) { 
    	this.remarks = remarks; }
}

package com.market.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class StallBooking {

    private String bookingID;
    private String farmerCode;
    private String farmerName;
    private String contactNumber;
    private String productCategory;
    private Date marketDate;
    private String stallNumber;
    private String stallType;
    private double bookingFeeAmount;
    private String feePaymentStatus;
    private String bookingStatus;
    private Timestamp createdTimestamp;
    private String cancellationReason;

    public String getBookingID() 
    { return bookingID; }
    public void setBookingID(String bookingID) 
    { this.bookingID = bookingID; }

    public String getFarmerCode() 
    { return farmerCode; }
    public void setFarmerCode(String farmerCode) 
    { this.farmerCode = farmerCode; }

    public String getFarmerName() 
    { return farmerName; }
    public void setFarmerName(String farmerName) 
    { this.farmerName = farmerName; }

    public String getContactNumber() 
    { return contactNumber; }
    public void setContactNumber(String contactNumber) 
    { this.contactNumber = contactNumber; }

    public String getProductCategory() 
    { return productCategory; }
    public void setProductCategory(String productCategory) 
    { this.productCategory = productCategory; }

    public Date getMarketDate() 
    { return marketDate; }
    public void setMarketDate(Date marketDate) 
    { this.marketDate = marketDate; }

    public String getStallNumber() 
    { return stallNumber; }
    public void setStallNumber(String stallNumber) 
    { this.stallNumber = stallNumber; }

    public String getStallType() 
    { return stallType; }
    public void setStallType(String stallType) 
    { this.stallType = stallType; }

    public double getBookingFeeAmount() 
    { return bookingFeeAmount; }
    public void setBookingFeeAmount(double bookingFeeAmount) 
    { this.bookingFeeAmount = bookingFeeAmount; }

    public String getFeePaymentStatus() 
    { return feePaymentStatus; }
    public void setFeePaymentStatus(String feePaymentStatus) 
    { this.feePaymentStatus = feePaymentStatus; }

    public String getBookingStatus() 
    { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) 
    { this.bookingStatus = bookingStatus; }

    public Timestamp getCreatedTimestamp() 
    { return createdTimestamp; }
    public void setCreatedTimestamp(Timestamp createdTimestamp) 
    { this.createdTimestamp = createdTimestamp; }

    public String getCancellationReason() 
    { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }
}

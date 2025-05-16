package com.core.io.fees.dto.requestdto;

public class FeeRequest {
    private String toCountryCode;
    private double amount;
    private String productSchema;
    public String getToCountryCode() {
        return toCountryCode;
    }

    public void setToCountryCode(String toCountryCode) {
        this.toCountryCode = toCountryCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getProductSchema() {
        return productSchema;
    }

    public void setProductSchema(String productSchema) {
        this.productSchema = productSchema;
    }
}


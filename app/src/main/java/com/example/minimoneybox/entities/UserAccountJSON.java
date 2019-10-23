package com.example.minimoneybox.entities;

import java.util.List;

public class UserAccountJSON {

    private Double TotalPlanValue;
    private Double TotalEarnings;
    private Double TotalContributionsNet;
    private Double TotalEarningsAsPercentage;
    private List<Plan> ProductResponses;

    public Double getTotalPlanValue() {
        return TotalPlanValue;
    }

    public void setTotalPlanValue(Double totalPlanValue) {
        TotalPlanValue = totalPlanValue;
    }

    public Double getTotalEarnings() {
        return TotalEarnings;
    }

    public void setTotalEarnings(Double totalEarnings) {
        TotalEarnings = totalEarnings;
    }

    public Double getTotalContributionsNet() {
        return TotalContributionsNet;
    }

    public void setTotalContributionsNet(Double totalContributionsNet) {
        TotalContributionsNet = totalContributionsNet;
    }

    public Double getTotalEarningsAsPercentage() {
        return TotalEarningsAsPercentage;
    }

    public void setTotalEarningsAsPercentage(Double totalEarningsAsPercentage) {
        TotalEarningsAsPercentage = totalEarningsAsPercentage;
    }

    public List<Plan> getProductResponses() {
        return ProductResponses;
    }

    public void setProductResponses(List<Plan> productResponses) {
        ProductResponses = productResponses;
    }
}

package com.example.minimoneybox.entities;

public class Plan {

    private Integer Id;
    private Double PlanValue;
    private Double Moneybox;
    private Double SubscriptionAmount;
    private Double TotalFees;
    private Boolean IsSelected;
    private Boolean IsFavourite;
    private String ContributionStatus;
    private Product Product;
    private String CollectionDayMessage;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Double getPlanValue() {
        return PlanValue;
    }

    public void setPlanValue(Double planValue) {
        PlanValue = planValue;
    }

    public Double getMoneybox() {
        return Moneybox;
    }

    public void setMoneybox(Double moneybox) {
        Moneybox = moneybox;
    }

    public Double getSubscriptionAmount() {
        return SubscriptionAmount;
    }

    public void setSubscriptionAmount(Double subscriptionAmount) {
        SubscriptionAmount = subscriptionAmount;
    }

    public Double getTotalFees() {
        return TotalFees;
    }

    public void setTotalFees(Double totalFees) {
        TotalFees = totalFees;
    }

    public Boolean getSelected() {
        return IsSelected;
    }

    public void setSelected(Boolean selected) {
        IsSelected = selected;
    }

    public Boolean getFavourite() {
        return IsFavourite;
    }

    public void setFavourite(Boolean favourite) {
        IsFavourite = favourite;
    }

    public String getContributionStatus() {
        return ContributionStatus;
    }

    public void setContributionStatus(String contributionStatus) {
        ContributionStatus = contributionStatus;
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product product) {
        Product = product;
    }

    public String getCollectionDayMessage() {
        return CollectionDayMessage;
    }

    public void setCollectionDayMessage(String collectionDayMessage) {
        CollectionDayMessage = collectionDayMessage;
    }
}
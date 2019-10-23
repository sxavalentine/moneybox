package com.example.minimoneybox.entities;

public class Product {

    private String Id;
    private String Name;
    private String CategoryType;
    private String Type;
    private String FriendlyName;
    private Boolean CanWithdraw;
    private String ProductHexCode;
    private Double AnnualLimit;
    private Double DepositLimit;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategoryType() {
        return CategoryType;
    }

    public void setCategoryType(String categoryType) {
        CategoryType = categoryType;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getFriendlyName() {
        return FriendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        FriendlyName = friendlyName;
    }

    public Boolean getCanWithdraw() {
        return CanWithdraw;
    }

    public void setCanWithdraw(Boolean canWithdraw) {
        CanWithdraw = canWithdraw;
    }

    public String getProductHexCode() {
        return ProductHexCode;
    }

    public void setProductHexCode(String productHexCode) {
        ProductHexCode = productHexCode;
    }

    public Double getAnnualLimit() {
        return AnnualLimit;
    }

    public void setAnnualLimit(Double annualLimit) {
        AnnualLimit = annualLimit;
    }

    public Double getDepositLimit() {
        return DepositLimit;
    }

    public void setDepositLimit(Double depositLimit) {
        DepositLimit = depositLimit;
    }
}

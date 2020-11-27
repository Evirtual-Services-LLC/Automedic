package com.evs.automedic.model;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName(value = "userId", alternate = {"id"})
    private String userId;
    @SerializedName(value = "fullName", alternate = {"name", "firstName", "userName"})

    private String fullName;
    /* @SerializedName("name")

     private String name;*/
    @SerializedName("gender")

    private String gender;
    @SerializedName("PersionIN")

    private String PersionIN;
    @SerializedName("links")

    private String links;
    @SerializedName("lastName")

    private String lastName;
    @SerializedName(value = "email", alternate = {"eamil"})

    private String email;
    @SerializedName("role")

    private String role;
    @SerializedName("GetSumQuote")

    private String GetSumQuote;
    @SerializedName("address")

    private String address;
    @SerializedName(value = "zipCode",alternate={"zipcode"})

    private String zipCode;
    @SerializedName(value = "contactNumber", alternate = {"phone", "mobile"})

    private String contactNumber;
    @SerializedName("image")

    private String image;
    @SerializedName("facilityName")

    private String facilityName;
    @SerializedName("Appoitment")

    private String appointment;
    @SerializedName("device")

    private String device;
    @SerializedName("deviceToken")

    private String deviceToken;
    @SerializedName("firebaseId")

    private String firebaseId;
    @SerializedName("socialId")

    private String socialId;
    @SerializedName("socialType")

    private String socialType;
    @SerializedName("drivingLicenseFront")

    private String drivingLicenseFront;
    @SerializedName("drivingLicenseBack")

    private String drivingLicenseBack;
    @SerializedName("CompanyName")

    private String companyName;
    @SerializedName("BusinessCategory")

    private String businessCategory;
    @SerializedName("BusinessPhone")

    private String businessPhone;
    @SerializedName("website")

    private String website;
    @SerializedName("BusinessAddress")

    private String businessAddress;
    @SerializedName("DealIn")

    private String dealIn;
    @SerializedName("CompanyStrength")

    private String companyStrength;
    @SerializedName("department")

    private String department;
    @SerializedName("designation")

    private String designation;
    @SerializedName("experence")

    private String experence;

    @SerializedName("gamertag")
    private String gamerTag;

    @SerializedName("documents")

    private String documents;
    @SerializedName("wallet")

    private String wallet;

    @SerializedName("bankName")
    private String bankName;

    @SerializedName("accountNo")
    private String accountNo;

    @SerializedName("RoutingNo")
    private String RoutingNo;

    @SerializedName("accountType")
    private String accountType;

    @SerializedName("NameOnAccount")
    private String NameOnAccount;

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    @SerializedName("subscriptionStatus")
    private String subscriptionStatus;

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
/* public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    /*public String getUsername() {
        return username;
    }*/

    public String getFacilityName() {
        return facilityName;
    }
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersionIN() {
        return PersionIN;
    }

    public void setPersionIN(String persionIN) {
        PersionIN = persionIN;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

   /* public void setUsername(String username) {
        this.username = username;
    }*/

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getDrivingLicenseFront() {
        return drivingLicenseFront;
    }

    public void setDrivingLicenseFront(String drivingLicenseFront) {
        this.drivingLicenseFront = drivingLicenseFront;
    }

    public String getDrivingLicenseBack() {
        return drivingLicenseBack;
    }

    public void setDrivingLicenseBack(String drivingLicenseBack) {
        this.drivingLicenseBack = drivingLicenseBack;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getDealIn() {
        return dealIn;
    }

    public void setDealIn(String dealIn) {
        this.dealIn = dealIn;
    }

    public String getCompanyStrength() {
        return companyStrength;
    }

    public void setCompanyStrength(String companyStrength) {
        this.companyStrength = companyStrength;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getExperence() {
        return experence;
    }

    public void setExperence(String experence) {
        this.experence = experence;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    public void setGamerTag(String gamerTag) {
        this.gamerTag = gamerTag;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getGetSumQuote() {
        return GetSumQuote;
    }

    public void setGetSumQuote(String getSumQuote) {
        GetSumQuote = getSumQuote;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getRoutingNo() {
        return RoutingNo;
    }

    public void setRoutingNo(String routingNo) {
        RoutingNo = routingNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getNameOnAccount() {
        return NameOnAccount;
    }

    public void setNameOnAccount(String nameOnAccount) {
        NameOnAccount = nameOnAccount;
    }

}

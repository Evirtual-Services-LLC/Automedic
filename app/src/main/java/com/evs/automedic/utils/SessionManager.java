package com.evs.automedic.utils;

import android.content.SharedPreferences;

public class SessionManager {

    private static String USERNAME = "USERNAME";
    private static String NAME = "NAME";
    private static String FNAME = "FNAME";
    private static String LNAME = "LNAME";
    private static String EMAILID = "EMAILID";
    private static String PRICE = "PRICE";
    private static String mobile = "mobile";
    private static String user_id = "user_id";
    private static String user_type = "user_type";
    private static String SESSION_CHECK_LOGIN = "SESSION_CHECK_LOGIN";
    private static String check_agreement = "CHECK_AGREEMENT";
    private static String password = "password";
    private static String address = "address";
    private static String image = "image";
    private static String facilityName = "facilityName";
    private static String appoitment = "Appoitment";
    private static String select_gender = "gender";
    private static String select_PersionIN = "PersionIN";
    private static String select_type = "select_type";
    private static String type = "TYPE";
    private static String Wallet = "WALLET";
    private static String device = "DEVICE";
    private static String device_token = "DEVICE_TOKEN";
    private static String FirebaseId = "FIREBASEID";
    private static String SocialId = "SocialId";
    private static String SocialType = "SocialType";
    private static String remember = "remember";

    private static String BType = "BType";
    private static String BName = "BName";
    private static String BEmail = "BEmail";
    private static String Baddress = "Baddress";
    private static String Bphone = "Bphone";
    private static String BLat = "BLat";
    private static String Blong = "Blong";
    private static String Amount = "Amount";
    private static String CardNumber = "CardNumber";
    private static String CardExpYear = "CardExpYear";
    private static String CardExpMonth = "CardExpMonth";
    private static String ACCOUNTNUMBER = "ACCOUNTNUMBER";
    private static String GAMER_TAG = "GAMER_TAG";
    private static String GETSUM_QUOTE = "GETSUM_QUOTE";

    private static String BANK_USER_NAME = "BANK_USER_NAME";
    private static String ROUTING_NUMBER = "ROUTING_NUMBER";
    private static String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
    private static String ACCOUNT_TYPE = "ACCOUNT_TYPE";
    private static String BRANCH_NAME = "BRANCH_NAME";


    public static void savePreference(SharedPreferences prefs, String key, Boolean value) {
        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean(key, value);
        e.apply();
    }

    public static void savePreference(SharedPreferences prefs, String key, int value) {
        SharedPreferences.Editor e = prefs.edit();
        e.putInt(key, value);
        e.apply();
    }

    public static void savePreference(SharedPreferences prefs, String key, String value) {
        SharedPreferences.Editor e = prefs.edit();
        e.putString(key, value);
        e.apply();
    }
    public static void dataclear(SharedPreferences prefs) {
        SharedPreferences.Editor e = prefs.edit();
        e.clear();
        e.apply();
    }
    public static void save_check_login(SharedPreferences prefs, Boolean value) {
        SessionManager.savePreference(prefs, SESSION_CHECK_LOGIN, value);
    }

    public static Boolean get_check_login(SharedPreferences prefs) {
        return prefs.getBoolean(SESSION_CHECK_LOGIN, false);
    }

    public static void save_remember(SharedPreferences prefs, Boolean value) {
        SessionManager.savePreference(prefs, remember, value);
    }


    public static Boolean get_remember(SharedPreferences prefs) {
        return prefs.getBoolean(remember, false);
    }

    public static void save_check_agreement(SharedPreferences prefs, Boolean value) {
        SessionManager.savePreference(prefs, check_agreement, value);
    }

    public static Boolean get_check_agreement(SharedPreferences prefs) {
        return prefs.getBoolean(check_agreement, false);
    }

    public static void save_name(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, NAME, value);
    }

    public static String get_name(SharedPreferences prefs) {
        return prefs.getString(NAME, "");
    }

    public static void save_account_number(SharedPreferences prefs, String accountNumber) {
        SessionManager.savePreference(prefs, ACCOUNTNUMBER, accountNumber);
    }

    public static String get_account_number(SharedPreferences prefs) {
        return prefs.getString(ACCOUNTNUMBER, "");
    }

    public static void save_username(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, USERNAME, value);
    }

    public static String get_username(SharedPreferences prefs) {
        return prefs.getString(USERNAME, "");
    }

    public static void save_gamer_tag(SharedPreferences prefs, String gamerTag) {
        SessionManager.savePreference(prefs, GAMER_TAG, gamerTag);
    }

    public static String get_gamer_tag(SharedPreferences prefs) {
        return prefs.getString(GAMER_TAG, "");
    }

    public static void save_amount(SharedPreferences prefs, String amount) {
        SessionManager.savePreference(prefs, Amount, amount);
    }

    public static String get_amount(SharedPreferences prefs) {
        return prefs.getString(Amount, "");
    }

    public static void save_card_number(SharedPreferences prefs, String cardNumber) {
        SessionManager.savePreference(prefs, CardNumber, cardNumber);
    }

    public static String get_card_number(SharedPreferences prefs) {
        return prefs.getString(CardNumber, "");

    }

    public static void save_card_exp_year(SharedPreferences prefs, String cardExpYear) {
        SessionManager.savePreference(prefs, CardExpYear, cardExpYear);
    }

    public static String get_card_exp_year(SharedPreferences prefs) {
        return prefs.getString(CardExpYear, "");

    }

    public static void save_card_exp_month(SharedPreferences prefs, String cardExpMonth) {
        SessionManager.savePreference(prefs, CardExpMonth, cardExpMonth);
    }

    public static String get_card_exp_month(SharedPreferences prefs) {
        return prefs.getString(CardExpMonth, "");

    }

    public static void save_get_sum_quote(SharedPreferences prefs, String getSumQuote) {
        SessionManager.savePreference(prefs, GETSUM_QUOTE, getSumQuote);
    }

    public static String get_get_sum_quote(SharedPreferences prefs) {
        return prefs.getString(GETSUM_QUOTE, "");

    }


    public static void save_fname(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, FNAME, value);
    }

    public static String get_fname(SharedPreferences prefs) {
        return prefs.getString(FNAME, "");
    }

    public static void save_lname(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, LNAME, value);
    }

    public static String get_lname(SharedPreferences prefs) {
        return prefs.getString(LNAME, "");
    }

    public static void save_emailid(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, EMAILID, value);
    }

    public static String get_emailid(SharedPreferences prefs) {
        return prefs.getString(EMAILID, "");
    }

    public static void save_password(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, password, value);
    }

    public static String get_password(SharedPreferences prefs) {
        return prefs.getString(password, "");
    }


    public static void save_mobile(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, mobile, value);
    }

    public static String get_mobile(SharedPreferences prefs) {
        return prefs.getString(mobile, "");
    }

    public static void save_user_id(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, user_id, value);
    }

    public static String get_user_id(SharedPreferences prefs) {
        return prefs.getString(user_id, "");
    }

    public static void save_select_type(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, select_type, value);
    }

    public static String get_select_type(SharedPreferences prefs) {
        return prefs.getString(select_type, "");
    }

    public static void save_userType(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, user_type, value);
    }


    public static void save_price(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, PRICE, value);
    }

    public static String get_price(SharedPreferences prefs) {

        return prefs.getString(PRICE, "");
    }


    public static void save_address(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, address, value);
    }

    public static String get_address(SharedPreferences prefs) {
        return prefs.getString(address, "");
    }

    public static void save_image(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, image, value);
    }

    public static String get_image(SharedPreferences prefs) {
        return prefs.getString(image, "");
    }


    public static String getFacilityName(SharedPreferences prefs) {
        return prefs.getString(facilityName,"");
    }

    public static void save_FacilityName(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, facilityName, value);

    }

    public static String getAppoitment(SharedPreferences prefs) {
        return prefs.getString(appoitment,"");
    }

    public static void save_Appoitment(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, appoitment, value);

    }

    public static String getGender(SharedPreferences prefs) {
        return prefs.getString(select_gender,"");
    }

    public static void save_Gender(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, select_gender, value);

    }


    public static String getPersonIn(SharedPreferences prefs) {
        return prefs.getString(select_PersionIN,"");
    }

    public static void save_PersonIn(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, select_PersionIN, value);

    }




    public static void save_type(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, type, value);
    }

    public static String get_type(SharedPreferences prefs) {
        return prefs.getString(type, "");
    }


    public static void save_wallet(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, Wallet, value);
    }

    public static String get_wallet(SharedPreferences prefs) {
        return prefs.getString(Wallet, "");
    }

    public static void save_device_token(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, device_token, value);
    }

    public static String get_device_token(SharedPreferences prefs) {
        return prefs.getString(device_token, "");
    }

    public static void save_device(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, device, value);
    }

    public static String get_device(SharedPreferences prefs) {
        return prefs.getString(device, "");
    }

    public static void save_firebaseId(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, FirebaseId, value);
    }

    public static String get_firebaseId(SharedPreferences prefs) {
        return prefs.getString(FirebaseId, "");
    }

    public static void save_socialType(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, SocialType, value);
    }

    public static String get_socialType(SharedPreferences prefs) {
        return prefs.getString(SocialType, "");
    }

    public static void save_socialId(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, SocialId, value);
    }

    public static String get_socialId(SharedPreferences prefs) {
        return prefs.getString(SocialId, "");
    }

    ////////////////////////////////////////

    public static void save_bankusername(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, BANK_USER_NAME, value);
    }

    public static String get_bankusername(SharedPreferences prefs) {
        return prefs.getString(BANK_USER_NAME, "");
    }

    public static void save_routing_no(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, ROUTING_NUMBER, value);
    }

    public static String get_routing_no(SharedPreferences prefs) {
        return prefs.getString(ROUTING_NUMBER, "");
    }

    public static void save_account_no(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, ACCOUNT_NUMBER, value);
    }

    public static String get_account_no(SharedPreferences prefs) {
        return prefs.getString(ACCOUNT_NUMBER, "");
    }

    public static void save_account_type(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, ACCOUNT_TYPE, value);
    }

    public static String get_account_type(SharedPreferences prefs) {
        return prefs.getString(ACCOUNT_TYPE, "");
    }

    public static void save_branch_name(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, BRANCH_NAME, value);
    }

    public static String get_branch_name(SharedPreferences prefs) {
        return prefs.getString(BRANCH_NAME, "");
    }





    ////////////////////////////////////////

    public static void save_BType(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, BType, value);
    }

    public static String get_BType(SharedPreferences prefs) {
        return prefs.getString(BType, "");
    }

    public static void save_BName(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, BName, value);
    }

    public static String get_BName(SharedPreferences prefs) {
        return prefs.getString(BName, "");
    }

    public static void save_BEmail(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, BEmail, value);
    }

    public static String get_BEmail(SharedPreferences prefs) {
        return prefs.getString(BEmail, "");
    }

    public static void save_Baddress(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, Baddress, value);
    }

    public static String get_Baddress(SharedPreferences prefs) {
        return prefs.getString(Baddress, "");
    }

    public static void save_Bphone(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, Bphone, value);
    }

    public static String get_Bphone(SharedPreferences prefs) {
        return prefs.getString(Bphone, "");
    }

    public static void save_BLat(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, BLat, value);
    }

    public static String get_BLat(SharedPreferences prefs) {
        return prefs.getString(BLat, "");
    }

    public static void save_Blong(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, Blong, value);
    }

    public static String get_Blong(SharedPreferences prefs) {
        return prefs.getString(Blong, "");
    }



}


package com.selfnv_payment.utilities;

import android.content.Context;

import java.math.BigDecimal;

/**
 * This class is where we store the constants
 */
public final class Constants {

    public static Context CONTEXT;


    // APPLICATION PREFERENCE FILE NAME
    public static final String PREF_FILE = "PREF_SELF";


    //Preferance variable
    public static final String PREF_TOTAL_AMOUT = "PREF_TOTAL_AMOUNT";

    // Firebase locations
    public static final String FIREBASE_USER_LOCATION = "users";
    public static final String FIREBASE_USER_LOG_LOCATION = "usersLog";
    public static final String FIREBASE_ITEM_MASTER_LOCATION = "itemMaster";
    public static final String FIREBASE_ITEM_MASTER_DATE_LOCATION = "itemMasterDATE";
    public static final String FIREBASE_ITEM_MASTER_IMAGE_LOCATION = "itemImage";
    public static final String FIREBASE_TRIGGER_LOCATION = "trigger";
    public static final String FIREBASE_SALES_DETAIL_PENDING_LOCATION = "salesDetailPending";
    public static final String FIREBASE_OPEN_ACCOUNT_LOCATION = "openAccount";
    public static final String FIREBASE_PAID_SALES_HEADER_LOCATION = "paidSalesHeader";
    public static final String FIREBASE_PAID_SALES_DETAIL_LOCATION = "paidSalesDetail";
    public static final String FIREBASE_PAID_SALES_IMAGE_LOCATION = "paidSalesImage";

    public static final String FIREBASE_PAYMENT_LOCATION = "payment";
    public static final String FIREBASE_OPEN_SALES_HEADER_LOCATION = "openSalesHeader";
    public static final String FIREBASE_OPEN_SALES_DETAIL_LOCATION = "openSalesDetail";
    public static final String FIREBASE_ARCHIVE_SALES_HEADER_LOCATION = "archiveSalesHeader";
    public static final String FIREBASE_ARCHIVE_SALES_DETAIL_LOCATION = "archiveSalesDetail";
    public static final String FIREBASE_ARCHIVE_PAYMENT_LOCATION = "archivePayment";
    public static final String FIREBASE_YEAR_MONTH_LOCATION = "yearMonth";
    public static final String FIREBASE_ZERO_OUT_LOCATION = "zeroOut";

    public static final String FIREBASE_ITEM_NAME = "itemName";
    public static final String FIREBASE_ITEM_DESC = "itemDesc";


    // Firebase property
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";
    public static final String FIREBASE_PROPERTY_USERS_STATUS = "status";
    public static final String FIREBASE_PROPERTY_CONNECTION_STATUS = "connectionsStatus";
    public static final String FIREBASE_PROPERTY_LAST_ONLINE = "lastOnline";
    public static final String FIREBASE_PROPERTY_IMAGE_URL = "imageUrl";
    public static final String FIREBASE_PROPERTY_TOTAL_AMOUNT = "totalAmount";


    // Local storage SharedPreferences common data
    public static final String COMMON_DATA_SHARED_PREFERENCES = "COMMON_DATA_SHARED_PREFERENCES";

    // Key local storage SharedPreferences data
    public static final String KEY_USERS_EMAIL = "KEY_USERS_EMAIL";
    public static final String KEY_USERS_NAME = "KEY_USERS_NAME";
    public static final String KEY_USERS_UID = "KEY_USERS_UID";
    public static final String KEY_USER_PHOTO = "KEY_USER_PHOTO";




    // ItemMasterAddEditCallbacks Interface request code
    public static final int REQUEST_CODE_DIALOG_ONE = 1;
    public static final int REQUEST_CODE_DIALOG_TWO = 2;
    public static final int REQUEST_CODE_DIALOG_THREE = 3;


    // SalesDetailCallbacks
    public static final int REQUEST_CODE_SALES_DIALOG_ONE = 1;
    public static final int REQUEST_CODE_SALES_DIALOG_TWO = 2;

    // SalesPendingAddFormDialogFragment
    public static final int REQUEST_CODE_SALES_ADD_ITEM_LOOPUP = 1;

    // OpenSalesAddFormDialogFragment
    public static final int REQUEST_CODE_OPEN_SALES_ADD_ITEM_LOOKUP = 2;

    // Tab3FormDialogFragment
    public static final int REQUEST_CODE_TAB3_ITEM_LOOPUP = 3;


    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}

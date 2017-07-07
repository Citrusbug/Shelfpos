
package com.selfnv_payment.utilities;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This is the contract data with the ContentProvider
 */
public class ContractData {

    // Constant for the content URI
    public static final String SCHEME = "content://";
    public static final String AUTHORITY =
            "com.shelftastic.www.shelftastic.data.ContentProviderPointOfSale";
    public static final String URI = SCHEME + AUTHORITY;

    // To make the CONTENT_URI we need SCHEME, AUTHORITY
    public static final Uri BASE_CONTENT_URI = Uri.parse(URI);

    // Directories
    public static final String DIR_TRIGGER_ITEM_MASTER = "triggeritemmaster";
    public static final String DIR_ITEM_MASTER = "itemmasterdir";
    public static final String DIR_SALES_DETAIL_PENDING = "salesdetailpending";


    /**
     * Trigger Item Master Entry
     */
    public static final class TriggerItemMasterEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(DIR_TRIGGER_ITEM_MASTER).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + AUTHORITY + "/" + DIR_TRIGGER_ITEM_MASTER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item" + AUTHORITY + "/" + DIR_TRIGGER_ITEM_MASTER;

        public static final String TABLE_NAME = "triggerItem";

        // Column
        public static final String COLUMN_TIME_STAMP = "timestamps";

        // Column Index
        public static final int INDEX_COL_TRIGGERL_ID = 0;
        public static final int INDEX_COL_TIME_STAMP = 1;

        // URI
        public static Uri buildTriggerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


    /**
     * Item Master Entry
     */
    public static final class ItemMasterEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(DIR_ITEM_MASTER).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + AUTHORITY + "/" + DIR_ITEM_MASTER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item" + AUTHORITY + "/" + DIR_ITEM_MASTER;

        public static final String TABLE_NAME = "itemMasterTable";

        // Column
        public static final String COLUMN_PUSH_KEY = "column_push_key";
        public static final String COLUMN_ITEM_NUMBER = "column_item_number";
        public static final String COLUMN_ITEM_DESC = "column_item_desc";
        public static final String COLUMN_ITEM_UNIT = "column_item_unit";
        public static final String COLUMN_ITEM_PRICE = "column_item_price";

        public static final String COLUMN_ITEM_NAME = "column_item_name";
        public static final String COLUMN_ITEM_SOH = "column_item_SOH";
        public static final String COLUMN_ITEM_FACINGS = "column_item_Facings";
        public static final String COLUMN_ITEM_MOQ = "column_item_MOQ";
        public static final String COLUMN_ITEM_MDQ = "column_item_MDQ";

        // Index column
        public static final int INDEX_COL_ID = 0;
        public static final int INDEX_COL_PUSH_KEY = 1;
        public static final int INDEX_COL_ITEM_NUMBER = 2;
        public static final int INDEX_COL_ITEM_DESC = 3;
        public static final int INDEX_COL_ITEM_UNIT = 4;
        public static final int INDEX_COL_ITEM_PRICE = 5;

        public static final int INDEX_COL_ITEM_NAME = 6;
        public static final int INDEX_COL_ITEM_SOH = 7;
        public static final int INDEX_COL_ITEM_FACINGS = 8;
        public static final int INDEX_COL_ITEM_MOQ = 9;
        public static final int INDEX_COL_ITEM_MDQ = 10;



        // URI
        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class SalesDetailPendingEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(DIR_SALES_DETAIL_PENDING).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + AUTHORITY + "/" + DIR_SALES_DETAIL_PENDING;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item" + AUTHORITY + "/" + DIR_SALES_DETAIL_PENDING;

        public static final String TABLE_NAME = "salesDetailPending";

        // Column
        public static final String COLUMN_PUSH_KEY = "column_push_key";
        public static final String COLUMN_ITEM_NUMBER = "column_item_number";
        public static final String COLUMN_ITEM_DESC = "column_item_desc";
        public static final String COLUMN_ITEM_UNIT = "column_item_unit";
        public static final String COLUMN_ITEM_PRICE = "column_item_price";
        public static final String COLUMN_ITEM_QUANTITY = "column_item_quantity";
        public static final String COLUMN_ITEM_DISCOUNT = "column_item_discount";
        public static final String COLUMN_ITEM_DISCOUNT_AMOUNT = "column_item_discount_amount";
        public static final String COLUMN_ITEM_AMOUNT = "column_item_amount";


        public static final String COLUMN_ITEM_NAME = "column_item_name";
        public static final String COLUMN_ITEM_SOH = "column_item_soh";
        public static final String COLUMN_ITEM_FACINGS = "column_item_facings";
        public static final String COLUMN_ITEM_MOQ = "column_item_moq";
        public static final String COLUMN_ITEM_MDQ = "column_item_mdq";





        // Index column
        public static final int INDEX_COL_ID = 0;
        public static final int INDEX_COL_PUSH_KEY = 1;
        public static final int INDEX_COL_ITEM_NUMBER = 2;
        public static final int INDEX_COL_ITEM_DESC = 3;
        public static final int INDEX_COL_ITEM_UNIT = 4;
        public static final int INDEX_COL_ITEM_PRICE = 5;
        public static final int INDEX_COL_ITEM_QUANTITY = 6;
        public static final int INDEX_COL_ITEM_DISCOUNT = 7;
        public static final int INDEX_COL_ITEM_DISCOUNT_AMOUNT = 8;
        public static final int INDEX_COL_ITEM_AMOUNT = 9;

        // URI
        public static Uri buildSalesDetailPendingUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}

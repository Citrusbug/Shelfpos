
package com.selfnv_payment.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is the model class for item images
 */
public class ItemImageModel implements Parcelable {

    //State
    private String imageUrl;
    private String itemPushKey;


    /**
     * Empty constructor
     */
    public ItemImageModel() {

    }

    /**
     * Constructor
     * @param imageUrl      The url for this image or the file name
     * @param itemPushKey   The push key of an item master created by Server
     */
    public ItemImageModel(String imageUrl, String itemPushKey){

        this.imageUrl = imageUrl;
        this.itemPushKey = itemPushKey;

    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemPushKey() {
        return itemPushKey;
    }

    public void setItemPushKey(String itemPushKey) {
        this.itemPushKey = itemPushKey;
    }

    protected ItemImageModel(Parcel in) {
        imageUrl = in.readString();
        itemPushKey = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(itemPushKey);
    }

    @SuppressWarnings("unused")
    public static final Creator<ItemImageModel> CREATOR = new Creator<ItemImageModel>() {
        @Override
        public ItemImageModel createFromParcel(Parcel in) {
            return new ItemImageModel(in);
        }

        @Override
        public ItemImageModel[] newArray(int size) {
            return new ItemImageModel[size];
        }
    };
}

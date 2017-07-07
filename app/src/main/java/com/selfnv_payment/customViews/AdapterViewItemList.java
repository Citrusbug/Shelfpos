

package com.selfnv_payment.customViews;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.selfnv_payment.R;


/**
 * This is a custom view class that combines views for the layout of the
 * item list card, this card will be used to element in the recycler view
 * that bind data for the ItemModel from /item/...
 */
public class AdapterViewItemList extends LinearLayout {


    // Members variables
    private LinearLayout mRoot, mSubTextDetail;
    private FrameLayout mImageFrame;
    private TextView mItemDesc, mItemPrice, mSoh, mItemName,mFacings,mMinimumOrderQuantity, mMinimumDisplayQuantity,mTax;
    private RecyclerView mItemImage;
    private ImageButton mPopupMenuViewObject;
    private PopupMenu mPopupMenu;


    /**
     * Constructor
     * @param context       Activity context
     */
    public AdapterViewItemList(Context context) {
        super(context);
        init(context);
    }


    /**
     * Constructor
     * @param context       Activity context
     * @param attrs         AttributeSet object
     */
    public AdapterViewItemList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    /**
     * Helper method to initialize the view group and the widget
     * @param context   Activity context
     */
    private void init(Context context) {


        // Initialize the layout file which is the root view of all widgets
        View mRootView = inflate(context, R.layout.adapter_view_item_list, this);

        // Initialize the view group and widgets
        mRoot = (LinearLayout) mRootView.findViewById(R.id.root);
        mImageFrame = (FrameLayout) mRootView.findViewById(R.id.image_frame);
        mItemImage = (RecyclerView) mRootView.findViewById(R.id.item_image);
        mSubTextDetail = (LinearLayout) mRootView.findViewById(R.id.sub_text_detail);
        mItemDesc = (TextView) mRootView.findViewById(R.id.item_desc);
        mItemPrice = (TextView) mRootView.findViewById(R.id.item_price);

        mMinimumDisplayQuantity = (TextView) mRootView.findViewById(R.id.item_minimum_display_quantity);
        mMinimumOrderQuantity = (TextView) mRootView.findViewById(R.id.item_minimum_order_quantity);
        mFacings = (TextView) mRootView.findViewById(R.id.item_facings);
        mSoh = (TextView) mRootView.findViewById(R.id.item_soh);
        mItemName = (TextView) mRootView.findViewById(R.id.item_name);
        mTax = (TextView)mRootView.findViewById(R.id.item_tax);

        mPopupMenuViewObject = (ImageButton) mRootView.findViewById(R.id.item_popup_menu);
        mPopupMenuViewObject.setBackgroundColor(Color.TRANSPARENT);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }


    /**
     * Code below are the getters and setters
     */
    public LinearLayout getmRoot() {
        return mRoot;
    }

    public void setmRoot(LinearLayout mRoot) {
        this.mRoot = mRoot;
    }

    public LinearLayout getmSubTextDetail() {
        return mSubTextDetail;
    }

    public void setmSubTextDetail(LinearLayout mSubTextDetail) {
        this.mSubTextDetail = mSubTextDetail;
    }

    public FrameLayout getmImageFrame() {
        return mImageFrame;
    }

    public void setmImageFrame(FrameLayout mImageFrame) {
        this.mImageFrame = mImageFrame;
    }

    public TextView getmItemDesc() {
        return mItemDesc;
    }

    public void setmItemDesc(TextView mItemDesc) {
        this.mItemDesc = mItemDesc;
    }

    public TextView getmItemPrice() {
        return mItemPrice;
    }

    public void setmItemPrice(TextView mItemPrice) {
        this.mItemPrice = mItemPrice;
    }

    public TextView getmSoh() {
        return mSoh;
    }

    public void setmSoh(TextView mSoh) {
        this.mSoh = mSoh;
    }

    public TextView getmItemName() {
        return mItemName;
    }

    public void setmItemName(TextView mItemName) {
        this.mItemName = mItemName;
    }

    public TextView getmFacings() {
        return mFacings;
    }

    public void setmFacings(TextView mFacings) {
        this.mFacings = mFacings;
    }

    public TextView getmMinimumOrderQuantity() {
        return mMinimumOrderQuantity;
    }

    public void setmMinimumOrderQuantity(TextView mMinimumOrderQuantity) {
        this.mMinimumOrderQuantity = mMinimumOrderQuantity;
    }

    public TextView getmMinimumDisplayQuantity() {
        return mMinimumDisplayQuantity;
    }

    public void setmMinimumDisplayQuantity(TextView mMinimumDisplayQuantity) {
        this.mMinimumDisplayQuantity = mMinimumDisplayQuantity;
    }

    public RecyclerView getmItemImage() {
        return mItemImage;
    }

    public void setmItemImage(RecyclerView mItemImage) {
        this.mItemImage = mItemImage;
    }

    public ImageButton getmPopupMenuViewObject() {
        return mPopupMenuViewObject;
    }

    public void setmPopupMenuViewObject(ImageButton mPopupMenuViewObject) {
        this.mPopupMenuViewObject = mPopupMenuViewObject;
    }

    public PopupMenu getmPopupMenu() {
        return mPopupMenu;
    }

    public void setmPopupMenu(PopupMenu mPopupMenu) {
        this.mPopupMenu = mPopupMenu;
    }

    public TextView getmTax() {
        return mTax;
    }

    public void setmTax(TextView mTax) {
        this.mTax = mTax;
    }
}

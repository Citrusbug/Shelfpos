package com.selfnv_payment.Fragment;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.selfnv_payment.Listner.ActivityCommunicator;
import com.selfnv_payment.Model.ItemImageModel;
import com.selfnv_payment.Model.ItemModel;
import com.selfnv_payment.R;
import com.selfnv_payment.customViews.AdapterViewItemList;
import com.selfnv_payment.utilities.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.selfnv_payment.utilities.ContractData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * This fragment will display a list of item using the adapter from FirebaseUI
 */
public class IteamFragment extends Fragment implements View.OnClickListener {


    // Constant
    private static final String TAG_LOG = IteamFragment.class.getSimpleName();
    private static final String KEY_DATA_ENCODED_EMAIL = TAG_LOG + "KEY_DATA_ENCODED_EMAIL";
    private static final String KEY_DATA_USER_NAME = TAG_LOG + "KEY_DATA_USER_NAME";
    private static final String KEY_DATA_USER_UID = TAG_LOG + "KEY_DATA_USER_UID";


    /**
     * The user login email encoded to match
     * the server spec
     */
    private String mUserEncodedEmail;

    /**
     * The user name from the server
     */
    private String mUserNama;

    /**
     * The user UID from the server
     */
    private String mUserUid;


    public MyAdapter mMyAdapter;


    // Views
    @Bind(R.id.item_master_list)
    RecyclerView mItemMasterList;
    @Bind(R.id.inputSearch)
    EditText inputSearch;
    @Bind(R.id.enter)
    TextView enter;


    /**
     * interface
     */
    ActivityCommunicator activityCommunicator;


    /**
     * {@link ItemModel}
     */
    private ItemModel mItemModel;

    /**
     * Empty constructor
     */
    public IteamFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Initialize the view
        final View view = inflater.inflate(R.layout.fragment_iteam, container, false);

        // Initialize the ButterKnife
        ButterKnife.bind(this, view);

        // Set more efficient
        mItemMasterList.setHasFixedSize(true);


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());

                if (mMyAdapter != null) {
                    mMyAdapter.cleanup();
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child("FQlruak1uXbF4Nv3MfUn1jRj3442")
                        .child(Constants.FIREBASE_ITEM_MASTER_LOCATION);

                // Initialize the adapter
                mMyAdapter = new MyAdapter(ItemModel.class, R.layout.adapter_each_card_in_item_list,
                        ViewHolder.class, databaseReference.orderByChild(Constants.FIREBASE_ITEM_DESC)
                        .startAt(text).endAt(text + "~").limitToFirst(5), "FQlruak1uXbF4Nv3MfUn1jRj3442") {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, final ItemModel model, int position) {

                        // Get the pushKey()
                        DatabaseReference keyRef = getRef(viewHolder.getLayoutPosition());

                        // Convert the keyRef to type String, then pass it to the UI
                        final String pushKey = keyRef.getKey();

                        // Get the item number
                        String itemNumber = model.getItemNumber();

                        // Get the item desc
                        String itemDesc = model.getItemDesc();
                        viewHolder.mAdapterView.getmItemDesc().setText(itemDesc);

                        // Get Unit of measure
                        String itemUnit = model.getUnitOfMeasure();

                        // Get item price
                        String itemPrice = model.getItemPrice();
                        viewHolder.mAdapterView.getmItemPrice().setText("Price : " + itemPrice);

                        // get SOH
                        String itemSoh = model.getItemSoh();
                        viewHolder.mAdapterView.getmSoh().setText("SOH : " + itemSoh);

                        // get Facings
                        String facings = model.getFacings();
                        viewHolder.mAdapterView.getmFacings().setText("Facing : " + facings);

                        // get minimum order qty
                        String minimumOrderQuantity = model.getMinimumOrderQuantity();
                        viewHolder.mAdapterView.getmMinimumOrderQuantity().setText("MOQ : " + minimumOrderQuantity);

                        // get min display qty
                        String minimumDisplayQuantity = model.getMinimumDisplayQuantity();
                        viewHolder.mAdapterView.getmMinimumDisplayQuantity().setText("MDQ : " + minimumDisplayQuantity);

                        //get tax

                        String tax = model.getItemTax();
                        viewHolder.mAdapterView.getmTax().setText("TAX : " + tax + " %");

                        // get name
                        String itemName = model.getItemName();
                        viewHolder.mAdapterView.getmItemName().setText("Name : " + itemName);

                        viewHolder.mAdapterView.getmRoot().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String itemNumber = model.getItemNumber();

                                activityCommunicator.onComunicateItemNumber(itemNumber);

                            }
                        });

                        /**
                         * Nested recycler view for item images
                         */


                        // Get the reference to the Item Image
                        DatabaseReference imageRef =
                                FirebaseDatabase.getInstance().getReference()
                                        .child("FQlruak1uXbF4Nv3MfUn1jRj3442")
                                        .child(Constants.FIREBASE_ITEM_MASTER_IMAGE_LOCATION)
                                        .child(pushKey);


                        // This adapter will display only image for each element that matches the item master pushKey
                        mNestedImageAdapter =
                                new FirebaseRecyclerAdapter<ItemImageModel, NestedImageViewHolder>(ItemImageModel.class, R.layout.adapter_each_card_nested_image, NestedImageViewHolder.class, imageRef) {


                                    @Override
                                    protected void populateViewHolder(final NestedImageViewHolder viewHolder, ItemImageModel itemImageInstance, int position) {


                                        // Get the pushKey() for this image in this position
                                        DatabaseReference imageKeyRef = getRef(viewHolder.getLayoutPosition());

                                        // Convert the keyRef to type String, then pass it to the UI
                                        final String imageKeyRefString = imageKeyRef.getKey();


                                        /**
                                         * Code below will find the image from the local or server then populate them in the UI
                                         */

                                        // Initialize storage
                                        StorageReference imageStorageRef = FirebaseStorage.getInstance().getReference()
                                                .child("FQlruak1uXbF4Nv3MfUn1jRj3442")
                                                .child(Constants.FIREBASE_ITEM_MASTER_IMAGE_LOCATION)
                                                .child(pushKey);

                                        // Get the file path from the model
                                        String filePath = itemImageInstance.getImageUrl();

                                        // Get the file Uri
                                        Uri file = Uri.fromFile(new File(filePath));

                                        // Get the local file
                                        File localFile = new File(filePath);


                                        // Check if the image available in local
                                        if (localFile.exists()) {
                                            try {
                                                // Display the image
                                                Glide.with(getActivity())
                                                        .load("file://" + localFile)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(viewHolder.mItemImageNested);
                                            } catch (Exception e) {
                                                Log.e(TAG_LOG, e.getMessage());
                                            }


                                        } else {


                                            // If not available in local then download from the server
                                            imageStorageRef.child(file.getLastPathSegment())
                                                    .getDownloadUrl().addOnSuccessListener(getActivity(), new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {

                                                    try {
                                                        // Display the image
                                                        Glide.with(getActivity())
                                                                .load(uri)
                                                                .centerCrop()
                                                                .crossFade()
                                                                .into(viewHolder.mItemImageNested);
                                                    } catch (Exception e) {
                                                        Log.e(TAG_LOG, e.getMessage());
                                                    }

                                                }
                                            });

                                        }


                                    }

                                    @Override
                                    public void onBindViewHolder(NestedImageViewHolder holder, int position, List<Object> payloads) {
                                        super.onBindViewHolder(holder, position, payloads);

                                        // Add animation here..
                                    }


                                    @Override
                                    public int getItemViewType(int position) {

                                        // The layout for each element on the list
                                        return R.layout.adapter_each_card_nested_image;

                                    }


                                    @Override

                                    public NestedImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                                        // Initialize the view object
                                        View nested = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
                                        return new NestedImageViewHolder(nested);

                                    }
                                }; // End of nested image adapter


                        // For the nested item image
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

                        // For the nested item image
                        viewHolder.mAdapterView.getmItemImage().setHasFixedSize(true);

                        // For the nested item image
                        viewHolder.mAdapterView.getmItemImage().setLayoutManager(linearLayoutManager);

                        // For the nested item image
                        viewHolder.mAdapterView.getmItemImage().setAdapter(mNestedImageAdapter);



                    }
                    @Override
                    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
                        super.onBindViewHolder(holder, position, payloads);
                        // Add animation here..
                    }


                    @Override
                    public int getItemViewType(int position) {

                        // The layout for each element in the list
                        return R.layout.adapter_each_card_in_item_list;
                    }


                    /**
                     * It will crash if we don't override this method, like the following code
                     * @param parent        ViewGroup object
                     * @param viewType      The layout xml frmo the method getItemViewType
                     * @return View holder type
                     */
                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        // Initialize the view object
                        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

                        // Return the view holder and pass the view object as argument
                        return new ViewHolder(view);

                    }
                };

                mItemMasterList.setAdapter(mMyAdapter);



            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });


        /**
         * Code below is for the number of span depend device (Phone / tablet)
         * and orientation (portrait / landscape)
         */
      /*int columnCount;
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);

        if (isTablet && isLandscape) {
            columnCount = 5;
        } else if (!isTablet && isLandscape) {
            columnCount = 3;
        } else if (isTablet && !isLandscape) {
            columnCount = 4;
        } else {
            columnCount = 2;
        }*/

        // Initialize the LayoutManager for item master
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setReverseLayout(true);
        layoutManager1.setStackFromEnd(true);

        // Set the layout manager for the item master
        mItemMasterList.setLayoutManager(layoutManager);






        // Server reference location item master
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("FQlruak1uXbF4Nv3MfUn1jRj3442")
                .child(Constants.FIREBASE_ITEM_MASTER_LOCATION);

        // Initialize the adapter
        mMyAdapter = new MyAdapter(ItemModel.class, R.layout.adapter_each_card_in_item_list,
                ViewHolder.class, databaseReference, "FQlruak1uXbF4Nv3MfUn1jRj3442") {

            @Override
            protected void populateViewHolder(final ViewHolder viewHolder, final ItemModel model, int position) {

                // Get the pushKey()
                DatabaseReference keyRef = getRef(viewHolder.getLayoutPosition());

                // Convert the keyRef to type String, then pass it to the UI
                final String pushKey = keyRef.getKey();

                // Get the item number
                String itemNumber = model.getItemNumber();

                // Get the item desc
                String itemDesc = model.getItemDesc();
                viewHolder.mAdapterView.getmItemDesc().setText(itemDesc);

                // Get Unit of measure
                String itemUnit = model.getUnitOfMeasure();

                // Get item price
                String itemPrice = model.getItemPrice();
                viewHolder.mAdapterView.getmItemPrice().setText("Price : " + itemPrice);

                // get SOH
                String itemSoh = model.getItemSoh();
                viewHolder.mAdapterView.getmSoh().setText("SOH : " + itemSoh);

                // get Facings
                String facings = model.getFacings();
                viewHolder.mAdapterView.getmFacings().setText("Facing : " + facings);

                // get minimum order qty
                String minimumOrderQuantity = model.getMinimumOrderQuantity();
                viewHolder.mAdapterView.getmMinimumOrderQuantity().setText("MOQ : " + minimumOrderQuantity);

                // get min display qty
                String minimumDisplayQuantity = model.getMinimumDisplayQuantity();
                viewHolder.mAdapterView.getmMinimumDisplayQuantity().setText("MDQ : " + minimumDisplayQuantity);

                //get tax

                String tax = model.getItemTax();
                viewHolder.mAdapterView.getmTax().setText("TAX : " + tax + " %");

                // get name
                String itemName = model.getItemName();
                viewHolder.mAdapterView.getmItemName().setText("Name : " + itemName);


                /**
                 * click on the item of the recyclerview
                 */

                viewHolder.mAdapterView.getmRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String itemNumber = model.getItemNumber();

                        activityCommunicator.onComunicateItemNumber(itemNumber);

                    }
                });

                /**
                 * Nested recycler view for item images
                 */


                // Get the reference to the Item Image
                DatabaseReference imageRef =
                        FirebaseDatabase.getInstance().getReference()
                                .child("FQlruak1uXbF4Nv3MfUn1jRj3442")
                                .child(Constants.FIREBASE_ITEM_MASTER_IMAGE_LOCATION)
                                .child(pushKey);


                // This adapter will display only image for each element that matches the item master pushKey
                mNestedImageAdapter =
                        new FirebaseRecyclerAdapter<ItemImageModel, NestedImageViewHolder>(ItemImageModel.class, R.layout.adapter_each_card_nested_image, NestedImageViewHolder.class, imageRef) {


                            @Override
                            protected void populateViewHolder(final NestedImageViewHolder viewHolder, ItemImageModel itemImageInstance, int position) {


                                // Get the pushKey() for this image in this position
                                DatabaseReference imageKeyRef = getRef(viewHolder.getLayoutPosition());

                                // Convert the keyRef to type String, then pass it to the UI
                                final String imageKeyRefString = imageKeyRef.getKey();


                                /**
                                 * Code below will find the image from the local or server then populate them in the UI
                                 */

                                // Initialize storage
                                StorageReference imageStorageRef = FirebaseStorage.getInstance().getReference()
                                        .child("FQlruak1uXbF4Nv3MfUn1jRj3442")
                                        .child(Constants.FIREBASE_ITEM_MASTER_IMAGE_LOCATION)
                                        .child(pushKey);

                                // Get the file path from the model
                                String filePath = itemImageInstance.getImageUrl();

                                // Get the file Uri
                                Uri file = Uri.fromFile(new File(filePath));

                                // Get the local file
                                File localFile = new File(filePath);


                                // Check if the image available in local
                                if (localFile.exists()) {
                                    try {
                                        // Display the image
                                        Glide.with(getActivity())
                                                .load("file://" + localFile)
                                                .centerCrop()
                                                .crossFade()
                                                .into(viewHolder.mItemImageNested);
                                    } catch (Exception e) {
                                        Log.e(TAG_LOG, e.getMessage());
                                    }


                                } else {


                                    // If not available in local then download from the server
                                    imageStorageRef.child(file.getLastPathSegment())
                                            .getDownloadUrl().addOnSuccessListener(getActivity(), new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            try {
                                                // Display the image
                                                Glide.with(getActivity())
                                                        .load(uri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(viewHolder.mItemImageNested);
                                            } catch (Exception e) {
                                                Log.e(TAG_LOG, e.getMessage());
                                            }

                                        }
                                    });

                                }


                            }

                            @Override
                            public void onBindViewHolder(NestedImageViewHolder holder, int position, List<Object> payloads) {
                                super.onBindViewHolder(holder, position, payloads);

                                // Add animation here..
                            }


                            @Override
                            public int getItemViewType(int position) {

                                // The layout for each element on the list
                                return R.layout.adapter_each_card_nested_image;

                            }


                            @Override

                            public NestedImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                                // Initialize the view object
                                View nested = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
                                return new NestedImageViewHolder(nested);

                            }
                        }; // End of nested image adapter


                // For the nested item image
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

                // For the nested item image
                viewHolder.mAdapterView.getmItemImage().setHasFixedSize(true);

                // For the nested item image
                viewHolder.mAdapterView.getmItemImage().setLayoutManager(linearLayoutManager);

                // For the nested item image
                viewHolder.mAdapterView.getmItemImage().setAdapter(mNestedImageAdapter);

            }


            @Override
            public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
                super.onBindViewHolder(holder, position, payloads);
                // Add animation here..
            }


            @Override
            public int getItemViewType(int position) {

                // The layout for each element in the list
                return R.layout.adapter_each_card_in_item_list;
            }


            /**
             * It will crash if we don't override this method, like the following code
             * @param parent        ViewGroup object
             * @param viewType      The layout xml frmo the method getItemViewType
             * @return View holder type
             */
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                // Initialize the view object
                View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

                // Return the view holder and pass the view object as argument
                return new ViewHolder(view);

            }
        };

        // Set the adapter for the item master
        mItemMasterList.setAdapter(mMyAdapter);

        enter.setOnClickListener(this);


        // Return the view object
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Cleanup the listeners
        if (mMyAdapter != null) {
            mMyAdapter.cleanup();
        }
    }


    /**
     * This method is to receive messages from the MainActivity rather than using
     * onActivityForResult().
     *
     * @param message any message
     */
    public void setAnyResult(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.enter :

               /* if (!(inputSearch.getText().equals(""))){

                    if (mMyAdapter != null) {
                        mMyAdapter.cleanup();
                    }

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("FQlruak1uXbF4Nv3MfUn1jRj3442")
                            .child(Constants.FIREBASE_ITEM_MASTER_LOCATION);

                    // Initialize the adapter
                    mMyAdapter = new MyAdapter(ItemModel.class, R.layout.adapter_each_card_in_item_list,
                            ViewHolder.class, databaseReference.orderByChild(Constants.FIREBASE_ITEM_NAME)
                            .equalTo(inputSearch.getText().toString()), "FQlruak1uXbF4Nv3MfUn1jRj3442") {
                        @Override
                        protected void populateViewHolder(ViewHolder viewHolder, final ItemModel model, int position) {

                            // Get the pushKey()
                            DatabaseReference keyRef = getRef(viewHolder.getLayoutPosition());

                            // Convert the keyRef to type String, then pass it to the UI
                            final String pushKey = keyRef.getKey();

                            // Get the item number
                            String itemNumber = model.getItemNumber();

                            // Get the item desc
                            String itemDesc = model.getItemDesc();
                            viewHolder.mAdapterView.getmItemDesc().setText(itemDesc);

                            // Get Unit of measure
                            String itemUnit = model.getUnitOfMeasure();

                            // Get item price
                            String itemPrice = model.getItemPrice();
                            viewHolder.mAdapterView.getmItemPrice().setText("Price : " + itemPrice);

                            // get SOH
                            String itemSoh = model.getItemSoh();
                            viewHolder.mAdapterView.getmSoh().setText("SOH : " + itemSoh);

                            // get Facings
                            String facings = model.getFacings();
                            viewHolder.mAdapterView.getmFacings().setText("Facing : " + facings);

                            // get minimum order qty
                            String minimumOrderQuantity = model.getMinimumOrderQuantity();
                            viewHolder.mAdapterView.getmMinimumOrderQuantity().setText("MOQ : " + minimumOrderQuantity);

                            // get min display qty
                            String minimumDisplayQuantity = model.getMinimumDisplayQuantity();
                            viewHolder.mAdapterView.getmMinimumDisplayQuantity().setText("MDQ : " + minimumDisplayQuantity);

                            //get tax

                            String tax = model.getItemTax();
                            viewHolder.mAdapterView.getmTax().setText("TAX : " + tax + " %");

                            // get name
                            String itemName = model.getItemName();
                            viewHolder.mAdapterView.getmItemName().setText("Name : " + itemName);

                            viewHolder.mAdapterView.getmRoot().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String itemNumber = model.getItemNumber();

                                    activityCommunicator.onComunicateItemNumber(itemNumber);

                                }
                            });

                            /**
                             * Nested recycler view for item images
                             */


                            // Get the reference to the Item Image
                   /*         DatabaseReference imageRef =
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("FQlruak1uXbF4Nv3MfUn1jRj3442")
                                            .child(Constants.FIREBASE_ITEM_MASTER_IMAGE_LOCATION)
                                            .child(pushKey);


                            // This adapter will display only image for each element that matches the item master pushKey
                            mNestedImageAdapter =
                                    new FirebaseRecyclerAdapter<ItemImageModel, NestedImageViewHolder>(ItemImageModel.class, R.layout.adapter_each_card_nested_image, NestedImageViewHolder.class, imageRef) {


                                        @Override
                                        protected void populateViewHolder(final NestedImageViewHolder viewHolder, ItemImageModel itemImageInstance, int position) {


                                            // Get the pushKey() for this image in this position
                                            DatabaseReference imageKeyRef = getRef(viewHolder.getLayoutPosition());

                                            // Convert the keyRef to type String, then pass it to the UI
                                            final String imageKeyRefString = imageKeyRef.getKey();


                                            /**
                                             * Code below will find the image from the local or server then populate them in the UI
                                             */

                                            // Initialize storage
                       /*                     StorageReference imageStorageRef = FirebaseStorage.getInstance().getReference()
                                                    .child("FQlruak1uXbF4Nv3MfUn1jRj3442")
                                                    .child(Constants.FIREBASE_ITEM_MASTER_IMAGE_LOCATION)
                                                    .child(pushKey);

                                            // Get the file path from the model
                                            String filePath = itemImageInstance.getImageUrl();

                                            // Get the file Uri
                                            Uri file = Uri.fromFile(new File(filePath));

                                            // Get the local file
                                            File localFile = new File(filePath);


                                            // Check if the image available in local
                                            if (localFile.exists()) {
                                                try {
                                                    // Display the image
                                                    Glide.with(getActivity())
                                                            .load("file://" + localFile)
                                                            .centerCrop()
                                                            .crossFade()
                                                            .into(viewHolder.mItemImageNested);
                                                } catch (Exception e) {
                                                    Log.e(TAG_LOG, e.getMessage());
                                                }


                                            } else {


                                                // If not available in local then download from the server
                                                imageStorageRef.child(file.getLastPathSegment())
                                                        .getDownloadUrl().addOnSuccessListener(getActivity(), new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {

                                                        try {
                                                            // Display the image
                                                            Glide.with(getActivity())
                                                                    .load(uri)
                                                                    .centerCrop()
                                                                    .crossFade()
                                                                    .into(viewHolder.mItemImageNested);
                                                        } catch (Exception e) {
                                                            Log.e(TAG_LOG, e.getMessage());
                                                        }

                                                    }
                                                });

                                            }


                                        }

                                        @Override
                                        public void onBindViewHolder(NestedImageViewHolder holder, int position, List<Object> payloads) {
                                            super.onBindViewHolder(holder, position, payloads);

                                            // Add animation here..
                                        }


                                        @Override
                                        public int getItemViewType(int position) {

                                            // The layout for each element on the list
                                            return R.layout.adapter_each_card_nested_image;

                                        }


                                        @Override

                                        public NestedImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                                            // Initialize the view object
                                            View nested = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
                                            return new NestedImageViewHolder(nested);

                                        }
                                    }; // End of nested image adapter


                            // For the nested item image
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

                            // For the nested item image
                            viewHolder.mAdapterView.getmItemImage().setHasFixedSize(true);

                            // For the nested item image
                            viewHolder.mAdapterView.getmItemImage().setLayoutManager(linearLayoutManager);

                            // For the nested item image
                            viewHolder.mAdapterView.getmItemImage().setAdapter(mNestedImageAdapter);



                        }
                        @Override
                        public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
                            super.onBindViewHolder(holder, position, payloads);
                            // Add animation here..
                        }


                        @Override
                        public int getItemViewType(int position) {

                            // The layout for each element in the list
                            return R.layout.adapter_each_card_in_item_list;
                        }


                        /**
                         * It will crash if we don't override this method, like the following code
                         * @param parent        ViewGroup object
                         * @param viewType      The layout xml frmo the method getItemViewType
                         * @return View holder type
                         */
                   /*     @Override
                        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                            // Initialize the view object
                            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

                            // Return the view holder and pass the view object as argument
                            return new ViewHolder(view);

                        }
                    };

                    mItemMasterList.setAdapter(mMyAdapter);
                }*/
                break;
        }
    }


    /**
     * View holder class for Item master
     */
    public class ViewHolder extends RecyclerView.ViewHolder {


        // Declare the custom view adapter
        public AdapterViewItemList mAdapterView;

        public String string;

        /**
         * Constructor
         *
         * @param itemView Object
         */
        public ViewHolder(View itemView) {
            super(itemView);

            // Initialize the customer adapter
            mAdapterView = (AdapterViewItemList) itemView.findViewById(R.id.adapter_custom_view_for_item_list);
        }
    }


    /**
     * Nested view holder class for the item image
     */
    public class NestedImageViewHolder extends RecyclerView.ViewHolder {

        // Views
        @Bind(R.id.item_image_nested)
        ImageView mItemImageNested;


        /**
         * Constructor
         *
         * @param itemView view object
         */
        public NestedImageViewHolder(View itemView) {
            super(itemView);

            // Initialize the butterKnife
            ButterKnife.bind(this, itemView);

        }
    }


    /**
     * Abstract class for the adapter
     */
    public abstract class MyAdapter extends FirebaseRecyclerAdapter<ItemModel, ViewHolder> {


        /**
         * The user UID
         */
        protected String mAdapterUserUid;

        /**
         * The Adapter for the nested RV for images
         */
        protected FirebaseRecyclerAdapter<ItemImageModel, NestedImageViewHolder> mNestedImageAdapter;

        /**
         * ArrayList type string as container to store item master's push() key
         * for all the element that the user select it to be deleted using the ActionMode
         */
        protected ArrayList<String> mListOfDeletePushKeys;

        /**
         * Container for the view holder that about to be delete
         */
        protected ArrayList<IteamFragment.ViewHolder> mViewHolderList;

        /**
         * Boolean as flag if ActionMode is on or off.
         */
        public boolean isContextualMode = false;


        private List<ItemModel> itemModels = new ArrayList<>();
        private List<ItemModel> itemModelscopy = new ArrayList<>();
        private List<String> mKeys;
        private ChildEventListener mListener;


        /**
         * constructor
         *
         * @param modelClass      {@link ItemModel} instance
         * @param modelLayout     The xml layout file
         * @param viewHolderClass The view holder instance
         * @param ref             The database server ref
         * @param userUid         The user's UID
         */
        public MyAdapter(Class<ItemModel> modelClass, int modelLayout, Class<ViewHolder> viewHolderClass, Query ref, String userUid) {
            super(modelClass, modelLayout, viewHolderClass, ref);
            this.mAdapterUserUid = userUid;
        }

        @Override
        public void cleanup() {
            super.cleanup();
            if (mNestedImageAdapter != null) {
                mNestedImageAdapter.cleanup();
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            activityCommunicator = (ActivityCommunicator) context;
        } catch (Exception e) {

        }

    }
}
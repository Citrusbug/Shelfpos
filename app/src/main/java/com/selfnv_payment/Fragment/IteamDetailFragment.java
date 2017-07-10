package com.selfnv_payment.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.selfnv_payment.Adapter.IteamListAdapter;
import com.selfnv_payment.Listner.ActivityCommunicator;
import com.selfnv_payment.Model.ItemModel;
import com.selfnv_payment.R;
import com.selfnv_payment.utilities.Constants;
import com.selfnv_payment.utilities.ContractData;
import com.selfnv_payment.utilities.Pref;
import com.selfnv_payment.zxing.IntentIntegrator;
import com.selfnv_payment.zxing.IntentResult;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kishan on 27-06-2017.
 */


public class IteamDetailFragment extends Fragment implements IteamListAdapter.ITotalCount {
    @Nullable

    /**
     * PUSH KEY
     */

    private String mMasterPushKey;


    /**
     * Item Number is a field/state in {@link IteamDetailFragment}, this is useful
     * to store a barcode of any.
     */
    private String mItemNumber;


    LinearLayoutManager llm;


    private static final String TAG_LOG = IteamDetailFragment.class.getSimpleName();


    /**
     * recyclerview
     */
    private RecyclerView rv;

    @Bind(R.id.enter_no)
    EditText enter_no;
    @Bind(R.id.enter)
    TextView enter;

    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button4)
    Button button4;
    @Bind(R.id.button5)
    Button button5;
    @Bind(R.id.button6)
    Button button6;
    @Bind(R.id.button7)
    Button button7;
    @Bind(R.id.button8)
    Button button8;
    @Bind(R.id.button9)
    Button button9;
    @Bind(R.id.button0)
    Button button0;
    @Bind(R.id.del)
    Button del;

    @Bind(R.id.subtotal_amount)
    TextView subtotal_amount;
    @Bind(R.id.finalamount)
    TextView finalamountText;

    /**
     * int value for check increment decrement total value
     */
    String evalue = "";

    /**
     * total amount
     */
    private float totalAmount;

    /**
     *
     */

    private float taxAmount;

    /**
     * final amount
     */
    private float finalAmount;

    /**
     * {@link ItemModel}
     */
    private ItemModel mItemModel;


    /**
     * iteam list adapter
     */
    IteamListAdapter iteamListAdapter;

    /**
     * Empty constructor
     */
    public IteamDetailFragment() {

    }


    private Context mContext;

    /**
     * On create call back method
     *
     * @param savedInstanceState {@link Bundle} object
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        Constants.CONTEXT = mContext;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragemnt_iteamdetails, container, false);

        ButterKnife.bind(this, view);


        rv = ButterKnife.findById(view, R.id.rv);
        rv.setHasFixedSize(true);

        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);


        enter_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    evalue = "1";
                } else {
                    evalue = "";
                }
            }
        });


        enter_no.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                enter_no.setSelection(enter_no.getText().length());
                return false;
            }
        });


        enter_no.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        return view;
    }


    @OnClick(R.id.enter)
    void getIteamfromEnter() {

        if (!(enter_no.getText().length() == 0)) {

            mItemNumber = enter_no.getText().toString();

            enter_no.setText("");

            addItemTOLIst(mItemNumber);
        } else {
            enter_no.setError("Please add number");
        }
    }

    /*@OnTouch(R.id.enter_no)
    boolean getNumberTouch(){

        evalue="1";

       // Toast.makeText(getActivity(),evalue,Toast.LENGTH_LONG).show();

        return false;
    }*/

    @OnClick(R.id.button1)
    void btn1() {
        if (evalue.equals("1"))
            enter_no.setText(enter_no.getText() + "1");
    }

    @OnClick(R.id.button2)
    void btn2() {
        if (evalue.equals("1"))
            enter_no.setText(enter_no.getText() + "2");
    }

    @OnClick(R.id.button3)
    void btn3() {
        if (evalue.equals("1"))
            enter_no.setText(enter_no.getText() + "3");

    }

    @OnClick(R.id.button4)
    void btn4() {
        if (evalue.equals("1"))
            enter_no.setText(enter_no.getText() + "4");
    }

    @OnClick(R.id.button5)
    void btn5() {
        if (evalue.equals("1"))
            enter_no.setText(enter_no.getText() + "5");

    }

    @OnClick(R.id.button6)
    void btn6() {
        if (evalue.equals("1"))
            enter_no.setText(enter_no.getText() + "6");
    }

    @OnClick(R.id.button7)
    void btn7() {
        if (evalue.equals("1"))
            enter_no.setText(enter_no.getText() + "7");

    }

    @OnClick(R.id.button8)
    void btn8() {
        if (evalue.equals("1"))
            enter_no.setText(enter_no.getText() + "8");

    }

    @OnClick(R.id.button9)
    void btn9() {
        if (evalue.equals("1"))
            enter_no.setText(enter_no.getText() + "9");

    }

    @OnClick(R.id.button0)
    void btn0() {
        if (evalue.equals("1"))
            enter_no.setText(enter_no.getText() + "0");
    }

    @OnClick(R.id.del)
    void Del(){

        if (iteamListAdapter != null) {
            totalAmount = 0;
            subtotal_amount.setText("0.0" );
            finalamountText.setText("0.0");
            rv.setAdapter(null);
        }
    }

    /**
     * Barcode scanner
     */
    @OnClick(R.id.barcode_scanner)
    void setmBarcodeScanner() {

        // Get the barcode scanner and do the work, later pass the result to onActivityResult()
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();

    }


    /**
     * Android's callback
     *
     * @param requestCode It is the int number that define when invokeing method {@link android.app.Activity#startActivityForResult(Intent, int, Bundle)}
     * @param resultCode  It is the int number passed back {@link android.app.Activity#setResult}
     * @param data        The {@link Intent} instance
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Retrieve barcode scanner result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        /**
         * Check if null then no result
         */
        if (scanningResult != null) {

            // Get the result from the scanner
            String scanContent = scanningResult.getContents();
            //String scanFormat = scanningResult.getFormatName();

            Log.e("scan result", scanContent);

            mItemNumber = scanContent;

            addItemTOLIst(mItemNumber);

        } else {
            Toast toast = Toast.makeText(getActivity(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void addItemTOLIst(final String mItemNumber) {

        if (mItemNumber != null) {


            // Query the server and retrieve the item master matching scanned item number,
            // then store them in a local database for later use
            DatabaseReference itemNumberRef =
                    FirebaseDatabase.getInstance().getReference()
                            .child("FQlruak1uXbF4Nv3MfUn1jRj3442")
                            .child(Constants.FIREBASE_ITEM_MASTER_LOCATION);

            itemNumberRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    // instantiate the Item master model
                    mItemModel = new ItemModel();

                    // Initialize the modeList to pack the item master model
                    ArrayList<ItemModel> modelList = new ArrayList<>();

                    // Initialize the keyList to pack the push() keys
                    //ArrayList<String> keyList = new ArrayList<>();

                    // Initialize the content resolver
                    ContentResolver contentResolver = null;
                    try {
                        contentResolver = getActivity().getContentResolver();
                    } catch (Exception e) {
                        Log.e(TAG_LOG, e.getMessage());
                    }

                    // Delete all before insert new data, since we are going to store
                    // some data in local database
                    try {
                        if (contentResolver != null) {
                            contentResolver.delete(ContractData.ItemMasterEntry.CONTENT_URI, null, null);
                        }
                    } catch (Exception e) {
                        Log.e(TAG_LOG, e.getMessage());
                    }

                    // Initialize the content values
                    ContentValues contentValues = new ContentValues();

                    // Iterate
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {

                        // Instantiate the item model from the server
                        ItemModel itemModel = snap.getValue(ItemModel.class);

                        // Get the specific data that matches the itemNumber
                        if (itemModel.getItemNumber().matches(mItemNumber)) {

                            // Pack the model and the key to the list
                            modelList.add(itemModel);
                            //keyList.add(snap.getKey());

                            // Pack the data into the context values;
                            contentValues.put(ContractData.ItemMasterEntry.COLUMN_PUSH_KEY, snap.getKey());
                            contentValues.put(ContractData.ItemMasterEntry.COLUMN_ITEM_NUMBER, itemModel.getItemNumber());
                            contentValues.put(ContractData.ItemMasterEntry.COLUMN_ITEM_DESC, itemModel.getItemDesc());
                            contentValues.put(ContractData.ItemMasterEntry.COLUMN_ITEM_UNIT, itemModel.getUnitOfMeasure());
                            contentValues.put(ContractData.ItemMasterEntry.COLUMN_ITEM_PRICE, itemModel.getItemPrice());


                            mMasterPushKey = snap.getKey().toString();

                            // Insert them to local database, intentionally using try black rather than if block
                            // because we want to log the error if any.
                            try {
                                if (contentResolver != null) {
                                    contentResolver.insert(ContractData.ItemMasterEntry.CONTENT_URI, contentValues);
                                }
                            } catch (Exception e) {
                                Log.e(TAG_LOG, e.getMessage());
                            }
                        }
                    }

                    /**
                     * If the barcode scanner has found more than one item with the
                     * same item number then this code will work...
                     */
                    if (modelList.size() > 1) {

                        // Instantiate the dialog fragment object and pass the encoded email as an argument
                        // MiniLookupItemMaster miniLookupItemMaster = MiniLookupItemMaster.newInstance(Constants.REQUEST_CODE_SALES_ADD_ITEM_LOOPUP, mUserUid, null, null, null);

                        // Show the Dialog Fragment on the screen
                        //  miniLookupItemMaster.show(getFragmentManager(), FRAGMENT_DIALOG_ITEM_MASTER_FORM);

                        // Dismiss this dialog
                        // getDialog().dismiss();
                    }

                    /**
                     * Else if the barcode just found one item number that match only one
                     * then this code will work...
                     */
                    else if (modelList.size() == 1) {

                        if (llm.getItemCount() == 0) {
                            setupAdapter(modelList);
                        } else {
                            iteamListAdapter.add(modelList);
                        }
                    }

                    /**
                     * Else if the barcode just found no match then this code will work...
                     */
                    else if (modelList.size() == 0) {

                        Toast.makeText(getActivity(), "there is no iteam", Toast.LENGTH_LONG).show();

                        // This code will prevent to save an empty/not available item
                        // once we set the mItemModel to null it will not be able to save.
                        mItemModel = null;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG_LOG, databaseError.getMessage());
                }
            });
        }
    }


    private void setupAdapter(final ArrayList<ItemModel> mData) {

        if (mData.size() > 0) {
            iteamListAdapter = new IteamListAdapter(this, getActivity(), mData);
            rv.setAdapter(iteamListAdapter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void getTotal(int updateValue, float prize) {


        // totalAmount = Float.parseFloat(Pref.getValue(Constants.PREF_TOTAL_AMOUT, "0"));

        /**
         * qty decrese
         */
        if (updateValue == -1) {
            totalAmount -= prize;
        }
        /**
         * qty increse
         */
        else if (updateValue == +1) {
            totalAmount += prize;
        }
        /**
         * item added in list
         */
        else if (updateValue == 0) {
            totalAmount += prize;
        }
        /**
         * discount minus
         */
        else if(updateValue == 2){
            totalAmount -= prize;
        }

        /**
         * discount plus
         */
        else if(updateValue == 3){
            totalAmount += prize;
        }

        totalAmount = Constants.round(totalAmount, 2);
        finalAmount = totalAmount + taxAmount;

        subtotal_amount.setText("" + totalAmount);
        finalamountText.setText(""+finalAmount);

        //Pref.setValue(Constants.PREF_TOTAL_AMOUT, totalAmount + "");
    }
}

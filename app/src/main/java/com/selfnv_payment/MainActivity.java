package com.selfnv_payment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.selfnv_payment.Fragment.IteamDetailFragment;
import com.selfnv_payment.Listner.ActivityCommunicator;

public class MainActivity extends AppCompatActivity implements ActivityCommunicator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onComunicateItemNumber(String itemnumber) {

        IteamDetailFragment iteamDetailFragment = (IteamDetailFragment) getFragmentManager().findFragmentById(R.id.iteamDetail);

        iteamDetailFragment.addItemTOLIst(itemnumber);

    }
}

package com.selfnv_payment.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selfnv_payment.Fragment.IteamDetailFragment;
import com.selfnv_payment.Model.ItemModel;
import com.selfnv_payment.R;
import com.selfnv_payment.utilities.Constants;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by abc on 12-May-16.
 */
public class IteamListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ItemModel> data;
    private Context ctx;
    private Activity activity;
    View itemView;
    private ArrayList<ItemModel> arraylist;

    String qty = "1";


    ItemModel itemModel;

    String radidisc_btn = "disc_per";
    String oldqty = "";
    String newqty = "";


    String oldDisc = "";
    String newDisc = "";

    ITotalCount iTotalCount = null;

    public interface ITotalCount {
        public void getTotal(int updateValue, float prize);
    }

    public IteamListAdapter(ITotalCount context, Activity act, ArrayList<ItemModel> mData) {
        //this.ctx = context;
        this.data = mData;
        this.activity = act;
        this.arraylist = new ArrayList<ItemModel>();
        this.arraylist.addAll(mData);
        this.iTotalCount = (ITotalCount) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.row_item, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        itemModel = getItem(position);

        ((Viewholder) holder).item_name.setText(itemModel.getItemName());
        ((Viewholder) holder).item_price.setText(itemModel.getItemPrice());
        ((Viewholder) holder).item_tax.setText(itemModel.getItemTax());


        Float productsubTotal = Constants.round(1 * Float.parseFloat(itemModel.getItemPrice()), 2);
        ((Viewholder) holder).item_total.setText("" + Float.toString(productsubTotal));

        iTotalCount.getTotal(0, productsubTotal);


        ((Viewholder) holder).item_qty.setTag(position);
        ((Viewholder) holder).relative_item.setTag(position);
        ((Viewholder) holder).item_disc.setTag(position);

        ((Viewholder) holder).item_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final TextView textView = (TextView) view;
                if (textView.getTag() == view.getTag()) {

                    oldqty = ((Viewholder) holder).item_qty.getText().toString();

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle("Quantity");
                    alertDialog.setMessage("Enter Quantity");

                    final EditText input = new EditText(activity);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    input.setText(((Viewholder) holder).item_qty.getText().toString());
                    input.setSelection(input.getText().length());
                    alertDialog.setView(input);
                    //alertDialog.setIcon(R.drawable.key);

                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    newqty = input.getText().toString();

                                    if (!(newqty.equals(""))) {

                                        int item = Integer.parseInt(newqty);
                                        Float productsubTotal = Constants.round(item * Float.parseFloat(data.get(Integer.parseInt("" + textView.getTag())).getItemPrice()), 2);


                                        if (Integer.parseInt(newqty) > Integer.parseInt(oldqty)) {

                                            //set qty and totel of item
                                            ((Viewholder) holder).item_qty.setText("" + newqty);
                                            ((Viewholder) holder).item_total.setText("" + Float.toString(productsubTotal));

                                            //count change qty
                                            Integer changeqty = Integer.parseInt(newqty) - Integer.parseInt(oldqty);

                                            //count differance amount
                                            Float changePlustotal = Constants.round(changeqty * Float.parseFloat(data.get(Integer.parseInt("" + textView.getTag())).getItemPrice()), 2);

                                            //if discount allready then add to changeplus total
                                            if (!((Viewholder) holder).item_disc.getText().toString().equals("0")) {

                                                changePlustotal = changePlustotal + Float.parseFloat(((Viewholder) holder).item_disc.getText().toString());

                                                ((Viewholder) holder).item_disc.setText("0");
                                            }

                                            iTotalCount.getTotal(+1, changePlustotal);

                                        } else if (Integer.parseInt(newqty) < Integer.parseInt(oldqty)) {

                                            //set qty and total
                                            ((Viewholder) holder).item_qty.setText("" + newqty);
                                            ((Viewholder) holder).item_total.setText("" + Float.toString(productsubTotal));

                                            //count change qty
                                            Integer changeqty = Integer.parseInt(oldqty) - Integer.parseInt(newqty);

                                            //count differance amount
                                            Float changeMinustotal = Constants.round(changeqty * Float.parseFloat(data.get(Integer.parseInt("" + textView.getTag())).getItemPrice()), 2);

                                            //if discount allready then minuse to changeminus total
                                            if (!((Viewholder) holder).item_disc.getText().toString().equals("0")) {

                                                changeMinustotal = changeMinustotal - Float.parseFloat(((Viewholder) holder).item_disc.getText().toString());

                                                ((Viewholder) holder).item_disc.setText("0");
                                            }

                                            iTotalCount.getTotal(-1, changeMinustotal);
                                        }
                                    }
                                }
                            });

                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                }
            }
        });


        ((Viewholder) holder).item_disc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                oldDisc = ((Viewholder) holder).item_disc.getText().toString();

                final TextView textView = (TextView) view;
                if (textView.getTag() == view.getTag()) {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                    LayoutInflater inflater = activity.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.discount_dialog, null);
                    dialogBuilder.setView(dialogView);

                    final EditText edt = (EditText) dialogView.findViewById(R.id.discount);

                    final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.radioDiscGrp);

                    /**
                     * radigroup checked chenge listener
                     */
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                            switch (checkedId) {
                                case R.id.disc_per:
                                    radidisc_btn = "disc_per";
                                    break;
                                case R.id.disc:
                                    radidisc_btn = "disc";
                                    break;
                            }
                        }
                    });

                    dialogBuilder.setTitle("Discount");
                    dialogBuilder.setMessage("Enter Discount");
                    dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            if (!(edt.getText().toString().equals(""))) {

                                if (radidisc_btn.equals("disc_per")) {

                                    int qty = Integer.parseInt(((Viewholder) holder).item_qty.getText().toString());


                                    // Float totalprice = Float.parseFloat(((Viewholder) holder).item_total.getText().toString()) - discount;
                                    Float discount = Constants.round(qty * Float.parseFloat(data.get(Integer.parseInt("" + textView.getTag())).getItemPrice()) * (Float.parseFloat(edt.getText().toString()) / 100), 2);
                                    Float totalprice = (Float.parseFloat(((Viewholder) holder).item_qty.getText().toString()) * Float.parseFloat(data.get(Integer.parseInt("" + textView.getTag())).getItemPrice())) - discount;

                                    ((Viewholder) holder).item_disc.setText("" + Float.toString(discount));
                                    ((Viewholder) holder).item_total.setText("" + Float.toString(totalprice));

                                    if (!((Viewholder) holder).item_disc.getText().toString().equals("0")) {

                                        Float old_disc = Float.parseFloat(oldDisc);

                                        if (discount > old_disc) {
                                            discount = discount - old_disc;

                                            iTotalCount.getTotal(2, discount);
                                        } else if (old_disc > discount) {
                                            discount = old_disc - discount;

                                            iTotalCount.getTotal(3, discount);
                                        }
                                    } else {
                                        iTotalCount.getTotal(2, discount);
                                    }
                                } else if (radidisc_btn.equals("disc")) {

                                    //  int qty = Integer.parseInt(((Viewholder)holder).item_qty.getText().toString());

                                    Float discount = Float.parseFloat(edt.getText().toString());
                                    Float totalprice = (Float.parseFloat(((Viewholder) holder).item_qty.getText().toString()) * Float.parseFloat(data.get(Integer.parseInt("" + textView.getTag())).getItemPrice())) - discount;

                                    ((Viewholder) holder).item_disc.setText("" + Float.toString(discount));
                                    ((Viewholder) holder).item_total.setText("" + Float.toString(totalprice));

                                    //allready some discount
                                    if (!((Viewholder) holder).item_disc.getText().toString().equals("0")) {

                                        Float old_disc = Float.parseFloat(oldDisc);

                                        if (discount > old_disc) {
                                            discount = discount - old_disc;

                                            iTotalCount.getTotal(2, discount);
                                        } else if (old_disc > discount) {
                                            discount = old_disc - discount;

                                            iTotalCount.getTotal(3, discount);
                                        }

                                    }
                                    else {
                                        iTotalCount.getTotal(2, discount);
                                    }
                                }
                            }
                        }
                    });
                    dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //pass
                        }
                    });
                    AlertDialog b = dialogBuilder.create();
                    b.show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ItemModel getItem(int pos) {
        return data.get(pos);
    }


    public void add(List<ItemModel> items) {
        int previousDataSize = this.data.size();
        this.data.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    private static class Viewholder extends RecyclerView.ViewHolder {

        protected TextView item_name;
        protected TextView item_price;
        public TextView item_tax;
        public TextView item_total;
        public TextView item_qty;
        public TextView item_disc;
        public RelativeLayout relative_item;


        public Viewholder(View itemView) {
            super(itemView);


            item_name = ButterKnife.findById(itemView, R.id.item_name);
            item_price = ButterKnife.findById(itemView, R.id.item_price);
            item_tax = ButterKnife.findById(itemView, R.id.item_tax);
            item_qty = ButterKnife.findById(itemView, R.id.item_qty);
            item_total = ButterKnife.findById(itemView, R.id.item_total);
            item_disc = ButterKnife.findById(itemView, R.id.item_disc);
            relative_item = ButterKnife.findById(itemView, R.id.relative_item);

        }
    }
}

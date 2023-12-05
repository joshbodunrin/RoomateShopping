package edu.uga.cs.roomateshopping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditItemDialogFragment extends DialogFragment {
    public static final int SAVE = 1; // Update item

    public static final int DELETE = 2; // delete item

    public static final int PURCHASE = 3; // purchase item

    private EditText itemView;

    private EditText priceView;

    private Button cancelButton;
    private Button deleteButton;
    private Button addButton;

    private Button editButton;

    int position;

    String key;

    String item;

    double price;

    Dialog d;


    public interface EditItemDialogListener {
        void updateItem(int position, Item item, int action);
    }

    public static EditItemDialogFragment newInstance(int position, String key, String item, double price) {

        EditItemDialogFragment dialogFragment = new EditItemDialogFragment();
        //supply item values as argument
        Bundle args = new Bundle();

        args.putString("key", key);
        args.putInt("position", position);
        args.putString("item",item);
        args.putDouble("price",price);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        key = getArguments().getString("key");
        position = getArguments().getInt("position");
        item = getArguments().getString("item");
        price = getArguments().getDouble("price");

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.edit_item_dialog, getActivity().findViewById(R.id.root));
        //Dialog dialog = new Dialog();

        itemView = layout.findViewById(R.id.itemView);
        priceView = layout.findViewById(R.id.priceView);



        itemView.setText(item);
        priceView.setText(Double.toString(price));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);



        //cancelButton = builder.

        builder.setTitle("Edit Item");

        //cancel button
        builder.setNegativeButton("ADD", new AddButtonClickListener());



        //deleteButton.setOnClickListener(new );





        // Save button
        builder.setPositiveButton("SAVE", new SaveButtonClickListener());

        //delete button
        builder.setNeutralButton("DELETE", new DeleteButtonClickListener());

        //builder.setPositiveButton("ADD", new AddButtonClickListener());

        // create alertdialog and show
        return builder.create();



    }


    private class AddButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String itemName = itemView.getText().toString();
            String p = priceView.getText().toString();
            double price = Double.parseDouble(p);
            Item item = new Item(itemName, price);
            item.setKey(key);

            EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getContext();

            listener.updateItem(position,item, -1);

            dismiss();



        }
    }

    private class SaveButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String itemName = itemView.getText().toString();
            String p = priceView.getText().toString();
            double price = Double.parseDouble(p);
            Item item = new Item(itemName, price);
            item.setKey(key);

            EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getContext();

            listener.updateItem(position,item, SAVE);

            dismiss();




        }
    }

    private class DeleteButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Item shoppingItem = new Item(item, price);
            shoppingItem.setKey(key);

            EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getContext();
            listener.updateItem(position,shoppingItem,DELETE);

            dismiss();

        }




    }



}

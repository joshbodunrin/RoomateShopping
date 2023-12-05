package edu.uga.cs.roomateshopping;

import static android.app.PendingIntent.getActivity;
import static java.lang.String.valueOf;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Represents the current shopping list. Needs Firebase.
 */
public class ItemListRecyclerAdapter extends RecyclerView.Adapter<ItemListRecyclerAdapter.ItemHolder> {

    public static final String TAG = "ItemListRecyclerAdapter";

    private List<Item> itemList;
    private Context context;

    private Activity activity;

    public ItemListRecyclerAdapter( List<Item> itemList, Context context, Activity activity ) {
        this.itemList = itemList;
        this.context = context;
        this.activity = activity;
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView cost;
        TextView buyer;

        public ItemHolder(View itemView ) {
            super(itemView);

            itemName = itemView.findViewById( R.id.itemName );
            cost = itemView.findViewById( R.id.itemCost );
            buyer = itemView.findViewById(R.id.itemBuyer);

        }

    }

    @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.item, parent, false );
        return new ItemHolder( view );
    }


    @Override
    public void onBindViewHolder( ItemHolder holder, int position ) {
        Item item = itemList.get( position );


        String key = item.getKey();
        String name = item.getName();
        double cost = item.getPrice();

        Log.d(TAG, "here is the value of key  " + item.getKey());
        //Log.d(TAG, "value of price here  " + item.getPrice());

        holder.itemName.setText( item.getName());
        holder.cost.setText(String.valueOf(item.getPrice()));
        if (item.getBuyer() != null)
            holder.buyer.setText(item.getBuyer());

        //LOOK AT THIS STUFF, THIS IS FROM JOBLEADS
        // We can attach an OnClickListener to the itemView of the holder;
        // itemView is a public field in the Holder class.
        // It will be called when the user taps/clicks on the whole item, i.e., one of
        // the job leads shown.
        // This will indicate that the user wishes to edit (modify or delete) this item.
        // We create and show an EditJobLeadDialogFragment.


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d( TAG, "onBindViewHolder: getItemId: " + holder.getItemId() );
                //Log.d( TAG, "onBindViewHolder: getAdapterPosition: " + holder.getAdapterPosition() );
                EditItemDialogFragment editItemFragment =
                        EditItemDialogFragment.newInstance( holder.getAdapterPosition(), key, name,cost );
                Log.d(TAG, "HERE    " + valueOf(context));
                //FragmentManager fragmentManager = ((FragmentActivity)context ).getSupportFragmentManager();

                //editItemFragment.show(getSupportFragmentManager(),null);
                editItemFragment.show(( (AppCompatActivity) activity).getSupportFragmentManager(),null);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

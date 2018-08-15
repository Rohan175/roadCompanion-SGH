package com.rsking175453.com.sgh_try1.fragmentCollection;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rsking175453.com.sgh_try1.R;
import com.rsking175453.com.sgh_try1.ScrollingActivity;
import com.rsking175453.com.sgh_try1.models.SingleItemModel;

import java.util.ArrayList;



public class linearListDataAdapter extends RecyclerView.Adapter<linearListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;

    public linearListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.individual_card, viewGroup,false);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {


        SingleItemModel singleItem = itemsList.get(i);
       // holder.area.setText(singleItem.getTaluka().toUpperCase());
        holder.status.setText(singleItem.getStatus().toUpperCase());

        holder.date.setText(singleItem.getTime().toUpperCase().substring(0,10) );

        Log.v("debug",itemsList.toString());

        Glide.with(mContext)
                .load(singleItem.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.ic_launcher_background).into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView area;

        protected ImageView itemImage;

        protected TextView status;
        protected TextView date;

        protected Button delete;
        protected Button share;

        protected CardView c;



        public SingleItemRowHolder(View view) {
            super(view);

            this.c = (CardView) view.findViewById(R.id.card);
            this.area = (TextView) view.findViewById(R.id.talukaSpinner);
            this.status = (TextView) view.findViewById(R.id.status);
            this.date = (TextView) view.findViewById(R.id.date);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);




            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), area.getText(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(v.getContext(),ScrollingActivity.class);
                    Log.v("debug",""+v.getId());
                    int id = getAdapterPosition();
                    i.putExtra("id",id);
                    i.putExtra("url",itemsList.get(id).getUrl());
                    i.putExtra("Area",itemsList.get(id).getTaluka());
                    i.putExtra("griv",itemsList.get(id).getGrivType());
                    i.putExtra("road",itemsList.get(id).getRoadType());
                    i.putExtra("city",itemsList.get(id).getDistrict());
                    i.putExtra("Discriptiom",itemsList.get(id).getDiscription());
                    i.putExtra("status",itemsList.get(id).getStatus());
                    i.putExtra("complainId",itemsList.get(id).getComplainId());
                    i.putExtra("estimatedTime",itemsList.get(id).getEstimatedTime());
                    i.putExtra("comment",itemsList.get(id).getComment());
                    i.putExtra("officerName",itemsList.get(id).getOfficerName());
                    i.putExtra("email",itemsList.get(id).getEmail());
                    i.putExtra("time",itemsList.get(id).getTime());

                    Log.v("debug",v.getId()+"");
                    v.getContext().startActivity(i);

                }
            });


        }

    }
}
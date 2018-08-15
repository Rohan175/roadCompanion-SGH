package com.rsking175453.com.sgh_try1.fragmentCollection;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rsking175453.com.sgh_try1.R;
import com.rsking175453.com.sgh_try1.ScrollingActivity;
import com.rsking175453.com.sgh_try1.models.SingleItemModel;

import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_item, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Log.v("debug",i+"");
        SingleItemModel singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getTaluka());

        Log.v("debug",itemsList.get(i).getUrl());
        Glide.with(mContext)
                .load(itemsList.get(i).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.talukaSpinner2);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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


                    Log.v("debug",v.getId()+"");
                    v.getContext().startActivity(i);
                    //Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();

                }
            });


        }

    }
}
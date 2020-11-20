package com.nimashi.pawpaw;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {


    List<Model> itemList1;
    Context context;
    RequestOptions option;


    public ItemAdapter(Context context,List<Model> itemList1) {
        this.itemList1 = itemList1;
        this.context = context;


        //request option for glide

        option=new RequestOptions().centerCrop().placeholder(R.drawable.edittext_bg).error(R.drawable.edittext_bg);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   //     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem,parent,false);
////        ViewHolder viewHolder=new ViewHolder(view);
////        return viewHolder;

        View view;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        view=inflater.inflate(R.layout.rowitem,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(itemList1.get(position).getImage()).apply(option).into(holder.itemImage);
       holder.itemName.setText(itemList1.get(position).getName());
        holder.itemCategory.setText(itemList1.get(position).getType());
       holder.itemSpeciality.setText(itemList1.get(position).getSpeciality());
        holder.itemAge.setText(itemList1.get(position).getAge());
        holder.itemPlace.setText(itemList1.get(position).getPlace());
        holder.itemId.setText(String.valueOf(itemList1.get(position).getId()));






    }

    @Override
    public int getItemCount() {
        return itemList1.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView itemImage;
        TextView itemName,itemSpeciality,itemAge,itemId,itemCategory,itemPlace;
        LinearLayout layout;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);


            itemImage =itemView.findViewById(R.id.pet_photo);
            itemName =itemView.findViewById(R.id.pet_name);
            itemCategory=itemView.findViewById(R.id.pet_category);
            itemSpeciality =itemView.findViewById(R.id.pet_speciality);
            itemAge =itemView.findViewById(R.id.pet_age);
            itemPlace=itemView.findViewById(R.id.address);
            itemId =itemView.findViewById(R.id.pet_id);
            layout=itemView.findViewById(R.id.rowitem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),DetailsActivity.class);
                    intent.putExtra("id",itemId.getText().toString());
                    intent.putExtra("name",itemName.getText().toString());
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
            });

        }
    }
}

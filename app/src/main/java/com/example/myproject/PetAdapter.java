package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<MyPetHolder> {

    private Context context;
    private List<PetDataClass> petDataList;

    public PetAdapter(Context context, List<PetDataClass> petDataList) {
        this.context = context;
        this.petDataList = petDataList;
    }

    @NonNull
    @Override
    public MyPetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_view_one,parent,false);

        return new MyPetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPetHolder holder, int position) {
        Glide.with(context).load(petDataList.get(position).getPetdataImage()).into(holder.viewImage);
        holder.viewPName.setText(petDataList.get(position).getPetdataName());
        holder.viewCName.setText(petDataList.get(position).getPetdatacName());
        holder.viewNote.setText(petDataList.get(position).getPetdatanote());
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    
            }
        });
        holder.delite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call a method in your activity to handle the deletion
                if (context instanceof ViewPetActivity) {
                    ((ViewPetActivity) context).deletePet(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return petDataList.size();
    }

    public void searchpetDatalist(ArrayList<PetDataClass>searchList){
        petDataList=searchList;
        notifyDataSetChanged();
    }


}
class MyPetHolder extends RecyclerView.ViewHolder{

    ImageView viewImage;
    TextView viewCName,viewPName,viewNote;
    CardView viewCard;
    Button delite,Edit;


    public MyPetHolder(@NonNull View itemView) {
        super(itemView);

        viewImage=itemView.findViewById(R.id.viewImage);
        viewCName=itemView.findViewById(R.id.viewCname);
        viewPName=itemView.findViewById(R.id.viewPname);
        viewNote = itemView.findViewById(R.id.viewPnote);
        viewCard=itemView.findViewById(R.id.viewCard2);
        delite=itemView.findViewById(R.id.deliteBtn);
        Edit=itemView.findViewById(R.id.updateBtn1);
    }
}

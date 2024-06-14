package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.SearchView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPetActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<PetDataClass> petDataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    AlertDialog dialog;

    SearchView searchView;

    PetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet);

        recyclerView = findViewById(R.id.petviewrecyclerView);
        recyclerView.setVisibility(View.GONE);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewPetActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        petDataList = new ArrayList<>();
         adapter = new PetAdapter(ViewPetActivity.this, petDataList);

        databaseReference = FirebaseDatabase.getInstance().getReference("Pet Data");
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPetActivity.this);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();

        searchView=findViewById(R.id.serch);
        searchView.clearFocus();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                petDataList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    PetDataClass dataClass = itemSnapshot.getValue(PetDataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    petDataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventListener != null) {
            databaseReference.removeEventListener(eventListener);
        }
    }

    public void deletePet(int position) {
        String keyToDelete = petDataList.get(position).getKey();

        petDataList.remove(position);

        adapter.notifyItemRemoved(position);
        databaseReference.child(keyToDelete).removeValue();
    }



    public void searchList(String text){
        ArrayList<PetDataClass> searchList=new ArrayList<>();
        for (PetDataClass dataClass:petDataList){
            if (dataClass.getPetdataType().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchpetDatalist(searchList);
    }

}

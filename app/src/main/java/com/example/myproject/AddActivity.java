package com.example.myproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddActivity extends AppCompatActivity {

    ImageView addImage;
    Button addBtn;
    EditText petName,petType,petBreed, petHeight, petWeigth, petaage, petaddress,petnote,petcName;


    String imageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addImage=findViewById(R.id.addimage);
        addBtn=findViewById(R.id.addBtn1);
        petName=findViewById(R.id.addname);
        petType=findViewById(R.id.addtype);
        petBreed=findViewById(R.id.addbreed);
        petWeigth=findViewById(R.id.addWigth);
        petHeight=findViewById(R.id.addHeigth);
        petaage=findViewById(R.id.addage);
        petaddress=findViewById(R.id.addaddress);
        petcName=findViewById(R.id.addCame);
        petnote=findViewById(R.id.addnote);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            addImage.setImageURI(uri);
                        } else {
                            Toast.makeText(AddActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savepetData();
            }
        });
    }

    public void savepetData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Pet Data")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadData(){
        String Pname = petName.getText().toString();
        String Ptype = petType.getText().toString();
        String PBredd = petBreed.getText().toString();
        String PHeigh = petHeight.getText().toString();
        String Pweight = petWeigth.getText().toString();
        String Page = petaage.getText().toString();
        String Paddress = petaddress.getText().toString();
        String PCname = petcName.getText().toString();
        String Pnote=petnote.getText().toString();

        PetDataClass petDataClass=new PetDataClass(Pname,Ptype,PBredd,PHeigh,Pweight,Page,Paddress,Pnote,PCname,imageURL);
        String uniqueId = FirebaseDatabase.getInstance().getReference("Pet Data").push().getKey();

        FirebaseDatabase.getInstance().getReference("Pet Data").child(uniqueId)
                .setValue(petDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddActivity.this, ViewPetActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
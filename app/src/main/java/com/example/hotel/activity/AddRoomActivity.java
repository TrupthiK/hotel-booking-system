package com.example.hotel.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.databinding.ActivityAddRoomBinding;
import com.example.hotel.roomapi.RoomUploadData;
import com.example.hotel.roomapi.RoomViewMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.net.ssl.SSLSessionBindingEvent;

import com.google.firebase.FirebaseApp;


@SuppressWarnings("deprecation")
public class AddRoomActivity extends AppCompatActivity implements RoomViewMessage {

    ActivityAddRoomBinding binding;

    private StorageReference storageReference;
    ProgressDialog progressDialog;

    EditText edTitle, edDesc, edLocation, edPrice;
    RoomUploadData roomUploadData;
    private StorageTask uploadtask;
    Button add;



    private static final String TAG = "addRoom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide(); //This Line hides the action bar

        binding = ActivityAddRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        add = binding.btnCreateEvent;

        edTitle = binding.edTitle;
        edDesc = binding.edDesc;
        edLocation = binding.edLocation;
        edPrice =  binding.edPrice;

        roomUploadData = new RoomUploadData(this);
        storageReference = FirebaseStorage.getInstance().getReference("RoomImages");
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading file...");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSignUpDetails();
            }
        });


    }





    private void checkSignUpDetails() {
        String isAvailable = "yes";
        String title = edTitle.getText().toString().trim();
        String description = edDesc.getText().toString().trim();
        String location = edLocation.getText().toString().trim();
        int price =Integer.parseInt(edPrice.getText().toString().trim());


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String id = timestamp.toString().trim();

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)){

            roomUploadData.onSuccessUpdate(this,id,title,description,isAvailable,location,null, price);

        }else{
            if(TextUtils.isEmpty(title)){
                edTitle.setError("Title is required");
            }if (TextUtils.isEmpty(description)){
                edDesc.setError("Description is required");
            }
            if (TextUtils.isEmpty(location)){
                edLocation.setError("Location is required");
            }
            if (TextUtils.isEmpty(edPrice.getText())) {
                edPrice.setError("Price is required");
            }
        }
    }




    @Override
    public void onUpdateFailure(String message) {

        Toast.makeText(AddRoomActivity.this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddRoomActivity.this, AdminPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();



    }

    @Override
    public void onUpdateSuccess(String message) {

        Toast.makeText(AddRoomActivity.this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddRoomActivity.this, AdminPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
}
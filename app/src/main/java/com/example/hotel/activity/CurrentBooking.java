package com.example.hotel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.adapter.CurrentBookingAdapter;
import com.example.hotel.bookingapi.BookingFetchData;
import com.example.hotel.bookingapi.BookingModel;
import com.example.hotel.bookingapi.BookingViewFetchMessage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class CurrentBooking extends AppCompatActivity implements BookingViewFetchMessage {
    private RecyclerView ListDataView;
    private CurrentBookingAdapter Adapter;
    ImageView menu, profile;
    TextView title;

    ArrayList<BookingModel> roomModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide (); //This Line hides the action bar
        setContentView(R.layout.activity_view_all);
        title = findViewById(R.id.pageTitle);

        menu = findViewById(R.id.onMenu);
        profile= findViewById(R.id.onProfile);
        title.setText("Current Booking List");

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentBooking.this, UserMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentBooking.this, UserProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        ListDataView = findViewById(R.id.AllListView);

        BookingFetchData roomFetchData = new BookingFetchData(this, this);

        RecyclerViewMethod();
        roomFetchData.onSuccessUpdate(this);

    }
    public void RecyclerViewMethod() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        ListDataView.setLayoutManager(manager);
        ListDataView.setItemAnimator(new DefaultItemAnimator());
        ListDataView.setHasFixedSize(true);

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
//        ListDataView.setLayoutManager(mLayoutManager);

        Adapter = new CurrentBookingAdapter(this, roomModelArrayList);
        ListDataView.setAdapter(Adapter);
        ListDataView.invalidate();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onUpdateSuccess(BookingModel message) {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if(message != null &&message.getStatus().equals("accepted")&& message.getCustomerEmail().equals(email)){
            BookingModel roomModel = new BookingModel(message.getId(),message.getCustomerEmail(),
                    message.getRoomID(), message.getRoomTitle(), message.getStartDate(),
                    message.getEndDate(),message.getStatus(),message.getImageUrl(),
                    message.getBookingDays(),message.getPrice(),message.getTotalPayment());
            roomModelArrayList.add(roomModel);

        }
        Adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CurrentBooking.this, HomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUpdateFailure(String message) {
        Toast.makeText(CurrentBooking.this, message, Toast.LENGTH_LONG).show();

    }
}
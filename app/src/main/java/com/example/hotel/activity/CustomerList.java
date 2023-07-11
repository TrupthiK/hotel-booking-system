package com.example.hotel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.hotel.adapter.BestOfferAdapter;
import com.example.hotel.adapter.CustomerListAdapter;
import com.example.hotel.adapter.ManageCurrentBookingAdapter;
import com.example.hotel.bookingapi.BookingFetchData;
import com.example.hotel.bookingapi.BookingModel;
import com.example.hotel.bookingapi.BookingViewFetchMessage;
import com.example.hotel.roomapi.RoomFetchData;
import com.example.hotel.roomapi.RoomModel;
import com.example.hotel.roomapi.RoomViewFetchMessage;
import com.example.hotel.userapi.UserFetchData;
import com.example.hotel.userapi.UserModel;
import com.example.hotel.userapi.UserViewFetchMessage;

import java.util.ArrayList;
import java.util.Objects;

public class CustomerList extends AppCompatActivity implements UserViewFetchMessage {
    private RecyclerView ListDataView;
    private CustomerListAdapter Adapter;
    ImageView menu;
    TextView title;

    ArrayList<UserModel> roomModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide (); //This Line hides the action bar
        setContentView(R.layout.admin_list_view);
        title = findViewById(R.id.pageTitle);

        menu = findViewById(R.id.onMenu);
        title.setText("Customer List");

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerList.this, AdminPanel.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        ListDataView = findViewById(R.id.AdminListView);
        UserFetchData roomFetchData = new UserFetchData(this, this);
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

        Adapter = new CustomerListAdapter(this, roomModelArrayList);
        ListDataView.setAdapter(Adapter);
        ListDataView.invalidate();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onUpdateSuccess(UserModel message) {
        if(message != null){
            UserModel roomModel = new UserModel(message.getName(),message.getEmail(),
                    message.getPassword());
            roomModelArrayList.add(roomModel);
        }
        Adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CustomerList.this, AdminPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUpdateFailure(String message) {
        Toast.makeText(CustomerList.this, message, Toast.LENGTH_LONG).show();

    }
}
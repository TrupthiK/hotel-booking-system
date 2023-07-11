package com.example.hotel.bookingapi;

public interface BookingViewMessage {
    void onUpdateFailure(String message);
    void onUpdateSuccess(String message);
}

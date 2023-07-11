package com.example.hotel.bookingapi;

public interface BookingViewFetchMessage {
    void onUpdateSuccess(BookingModel message);
    void onUpdateFailure(String message);
}

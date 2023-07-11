package com.example.hotel.roomapi;

public interface RoomViewMessage {

    void onUpdateFailure(String message);
    void onUpdateSuccess(String message);
}

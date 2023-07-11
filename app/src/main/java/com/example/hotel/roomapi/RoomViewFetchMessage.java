package com.example.hotel.roomapi;

public interface RoomViewFetchMessage {
    void onUpdateSuccess(RoomModel message);
    void onUpdateFailure(String message);

}

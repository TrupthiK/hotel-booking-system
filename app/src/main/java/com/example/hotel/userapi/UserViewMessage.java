package com.example.hotel.userapi;

public interface UserViewMessage {
    void onUpdateFailure(String message);
    void onUpdateSuccess(String message);
}

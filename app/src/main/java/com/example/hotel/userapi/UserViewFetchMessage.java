package com.example.hotel.userapi;

public interface UserViewFetchMessage {
    void onUpdateSuccess(UserModel message);
    void onUpdateFailure(String message);
}

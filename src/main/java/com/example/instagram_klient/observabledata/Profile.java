package com.example.instagram_klient.observabledata;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.instagram_klient.BR;

public class Profile extends BaseObservable {
    @Bindable
    private String name;

    @Bindable
    private String lastName;

    @Bindable
    private String email;

    @Bindable
    private int profilePicId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public int getProfilePicId() {
        return profilePicId;
    }

    public void setProfilePicId(int profilePicId) {
        this.profilePicId = profilePicId;
        notifyPropertyChanged(BR.profilePicId);
    }
//    public  getPhoto()
//    {
//
//    }

}

package com.example.instagram_klient.model;

import com.google.gson.annotations.SerializedName;

public class SingleChange {
    private String status;

    @SerializedName("lastModifiedDate")
    private String date;

    private String url;
}

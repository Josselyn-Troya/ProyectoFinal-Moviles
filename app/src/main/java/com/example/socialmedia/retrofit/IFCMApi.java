package com.example.socialmedia.retrofit;

import com.example.socialmedia.models.FCMBody;
import com.example.socialmedia.models.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAADFRojaY:APA91bE348XIFpoc0X8QZxRATqmUCq9MoAgRpebiOXi-YClZp7C77veuu9_LaPxsygT3vkIX7q7xi8RuCE1TAcmesWiC7WlOrO-3QE6YuaBuuDxnnPQlumd6NRKRmetpRus8e7LGiMP8"
    })
    @POST("fcm/send")
    Call<FCMResponse> send(@Body FCMBody body);
}

package com.jaydipumaretiya.util;

import com.jaydipumaretiya.model.ListResponse;
import com.jaydipumaretiya.model.Responsedata;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("VegetableController/PaymentSummary")
    Call<ListResponse<Responsedata>> paymentSummary(@Field("user_id") String userId);
}

package com.jaydipumaretiya.basicprojectsetup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jaydipumaretiya.model.ListResponse;
import com.jaydipumaretiya.model.Responsedata;
import com.jaydipumaretiya.util.APIClient;
import com.jaydipumaretiya.util.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        APIInterface apiService = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<Responsedata>> paymentSummary = apiService.paymentSummary("User ID");
        paymentSummary.enqueue(new Callback<ListResponse<Responsedata>>() {
            @Override
            public void onResponse(Call<ListResponse<Responsedata>> call, Response<ListResponse<Responsedata>> response) {
                Log.e("", "" + response.body().ResponseCode());
            }

            @Override
            public void onFailure(Call<ListResponse<Responsedata>> call, Throwable t) {

            }
        });
    }
}

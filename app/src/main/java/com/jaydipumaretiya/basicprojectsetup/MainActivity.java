package com.jaydipumaretiya.basicprojectsetup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jaydipumaretiya.model.ListResponse;
import com.jaydipumaretiya.model.Responsedata;
import com.jaydipumaretiya.util.APIClient;
import com.jaydipumaretiya.util.APIInterface;
import com.thebrownarrow.util.UtilClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        TextView tvToolbarTitle = (TextView) findViewById(R.id.tv_toolbarTitle);
        TextView tvToolbarSubTitle = (TextView) findViewById(R.id.tv_toolbarSubTitle);
        tvToolbarTitle.setText("This is title");
        tvToolbarSubTitle.setText("This is sub title");

        APIInterface apiService = APIClient.getClient().create(APIInterface.class);
        Call<ListResponse<Responsedata>> paymentSummary = apiService.paymentSummary("EA08D9E8-025C-4537-80D1-CB016E4A5EAC");
        paymentSummary.enqueue(new Callback<ListResponse<Responsedata>>() {
            @Override
            public void onResponse(Call<ListResponse<Responsedata>> call, Response<ListResponse<Responsedata>> response) {
                UtilClass.printLog(TAG, response.body().ResponseCode());
            }

            @Override
            public void onFailure(Call<ListResponse<Responsedata>> call, Throwable throwable) {
                UtilClass.printLog(TAG, throwable.getMessage());
            }
        });
    }
}

package com.app.engineeringaipracticaltest.ui;

import com.app.engineeringaipracticaltest.R;
import com.app.engineeringaipracticaltest.model.ClsUserResponse;
import com.app.engineeringaipracticaltest.network.ApiClient;

import org.jetbrains.annotations.NotNull;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControler();
        setAdapter();
        callService();
    }

    private void callService() {
        ApiClient.getBaseApiMethods().getUserList(10, 0).enqueue(new Callback<ClsUserResponse>() {
            @Override
            public void onResponse(@NotNull Call<ClsUserResponse> call,
                @NotNull Response<ClsUserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus()) {
                        Timber.e("get success response");
                    }
                } else {
                    Timber.e("failed to get response");
                }
            }

            @Override
            public void onFailure(@NotNull Call<ClsUserResponse> call, @NotNull Throwable t) {
                Timber.e("failed to get response");
            }
        });
    }

    private void setAdapter() {

    }

    private void initControler() {

    }
}

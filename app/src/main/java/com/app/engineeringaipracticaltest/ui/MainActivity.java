package com.app.engineeringaipracticaltest.ui;

import com.app.engineeringaipracticaltest.R;
import com.app.engineeringaipracticaltest.adapter.UserAdapter;
import com.app.engineeringaipracticaltest.model.ClsUserResponse;
import com.app.engineeringaipracticaltest.model.User;
import com.app.engineeringaipracticaltest.network.ApiClient;

import org.jetbrains.annotations.NotNull;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.alexbykov.nopaginate.callback.OnLoadMoreListener;
import ru.alexbykov.nopaginate.paginate.NoPaginate;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final int pageSize = 10;

    private RecyclerView rvUserList;

    private SwipeRefreshLayout swipeRefreshLayout;

    private NoPaginate noPaginate;

    private UserAdapter userAdapter;

    private int pageNo = 1;

    private boolean hasMore = true;

    private ArrayList<User> userArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControler();
    }

    private void initControler() {
        rvUserList = findViewById(R.id.rvUserList);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        setAdapter();
    }

    private void setAdapter() {
        userAdapter = new UserAdapter();
        rvUserList.addItemDecoration(new DividerItemDecoration
            (this, DividerItemDecoration.VERTICAL));
        rvUserList.setAdapter(userAdapter);
        callService(0);
        noPaginate = NoPaginate.with(rvUserList)
            .setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    /* *
                     * check for next availble data
                     * */
                    if (hasMore) {
                        //http call
                        noPaginate.showLoading(true);
                        int offset = pageSize * (pageNo - 1);
                        /**
                         * fetch data from server
                         * */
                        callService(offset);
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        noPaginate.showLoading(false);
                        noPaginate.setNoMoreItems(true);
                    }
                }
            })
            .build();
    }

    private void callService(final int offset) {
        ApiClient.getBaseApiMethods().getUserList(pageSize, offset)
            .enqueue(new Callback<ClsUserResponse>() {
                @Override
                public void onResponse(@NotNull Call<ClsUserResponse> call,
                    @NotNull Response<ClsUserResponse> response) {

                    noPaginate.showLoading(false);
                    swipeRefreshLayout.setRefreshing(false);

                    /**
                     * check for success response
                     * */
                    if (response.isSuccessful()) {
                        ClsUserResponse clsUserResponse = response.body();
                        if (clsUserResponse != null && clsUserResponse.getStatus()) {
                            if (pageNo == 1) {
                                userArrayList.clear();
                            }
                            hasMore = clsUserResponse.getData().getHasMore();
                            userArrayList.addAll(response.body().getData().getUsers());
                            userAdapter.setItems(userArrayList);

                            /**
                             * if has more neta then increment page count
                             * */
                            if (hasMore)
                                pageNo++;
                        }
                    } else {
                        noPaginate.showError(true);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ClsUserResponse> call, @NotNull Throwable t) {
                    noPaginate.showError(true);
                    noPaginate.showLoading(false);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noPaginate.unbind();
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        userArrayList.clear();
        noPaginate.setNoMoreItems(false);
        noPaginate.showLoading(false);
        noPaginate.showError(false);
        userAdapter.notifyDataSetChanged();
    }
}

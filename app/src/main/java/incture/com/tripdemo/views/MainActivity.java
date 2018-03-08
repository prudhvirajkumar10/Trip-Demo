package incture.com.tripdemo.views;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import incture.com.tripdemo.R;
import incture.com.tripdemo.models.DataRes;
import incture.com.tripdemo.presenters.DataContract;
import incture.com.tripdemo.presenters.Presenter;

public class MainActivity extends AppCompatActivity implements DataContract.View, SwipeRefreshLayout
                                                .OnRefreshListener{

    private Presenter presenter;
    private Toolbar toolbar;
    private TextView title;
    private RecyclerView list;
    private SwipeRefreshLayout swipe;

    private TripAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = findViewById(R.id.list);
        title = findViewById(R.id.title);
        swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));

        presenter = new Presenter(this);
        swipe.setRefreshing(true);
        presenter.getDataFromRemote(getApplicationContext(), "");
    }

    @Override
    public void onDataSuccess(String message, List<DataRes> dataList) {
        if (swipe.isRefreshing()) swipe.setRefreshing(false);
        adapter = new TripAdapter(getApplicationContext(), dataList);
        list.setAdapter(adapter);
    }

    @Override
    public void onDataError(String message) {
        Log.e("Status", message);
    }

    @Override
    public void onRefresh() {
        presenter.getDataFromRemote(getApplicationContext(), "");
    }
}

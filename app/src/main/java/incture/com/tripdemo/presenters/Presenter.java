package incture.com.tripdemo.presenters;

import android.content.Context;

import java.util.List;

import incture.com.tripdemo.models.DataRes;

/**
 * Created by Ritwik.Jain on 3/8/2018.
 */

public class Presenter implements DataContract.Presenter, DataContract.onGetDataListener {

    private DataContract.View view;
    private Intractor intractor;

    public Presenter(DataContract.View view) {
        this.view = view;
        intractor = new Intractor(this);
    }

    public void getDataFromRemote(Context context, String url) {
        intractor.initRemoteCall(context, url);
    }

    @Override
    public void onSuccess(String message, List<DataRes> dataList) {
        view.onDataSuccess(message, dataList);
    }

    @Override
    public void onFailure(String message) {
        view.onDataError(message);
    }
}

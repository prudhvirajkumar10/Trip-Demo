package incture.com.tripdemo.presenters;

import android.content.Context;

import java.util.List;

import incture.com.tripdemo.models.DataRes;

/**
 * Created by Ritwik.Jain on 3/8/2018.
 */

public interface DataContract {

    interface View {
        void onDataSuccess(String message, List<DataRes> dataList);
        void onDataError(String message);
    }

    interface Presenter {
        void getDataFromRemote(Context context, String url);
    }

    interface Interactor {
        void initRemoteCall(Context context, String url);
    }

    interface onGetDataListener {
        void onSuccess(String message, List<DataRes> dataList);
        void  onFailure(String message);
    }
}

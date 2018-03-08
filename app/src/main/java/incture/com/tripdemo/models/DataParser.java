package incture.com.tripdemo.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ritwik.Jain on 3/8/2018.
 */

public class DataParser {

    private static DataParser instance;

    private DataParser(){

    }

    public static DataParser getInstance() {
        if (instance == null) {
            instance = new DataParser();
        }
        return instance;
    }

    private List<DataRes> dataList = null;

    public List<DataRes> getData() {
        return dataList;
    }

    public List<DataRes> parser(JSONObject object) {
        if (object.optInt("code") == 200 && object.optBoolean("status")) {
            dataList = new ArrayList<>();
            JSONObject data = object.optJSONObject("data");
            JSONArray deliver = data.optJSONArray("deliveryHeader");
            if (deliver != null && deliver.length() > 0) {
                for (int i = 0 ; i < deliver.length() ; i++) {
                    JSONObject obj = deliver.optJSONObject(i);
                    DataRes dataRes = new DataRes();
                    dataRes.setTripId(data.optString("tripId"));
                    dataRes.setOrderId(obj.optString("deliveryNoteId"));
                    dataRes.setShipToAddress(obj.optString("soldToAddress"));
                    dataRes.setLongitude(obj.optString("longitude"));
                    dataRes.setLatitude(obj.optString("latitude"));
                    JSONArray array = obj.optJSONArray("deliveryItems");
                    if (array != null && array.length() > 0) {
                        dataRes.setItemsCount(array.length());
                    }
                    else {
                        dataRes.setItemsCount(0);
                    }
                    dataList.add(dataRes);
                }
            }
        }
        return getData();
    }

    @Override
    public String toString() {
        return "DataParser{" +
                "data=" + dataList +
                '}';
    }
}

package incture.com.tripdemo.presenters;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import incture.com.tripdemo.models.DataParser;
import incture.com.tripdemo.models.DataRes;
import incture.com.tripdemo.utils.Auth;

/**
 * Created by Ritwik.Jain on 3/8/2018.
 */

public class Intractor implements DataContract.Interactor {

    private DataContract.onGetDataListener listener;
    List<DataRes> dataList = new ArrayList<>();

    public Intractor(DataContract.onGetDataListener listener) {
        this.listener = listener;
    }

    public void initRemoteCall(Context context, String url) {
        final JSONObject req = new JSONObject();
        try {
            req.put("tripId", "TRIP00000001");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, Auth.API_BASE_URL + Auth.BASE,
                req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<DataRes> dataList = DataParser.getInstance().parser(response);
                listener.onSuccess("Success", dataList);
                Log.e("Response Volley", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    listener.onFailure(error.getMessage());
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() {
                try {
                    Log.i("json", req.toString());
                    return req.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = Auth.USERNAME + ":" + Auth.PASSWORD;
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}

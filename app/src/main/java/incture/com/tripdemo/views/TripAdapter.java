package incture.com.tripdemo.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import incture.com.tripdemo.R;
import incture.com.tripdemo.models.DataRes;

/**
 * Created by Ritwik.Jain on 3/8/2018.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private Context context;
    private List<DataRes> dataList;
    private LayoutInflater inflater;

    public TripAdapter(Context context, List<DataRes> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_data, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        final DataRes dataRes = dataList.get(position);
        holder.name.setText(dataRes.getShipToAddress());
        holder.orderId.setText(dataRes.getOrderId());
        holder.items.setText("Items : " + dataRes.getItemsCount());

        holder.locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=" + dataRes.getLatitude() + "," + dataRes.getLongitude()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView name, orderId, items;
        Button locate;
        public TripViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            orderId = itemView.findViewById(R.id.order_id);
            items = itemView.findViewById(R.id.items);
            locate = itemView.findViewById(R.id.locate);
        }
    }
}

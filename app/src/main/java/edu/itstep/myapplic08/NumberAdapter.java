package edu.itstep.myapplic08;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumberViewHolder> {

    private OnItemClickListener onItemClickListener;
    private Context context;
    private int resource;
    private List<String> data;
    private LayoutInflater inflater;
    private int counterHolder;

    public NumberAdapter(Context context, int resource, List<String> data, OnItemClickListener onItemClickListener) {

        this.context = context;
        this.resource = resource;
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(resource, parent, false);
        NumberViewHolder numberViewHolder = new NumberViewHolder(v);
        numberViewHolder.tvHolder.setText("holder # " + ++counterHolder);
        return numberViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        String phone = data.get(position);
        holder.tvData.setText(phone);
//        holder.tvData.setOnClickListener(view -> {
//            Toast.makeText(context, phone, Toast.LENGTH_SHORT).show();
//        });
//        holder.itemView.setOnClickListener(view -> {
//            Toast.makeText(context, phone, Toast.LENGTH_SHORT).show();
//        });

        holder.tvData.setOnClickListener(view -> {
            onItemClickListener.onItemClick(phone);
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder {
        TextView tvHolder;
        TextView tvData;
        View itemView;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHolder = itemView.findViewById(R.id.tvHolder);
            tvData = itemView.findViewById(R.id.tvData);
            this.itemView = itemView;

        }
    }

    public interface OnItemClickListener {
        void onItemClick(String phone);
    }
}

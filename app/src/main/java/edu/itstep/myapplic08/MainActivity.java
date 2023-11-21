package edu.itstep.myapplic08;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvNumbers;
    private NumberAdapter adapter;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 100; i <= 300; i++) {
            data.add("+38-066-66-66-" + i);
        }

        rvNumbers = findViewById(R.id.rvNumbers);
        adapter = new NumberAdapter(this, R.layout.item_list, data, this::callNumber);
        rvNumbers.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
        );
        rvNumbers.setLayoutManager(layoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                showDeleteConfirmationDialog(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(rvNumbers);
    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "Delete" button, perform deletion
                        data.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "Cancel" button, dismiss the dialog
                        adapter.notifyItemChanged(position); // Restore the item to its original state
                    }
                });
        // Create the AlertDialog object and show it
        builder.create().show();
    }

    private void callNumber(String phone) {
        int permissionCallPhone = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
        );
        if (permissionCallPhone != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    0
            );
        } else {
            Intent intent = new Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:" + phone)
            );
            startActivity(intent);
        }
    }
}

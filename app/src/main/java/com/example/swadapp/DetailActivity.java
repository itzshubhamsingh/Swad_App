package com.example.swadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.swadapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        binding  = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final DBHelper helper = new DBHelper(this);

        if(getIntent().getIntExtra("type", 0)==1) {

            int image = getIntent().getIntExtra("image", 0);
            int price = Integer.parseInt(getIntent().getStringExtra("price"));
            final String name = getIntent().getStringExtra("name");
            final String description = getIntent().getStringExtra("desc");

            binding.detailImage.setImageResource(image);
            binding.detailPrice.setText(String.format("%d", price));
            binding.detailActivityName.setText(name);
            binding.detailDescription.setText(description);



            binding.detailActivityOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isInserted = helper.insertOrder(
                            binding.fullname.getText().toString(),
                            binding.phonenumber.getText().toString(),
                            price,
                            image,
                            description,
                            name,
                            Integer.parseInt(binding.qty.getText().toString())
                    );
                    if (isInserted) {
                        Toast.makeText(DetailActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this, "Error Placing the Order", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            int id = getIntent().getIntExtra("id", 0);
            Cursor cursor = helper.getOrderById(id);
            binding.detailImage.setImageResource(cursor.getInt(4));
            binding.detailPrice.setText(String.format("%d", cursor.getInt(3)));
            binding.detailActivityName.setText(cursor.getString(7));
            binding.detailDescription.setText(cursor.getString(6));

            binding.fullname.setText(cursor.getString(1));
            binding.phonenumber.setText(cursor.getString(2));
            binding.detailActivityOrder.setText("Update Now");
            binding.detailActivityOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isUpdated = helper.updateOrder(binding.fullname.getText().toString(),
                            binding.phonenumber.getText().toString(),
                            Integer.parseInt(binding.detailPrice.getText().toString()),
                            cursor.getInt(4),
                            binding.detailDescription.getText().toString(),
                            binding.detailActivityName.getText().toString(),
                            1,
                            id);

                    if(isUpdated){
                        Toast.makeText(DetailActivity.this, "Order Updated", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(DetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
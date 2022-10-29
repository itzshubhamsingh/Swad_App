package com.example.swadapp.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swadapp.DBHelper;
import com.example.swadapp.DetailActivity;
import com.example.swadapp.MainActivity2;
import com.example.swadapp.Models.MainModel;
import com.example.swadapp.R;
import com.example.swadapp.SignIn;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewholder>{
    ArrayList<MainModel> list;
    int lastPos = -1;
    Context context;
    private FirebaseAuth mAuth;

    public MainAdapter(ArrayList<MainModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_mainfood, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        mAuth = FirebaseAuth.getInstance();

        final MainModel model = list.get(position);
        holder.foodimage.setImageResource(model.getImage());
        holder.price.setText(model.getPrice());
        holder.description.setText(model.getDescription());
        holder.mainName.setText(model.getName());

        setAnimation(holder.itemView, position); //  Setting animation

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser()!=null) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("image", model.getImage());
                    intent.putExtra("name", model.getName());
                    intent.putExtra("price", model.getPrice());
                    intent.putExtra("desc", model.getDescription());
                    intent.putExtra("type", 1);
                    context.startActivity(intent);
                }
                else{
                    new AlertDialog.Builder(context)
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setTitle("Alert")
                            .setMessage("You must be Logged in to Order Items")
                            .setNegativeButton("Login", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, SignIn.class);
                                    context.startActivity(intent);
                                    ((Activity)view.getContext()).finish();

                                }
                            })
                            .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            }
        });
    }

    void setAnimation(View view, int position){
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        view.setAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView foodimage;
        TextView mainName, price, description;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            foodimage = itemView.findViewById(R.id.orderimage);
            mainName = itemView.findViewById(R.id.orderName);
            price = itemView.findViewById(R.id.ordernumber);
            description = itemView.findViewById(R.id.description);

        }
    }
}

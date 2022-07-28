package com.example.swadapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swadapp.Adapters.OrdersAdapter;
import com.example.swadapp.DBHelper;
import com.example.swadapp.Models.OrdersModel;
import com.example.swadapp.R;
import com.example.swadapp.databinding.FragmentGalleryBinding;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    RecyclerView recyclerView;
    private FragmentGalleryBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentGalleryBinding.inflate(getLayoutInflater());
        View root =binding.getRoot();
        recyclerView=root.findViewById(R.id.orderrecyclerview);
//        ArrayList<OrdersModel> list = new ArrayList<>();


        DBHelper helper = new DBHelper(getActivity());
        ArrayList<OrdersModel> list = helper.getOrders();

        OrdersAdapter adapter=new OrdersAdapter(list, root.getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        binding.orderrecyclerview.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
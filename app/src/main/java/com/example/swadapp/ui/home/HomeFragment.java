package com.example.swadapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swadapp.Adapters.MainAdapter;
import com.example.swadapp.Models.MainModel;
import com.example.swadapp.R;
import com.example.swadapp.databinding.ContentMainBinding;
import com.example.swadapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    private FragmentHomeBinding binding;
//    ContentMainBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root =binding.getRoot();
        recyclerView=root.findViewById(R.id.recyclerview);
        ArrayList<MainModel> list = new ArrayList<>();
        list.add(new MainModel(R.drawable.alootikki, "Aloo Tikki", "5", "The Great Indian Aloo Tikki served with Sauce."));
        list.add(new MainModel(R.drawable.coke, "Coke", "20", "Chilled Coke served with extra ice."));
        list.add(new MainModel(R.drawable.grilledfish, "Grilled Fish", "60", "This grilled fish recipe features halibut fillets marinated in olive oil, " +
                "lemon juice, basil, and garlic, that are cooked to perfection on the grill."));
        list.add(new MainModel(R.drawable.japanese_noodles, "Japanese Noodles", "50", "Japanese Noodles are noodles made in Japan"));
        list.add(new MainModel(R.drawable.breadpakoda, "Bread Pakoda", "10", "Bread Pakoda is the Indian Fast Food in which the Bread is Fried in Oil " +
                "with some potato filling in it"));
        list.add(new MainModel(R.drawable.pastries, "Pastries", "35", "Fresh Pastries are Available with no Egg"));
        list.add(new MainModel(R.drawable.icecream, "Ice Cream", "60", "The great Taste of Ice Cream"));
        list.add(new MainModel(R.drawable.maggie, "Maggie", "30", "Description will be Available soon"));
        list.add(new MainModel(R.drawable.vegroll, "Veg Roll", "50", "Description will be Available soon"));
        list.add(new MainModel(R.drawable.masaladosa, "Masala Dosa", "60", "Description will be Available soon"));
        list.add(new MainModel(R.drawable.chickenwing, "Chicken Wing", "80", "Description will be Available soon"));
        list.add(new MainModel(R.drawable.chillimushroom, "Chilli Mushroom", "40", "Description will be Available soon"));
        list.add(new MainModel(R.drawable.roastedchicken, "Roasted Chicken", "100", "Description will be Available soon"));
        list.add(new MainModel(R.drawable.paneer, "Paneer", "120", "Description will be Available soon"));
        list.add(new MainModel(R.drawable.frenchfries, "French Fries", "95", "Description will be Available soon"));

        MainAdapter adapter = new MainAdapter(list, root.getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        binding.recyclerview.setLayoutManager(layoutManager);
        return root;




    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
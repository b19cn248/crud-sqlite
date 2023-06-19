package com.example.crud_sqlite.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud_sqlite.R;
import com.example.crud_sqlite.UpdateDeleteActivity;
import com.example.crud_sqlite.adapter.RecycleViewAdapter;
import com.example.crud_sqlite.dal.MyDatabase;
import com.example.crud_sqlite.model.Item;

import java.util.List;

public class FragmentHistory extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private MyDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.hRecycleView);
        adapter = new RecycleViewAdapter();
        db = new MyDatabase(getContext());
//        Item i = new Item(1, "Di du lich", "Tien di lai", "1000", "25/04/2023");
//        db.addItem(i);
        List<Item> list = db.getAll();
        adapter.setItemListener(this);
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "sdfsdfsd", Toast.LENGTH_SHORT).show();
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Item> list = db.getAll();
        adapter.setList(list);
    }
}

package com.example.crud_sqlite.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener {
  private RecyclerView recyclerView;
  private MyDatabase db;
  private RecycleViewAdapter adapter;
  private TextView tvTong;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView = view.findViewById(R.id.homeRView);
    tvTong = view.findViewById(R.id.tvTong);
    adapter = new RecycleViewAdapter();
    db = new MyDatabase(getContext());
    Date d = new Date();
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    List<Item> list = db.getByDate(f.format(d));
//        List<Item> list = db.getByDate("24/04/2023");
    adapter.setList(list);
    adapter.setItemListener(this);
    recyclerView.setAdapter(adapter);
    tvTong.setText("Tong tien:" + tong(list));
    LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(manager);
  }

  private int tong(List<Item> list) {
    int t = 0;
    for (Item i : list) {
      t += Integer.parseInt(i.getPrice());
    }
    return t;
  }

  @Override
  public void onItemClick(View view, int position) {
    Item item = adapter.getItem(position);
    Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
    intent.putExtra("item", item);
    Toast.makeText(getContext(), item.getId(), Toast.LENGTH_SHORT).show();
    startActivity(intent);
  }

  @Override
  public void onResume() {
    super.onResume();
    Date d = new Date();
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    List<Item> list = db.getByDate(f.format(d));
    adapter.setList(list);
    tvTong.setText("Tong tien:" + tong(list));
  }
}

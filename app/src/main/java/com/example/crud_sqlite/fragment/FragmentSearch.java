package com.example.crud_sqlite.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud_sqlite.R;
import com.example.crud_sqlite.adapter.RecycleViewAdapter;
import com.example.crud_sqlite.dal.MyDatabase;
import com.example.crud_sqlite.model.Item;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private TextView tvTong;
    private Button btSearch;
    private SearchView searchView;
    private EditText eFrom,eTo;
    private Spinner sp;
    private RecycleViewAdapter adapter;
    private MyDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        db = new MyDatabase(getContext());
        List<Item> list = db.getAll();
        adapter.setList(list);
        recyclerView.setAdapter(adapter);
        tvTong.setText("Tong tien: " + tong(list)+"K");
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> list = db.searchByTitle(s);
                tvTong.setText("Tong tien: " + tong(list)+"K");
                adapter.setList(list);
                return true;
            }
        });
        eFrom.setOnClickListener(this);
        eTo.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String cate = sp.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), cate, Toast.LENGTH_SHORT).show();
                List<Item> list;
                if (!cate.equalsIgnoreCase("All")) {
                    list = db.searchByCatgory(cate);
                }
                else {
                    list = db.getAll();
                }
                adapter.setList(list);
                tvTong.setText("Tong tien: " + tong(list)+"K");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                List<Item> list = db.getAll();
                tvTong.setText("Tong tien: " + tong(list)+"K");
                adapter.setList(list);
            }
        });
    }

    private int tong(List<Item> list) {
        int t = 0;
        for (Item i :list) {
            t+= Integer.parseInt(i.getPrice());
        }
        return t;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycleView);
        tvTong = view.findViewById(R.id.tvTong);
        btSearch = view.findViewById(R.id.btSearch);
        searchView = view.findViewById(R.id.search);
        eFrom = view.findViewById(R.id.eFrom);
        eTo = view.findViewById(R.id.eTo);
        sp = view.findViewById(R.id.spCat);
        String[] arr = getResources().getStringArray(R.array.category);
        String[] arr1 = new String[arr.length+1];
        arr1[0] = "All";
        for (int i=0 ; i<arr.length;i++) {
            arr1[i+1] = arr[i];
        }
        sp.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,arr1));
    }

    @Override
    public void onClick(View view) {
        if (view == eFrom) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if (m > 8) {
                        date = d + "/" + (m + 1) + "/" + y;
                    } else {
                        date = d + "/0" + (m + 1) + "/" + y;
                    }
                    eFrom.setText(date);
                }
            }, year, month, day);
            dialog.show();
        }

        if (view == eTo) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if (m > 8) {
                        date = d + "/" + (m + 1) + "/" + y;
                    } else {
                        date = d + "/0" + (m + 1) + "/" + y;
                    }
                    eTo.setText(date);
                }
            }, year, month, day);
            dialog.show();
        }

        if (view == btSearch) {
            String from = eFrom.getText().toString();
            String to = eTo.getText().toString();
            if (!from.isEmpty() && !to.isEmpty()) {
                List<Item> list = db.searchByDate(from,to);
                tvTong.setText("Tong tien: " + tong(list)+"K");
                adapter.setList(list);
            }
        }
    }
}

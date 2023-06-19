package com.example.crud_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crud_sqlite.dal.MyDatabase;
import com.example.crud_sqlite.model.Item;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btUpdate, btRemove, btBack;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        eTitle.setText(item.getTitle());
        ePrice.setText(item.getPrice() + "");
        eDate.setText(item.getDate());
        int p = 0;
        for (int i = 0; i < sp.getCount(); i++) {
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())) {
                p = i;
                break;
            }
        }
        sp.setSelection(p);
    }

    private void initView() {
        eTitle = findViewById(R.id.updateTitle);
        ePrice = findViewById(R.id.updatePrice);
        eDate = findViewById(R.id.updateDate);
        btUpdate = findViewById(R.id.btUpdate);
        btBack = findViewById(R.id.btBack);
        btRemove = findViewById(R.id.btRemove);
        sp = findViewById(R.id.spCate);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.category)));
        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        btBack.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MyDatabase db = new MyDatabase(this);

        if (view == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if (m > 8) {
                        date = d + "/" + (m + 1) + "/" + y;
                    } else {
                        date = d + "/0" + (m + 1) + "/" + y;
                    }
                    eDate.setText(date);
                }
            }, year, month, day);
            dialog.show();
        }

        if (view == btBack) {
            finish();
        }

        if (view == btUpdate) {
//            String t = eTitle.getText().toString();
//            String p = ePrice.getText().toString();
//            String c = sp.getSelectedItem().toString();
//            String d = eDate.getText().toString();
//            if (!t.isEmpty() && p.matches("\\d+")) {
//                int id = item.getId();
//                Item i = new Item(id,t,c,p,d);
//                db.update(i);
//                finish();
//            }
            Toast.makeText(getApplicationContext(), item.getId(), Toast.LENGTH_SHORT).show();
        }

        if (view == btRemove) {
            int id = item.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thong bao xoa");
            builder.setMessage("Ban co chac muon xoa");
            builder.setIcon(R.drawable.add);
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    db.delete(id);
                    Toast.makeText(UpdateDeleteActivity.this, item.getId(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
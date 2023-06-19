package com.example.crud_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.crud_sqlite.dal.MyDatabase;
import com.example.crud_sqlite.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btAdd, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
    }

    private void initView() {
        eTitle = findViewById(R.id.addTitle);
        ePrice = findViewById(R.id.addPrice);
        eDate = findViewById(R.id.addDate);
        btAdd = findViewById(R.id.btAdd);
        btCancel = findViewById(R.id.btCancel);
        sp = findViewById(R.id.spCategory);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.category)));
        btAdd.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        if (view == btCancel) {
            finish();
        }

        if (view == btAdd) {
            String t = eTitle.getText().toString();
            String p = ePrice.getText().toString();
            String c = sp.getSelectedItem().toString();
            String d = eDate.getText().toString();
            if (!t.isEmpty() && p.matches("\\d+")) {
                Item i = new Item(t,c,p,d);
                MyDatabase db = new MyDatabase(this);
                db.addItem(i);
                finish();
            }
        }
    }
}
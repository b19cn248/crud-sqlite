package com.example.crud_sqlite.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.crud_sqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "Chitieu.db";
  private static final int DATABASE_VERSION = 3;

  public MyDatabase(@Nullable Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String sqlCreateDB = "CREATE TABLE items(" +

          "id INTEGER PRIMARY KEY AUTOINCREMENT," +
          "title TEXT," +
          "category TEXT," +
          "price TEXT," +
          "date TEXT)";

    String createTableUser = "CREATE TABLE user (\n" +
          "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
          "    fullname TEXT,\n" +
          "    email TEXT,\n" +
          "    password TEXT\n" +
          ");";

    db.execSQL(sqlCreateDB);
    db.execSQL(createTableUser);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
  }

  @Override
  public void onOpen(SQLiteDatabase db) {
    super.onOpen(db);
  }

  public List<Item> getAll() {
    List<Item> list = new ArrayList<>();
    SQLiteDatabase st = getReadableDatabase();
    String order = "date DESC";
    Cursor rs = st.query("items", null, null, null, null, null, order);
    while (rs != null && rs.moveToNext()) {
      int id = rs.getInt(0);
      String title = rs.getString(1);
      String category = rs.getString(2);
      String price = rs.getString(3);
      String date = rs.getString(4);
      list.add(new Item(id, title, category, price, date));
    }
    return list;
  }

  public long addItem(Item i) {
    ContentValues values = new ContentValues();
    values.put("id", i.getId());
    values.put("title", i.getTitle());
    values.put("category", i.getCategory());
    values.put("price", i.getPrice());
    values.put("date", i.getDate());
    SQLiteDatabase db = getWritableDatabase();
    return db.insert("items", null, values);
  }

//    public List<Item> getByDate(String date) {
//        List<Item> list = new ArrayList<>();
//        String where = "date like ?";
//        String[] whereArgs = {date};
//        SQLiteDatabase st = getReadableDatabase();
//        Cursor rs = st.query("items", null, where, whereArgs, null, null, null);
//        while (rs != null && rs.moveToNext()) {
//            int id = rs.getInt(0);
//            String title = rs.getString(1);
//            String category = rs.getString(2);
//            String price = rs.getString(3);
//            list.add(new Item(id, title, category, price, date));
//        }
//        return list;
//    }

  public List<Item> getByDate(String date) {
    List<Item> list = new ArrayList<>();
    String whereClause = "date like ?";
    String[] whereArgs = {date};
    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    Cursor rs = sqLiteDatabase.query("items",
          null, whereClause, whereArgs,
          null, null, null);
    while ((rs != null) && (rs.moveToNext())) {
      int id = rs.getInt(0);
      String title = rs.getString(1);
      String category = rs.getString(2);
      String price = rs.getString(3);
      list.add(new Item(id, title, category, price, date));
    }
    return list;
  }

  public List<Item> searchByTitle(String key) {
    List<Item> list = new ArrayList<>();
    String whereClause = "title like ?";
      String[] whereArgs = {"%" + key + "%"};
    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    Cursor rs = sqLiteDatabase.query("items",
          null, whereClause, whereArgs,
          null, null, null);
    while ((rs != null) && (rs.moveToNext())) {
      int id = rs.getInt(0);
      String title = rs.getString(1);
      String category = rs.getString(2);
      String price = rs.getString(3);
      String date = rs.getString(4);
      list.add(new Item(id, title, category, price, date));
    }
    return list;
  }

  public List<Item> searchByCatgory(String category) {
    List<Item> list = new ArrayList<>();
    String whereClause = "category like ?";
    String[] whereArgs = {category};
    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    Cursor rs = sqLiteDatabase.query("items",
          null, whereClause, whereArgs,
          null, null, null);
    while ((rs != null) && (rs.moveToNext())) {
      int id = rs.getInt(0);
      String title = rs.getString(1);
      String c = rs.getString(2);
      String price = rs.getString(3);
      String date = rs.getString(4);
      list.add(new Item(id, title, c, price, date));
    }
    return list;
  }

  public List<Item> searchByDate(String from, String to) {
    List<Item> list = new ArrayList<>();
    String whereClause = "date BETWEEN ? AND ?";
    String[] whereArgs = {from.trim(), to.trim()};
    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    Cursor rs = sqLiteDatabase.query("items",
          null, whereClause, whereArgs,
          null, null, null);
    while ((rs != null) && (rs.moveToNext())) {
      int id = rs.getInt(0);
      String title = rs.getString(1);
      String category = rs.getString(2);
      String price = rs.getString(3);
      String date = rs.getString(4);
      list.add(new Item(id, title, category, price, date));
    }
    return list;
  }

//  public List<Item> searchByDate(String from, String to) {
//    List<Item> itemList = new ArrayList<>();
//    SQLiteDatabase db = this.getReadableDatabase();
//    String[] columns = {"id", "title", "category", "price", "date"};
//    String selection = "date BETWEEN ? AND ?";
//    String[] selectionArgs = {from, to};
//    Cursor cursor = db.query("items", columns, selection, selectionArgs, null, null, null);
//
//    if (cursor.moveToFirst()) {
//      do {
//        int id = cursor.getInt(cursor.getColumnIndex("id"));
//        String title = cursor.getString(cursor.getColumnIndex("title"));
//        String category = cursor.getString(cursor.getColumnIndex("category"));
//        String price = cursor.getString(cursor.getColumnIndex("price"));
//        String date = cursor.getString(cursor.getColumnIndex("date"));
//
//        Item item = new Item(id, title, category, price, date);
//        itemList.add(item);
//      } while (cursor.moveToNext());
//    }
//
//    cursor.close();
//    db.close();
//    return itemList;
//  }

  public int update(Item i) {
    ContentValues values = new ContentValues();
    values.put("title", i.getTitle());
    values.put("category", i.getCategory());
    values.put("price", i.getPrice());
    values.put("date", i.getDate());
    SQLiteDatabase db = getWritableDatabase();
    String where = "id=?";
    String[] whereArgs = {Integer.toString(i.getId())};
    return db.update("items", values, where, whereArgs);
  }

  public int delete(int id) {
    String where = "id=?";
    String[] whereArgs = {Integer.toString(id)};
    SQLiteDatabase db = getWritableDatabase();
    return db.delete("items", where, whereArgs);
  }
}

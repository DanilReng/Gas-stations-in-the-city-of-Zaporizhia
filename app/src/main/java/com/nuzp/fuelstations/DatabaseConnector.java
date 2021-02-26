package com.nuzp.fuelstations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseConnector extends SQLiteOpenHelper {
    public SQLiteDatabase database;
    private final Context context;
    private final static String DATABASE_NAME = "fuelStations.db";
    private String DATABASE_PATH;
    private final String
            GET_BRANDS = "SELECT * from brands",
            GET_STATIONS = "SELECT * FROM stations WHERE stations.brand_id = ?",
            GET_BRAND = "SELECT brands.brand FROM brands WHERE brands.brand_id = ?";

    private static DatabaseConnector instance = null;

    public static DatabaseConnector getInstance(Context context) {
        if (instance == null) {
            return new DatabaseConnector(context);
        }
        return instance;
    }

    public DatabaseConnector(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        DATABASE_PATH = context.getFilesDir().getPath() + "/";
        try {
            copyDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        database = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean copyDataBase() throws IOException {
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        if (!((new File(outFileName)).exists())) {
            InputStream myInput = context.getAssets().open(DATABASE_NAME);
            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Brand> getBrands() {
        Cursor cursor = database.rawQuery(GET_BRANDS, null);

        ArrayList<Brand> brands = new ArrayList<>();

        while (cursor.moveToNext())
            brands.add(new Brand(Integer.parseInt(cursor.getString(0)), cursor.getString(1)));

        cursor.close();
        return brands;
    }

    public ArrayList<Station> getStations(int brand_id) {
        Cursor cursor = database.rawQuery(GET_STATIONS, new String[]{String.valueOf(brand_id)});

        ArrayList<Station> stations = new ArrayList<>();

        while (cursor.moveToNext())
            stations.add(new Station(
                    cursor.getInt(cursor.getColumnIndex("station_id")),
                    cursor.getInt(cursor.getColumnIndex("brand_id")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("address")),
                    cursor.getString(cursor.getColumnIndex("phone"))
            ));

        cursor.close();
        return stations;
    }

    public String getBrandById(int brand_id) {
        Cursor cursor = database.rawQuery(GET_BRAND, new String[]{String.valueOf(brand_id)});
        cursor.moveToFirst();
        String brand = cursor.getString(0);
        cursor.close();
        return brand;
    }
}

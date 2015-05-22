package hevs.ch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Evelyn on 22.05.2015.
 */
public class DatabaseAccess {

    private static SQLHelper helper;
    private static SQLiteDatabase database;
    private Context context;

    // *************************************************************************
    //                      open connection
    // *************************************************************************
    public DatabaseAccess(Context context){
        this.context = context;
    }

    private void openConnection(){
        helper = new SQLHelper(context, null);
        database = helper.getWritableDatabase();


    }
    // *************************************************************************
    //                      close connection
    // *************************************************************************
    public  void close(){
        helper.close();
    }

    // *************************************************************************
    //                      saving locally
    // *************************************************************************
    public long writeImage(HistoricImage image){
        long id;

        openConnection();
        ContentValues values = new ContentValues();
        values.put(SQLHelper.IMAGE_LONGITUDE, image.getLongitude());
        values.put(SQLHelper.IMAGE_LATITUDE, image.getLatitude());
        values.put(SQLHelper.IMAGE_URL, image.getUrl());

        id = database.insert(SQLHelper.TABLE_NAME_IMAGE, null, values);
        close();
        return id;
    }

    public List<HistoricImage> readAllImage(){
        List<HistoricImage> images = new ArrayList<>();
        HistoricImage image;
        Cursor cursor;

        openConnection();

        String sql = "SELECT * FROM "+SQLHelper.TABLE_NAME_IMAGE;
        cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            image = new HistoricImage();

            image.setId(cursor.getLong(cursor.getColumnIndex(SQLHelper.IMAGE_ID)));
            image.setLatitude(cursor.getDouble(cursor.getColumnIndex(SQLHelper.IMAGE_LATITUDE)));
            image.setLongitude(cursor.getDouble(cursor.getColumnIndex(SQLHelper.IMAGE_LONGITUDE)));
            image.setUrl(cursor.getString(cursor.getColumnIndex(SQLHelper.IMAGE_URL)));

            images.add(image);
            cursor.moveToNext();
        }
        cursor.close();

        return images;
    }

}

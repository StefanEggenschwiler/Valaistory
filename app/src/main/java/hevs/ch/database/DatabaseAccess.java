package hevs.ch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
        helper = new SQLHelper(context);
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
        values.put(SQLHelper.IMAGE_Description, image.getDescription());

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
            image.setDescription(cursor.getString(cursor.getColumnIndex(SQLHelper.IMAGE_Description)));

            images.add(image);
            cursor.moveToNext();
        }
        cursor.close();
        close();

        return images;
    }

    public void writeSomeDummyData(){
        HistoricImage image1 = new HistoricImage();
        image1.setDescription("Construction dun batiment a Viege");
        image1.setUrl("http://photo.memovs.ch/009ph/009phj/009ph-00038.jpg");
        image1.setLongitude(7.53948838d);
        image1.setLatitude(46.28252409d);

        writeImage(image1);

        HistoricImage image2 = new HistoricImage();
        image2.setDescription("Ancien hopital bourgeoisial Sion");
        image2.setUrl("http://photo.memovs.ch/008ph/008phj/008ph-00356.jpg");
        image2.setLongitude(7.53869981d);
        image2.setLatitude(46.28243882d);

        writeImage(image2);

        HistoricImage image3 = new HistoricImage();
        image3.setDescription("Chapelle Ayent");
        image3.setUrl("http://photo.memovs.ch/008ph/008phj/008ph-00870.jpg");
        image3.setLongitude(7.53873736d);
        image3.setLatitude(46.28298751d);

        writeImage(image3);

        HistoricImage image4 = new HistoricImage();
        image4.setDescription("Hotel Central Martigny");
        image4.setUrl("http://photo.memovs.ch/008ph/008phj/008ph-00402.jpg");
        image4.setLongitude(7.54017502d);
        image4.setLatitude(46.28292819d);

        writeImage(image4);

        HistoricImage image5 = new HistoricImage();
        image5.setDescription("Village de Champery");
        image5.setUrl("http://photo.memovs.ch/008ph/008phj/008ph-00216.jpg");
        image5.setLongitude(7.53917500d);
        image5.setLatitude(46.28292810d);

        writeImage(image5);
    }

}

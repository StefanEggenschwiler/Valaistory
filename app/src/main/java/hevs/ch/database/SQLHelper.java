package hevs.ch.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Evelyn on 22.05.2015.
 */
public class SQLHelper extends SQLiteOpenHelper {

    private Context context;

    // DB settings
    private static final String DATABASE_NAME = "valaistory.db";
    private static final int DATABASE_VERSION = 1;

    // titles of the table
    public static final String TABLE_NAME_IMAGE = "Historic_Image";

    // Title of Columns of Image
    public static final String IMAGE_ID = "id_image";
    public static final String IMAGE_LONGITUDE = "longitude";
    public static final String IMAGE_LATITUDE = "latitude";
    public static final String IMAGE_URL = "url";
    public static final String IMAGE_Description = "description";


    // *************************************************************************
    //                      create table
    // *************************************************************************
    // Championship_user
    public static final String TABLE_CREATE_IMAGE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_IMAGE + "(" +
            IMAGE_ID + " INTEGER PRIMARY KEY," +
            IMAGE_LONGITUDE + " DOUBLE, " +
            IMAGE_LATITUDE + " DOUBLE, " +
            IMAGE_URL + "TEXT," +
            IMAGE_Description + "TEXT" +
            ")";

    public SQLHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_IMAGE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_IMAGE);

        // create fresh tables
        onCreate(db);
    }
}

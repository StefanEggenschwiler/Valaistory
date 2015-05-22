package hevs.ch.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Evelyn on 22.05.2015.
 */
public class DatabaseAccess {

    private static SQLHelper helper;
    private static SQLiteDatabase database;

    // *************************************************************************
    //                      open connection
    // *************************************************************************
    private static void openConnection(Context context){
        helper = new SQLHelper(context, null);
        database = helper.getWritableDatabase();
    }
    // *************************************************************************
    //                      close connection
    // *************************************************************************
    public static void close(){
        helper.close();
    }

    // *************************************************************************
    //                      saving locally
    // *************************************************************************
}

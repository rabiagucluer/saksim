package com.example.tugcesakarya.saksim;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tugcesakarya on 29.08.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int database_VERSION = 1;
    static final String DB_PATH = "data/data/com.example.tugcesakarya.saksim/databases/";
    static final String DB_NAME = "SaksimReal.db";
    SQLiteDatabase sqLiteDatabase;
    Context context;

    public DatabaseHelper(Context mContext) {
        super(mContext, DB_NAME, null, database_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Eğer veritabanı yoksa kopyalayıp oluşturacak, varsa hiç bir şey yapmayacak

    public void createDatabase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //database varsa hiç bir şey yapma
        } else {
            getReadableDatabase();
            //veritabanı daha önce oluşturulmamış burada veritabanını kopyalıyoruz
            copyDatabase();
        }


    }

    // veritabanı daha önce oluşturulmuş mu, oluşturulmamış mı bunu öğrenmek için
    // oluşturulmuşsa true, oluşturulmamışsa false değeri döndürür
    public boolean checkDataBase() {


        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {

            checkDB.close();
        }
        return checkDB != null ? true : false;

    }

    public void copyDatabase() throws IOException{

        //assets --> DB_PATH+DB_NAME;
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int lenght;
            while ((lenght = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, lenght);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void openDatabase () throws IOException{

        String myPath = DB_PATH + DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }

    public SQLiteDatabase getDatabase() {

        return sqLiteDatabase;
    }

}

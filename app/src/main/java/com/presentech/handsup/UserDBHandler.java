package com.presentech.handsup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * Created by Lewis on 17/04/2016.
 */

/*This class is used to handle User Database related functions*/
public class UserDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB.db";
    private static final String TABLE_USERS = "users";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCKOUT = "lockout";

    private final Context myContext;
    private final static String TAG = "DatabaseHandler";
    private String DATABASE_PATH = "/data/data/com.presentech.handsup/databases/";

    public UserDBHandler(Context context, String name,
                         SQLiteDatabase.CursorFactory factory, int version, String filePath) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    //removes the old database and create a new one
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    //Method to add new user to userDB
    public void addUser(User user) {
        //Content Values user to store user data for insertion into SQLite Database
        ContentValues values = new ContentValues();
        //Get user data
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_LOCKOUT, user.getLockout());

        SQLiteDatabase db = this.getWritableDatabase();

        //Update Database Table
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    //Method to update user lockout state in userDB
    public boolean updateUser(String email, String lockout) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Create content values for updated user data
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LOCKOUT, lockout);
        //Update lockout field for record with email address matching value of email
        db.update(TABLE_USERS, contentValues, "email = ?", new String[]{email});
        return true;
    }

    //Method to find user in userDB using email address
    public User findUser(String email) {
        String query = "Select * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " =  \"" + email + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        User user = new User();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            user.setID(Integer.parseInt(cursor.getString(0)));
            user.setEmail(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setName(cursor.getString(3));
            user.setLockout(cursor.getString(4));
            cursor.close();
        } else {
            user = null;
        }
        db.close();
        //Return user if found in UserDB
        return user;
    }

    //Method to delete user from userDB, searching via email
    //Not currently used in app
    public boolean deleteUser(String email) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " =  \"" + email + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();

        //Search for user using ID and Delete from DB
        if (cursor.moveToFirst()) {
            user.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_USERS, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(user.getID())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    //Method to copy UserDB file from device assets folder to databases folder
    public void copyDataBase() throws IOException {
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        //Get userDB.db file from assets folder
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        //Copy input file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    //Method to check if UserDB file exists on device
    public boolean checkUserDB() {
        boolean checkDB = false;
        try {
            File file = new File(DATABASE_PATH + DATABASE_NAME);
            checkDB = file.exists();
        }
        //throw exception if database file not found
        catch (SQLiteException e) {
            Log.d(TAG, e.getMessage());
        }
        return checkDB;
    }
}
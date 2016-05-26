



/**Created by Ed 13/05/2016*/
/** Resource http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/ used in creation*/
package com.presentech.handsup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class feedbackDatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "PRESENTATION_DATE";
    // Contacts table name
    private static final String TABLE_FEEDBACK = "TABLE_OF_FEEDBACK";
    private String DATABASE_PATH = "/data/data/com.presentech.handsup/FeedbackDatabases/";
    private final Context myContext;
    SQLiteDatabase db;

/**Collumn names - reflects data in SingleFeedback.java*/
    private static final String COLUMN_UUID = "uuid"; /**Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
    private static final String COLUMN_SLIDE = "slideNumber"; /**Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
    private static final String COLUMN_SLIDE_ITERATION = "slideIteration"; /**Slide iteration - for when a slide is visited multiple times.*/
    private static final String COLUMN_QUESTION = "questionNumber"; /**In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
    private static final String COLUMN_ABC = "abc";
    private static final String COLUMN_GOODMEHBAD = "goodmehbad";
    private static final String COLUMN_COMMENTS = "comments";  /**If anything but null the text should be interprted - acts as flag for text input.*/
    private static final String COLUMN_TIME_RECEIVED = "timeReceived"; /**To be set on reception - store in mm relative to system epoch*/
    private static final String COLUMN_KEY_ID = "id";




    public feedbackDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                   int version, String filepath) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION); //TODO - this is where multiple saves can be sorted

        this.myContext = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FEEDBACK + "(" +
        COLUMN_UUID + " TEXT," +
        COLUMN_SLIDE + " INTEGER," +
        COLUMN_SLIDE_ITERATION + " INTEGER," +
        COLUMN_QUESTION + " INTEGER,"    +
        COLUMN_ABC + " INTEGER," +
        COLUMN_GOODMEHBAD + " INTEGER," +
        COLUMN_COMMENTS + " TEXT," +
        COLUMN_TIME_RECEIVED + " INTEGER," +
        COLUMN_KEY_ID + " INTEGER PRIMARY KEY" +
        /**Checks*/
        //"CHECK (" + COLUMN_ABC + " IN (-1,0,1,2))" +
        //"CHECK (" + COLUMN_GOODMEHBAD + " IN (-1,0,1,2))" +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
        // Create tables again
        onCreate(db);
    }

    // Adding new contact
     public void addFeedbackCollumn(SingleFeedback feedback) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_UUID, feedback.getUUID());
        values.put(COLUMN_SLIDE, feedback.getSLIDE());
        values.put(COLUMN_SLIDE_ITERATION, feedback.getSLIDE_ITERATION());
        values.put(COLUMN_QUESTION, feedback.getQUESTION());
        values.put(COLUMN_ABC, feedback.getABC());
        values.put(COLUMN_GOODMEHBAD,  feedback.getGOOD_MEH_BAD());
        values.put(COLUMN_COMMENTS, feedback.getTEXT());
        values.put(COLUMN_TIME_RECEIVED,  feedback.getTIME_RECEIVED());
        //Open db connection, add contact, close connection.
        db = this.getWritableDatabase();
        db.insert(TABLE_FEEDBACK, null, values);
        db.close();
    }

    // Getting All Feedback
    public List<SingleFeedback> getAllFeedback() {

        List<SingleFeedback> feedbackList = new ArrayList<SingleFeedback>();
        String selectQuery = "SELECT  * FROM " + TABLE_FEEDBACK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SingleFeedback feedback;
        cursor.moveToFirst();
        int COLUMNindex_UUID = cursor.getColumnIndex(COLUMN_UUID);
        int COLUMNindex_SLIDE = cursor.getColumnIndex(COLUMN_SLIDE);
        int COLUMNindex_SLIDE_ITERATION = cursor.getColumnIndex(COLUMN_SLIDE_ITERATION);
        int COLUMNindex_QUESTION = cursor.getColumnIndex(COLUMN_QUESTION);
        int COLUMNindex_ABC = cursor.getColumnIndex(COLUMN_ABC);
        int COLUMNindex_GOODMEHBAD = cursor.getColumnIndex(COLUMN_GOODMEHBAD);
        int COLUMNindex_COMMENTS = cursor.getColumnIndex(COLUMN_COMMENTS);
        int COLUMNindex_TIME_RECEIVED = cursor.getColumnIndex(COLUMN_TIME_RECEIVED);
        int COLUMNindex_KEY_ID = cursor.getColumnIndex(COLUMN_KEY_ID);

        // add to list until table runs out of rows
            do {
                feedback = new SingleFeedback();
                feedback.setUUID(cursor.getString(COLUMNindex_UUID));
                feedback.setSLIDE(cursor.getDouble(COLUMNindex_SLIDE));
                feedback.setSLIDE_ITERATION(cursor.getInt(COLUMNindex_SLIDE_ITERATION));
                feedback.setQUESTION(cursor.getInt(COLUMNindex_QUESTION));
                feedback.setABC(cursor.getInt(COLUMNindex_ABC));
                feedback.setGOOD_MEH_BAD(cursor.getInt(COLUMNindex_GOODMEHBAD));
                feedback.setTEXT(cursor.getString(COLUMNindex_COMMENTS));
                feedback.setTIME_RECEIVED(cursor.getLong(COLUMNindex_TIME_RECEIVED));
                // Adding contact to list
                feedbackList.add(feedback);
            } while (cursor.moveToNext());

        return feedbackList; //TODO more error handling?
    }


    public void deleteTable() {
        List<SingleFeedback> feedbackList = new ArrayList<SingleFeedback>();
        String selectQuery = "SELECT  * FROM " + TABLE_FEEDBACK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SingleFeedback feedback;
        cursor.moveToFirst();
        db.delete(TABLE_FEEDBACK, null, null);
        db.close();
    }

// THIS CODE IS DEAD TO ME
//    // Getting single contact
//    public SingleFeedback getFeedback(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_FEEDBACK,
//                new String[]{COLUMN_KEY_ID,
//                        COLUMN_UUID,
//                        COLUMN_SLIDE,
//                        COLUMN_SLIDE_ITERATION,
//                        COLUMN_QUESTION,
//                        COLUMN_ABC,
//                        COLUMN_GOODMEHBAD,
//                        COLUMN_COMMENTS,
//                        COLUMN_TIME_RECEIVED},
//                COLUMN_KEY_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//
//        String updateNameEvent = null;
//        if (cursor != null)
//            cursor.moveToFirst();
////Checks not NULL.
//        SingleFeedback feedback;
//             feedback = new SingleFeedback();
//            if (cursor != null && cursor.moveToFirst() && cursor.getCount() != 0) {
//                feedback.setUUID(cursor.getString(1));
//                feedback.setSLIDE(Float.parseFloat(cursor.getString(2)));
//                feedback.setSLIDE_ITERATION(Integer.parseInt(cursor.getString(3)));
//                feedback.setQUESTION(Integer.parseInt(cursor.getString(4)));
//                feedback.setABC(Integer.parseInt(cursor.getString(5)));
//                feedback.setGOOD_MEH_BAD(Integer.parseInt(cursor.getString(6)));
//                feedback.setTEXT(cursor.getString(7));
//                feedback.setTIME_RECEIVED(Long.parseLong(cursor.getString(8)));
//            }
//
//        return feedback;
//
//        // TODO - error handling if  the curser pointer if null or 0.
//    }

}
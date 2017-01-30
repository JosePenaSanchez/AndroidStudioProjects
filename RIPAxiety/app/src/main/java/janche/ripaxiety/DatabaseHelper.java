package janche.ripaxiety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.icu.util.ULocale;
import android.util.Log;

import java.util.Date;
import java.util.Locale;

/**
 * Created by: Jose Pena 11/15/16.
 * modified by: Patrick Abbey 12/5/16
 * modified by: Jose Pena 12/7/2016
 */


//We inherit class:SQLiteOpenHelper methods and data.
//SQLiteOpenHelper is the superclass to handle SQLite.
public class DatabaseHelper extends SQLiteOpenHelper
{
    //Database Name, Table Name, Column Names
    public static final String DATABASE_NAME = "ripanxiety.db";




    //Table 1. User tables
    public static final String TABLE_USERS = "USERS";
    //USER_ID var isn't really used to insert id, because this primary is set to autoincrement
    public static final String USER_ID = "USER_ID";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD_HASH = "PASSWORD_HASH";




    //Table 2. Assessment
    public static final String  TABLE_NAME2 = "ASSESSMENT";
    //ASSESSMENT_ID var isn't really used to insert id, because this primary key is set to autoincrement
    public static final String COL_A = "ASSESSMENT_ID";
    public static final String COL_B= "DATE_TAKEN";
    public static final String COL_C = "ASSESSMENT_SCORE";
    public static final String COL_D = "INTENSITY";


    //Table 3. Goals
    public static final String TABLE_NAME3 = "GOALS";
    //GOAL_ID var isn't really used to insert id, because this column is set to autoincrement
    public static final String COL_00 = "GOAL_ID";
    public static final String COL_01 = "GOAL_SENTENCE";
    public static final String COL_10 = "COMPLETE";
    public static final String COL_11 = "GOAL_PROGRESS";
    public static final String COL_100 = "GOAL_DATE";
    public static final String COL_111 = "USER_ID";

    //Constructor
    //Must make a constructor to use SQLiteOpenHelper to create database
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }


    //Must overide onCreate method to use SQLiteOpenHelper to create table
    //This is a required part. It makes the tables.
    //I've added the assessment and goals table on it too.
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //execSQL executes query
        //This was the old statment. I changed the column names so I commented this statement out. And replaced a new one
        //db.execSQL("create table " + TABLE_NAME1 + "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD_HASH TEXT)");
        db.execSQL("create table " + TABLE_USERS + "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD_HASH TEXT)");
        db.execSQL("create table " + TABLE_NAME2 + "(ASSESSMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,USER_ID INTEGER, DATE_TAKEN DATETIME CURRENT_TIMESTAMP, ASSESSMENT_SCORE INTEGER, INTENSITY INTEGER)");
        db.execSQL("create table " + TABLE_NAME3 + "(GOAL_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, GOAL_SENTENCE TEXT, COMPLETE TEXT, GOAL_PROGRESS INTEGER, GOAL_DATE DATE)");
    }


    //Must override onUpgrade method to use SQLiteOpenHelper
    //This is just a required part.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        //I commented the below statment for version control. I changed table1's table name, so I replace that
        //SQL query with a new one, to reflect changes
        //db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME1 + "," + TABLE_NAME2 + "," + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USERS + "," + TABLE_NAME2 + "," + TABLE_NAME3);
        onCreate(db);
    }


    //This part works and was tested. It adds a user name, user id, and password onto the database, via SignUpFragment.java
    //It is not complete, before it adds anything to the table, it must check to see if the provided username variable is not
    //already stored in the databse, and it doesn't do that yet.
    public boolean insertNewUsersData(String username, String passwordhash)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME,username);
        contentValues.put(PASSWORD_HASH,passwordhash);

        //checks to see if values have been added by returning a boolean. I will use the boolean in SignUPFragment.java
        long result = db.insert(TABLE_USERS, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    //I added this new function
    //Check user accepts a username string as input and returns a string
    //String that is returned indicates if a username can be added
    public String checkUser(String input)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuffer buffer = new StringBuffer();
        Cursor result = db.rawQuery("SELECT USERNAME FROM USERS WHERE USERNAME = '" + input + "'",null);
        String message = "Message Initialized";

        //No such user exists, it is okay to store this new username into the database
        if(result.getCount() == 0)
        {
            message = "Okay to add";
            return message;
        }

        //There 1 user with such username, so we can't store this new username
        else if(result.getCount() == 1)
        {
            message = "Username already exists, Not okay to add";
            return message;
        }

        //2 or more users of this same name exists, which shouldn't happen, so there is a bug
        else if(result.getCount() >= 2)
        {
            message = "there is a duplicate users with the same username in database";
            return message;
        }

        return message;
    }



    //not sure if i need a complete section here
    //This part is not tested yet, and not completed.
    //This part will allows us to save the checklist items.
    //I am not sure how we should implement this table yet, because I haven't scrutinized the checklist code
    //I'm not sure what it is that we should store.
    //I created column, which may or may not be use..
    public boolean insertNewGoalData(String goal_sentence)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_01,goal_sentence);
        contentValues.put(COL_11, 1);
        contentValues.put(COL_10, "FALSE");

        //System date and time of when new record was added
        contentValues.put(COL_100, getDateTime());


        //checks to see if values have been added
        long result = db.insert(TABLE_NAME3, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public void updateGoalProgress(Cursor cur, int day)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_11, day);
        db.update(TABLE_NAME3, cv, "GOAL_ID=" + cur.getInt(cur.getColumnIndex("GOAL_ID")), null);
    }

    public void updateGoalChecked(Cursor cur, boolean checked)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (checked)
        {
            cv.put(COL_10, "TRUE");
        }
        else
        {
            cv.put(COL_10, "FALSE");
        }
        db.update(TABLE_NAME3, cv, "GOAL_ID=" + cur.getInt(cur.getColumnIndex("GOAL_ID")), null);

    }

    public void updateGoalDate(Cursor cur)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_100, getDateTime());
        db.update(TABLE_NAME3, cv, "GOAL_ID=" + cur.getInt(cur.getColumnIndex("GOAL_ID")), null);
    }

    //not sure if this part is completed yet. Most likely not. I haven't been focusing much
    //on assessment yet. Again. this part needs to be tested and maybe modified
    //It has not been tested yet.
    public boolean insertNewAssessmentData(String score, int user_id_fk, int intensity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //SystemDateTime when new assessment row is added
        contentValues.put(COL_B,getDateTime());
        contentValues.put(COL_C,score);
        contentValues.put(COL_D,intensity);


        //checks to see if values have been added, may use returned booleans to check if stuff was added.
        //if stuff is not added, result should be set to -1.. (that is just an SQLite thing..)
        long result = db.insert(TABLE_NAME3, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }



    //This function returns the current systems data and time. Can be used for checklist and assessment.
    //This part is not tested yet. It needs to be tested. So I don't know if it works.
    public String getDateTime()
    {
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault() );
        Date date = new Date();
        return dateFormat.format(date);
    }


    //This function returns all the data stored in the user's table. This part is used for testing. it works.
    //It is so we can see if the data is stored in the database table or not.
    //It must be called using a button click in the activity page that we are testing..
    //It only returns the data, as a Cursor data type (it is not a text, string etc.. we must convert the Cursor data type to a string)
    //Look at SignUpFragment.java to see an example on how a data conversion from Cursor to String is done.
    //You must have a place to display the text.. I suggest a diologbox (an example of that is in SignUpFragment.java)
  
    //used for testing purposes, provides cursor to all data on User's table
    public Cursor getAllData ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        return result;
    }
  
  
  
     public int validateLogIn(String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT USERNAME, PASSWORD_HASH FROM USERS WHERE USERNAME = '" + username + "' and PASSWORD_HASH = '" + password + "'",null);



        //No such record exsists. So return false as a flag to tell user that their password or username is incorrect
        if (result.getCount() == 0)
            return 0;

        //record exsists, return true to log ing
        else if(result.getCount() == 1)
            return 1;

        //this part is used for testing
        //There is more than 1 of this record in the database, there is a bug
        else
            return 3;
    }
  
    //Commented the bottom part because I modified it. left it here just incase my modifications messes up
    //anything anyone has.
   /* 
    public Cursor getallData ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
        return result;

    }
*/
    public Cursor getAllChecklistData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME3, null);
        return result;
    }

    public int getIntensity()
    {
        //SQLiteDatabase db = this.getWritableDatabase();
        //Cursor result = db.rawQuery("SELECT INTENSITY FROM " + TABLE_NAME2, null);
        //return result.getInt(0);
        return 1;
    }

}


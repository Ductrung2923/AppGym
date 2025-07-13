package com.example.gym.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gym.Model.UserSelection;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WorkoutDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SELECTION = "user_selections";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSelectionTable = "CREATE TABLE " + TABLE_SELECTION + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "gender TEXT, " +
                "muscle_group TEXT, " +
                "exercise TEXT, " +
                "difficulty TEXT, " +
                "timestamp INTEGER)";
        db.execSQL(createSelectionTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveUserSelection(UserSelection selection) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("gender", selection.getGender());
        values.put("muscle_group", selection.getMuscleGroup());
        values.put("exercise", selection.getExercise());
        values.put("difficulty", selection.getDifficulty());
        values.put("timestamp", selection.getTimestamp());

        db.insert(TABLE_SELECTION, null, values);
        db.close();
    }

//    public UserSelection getSelections() {
//        UserSelection selections = new UserSelection();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM user_selections", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                UserSelection selection = new UserSelection(
//                        cursor.getString(cursor.getColumnIndexOrThrow("gender")),
//                        cursor.getString(cursor.getColumnIndexOrThrow("muscle_group")),
//                        cursor.getString(cursor.getColumnIndexOrThrow("exercise")),
//                        cursor.getString(cursor.getColumnIndexOrThrow("difficulty"))
//                );
//                selection.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
//                selection.setTimestamp(cursor.getLong(cursor.getColumnIndexOrThrow("timestamp")));
//
//                selections.add(selection);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return selections;
//    }

}


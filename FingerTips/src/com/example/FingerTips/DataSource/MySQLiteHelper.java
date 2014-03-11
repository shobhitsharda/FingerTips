package com.example.FingerTips.DataSource;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{

	public static final String TABLE_SUBJECTS = "Subjects";
	public static final String COLUMN_SUBJECT_ID = "subject_id";
	public static final String COLUMN_SUBJECT_NAME = "subject_name";

	public static final String TABLE_CHAPTERS = "Chapters";
	public static final String COLUMN_CHAPTER_ID = "chapter_id";
	public static final String COLUMN_CHAPTER_NAME = "chapter_name";
	
	public static final String TABLE_TOPICS = "Topics";
	public static final String COLUMN_TOPIC_ID = "topic_id";
	public static final String COLUMN_TOPIC_NAME = "topic_name";
	public static final String COLUMN_TOPIC_DATA = "topic_data";
	
	public static final String TABLE_LA = "LA";
	public static final String COLUMN_LA_ID = "la_id";
	public static final String COLUMN_TOPIC_STAT = "topic_stat";
	
	private static final String DATABASE_NAME = "FingerTips.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_SUBJECTS = "create table "
			+ TABLE_SUBJECTS + "(" 	+ COLUMN_SUBJECT_ID + " integer primary key autoincrement, " 
									+ COLUMN_SUBJECT_NAME + " text not null);";

	private static final String DATABASE_CREATE_CHAPTERS = "create table "
			+ TABLE_CHAPTERS + "(" 	+ COLUMN_CHAPTER_ID	+ " integer primary key autoincrement, " 
									+ COLUMN_SUBJECT_ID + " integer, "
									+ COLUMN_CHAPTER_NAME + " text not null," 
									+ "FOREIGN KEY(subject_id) REFERENCES Subjects(subject_id));";
	
	private static final String DATABASE_CREATE_TOPICS = "create table "
			+ TABLE_TOPICS + "(" 	+ COLUMN_TOPIC_ID	+ " integer primary key autoincrement, " 
									+ COLUMN_SUBJECT_ID + " integer, "
									+ COLUMN_CHAPTER_ID + " integer, "
									+ COLUMN_TOPIC_NAME + " text not null,"
									+ COLUMN_TOPIC_DATA + " text not null, "
									+ "FOREIGN KEY(subject_id) REFERENCES Subjects(subject_id)" 
									+ "FOREIGN KEY(chapter_id) REFERENCES Chapters(chapter_id));";
	
	private static final String DATABASE_CREATE_LA = "create table "
			+ TABLE_LA + "(" 	+ COLUMN_LA_ID + " integer primary key autoincrement, "
									+ COLUMN_TOPIC_ID + " integer, " 
									+ COLUMN_TOPIC_STAT + " integer, "
									+ "FOREIGN KEY(topic_id) REFERENCES Topics(topic_id));";
	
	public MySQLiteHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		
		
		try
		{
			db.execSQL(DATABASE_CREATE_SUBJECTS);
			db.execSQL(DATABASE_CREATE_CHAPTERS);
			db.execSQL(DATABASE_CREATE_TOPICS);
			db.execSQL(DATABASE_CREATE_LA);
			File DefaultDir = new File(Environment.getExternalStorageDirectory()+"/FingerTips");
			DefaultDir.mkdir();
//			db.execSQL("PRAGMA foreign_keys = ON;");
			
		}
		catch (SQLiteException e) {
			// TODO: handle exception
			Log.e("createerr",e.toString());
		}

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPICS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LA);
		onCreate(db);
	}

}

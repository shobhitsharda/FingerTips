package com.example.FingerTips.DataSource;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LADataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] Col_topics_stat = {MySQLiteHelper.COLUMN_TOPIC_STAT };
	
	private String[] col_topic_id = {MySQLiteHelper.COLUMN_TOPIC_ID};
	

	public LADataSource(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void createLA(int topic_id)
	{
		ContentValues la_values = new ContentValues();
		la_values.put(MySQLiteHelper.COLUMN_TOPIC_ID, topic_id);
		la_values.put(MySQLiteHelper.COLUMN_TOPIC_STAT, 0);
		database.insert(MySQLiteHelper.TABLE_LA, null, la_values);
	}
	
	public void deleteLA(int topic_id) {

		database.delete(MySQLiteHelper.TABLE_LA, MySQLiteHelper.COLUMN_TOPIC_ID + " = " + topic_id, null);
	}
	
	public void updateLA(int topic_id)
	{
		int topic_count = getTopic_stat(topic_id) + 1;
		ContentValues la_values = new ContentValues();
		la_values.put(MySQLiteHelper.COLUMN_TOPIC_STAT, topic_count);

		database.update(MySQLiteHelper.TABLE_LA, la_values, MySQLiteHelper.COLUMN_TOPIC_ID + " = " + topic_id, null);
	}

	public int getChapter_Stat(int chapter_id) {

		Cursor cursor = database.query(MySQLiteHelper.TABLE_TOPICS,
				col_topic_id, MySQLiteHelper.COLUMN_CHAPTER_ID + " = " + chapter_id, null, null, null, null);
								
		int chapter_count = 0;
		if(cursor!=null && cursor.getCount()>0)
		{
		cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				chapter_count = chapter_count + getTopic_stat(cursor.getInt(0));
				cursor.moveToNext();
			}
		}
		cursor.close();
		return chapter_count;
	}
	
	public int getSubject_Stat(int subject_id) {
 		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_TOPICS,
				col_topic_id, MySQLiteHelper.COLUMN_SUBJECT_ID + " = " + subject_id, null, null, null, null);
				
		cursor.moveToFirst();
		int subject_count = 0;
		
		while (!cursor.isAfterLast()) {
			subject_count = subject_count + getTopic_stat(cursor.getInt(0));
			cursor.moveToNext();
		}
		cursor.close();
		return subject_count;
	}
	
	public int getTopic_stat(int topic_id)
	{
		Cursor cursor = database.query(MySQLiteHelper.TABLE_LA,
				Col_topics_stat, MySQLiteHelper.COLUMN_TOPIC_ID + " = "+ topic_id, null, null, null, null);
		
		cursor.moveToFirst();
		int topic_count = 0;
		while (!cursor.isAfterLast()) {
			topic_count = cursor.getInt(0);
			cursor.moveToNext();
		}
		cursor.close();
		return topic_count;
	}
}

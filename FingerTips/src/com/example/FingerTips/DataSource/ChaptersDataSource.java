package com.example.FingerTips.DataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import com.example.FingerTips.HelperClass.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ChaptersDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_CHAPTER_ID,
			MySQLiteHelper.COLUMN_SUBJECT_ID,
			MySQLiteHelper.COLUMN_CHAPTER_NAME };
	
	private String[] column_topic_data = {MySQLiteHelper.COLUMN_TOPIC_DATA};
	
	Boolean check_new = false;
	
	public ChaptersDataSource(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Chapter createChapter(String chapter_name, int subject_id)
	{
		long insertId=0;

		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_CHAPTER_NAME, chapter_name);
		values.put(MySQLiteHelper.COLUMN_SUBJECT_ID, subject_id);
		insertId = database.insert(MySQLiteHelper.TABLE_CHAPTERS, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_CHAPTERS,
				allColumns, MySQLiteHelper.COLUMN_CHAPTER_ID + " = " + insertId,null,
				null, null, null);
		cursor.moveToFirst();
		check_new = true;
		Chapter newChapter = cursorToChapter(cursor,check_new);
		cursor.close();
		return newChapter;
	}
	
	public void deleteChapter(Chapter chapter) {
		long id = chapter.getChapter_id();
		System.out.println("Chapter deleted with id: " + id);
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_TOPICS,
				column_topic_data, MySQLiteHelper.COLUMN_CHAPTER_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			String topic_data = cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_TOPIC_DATA));
			File img_to_delete = new File(topic_data);
			img_to_delete.delete();
			cursor.moveToNext();
		}
		
		database.delete(MySQLiteHelper.TABLE_TOPICS, MySQLiteHelper.COLUMN_CHAPTER_ID + " = " + id, null);
		
		database.delete(MySQLiteHelper.TABLE_CHAPTERS, MySQLiteHelper.COLUMN_CHAPTER_ID	+ " = " + id, null);
	}

	public List<Chapter> getAllChapter(int subject_id) {
		check_new = false;
		List<Chapter> chapters = new ArrayList<Chapter>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_CHAPTERS,
				allColumns, MySQLiteHelper.COLUMN_SUBJECT_ID + " = " + subject_id, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Chapter chapter = cursorToChapter(cursor,check_new);
			chapters.add(chapter);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return chapters;
	}
	
	private Chapter cursorToChapter(Cursor cursor, Boolean bool) {
		Chapter chapter = new Chapter();
		chapter.setChapter_id(cursor.getInt(0));
		chapter.setChapter_name(cursor.getString(2));
		return chapter;
	}
}

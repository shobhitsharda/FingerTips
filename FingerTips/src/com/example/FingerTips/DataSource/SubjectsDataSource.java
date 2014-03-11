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

public class SubjectsDataSource {
	
	// Database fields
		private SQLiteDatabase database;
		private MySQLiteHelper dbHelper;
		private String[] allColumns = { MySQLiteHelper.COLUMN_SUBJECT_ID,
				MySQLiteHelper.COLUMN_SUBJECT_NAME };
		
		private String[] column_topic_data = {MySQLiteHelper.COLUMN_TOPIC_DATA};
		Boolean check_new = false;
		
		public SubjectsDataSource(Context context)
		{
			dbHelper = new MySQLiteHelper(context);
		}
		
		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		public Subject createsubject(String subject_name)
		{
			check_new = true;
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelper.COLUMN_SUBJECT_NAME, subject_name);
			long insertId = database.insert(MySQLiteHelper.TABLE_SUBJECTS, null, values);
			Cursor cursor = database.query(MySQLiteHelper.TABLE_SUBJECTS,
					allColumns, MySQLiteHelper.COLUMN_SUBJECT_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			Subject newSubject = cursorToSubject(cursor,check_new);
			cursor.close();
			return newSubject;
		}
		
		public void deleteSubject(Subject subject) {
			long id = subject.getSubject_id();
			System.out.println("Comment deleted with id: " + id);

			Cursor cursor = database.query(MySQLiteHelper.TABLE_TOPICS,
					column_topic_data, MySQLiteHelper.COLUMN_SUBJECT_ID + " = " + id, null, null, null, null);
			cursor.moveToFirst();
			while(!cursor.isAfterLast())
			{
				String topic_data = cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_TOPIC_DATA));
				File img_to_delete = new File(topic_data);
				img_to_delete.delete();
				cursor.moveToNext();
			}
			
			database.delete(MySQLiteHelper.TABLE_TOPICS, MySQLiteHelper.COLUMN_SUBJECT_ID + " = " + id, null);
			
			database.delete(MySQLiteHelper.TABLE_CHAPTERS,MySQLiteHelper.COLUMN_SUBJECT_ID + " = " + id, null);
			
			database.delete(MySQLiteHelper.TABLE_SUBJECTS, MySQLiteHelper.COLUMN_SUBJECT_ID	+ " = " + id, null);
		}

		public List<Subject> getAllSubject() {
			check_new = false;
			List<Subject> subjects = new ArrayList<Subject>();

			Cursor cursor = database.query(MySQLiteHelper.TABLE_SUBJECTS,
					allColumns, null, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Subject subject = cursorToSubject(cursor,check_new);
				subjects.add(subject);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return subjects;
		}
		
		private Subject cursorToSubject(Cursor cursor,Boolean bool) {
			Subject subject = new Subject();
			subject.setSubject_id(cursor.getInt(0));
			subject.setSubject_name(cursor.getString(1));
			return subject;
		}
}

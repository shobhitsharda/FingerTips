package com.example.FingerTips.DataSource;

import java.util.ArrayList;
import java.util.List;

import com.example.FingerTips.HelperClass.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class TopicsDataSource {
	
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_TOPIC_ID,
			MySQLiteHelper.COLUMN_SUBJECT_ID,
			MySQLiteHelper.COLUMN_CHAPTER_ID,
			MySQLiteHelper.COLUMN_TOPIC_NAME,
			MySQLiteHelper.COLUMN_TOPIC_DATA };
	Boolean check_new = false;
	
	public TopicsDataSource(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Topic createTopic(String topic_name, String topic_data, int subject_id, int chapter_id)
	{
		check_new = true;
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_SUBJECT_ID, subject_id);
		values.put(MySQLiteHelper.COLUMN_CHAPTER_ID, chapter_id);
		values.put(MySQLiteHelper.COLUMN_TOPIC_NAME, topic_name);
		values.put(MySQLiteHelper.COLUMN_TOPIC_DATA, topic_data);

		long insertId = database.insert(MySQLiteHelper.TABLE_TOPICS, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_TOPICS,
				allColumns, MySQLiteHelper.COLUMN_TOPIC_ID + " = " + insertId,null,
				null, null, null);
		cursor.moveToFirst();
		Topic newTopic = cursorToTopic(cursor,check_new);
		cursor.close();
		return newTopic;
	}
	
	public void deleteTopic(Topic topic) {
		long id = topic.getTopic_id();
		database.delete(MySQLiteHelper.TABLE_TOPICS, MySQLiteHelper.COLUMN_TOPIC_ID + " = " + id, null);
	}

	public List<Topic> getAllTopic(int subject_id, int chapter_id) {
		check_new = false;
		List<Topic> topics = new ArrayList<Topic>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_TOPICS,
				allColumns, MySQLiteHelper.COLUMN_SUBJECT_ID + " = " + subject_id
				+ " and " + MySQLiteHelper.COLUMN_CHAPTER_ID + " = " + chapter_id, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Topic topic = cursorToTopic(cursor, check_new);
			topics.add(topic);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return topics;
	}
	
	private Topic cursorToTopic(Cursor cursor, Boolean bool) {
		Topic topic = new Topic();
		topic.setTopic_id(cursor.getInt(0));
		topic.setTopic_name(cursor.getString(3));
		topic.setTopic_data(cursor.getString(4));
		return topic;
	}	
}

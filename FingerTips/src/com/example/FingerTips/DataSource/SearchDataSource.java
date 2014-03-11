package com.example.FingerTips.DataSource;

import java.util.ArrayList;
import java.util.List;

import com.example.FingerTips.HelperClass.*;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SearchDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	String[] allSubjects = {MySQLiteHelper.COLUMN_SUBJECT_ID, MySQLiteHelper.COLUMN_SUBJECT_NAME};
	String[] allChapters = {MySQLiteHelper.COLUMN_CHAPTER_ID, MySQLiteHelper.COLUMN_CHAPTER_NAME};
	String[] allTopics = {MySQLiteHelper.COLUMN_TOPIC_ID, MySQLiteHelper.COLUMN_TOPIC_NAME, MySQLiteHelper.COLUMN_TOPIC_DATA};
	
	String[] subject_id = {MySQLiteHelper.COLUMN_SUBJECT_ID};
	String[] chapter_id = {MySQLiteHelper.COLUMN_CHAPTER_ID};
	String[] subject_all = {MySQLiteHelper.COLUMN_SUBJECT_ID,MySQLiteHelper.COLUMN_SUBJECT_NAME};
	String[] chapter_all = {MySQLiteHelper.COLUMN_CHAPTER_ID,MySQLiteHelper.COLUMN_CHAPTER_NAME};

	public SearchDataSource(Context context){
		// TODO Auto-generated constructor stub
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Search getAllSearch(String search_text) {
		
		Search search = new Search();
		
		List<Subject> subjects = new ArrayList<Subject>();		
		
		Cursor cursor_subject = database.query(MySQLiteHelper.TABLE_SUBJECTS,
				allSubjects, MySQLiteHelper.COLUMN_SUBJECT_NAME + " = '" + search_text+"'", null, null, null, null);

		cursor_subject.moveToFirst();
		while (!cursor_subject.isAfterLast()) {
			Subject subject = cursorToSubject(cursor_subject);
			subjects.add(subject);
			cursor_subject.moveToNext();
		}
		// Make sure to close the cursor
		cursor_subject.close();
		search.setSearch_subject_list(subjects);
		
		List<Chapter> chapters = new ArrayList<Chapter>();		
		
		Cursor cursor_chapter = database.query(MySQLiteHelper.TABLE_CHAPTERS,
				allChapters, MySQLiteHelper.COLUMN_CHAPTER_NAME + " = '" + search_text+"'", null, null, null, null);

		cursor_chapter.moveToFirst();
		while (!cursor_chapter.isAfterLast()) {
			Chapter chapter = cursorToChapter(cursor_chapter);
			chapters.add(chapter);
			cursor_chapter.moveToNext();
		}
		// Make sure to close the cursor
		cursor_chapter.close();
		search.setSearch_chapter_list(chapters);


		List<Topic> topics = new ArrayList<Topic>();		
		
		Cursor cursor_topic = database.query(MySQLiteHelper.TABLE_TOPICS,
				allTopics, MySQLiteHelper.COLUMN_TOPIC_NAME + " = '" + search_text+"'", null, null, null, null);

		cursor_topic.moveToFirst();
		while (!cursor_topic.isAfterLast()) {
			Topic topic = cursorToTopic(cursor_topic);
			topics.add(topic);
			cursor_topic.moveToNext();
		}
		// Make sure to close the cursor
		cursor_topic.close();
		search.setSearch_topic_list(topics);

		
		
		return search;
	}

	private Subject cursorToSubject(Cursor cursor) {
		Subject subject = new Subject();
		subject.setSubject_id(cursor.getInt(0));
		subject.setSubject_name(cursor.getString(1));
		return subject;
	}
	
	private Chapter cursorToChapter(Cursor cursor) {
		Chapter chapter = new Chapter();
		chapter.setChapter_id(cursor.getInt(0));
		chapter.setChapter_name(cursor.getString(1));
		return chapter;
	}
	
	private Topic cursorToTopic(Cursor cursor) {
		Topic topic = new Topic();
		topic.setTopic_id(cursor.getInt(0));
		topic.setTopic_name(cursor.getString(1));
		topic.setTopic_data(cursor.getString(2));
		return topic;
	}
	
	public Search get_subject(int chapter_id)
	{
		Search search = new Search();
		
		Cursor cursor_subject_id = database.query(MySQLiteHelper.TABLE_CHAPTERS,
				subject_id, MySQLiteHelper.COLUMN_CHAPTER_ID + " = " + chapter_id, null, null, null, null);

		cursor_subject_id.moveToFirst();
		
		Cursor cursor_subject = database.query(MySQLiteHelper.TABLE_SUBJECTS,
				subject_all, MySQLiteHelper.COLUMN_SUBJECT_ID + " = " + cursor_subject_id.getInt(0), null, null, null, null);
		
		cursor_subject.moveToFirst();
		search = cursorToSearch(cursor_subject);

		cursor_subject_id.close();
		cursor_subject.close();
		
		return search;
	}
	
	private Search cursorToSearch(Cursor cursor) {
		Subject search_subject = new Subject();
		search_subject.setSubject_id(cursor.getInt(0));
		search_subject.setSubject_name(cursor.getString(1));
		
		Search search = new Search();
		search.setSearch_subject(search_subject);
		return search;
	}
	
	public Search get_subject_chapter(int topic_id)
	{
		Search search = new Search();
		
		Cursor cursor_chapter_id = database.query(MySQLiteHelper.TABLE_TOPICS,
				chapter_id, MySQLiteHelper.COLUMN_TOPIC_ID + " = " + topic_id, null, null, null, null);
		
		cursor_chapter_id.moveToFirst();
		
		Cursor cursor_chater = database.query(MySQLiteHelper.TABLE_CHAPTERS,
				allChapters, MySQLiteHelper.COLUMN_CHAPTER_ID + " = " + cursor_chapter_id.getInt(0), null, null, null, null);
		
		cursor_chater.moveToFirst();
		
		Chapter chapter = cursorToChapter(cursor_chater);
		
		
		Cursor cursor_subject_id = database.query(MySQLiteHelper.TABLE_CHAPTERS,
				subject_id, MySQLiteHelper.COLUMN_CHAPTER_ID + " = " + chapter.getChapter_id(), null, null, null, null);

		cursor_subject_id.moveToFirst();
		
		Cursor cursor_subject = database.query(MySQLiteHelper.TABLE_SUBJECTS,
				allSubjects, MySQLiteHelper.COLUMN_SUBJECT_ID + " = " + cursor_subject_id.getInt(0), null, null, null, null);
		
		cursor_subject.moveToFirst();
		Subject subject = cursorToSubject(cursor_subject);
		
		search = cursorToSubject_Chapter(subject,chapter);

		cursor_chapter_id.close();
		cursor_chater.close();
		cursor_subject.close();
		cursor_subject.close();
		return search;
	}
	
	private Search cursorToSubject_Chapter(Subject subject, Chapter chapter) {

		Search search_result = new Search();
		
		search_result.setSearch_subject(subject);
		search_result.setSearch_chapter(chapter);
		return search_result;
	}


}

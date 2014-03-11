package com.example.FingerTips.HelperClass;

public class Chapter {
	
	private int chapter_id;
	private String chapter_name;
	private int la_chapter_stat;
	
	public int getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(int chapter_id) {
		this.chapter_id = chapter_id;
	}
	public String getChapter_name() {
		return chapter_name;
	}
	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}
	
	// Will be used by the ArrayAdapter in the ListView
		@Override
		public String toString() {
			return chapter_name;
		}
		public int getLa_chapter_stat() {
			return la_chapter_stat;
		}
		public void setLa_chapter_stat(int la_chapter_stat) {
			this.la_chapter_stat = la_chapter_stat;
		}	

}

package com.example.FingerTips.HelperClass;

public class Subject {
	
	private int subject_id;
	private String subject_name;
	private int la_subject_stat;

	
	public int getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	
	public String toString() {
		return subject_name;
	}
	public int getLa_subject_stat() {
		return la_subject_stat;
	}
	public void setLa_subject_stat(int la_subject_stat) {
		this.la_subject_stat = la_subject_stat;
	}

}

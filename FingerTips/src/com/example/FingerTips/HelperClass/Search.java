package com.example.FingerTips.HelperClass;

import java.util.ArrayList;
import java.util.List;

public class Search {

	private Subject search_subject;
	private Chapter search_chapter;
	private Topic search_topic;
	private List<Subject> search_subject_list = new ArrayList<Subject>();
	private List<Chapter> search_chapter_list = new ArrayList<Chapter>();
	private List<Topic> search_topic_list = new ArrayList<Topic>();
	
 	
	public List<Subject> getSearch_subject_list() {
		return search_subject_list;
	}
	public void setSearch_subject_list(List<Subject> search_subject_list) {
		this.search_subject_list = search_subject_list;
	}
	public List<Chapter> getSearch_chapter_list() {
		return search_chapter_list;
	}
	public void setSearch_chapter_list(List<Chapter> search_chapter_list) {
		this.search_chapter_list = search_chapter_list;
	}
	public List<Topic> getSearch_topic_list() {
		return search_topic_list;
	}
	public void setSearch_topic_list(List<Topic> search_topic_list) {
		this.search_topic_list = search_topic_list;
	}
	public Subject getSearch_subject() {
		return search_subject;
	}
	public void setSearch_subject(Subject search_subject) {
		this.search_subject = search_subject;
	}
	public Chapter getSearch_chapter() {
		return search_chapter;
	}
	public void setSearch_chapter(Chapter search_chapter) {
		this.search_chapter = search_chapter;
	}
	public Topic getSearch_topic() {
		return search_topic;
	}
	public void setSearch_topic(Topic search_topic) {
		this.search_topic = search_topic;
	}
}

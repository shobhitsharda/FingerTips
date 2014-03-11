package com.example.FingerTips;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.FingerTips.DataSource.LADataSource;
import com.example.FingerTips.DataSource.SubjectsDataSource;
import com.example.FingerTips.HelperClass.Subject;

public class LearningAnalyticsSubjectActivity extends Activity{
	
	private SubjectsDataSource subject_datasource;
	List<Subject> subjects;
	ListView listview_subjects;
	ListView listview_stats_subjects;
	int selected_position;
	LADataSource la_datasource;
	List<Integer> subject_stats_list;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.la_subjects_layout);

		subject_datasource = new SubjectsDataSource(this);
		subject_datasource.open();

		subjects = subject_datasource.getAllSubject();
		
		la_datasource = new LADataSource(this);
		la_datasource.open();
		
		subject_stats_list = new ArrayList<Integer>();
		for(int i=0;i<subjects.size();i++)
		{
			subject_stats_list.add(la_datasource.getSubject_Stat(subjects.get(i).getSubject_id()));
		}
		
		listview_stats_subjects = (ListView) findViewById(R.id.listView_stats_subjects);
		ArrayAdapter<Integer> stats_adapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_list_item_1, subject_stats_list);
		
		listview_stats_subjects.setAdapter(stats_adapter);
		
		la_datasource.close();
		
		listview_subjects = (ListView) findViewById(R.id.listView_stats_all_subjects);
		ArrayAdapter<Subject> adapter = new ArrayAdapter<Subject>(this,
				android.R.layout.simple_list_item_1, subjects);
		listview_subjects.setAdapter(adapter);

		listview_subjects.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				
				Intent chapter_screen = new Intent(getApplicationContext(),LearningAnalyticsChapterActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("subject_id_db", subject_datasource.getAllSubject().get(position).getSubject_id());
				bundle.putString("subject_name_db", subject_datasource.getAllSubject().get(position).getSubject_name());
				chapter_screen.putExtras(bundle);
//				subject_datasource.close();
				startActivity(chapter_screen);
				
			}
		});
				
	    Button btn_home = (Button) findViewById(R.id.btn_home);
	    btn_home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent home_screen = new Intent(getApplicationContext(),FingerTipsActivity.class);
				startActivity(home_screen);
			}
		});
	}

		@Override
		protected void onResume() {
			subject_datasource.open();
			super.onResume();
		}

		@Override
		protected void onPause() {
			subject_datasource.close();
			super.onPause();
		}

}

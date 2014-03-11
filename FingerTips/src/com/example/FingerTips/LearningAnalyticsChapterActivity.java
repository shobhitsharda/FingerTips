package com.example.FingerTips;

import java.util.ArrayList;
import java.util.List;

import com.example.FingerTips.DataSource.ChaptersDataSource;
import com.example.FingerTips.DataSource.LADataSource;
import com.example.FingerTips.HelperClass.Chapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class LearningAnalyticsChapterActivity extends Activity{
	
	private ChaptersDataSource chapter_datasource;
	List<Chapter> chapters;
	ListView listview_chapters;
	int subject_id_db;
	String subject_name_db;
	Bundle bundle;
	int selected_position;
	LADataSource la_datasource;
	List<Integer> chapter_stats_list;
	ListView listview_stats_chapters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
       	super.onCreate(savedInstanceState);
        setContentView(R.layout.la_chapters_layout);

        Bundle bundle_rcvd_subject = getIntent().getExtras();
    	subject_id_db = bundle_rcvd_subject.getInt("subject_id_db");
    	subject_name_db = bundle_rcvd_subject.getString("subject_name_db");
    	
    	TextView txt_chapter_display = (TextView) findViewById(R.id.txt_chapter_display_la);
    	txt_chapter_display.setText(subject_name_db + " : Chapters");
        chapter_datasource = new ChaptersDataSource(this);
		chapter_datasource.open();
	    
		chapters = chapter_datasource.getAllChapter(subject_id_db);
		
		la_datasource = new LADataSource(this);
		la_datasource.open();
		
		chapter_stats_list = new ArrayList<Integer>();
		for(int i=0;i<chapters.size();i++)
		{
			chapter_stats_list.add(la_datasource.getChapter_Stat(chapters.get(i).getChapter_id()));
		}
		

		listview_stats_chapters = (ListView) findViewById(R.id.listView_stats_chapters);
		ArrayAdapter<Integer> stats_adapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_list_item_1, chapter_stats_list);
		
		listview_stats_chapters.setAdapter(stats_adapter);
		
		la_datasource.close();

		
        listview_chapters = (ListView) findViewById(R.id.listView_stats_all_chapters);
        listview_chapters.setAdapter((ListAdapter) new ArrayAdapter<Chapter>(
                this, android.R.layout.simple_list_item_1, 
                chapters));
        
        listview_chapters.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				
				bundle = new Bundle();
				bundle.putInt("subject_id_db", subject_id_db);
				bundle.putString("subject_name_db", subject_name_db);
				bundle.putInt("chapter_id_db",chapter_datasource.getAllChapter(subject_id_db).get(position).getChapter_id());
				bundle.putString("chapter_name_db", chapter_datasource.getAllChapter(subject_id_db).get(position).getChapter_name());
				Intent topic_screen = new Intent(getApplicationContext(),LearningAnalyticsTopicActivity.class);
				topic_screen.putExtras(bundle);
//				chapter_datasource.close();
				startActivity(topic_screen);
						
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
		chapter_datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		chapter_datasource.close();
		super.onPause();
	}


}

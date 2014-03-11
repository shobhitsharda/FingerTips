package com.example.FingerTips;

import java.util.ArrayList;
import java.util.List;

import com.example.FingerTips.DataSource.LADataSource;
import com.example.FingerTips.DataSource.TopicsDataSource;
import com.example.FingerTips.HelperClass.Topic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LearningAnalyticsTopicActivity extends Activity{

	int subject_id_db;
	String subject_name_db;
	int chapter_id_db;
	String chapter_name_db;
	int selected_position;
	
	TopicsDataSource topic_datasource;
	List<Topic> topics;
	ListView listview_topics;
	
	LADataSource la_datasource;
	List<Integer> topic_stats_list;
	ListView listview_stats_topics;

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
       	super.onCreate(savedInstanceState);
        setContentView(R.layout.la_topics_layout);
        
        Bundle bundle_rcvd_all_data = getIntent().getExtras();
        
        subject_id_db = bundle_rcvd_all_data.getInt("subject_id_db");
    	subject_name_db = bundle_rcvd_all_data.getString("subject_name_db");
    	chapter_id_db = bundle_rcvd_all_data.getInt("chapter_id_db");
        chapter_name_db = bundle_rcvd_all_data.getString("chapter_name_db");

        TextView txt_topic_display = (TextView) findViewById(R.id.txt_topic_display_la);
        txt_topic_display.setText(subject_name_db + " : " + chapter_name_db +" : " + "Topics");
        
		topic_datasource = new TopicsDataSource(this);
		topic_datasource.open();
		
		topics = topic_datasource.getAllTopic(subject_id_db, chapter_id_db);
		
		la_datasource = new LADataSource(this);
		la_datasource.open();
		
		topic_stats_list = new ArrayList<Integer>();
		for(int i=0;i<topics.size();i++)
		{
			topic_stats_list.add(la_datasource.getTopic_stat(topics.get(i).getTopic_id()));
		}

		listview_stats_topics = (ListView) findViewById(R.id.listView_stats_topics);
		ArrayAdapter<Integer> stats_adapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_list_item_1, topic_stats_list);

		listview_stats_topics.setAdapter(stats_adapter);
		
		la_datasource.close();
		
		List<String> topics_name_list = new ArrayList<String>();
		
		for(int i=0;i<topics.size();i++)
		{
			topics_name_list.add(topics.get(i).getTopic_name());
		}
		
		listview_topics = (ListView) findViewById(R.id.listView_stats_all_topics);
        listview_topics.setAdapter((ListAdapter) new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, 
                topics_name_list));

	    Button btn_home = (Button) findViewById(R.id.btn_home);
	    btn_home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent home_screen = new Intent(getApplicationContext(),FingerTipsActivity.class);
				startActivity(home_screen);
			}
		});
    }
}

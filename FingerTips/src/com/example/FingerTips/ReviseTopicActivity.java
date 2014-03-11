package com.example.FingerTips;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.FingerTips.DataSource.*;
import com.example.FingerTips.HelperClass.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ReviseTopicActivity extends Activity{

	int subject_id_db;
	String subject_name_db;
	int chapter_id_db;
	String chapter_name_db;
	int selected_position;
	
	TopicsDataSource topic_datasource;
	List<Topic> topics;
	ListView listview_topics;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
       	super.onCreate(savedInstanceState);
        setContentView(R.layout.revise_topic_layout);
        
        Bundle bundle_rcvd_all_data = getIntent().getExtras();
        
        subject_id_db = bundle_rcvd_all_data.getInt("subject_id_db");
    	subject_name_db = bundle_rcvd_all_data.getString("subject_name_db");
    	chapter_id_db = bundle_rcvd_all_data.getInt("chapter_id_db");
        chapter_name_db = bundle_rcvd_all_data.getString("chapter_name_db");
        
        TextView txt_topic_display = (TextView) findViewById(R.id.txt_topic_display_revise);
        txt_topic_display.setText(subject_name_db + " : " + chapter_name_db + " : Topics");

		topic_datasource = new TopicsDataSource(this);
		topic_datasource.open();
		
		topics = topic_datasource.getAllTopic(subject_id_db, chapter_id_db);
		
		
		
		List<String> topics_name_list = new ArrayList<String>();
		
		for(int i=0;i<topics.size();i++)
		{
			topics_name_list.add(topics.get(i).getTopic_name());
		}
		
		listview_topics = (ListView) findViewById(R.id.listView_revise_topics);
        listview_topics.setAdapter((ListAdapter) new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, 
                topics_name_list));

        listview_topics.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				
				
				Intent data_screen = new Intent(getApplicationContext(),ReviseDataActivity.class);
		        Bundle bundle = new Bundle();
				bundle.putInt("subject_id_db", subject_id_db);
				bundle.putString("subject_name_db", subject_name_db);
				bundle.putInt("chapter_id_db", chapter_id_db);
				bundle.putString("chapter_name_db", chapter_name_db);
				
		        bundle.putInt("topic_id", topics.get(position).getTopic_id());
		        bundle.putString("topic_name", topics.get(position).getTopic_name());
		        bundle.putString("topic_data_uri", topics.get(position).getTopic_data());
		        data_screen.putExtras(bundle);
//		        topic_datasource.close();
		        startActivity(data_screen);
			}			
		});

        listview_topics.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				
                AlertDialog.Builder alert_DeleteTopic = new AlertDialog.Builder(ReviseTopicActivity.this);
                alert_DeleteTopic.setTitle("Delete");
                alert_DeleteTopic.setMessage("Are you sure you want to Delete the Topic " + topics.get(position).getTopic_name()+" ??");
                
               selected_position = position;

                alert_DeleteTopic.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						try
						{
							File img_to_delete = new File(topics.get(selected_position).getTopic_data());
							@SuppressWarnings("unchecked")
							ArrayAdapter<String> adapter = (ArrayAdapter<String>) listview_topics.getAdapter();
							topic_datasource.deleteTopic(topics.get(selected_position));
							adapter.remove(adapter.getItem(selected_position));
							adapter.notifyDataSetChanged();							
							img_to_delete.delete();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}						
					}
				});
                
                alert_DeleteTopic.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        
                        return;
                    }
                });

				alert_DeleteTopic.show();

				
				return false;
			}
		});

        
        Button btn_revise_all_topics = (Button) findViewById(R.id.btn_topic_revise_all);
        btn_revise_all_topics.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent revise_all_topic_screen = new Intent(getApplicationContext(),ReviseAllTopicsActivity.class);
				Bundle bundle = new Bundle();
				List<Topic> topics_new_topic_data = topic_datasource.getAllTopic(subject_id_db, chapter_id_db);
				ArrayList<String> topics_data_list = new ArrayList<String>();
				ArrayList<String> topics_name_list = new ArrayList<String>();
				for(int i=0;i<topics_new_topic_data.size();i++)
				{
					topics_data_list.add(topics.get(i).getTopic_data());
					topics_name_list.add(topics.get(i).getTopic_name());
				}
				bundle.putStringArrayList("topics_data_list", topics_data_list);
				bundle.putStringArrayList("topics_name_list", topics_name_list);
				bundle.putString("subject_name_db", subject_name_db);
				bundle.putString("chapter_name_db", chapter_name_db);
				revise_all_topic_screen.putExtras(bundle);
	        	startActivity(revise_all_topic_screen);
				
			}
		});
        
        Button btn_revise_all_random_topics = (Button) findViewById(R.id.btn_topic_revise_random);
        btn_revise_all_random_topics.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Bundle bundle = new Bundle();
				List<Topic> topics_new_topic_data = topic_datasource.getAllTopic(subject_id_db, chapter_id_db);
				ArrayList<String> topics_data_list = new ArrayList<String>();
				ArrayList<String> topics_name_list = new ArrayList<String>();
				for(int i=0;i<topics_new_topic_data.size();i++)
				{
					topics_data_list.add(topics.get(i).getTopic_data());
					topics_name_list.add(topics.get(i).getTopic_name());
				}
				bundle.putStringArrayList("topics_data_list", topics_data_list);
				bundle.putStringArrayList("topics_name_list", topics_name_list);
				bundle.putString("subject_name_db", subject_name_db);
				bundle.putString("chapter_name_db", chapter_name_db);
				Intent revise_all_random_topic_screen = new Intent(getApplicationContext(),ReviseAllRandomTopicsActivity.class);
				revise_all_random_topic_screen.putExtras(bundle);
	        	startActivity(revise_all_random_topic_screen);
				
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
}
package com.example.FingerTips;

import java.util.List;

import com.example.FingerTips.DataSource.ChaptersDataSource;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CreateChapterActivity extends Activity{
	
	private ChaptersDataSource chapter_datasource;
	List<Chapter> chapters;
	ListView listview_chapters;
	int subject_id_db;
	String subject_name_db;
	Bundle bundle;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
       	super.onCreate(savedInstanceState);
        setContentView(R.layout.create_chapter_layout);

        Bundle bundle_rcvd_subject = getIntent().getExtras();
    	subject_id_db = bundle_rcvd_subject.getInt("subject_id_db");
    	subject_name_db = bundle_rcvd_subject.getString("subject_name_db");
    	
    	TextView txt_chapter_display = (TextView) findViewById(R.id.txt_chapter_display_create);
    	txt_chapter_display.setText(subject_name_db + " : Chapters");
    	
        chapter_datasource = new ChaptersDataSource(this);
		chapter_datasource.open();
	    
		chapters = chapter_datasource.getAllChapter(subject_id_db);
		
        listview_chapters = (ListView) findViewById(R.id.listView_create_chapters);
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
				
                AlertDialog.Builder alert_AddTopic = new AlertDialog.Builder(CreateChapterActivity.this);
                //alert_AddTopic.setTitle("Add a New Topic Category");
                alert_AddTopic.setMessage("Enter Topic : ");
                
                final EditText input_AddTopic = new EditText(CreateChapterActivity.this);
                alert_AddTopic.setView(input_AddTopic);

                alert_AddTopic.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String value_addTopic = input_AddTopic.getText().toString();
						bundle.putString("topic_name", value_addTopic);
						
						Intent data_screen = new Intent(getApplicationContext(),CreateDataActivity.class);
						data_screen.putExtras(bundle);
//						chapter_datasource.close();
						startActivity(data_screen);
						
					}
				});
                
                alert_AddTopic.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        
                        return;
                    }
                });
                
				alert_AddTopic.show();
			}
		});
        
        
	    Button button_chapter_add = (Button) findViewById(R.id.btn_chapter_add);
	    button_chapter_add.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
                AlertDialog.Builder alert_AddChapter = new AlertDialog.Builder(CreateChapterActivity.this);
                //alert_AddChapter.setTitle("Add a New Chapter Category");
                alert_AddChapter.setMessage("Enter Chapter : ");
                
                final EditText input_AddChapter = new EditText(CreateChapterActivity.this);
                alert_AddChapter.setView(input_AddChapter);

                alert_AddChapter.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String chapter_name = input_AddChapter.getText().toString();
						@SuppressWarnings("unchecked")
						ArrayAdapter<Chapter> adapter = (ArrayAdapter<Chapter>) listview_chapters.getAdapter();
						Chapter chap = chapter_datasource.createChapter(chapter_name, subject_id_db);
						adapter.add(chap);
						adapter.notifyDataSetChanged();

					}
				});
                
                alert_AddChapter.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        
                        return;
                    }
                });

				alert_AddChapter.show();
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
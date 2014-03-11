package com.example.FingerTips;

import java.util.List;

import com.example.FingerTips.DataSource.ChaptersDataSource;
import com.example.FingerTips.HelperClass.Chapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ReviseChapterActivity extends Activity{
	
	private ChaptersDataSource chapter_datasource;
	List<Chapter> chapters;
	ListView listview_chapters;
	int subject_id_db;
	String subject_name_db;
	Bundle bundle;
	int selected_position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
       	super.onCreate(savedInstanceState);
        setContentView(R.layout.revise_chapter_layout);

        Bundle bundle_rcvd_subject = getIntent().getExtras();
    	subject_id_db = bundle_rcvd_subject.getInt("subject_id_db");
    	subject_name_db = bundle_rcvd_subject.getString("subject_name_db");
    	
    	TextView txt_chapter_display = (TextView) findViewById(R.id.txt_chapter_display_revise);
    	txt_chapter_display.setText(subject_name_db + " : Chapters");
        chapter_datasource = new ChaptersDataSource(this);
		chapter_datasource.open();
	    
		chapters = chapter_datasource.getAllChapter(subject_id_db);
		
        listview_chapters = (ListView) findViewById(R.id.listView_revise_chapters);
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
				Intent topic_screen = new Intent(getApplicationContext(),ReviseTopicActivity.class);
				topic_screen.putExtras(bundle);
//				chapter_datasource.close();
				startActivity(topic_screen);
						
			}
		});
        
        listview_chapters.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub

                AlertDialog.Builder alert_DeleteChapter = new AlertDialog.Builder(ReviseChapterActivity.this);
                alert_DeleteChapter.setTitle("Delete");
                alert_DeleteChapter.setMessage("Are you sure you want to Delete the Chapter "+ chapters.get(position).getChapter_name() +" ??");
                
               selected_position = position;

                alert_DeleteChapter.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						try
						{
							@SuppressWarnings("unchecked")
							ArrayAdapter<Chapter> adapter = (ArrayAdapter<Chapter>) listview_chapters.getAdapter();
							chapter_datasource.deleteChapter(chapters.get(selected_position));
							adapter.remove(adapter.getItem(selected_position));
							adapter.notifyDataSetChanged();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				});
                
                alert_DeleteChapter.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        
                        return;
                    }
                });

				alert_DeleteChapter.show();
				
				return false;
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
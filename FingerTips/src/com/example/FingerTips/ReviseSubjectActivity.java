package com.example.FingerTips;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ReviseSubjectActivity extends Activity{
	
	private SubjectsDataSource subject_datasource;
	List<Subject> subjects;
	ListView listview_subjects;
	int selected_position;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.revise_subject_layout);

		subject_datasource = new SubjectsDataSource(this);
		subject_datasource.open();

		subjects = subject_datasource.getAllSubject();

		listview_subjects = (ListView) findViewById(R.id.listView_revise_subjects);
		ArrayAdapter<Subject> adapter = new ArrayAdapter<Subject>(this,
				android.R.layout.simple_list_item_1, subjects);
		listview_subjects.setAdapter(adapter);

		listview_subjects.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				
				Intent chapter_screen = new Intent(getApplicationContext(),ReviseChapterActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("subject_id_db", subject_datasource.getAllSubject().get(position).getSubject_id());
				bundle.putString("subject_name_db", subject_datasource.getAllSubject().get(position).getSubject_name());
				chapter_screen.putExtras(bundle);
				subject_datasource.close();
				startActivity(chapter_screen);
				
			}
		});
		
		listview_subjects.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				
                AlertDialog.Builder alert_DeleteSubject = new AlertDialog.Builder(ReviseSubjectActivity.this);
                alert_DeleteSubject.setTitle("Delete");
                alert_DeleteSubject.setMessage("Are you sure you want to Delete the Subject "+ subjects.get(position).getSubject_name() +" ??");
                
               selected_position = position;

                alert_DeleteSubject.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						try
						{
							@SuppressWarnings("unchecked")
							ArrayAdapter<Subject> adapter = (ArrayAdapter<Subject>) listview_subjects.getAdapter();
							subject_datasource.deleteSubject(subjects.get(selected_position));
							adapter.remove(adapter.getItem(selected_position));
							adapter.notifyDataSetChanged();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}						
					}
				});
                
                alert_DeleteSubject.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        
                        return;
                    }
                });

				alert_DeleteSubject.show();

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
			subject_datasource.open();
			super.onResume();
		}

		@Override
		protected void onPause() {
			subject_datasource.close();
			super.onPause();
		}
}
package com.example.FingerTips;

import com.example.FingerTips.HelperClass.*;
import com.example.FingerTips.DataSource.*;

import java.util.List;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class CreateSubjectActivity extends Activity{
	private SubjectsDataSource subject_datasource;
	List<Subject> subjects;
	ListView listview_subjects;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_subject_layout);

		subject_datasource = new SubjectsDataSource(this);
		subject_datasource.open();

		subjects = subject_datasource.getAllSubject();

		listview_subjects = (ListView) findViewById(R.id.listView_create_subjects);
		ArrayAdapter<Subject> adapter = new ArrayAdapter<Subject>(this,
				android.R.layout.simple_list_item_1, subjects);
		listview_subjects.setAdapter(adapter);

		listview_subjects.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				
				Intent chapter_screen = new Intent(getApplicationContext(),CreateChapterActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("subject_id_db", subject_datasource.getAllSubject().get(position).getSubject_id());
				bundle.putString("subject_name_db", subject_datasource.getAllSubject().get(position).getSubject_name());
				chapter_screen.putExtras(bundle);
//				subject_datasource.close();
				startActivity(chapter_screen);				
			}
		});
		
	    Button button_subject_add = (Button) findViewById(R.id.btn_subject_add);
	    button_subject_add.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
                AlertDialog.Builder alert_AddSubject = new AlertDialog.Builder(CreateSubjectActivity.this);
                //alert_AddSubject.setTitle("Add a New Subject Category");
                alert_AddSubject.setMessage("Enter Subject : ");
                
                final EditText input_AddSubject = new EditText(CreateSubjectActivity.this);
                alert_AddSubject.setView(input_AddSubject);

                alert_AddSubject.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String value_addSubject = input_AddSubject.getText().toString();
						@SuppressWarnings("unchecked")
						ArrayAdapter<Subject> adapter = (ArrayAdapter<Subject>) listview_subjects.getAdapter();
						Subject sub = subject_datasource.createsubject(value_addSubject);
						adapter.add(sub);
						adapter.notifyDataSetChanged();

					}
				});
                
                alert_AddSubject.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        
                        return;
                    }
                });

				alert_AddSubject.show();

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
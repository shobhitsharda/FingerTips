package com.example.FingerTips;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.FingerTips.DataSource.*;
import com.example.FingerTips.HelperClass.Topic;

public class CreateDataActivity extends Activity {
	
	int subject_id_db;
	String subject_name_db;
	int chapter_id_db;
	String chapter_name_db;
	String topic_name;
	String topic_data;
	EditText edit_enter_data;
	AlertDialog.Builder alert_Data_Confirm_Feedback;
	LADataSource la_datasource;
	
	private TopicsDataSource topic_datasource;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
       	super.onCreate(savedInstanceState);
        setContentView(R.layout.create_data_layout);
        
        Bundle bundle_rcvd_all_data = getIntent().getExtras();
        
        subject_id_db = bundle_rcvd_all_data.getInt("subject_id_db");
    	subject_name_db = bundle_rcvd_all_data.getString("subject_name_db");
    	chapter_id_db = bundle_rcvd_all_data.getInt("chapter_id_db");
        chapter_name_db = bundle_rcvd_all_data.getString("chapter_name_db");
        topic_name = bundle_rcvd_all_data.getString("topic_name");
        
        TextView txt_topic_name = (TextView) findViewById(R.id.txt_topic_display_create);
        txt_topic_name.setText(subject_name_db + " : " + chapter_name_db + " : " + topic_name);
        
        edit_enter_data = (EditText) findViewById(R.id.edit_enter_data);
        
        
		topic_datasource = new TopicsDataSource(this);
		topic_datasource.open();
		
		la_datasource = new LADataSource(this);
		
		Button btn_save_data = (Button) findViewById(R.id.btn_save_data);
		btn_save_data.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				View view_edit_enter_data = edit_enter_data.getRootView();
				
				
				if(view_edit_enter_data!= null)
				{
					View visible_btn_save = findViewById(R.id.btn_save_data);
					visible_btn_save.setVisibility(View.INVISIBLE);
					
					View visible_btn_home = findViewById(R.id.btn_home);
					visible_btn_home.setVisibility(View.INVISIBLE);
					
					View visible_btn_camera = findViewById(R.id.btn_camera);
					visible_btn_camera.setVisibility(View.INVISIBLE);
					
					View visible_topic_name = findViewById(R.id.txt_topic_display_create);
					visible_topic_name.setVisibility(View.INVISIBLE);
					
					view_edit_enter_data.setDrawingCacheEnabled(true);
					view_edit_enter_data.buildDrawingCache();
					Bitmap bm = view_edit_enter_data.getDrawingCache();
					
					try
					{
						if(bm!=null)
						{
							File file = new File(Environment.getExternalStorageDirectory()+"/FingerTips/",topic_name+ ".jpg");
							OutputStream fos = new FileOutputStream(file);
							BufferedOutputStream bos = new BufferedOutputStream(fos);
							bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
							topic_data = Environment.getExternalStorageDirectory().toString() + "/FingerTips/"+topic_name+".jpg";
							Topic topic_temp =	topic_datasource.createTopic(topic_name, topic_data, subject_id_db, chapter_id_db);
							
							la_datasource.open();
							la_datasource.createLA(topic_temp.getTopic_id());
							la_datasource.close();
							
							bos.flush();
							bos.close();
						}
					}
					catch(Exception e)
					{
						System.out.println("Error : " + e);
						e.printStackTrace();
					}
					
					visible_topic_name.setVisibility(View.VISIBLE);
					visible_btn_camera.setVisibility(View.VISIBLE);
					visible_btn_home.setVisibility(View.VISIBLE);
					visible_btn_save.setVisibility(View.VISIBLE);
					

				}
				
                alert_Data_Confirm_Feedback = new AlertDialog.Builder(CreateDataActivity.this);
                alert_Data_Confirm_Feedback.setTitle("Confirmation");
                alert_Data_Confirm_Feedback.setMessage("Data Successfully Saved");

                alert_Data_Confirm_Feedback.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
				
						Intent main_screen = new Intent(getApplicationContext(),FingerTipsActivity.class);
//						topic_datasource.close();
						startActivity(main_screen);

					}
				});
                
                alert_Data_Confirm_Feedback.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        
                        return;
                    }
                });

				alert_Data_Confirm_Feedback.show();
			}
		});
		
        Button camera_button = (Button) findViewById(R.id.btn_camera);
        camera_button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent use_camera = new Intent("android.media.action.IMAGE_CAPTURE");
				File photo = new File(Environment.getExternalStorageDirectory() + "/FingerTips/", topic_name+".jpg");
				use_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
				startActivityForResult(use_camera, R.id.btn_camera);
								
				topic_data = Environment.getExternalStorageDirectory().toString() + "/FingerTips/"+topic_name+".jpg";
				
				Topic topic_temp = topic_datasource.createTopic(topic_name, topic_data, subject_id_db, chapter_id_db);
				
				la_datasource.open();
				la_datasource.createLA(topic_temp.getTopic_id());
				la_datasource.close();
				
                alert_Data_Confirm_Feedback = new AlertDialog.Builder(CreateDataActivity.this);
                alert_Data_Confirm_Feedback.setTitle("Confirmation");
                alert_Data_Confirm_Feedback.setMessage("Data Successfully Saved");

                alert_Data_Confirm_Feedback.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
				
						Intent main_screen = new Intent(getApplicationContext(),FingerTipsActivity.class);
//						topic_datasource.close();
						startActivity(main_screen);

					}
				});
                
                alert_Data_Confirm_Feedback.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        
                        return;
                    }
                });
                
				alert_Data_Confirm_Feedback.show();
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
		topic_datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		topic_datasource.close();
		super.onPause();
	}
}
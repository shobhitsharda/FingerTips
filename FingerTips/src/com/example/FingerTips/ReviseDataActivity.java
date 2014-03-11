package com.example.FingerTips;

import com.example.FingerTips.DataSource.LADataSource;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ReviseDataActivity extends Activity{

	String topic_data_uri;
	String topic_name;
	int subject_id_db;
	String subject_name_db;
	int chapter_id_db;
	String chapter_name_db;
	int topic_id;
	LADataSource la_datasource;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.revise_data_layout);
        
        la_datasource = new LADataSource(this);
        
        Bundle bundle_rcvd_uri = getIntent().getExtras();
        subject_id_db = bundle_rcvd_uri.getInt("subject_id_db");
        subject_name_db = bundle_rcvd_uri.getString("subject_name_db");
        chapter_id_db = bundle_rcvd_uri.getInt("chapter_id_db");
        chapter_name_db = bundle_rcvd_uri.getString("chapter_name_db");
        topic_name = bundle_rcvd_uri.getString("topic_name");
        topic_data_uri = bundle_rcvd_uri.getString("topic_data_uri");
        topic_id = bundle_rcvd_uri.getInt("topic_id");
        

		la_datasource.open();
		la_datasource.updateLA(topic_id);
		la_datasource.close();
        
        TextView txt_topic_name = (TextView) findViewById(R.id.txt_topic_display_revise);
        txt_topic_name.setText(subject_name_db + " : " + chapter_name_db + " : " + topic_name);
        
        ImageView img_view_topic_data = (ImageView) findViewById(R.id.imageView_topic_data);
        img_view_topic_data.setImageDrawable(Drawable.createFromPath(topic_data_uri));
        
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
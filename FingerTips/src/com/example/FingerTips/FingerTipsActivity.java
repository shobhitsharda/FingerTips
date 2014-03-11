package com.example.FingerTips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FingerTipsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button create_button = (Button) findViewById(R.id.Button_Create_Id);
        create_button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent create_screen = new Intent(getApplicationContext(),CreateSubjectActivity.class);
				startActivity(create_screen);
			}
		});
        
        Button revise_button = (Button) findViewById(R.id.Button_Revise_Id);
        revise_button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent revise_screen = new Intent(getApplicationContext(),ReviseSubjectActivity.class);
				startActivity(revise_screen);
			}
		});

        Button search_button = (Button) findViewById(R.id.Button_Search_Id);
        search_button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent search_screen = new Intent(getApplicationContext(),SearchActivity.class);
				Intent search_screen = new Intent(getApplicationContext(),SearchActivity.class);
				startActivity(search_screen);
			}
		});        
        /*
         * This button is hidden and is placed just above the "Create" button
         */
        Button learning_analytics = (Button) findViewById(R.id.Button_LearningAnalytics_Id);
        learning_analytics.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent la_screen = new Intent(getApplicationContext(),LearningAnalyticsSubjectActivity.class);
				startActivity(la_screen);
			}
		});
    }
}
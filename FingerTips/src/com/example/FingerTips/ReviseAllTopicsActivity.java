package com.example.FingerTips;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ReviseAllTopicsActivity extends Activity{
	
	String chapter_name_db,subject_name_db;
	ArrayList<String> topics_data_list;
	ArrayList<String> topics_name_list;
	int i=1,temp=1;
	ImageView img_view_topic_data;
	Boolean bool = true;
	Boolean bool_prev = false, bool_next = false;
	TextView txt_topic_revise_all;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.revise_all_data_layout);
		
        Bundle bundle_rcvd_all_data = getIntent().getExtras();
        chapter_name_db = bundle_rcvd_all_data.getString("chapter_name_db");
        subject_name_db = bundle_rcvd_all_data.getString("subject_name_db");
        topics_data_list = bundle_rcvd_all_data.getStringArrayList("topics_data_list");
        topics_name_list = bundle_rcvd_all_data.getStringArrayList("topics_name_list");
                
        txt_topic_revise_all = (TextView) findViewById(R.id.txt_topic_display_revise_all);
        

        final ViewFlipper MyViewFlipper  = (ViewFlipper) findViewById(R.id.viewFlipper1);
        
        img_view_topic_data = (ImageView) findViewById(R.id.img_view_all_topics_data);
        img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(0)));
		txt_topic_revise_all.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(0));
        
        Button btn_next = (Button)findViewById(R.id.btn_next);
        Button btn_prev = (Button)findViewById(R.id.btn_previous);

        Animation animationFlipIn  = AnimationUtils.loadAnimation(this, R.anim.flipin);
        Animation animationFlipOut = AnimationUtils.loadAnimation(this, R.anim.flipout);
        MyViewFlipper.setInAnimation(animationFlipIn);
        MyViewFlipper.setOutAnimation(animationFlipOut);

        btn_next.setOnClickListener(new Button.OnClickListener(){

        	public void onClick(View arg0) {
          		//TODO Auto-generated method stub
          		MyViewFlipper.showNext();
          		bool_next = true;
              		
              		if(bool_prev == true)
              		{
              			bool_prev = false;
              			if((i+1)==topics_data_list.size())
              			{
              				img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(0)));
              				txt_topic_revise_all.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(0));
              				i=1;
              			}
              			else
              			{
              				img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(i+1)));
              				txt_topic_revise_all.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(i+1));
              				i=i+2;
              			}
              		}
              		else if(i<(topics_data_list.size()))
              		{
              			img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(i)));
              			txt_topic_revise_all.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(i));
              			i++;
              			bool = false;
              		}
              		else
              		{
              			img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(0)));
              			txt_topic_revise_all.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(0));
              			i=1;
              		}
         	}
          });
        
          btn_prev.setOnClickListener(new Button.OnClickListener(){

          	public void onClick(View arg0) {
          		// TODO Auto-generated method stub
          		MyViewFlipper.showPrevious();
          		
          		bool_prev = true;
          		if(bool == true)
          		{
          			i = topics_data_list.size()-1;
          			img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(i)));
          			txt_topic_revise_all.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(i));
          			bool = false;
          		}
          		else if(bool_next == true)
          		{
          			bool_next = false;
          			if(i==1)
          			{
          				i = topics_data_list.size()-1;
          				img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(i)));
          				txt_topic_revise_all.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(i));
          				
          			}
          			else
          			{
          				i=i-2;
          				img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(i)));
          				txt_topic_revise_all.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(i));
          			}

          		}
          		else if(i==0)
          		{
          			i = topics_data_list.size()-1;
          			img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(i)));
          			txt_topic_revise_all.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(i));
          		}
          		else
          		{
          			i--;
          			img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(i)));
          			txt_topic_revise_all.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(i));
          		}
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
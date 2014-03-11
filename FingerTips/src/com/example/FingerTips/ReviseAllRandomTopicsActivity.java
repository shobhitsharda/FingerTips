package com.example.FingerTips;

import java.util.ArrayList;
import java.util.Random;

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

public class ReviseAllRandomTopicsActivity extends Activity{

	String chapter_name_db,subject_name_db;
	ArrayList<String> topics_data_list,topics_name_list;
	int i=1,temp=1,max=0,min=0;
	ImageView img_view_topic_data;
	Boolean bool = true;
	Boolean bool_prev = false, bool_next = false;
	Random rand = new Random();
	TextView txt_topic_revise_random;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.revise_all_random_data_layout);
		
        Bundle bundle_rcvd_all_data = getIntent().getExtras();
        chapter_name_db = bundle_rcvd_all_data.getString("chapter_name_db");
        subject_name_db = bundle_rcvd_all_data.getString("subject_name_db");
        topics_data_list = bundle_rcvd_all_data.getStringArrayList("topics_data_list");
        topics_name_list = bundle_rcvd_all_data.getStringArrayList("topics_name_list");
        
        txt_topic_revise_random = (TextView) findViewById(R.id.txt_topic_display_revise_random);
        txt_topic_revise_random.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(0));

        final ViewFlipper MyViewFlipper  = (ViewFlipper) findViewById(R.id.viewFlipper1);
        
        img_view_topic_data = (ImageView) findViewById(R.id.img_view_all_topics_data);
        img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(0)));
		
			
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
          		min = 0;
          		max = topics_data_list.size();
          		i = rand.nextInt(max-min) + min;
          		
      			img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(i)));
      			txt_topic_revise_random.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(i));
      			rand = new Random();
        	}
        });
        
          btn_prev.setOnClickListener(new Button.OnClickListener(){

          	public void onClick(View arg0) {
          		// TODO Auto-generated method stub
          		MyViewFlipper.showPrevious();
          		
          		MyViewFlipper.showNext();
          		min = 0;
          		max = topics_data_list.size();
          		i = rand.nextInt(max-min) + min;
          		
      			img_view_topic_data.setImageDrawable(Drawable.createFromPath(topics_data_list.get(i)));
      			txt_topic_revise_random.setText(subject_name_db + " : " + chapter_name_db + " : " + topics_name_list.get(i));
      			rand = new Random();      			
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

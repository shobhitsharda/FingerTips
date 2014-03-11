package com.example.FingerTips;

import java.util.ArrayList;
import java.util.List;

import com.example.FingerTips.DataSource.*;
import com.example.FingerTips.HelperClass.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SearchActivity extends Activity{
	private SearchDataSource search_datasource;
	EditText edit_search_text;
	ListView listview_search_list;
	List<String> searched_list = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	Search search;
	Bundle bundle;
	LinearLayout linearlayout_main;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		
		linearlayout_main = (LinearLayout) findViewById(R.id.linearlayout_main);
		search_datasource = new SearchDataSource(this);
		search_datasource.open();
		
		edit_search_text = (EditText) findViewById(R.id.editText_search_text);
		searched_list.add("");
		
		
		listview_search_list = (ListView) findViewById(R.id.listView_searchlist);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, searched_list);
		listview_search_list.setAdapter(adapter);
		listview_search_list.setVisibility(View.INVISIBLE);



		Button btn_search = (Button) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				listview_search_list.setVisibility(View.VISIBLE);
				searched_list.clear();
				search = search_datasource.getAllSearch(edit_search_text.getText().toString());
				Boolean bool = search.getSearch_subject_list().size()>0 || 
						search.getSearch_chapter_list().size()>0 ||
						search.getSearch_topic_list().size()>0;
				if(bool)
				{
					if(search.getSearch_subject_list().size()>0)
					{						
						for(int i=0;i<search.getSearch_subject_list().size();i++)
						{
							//searched_list.add(search.getSearch_subject_list().get(i).getSubject_name());
							@SuppressWarnings("unchecked")
							ArrayAdapter<String> adapter_temp = (ArrayAdapter<String>) listview_search_list.getAdapter();
							adapter_temp.add(search.getSearch_subject_list().get(i).getSubject_name());
							//searched_list.add(search.getSearch_subject_list().get(i).getSubject_name());
							adapter_temp.notifyDataSetChanged();
						}
					}
					if(search.getSearch_chapter_list().size()>0)
					{
						for(int i=0;i<search.getSearch_chapter_list().size();i++)
						{
							//searched_list.add(search.getSearch_subject_list().get(i).getSubject_name());
							@SuppressWarnings("unchecked")
							ArrayAdapter<String> adapter_temp = (ArrayAdapter<String>) listview_search_list.getAdapter();
							adapter_temp.add(search.getSearch_chapter_list().get(i).getChapter_name());
							adapter_temp.notifyDataSetChanged();
						}
					}
					if(search.getSearch_topic_list().size()>0)
					{
						for(int i=0;i<search.getSearch_topic_list().size();i++)
						{
							//searched_list.add(search.getSearch_subject_list().get(i).getSubject_name());
							@SuppressWarnings("unchecked")
							ArrayAdapter<String> adapter_temp = (ArrayAdapter<String>) listview_search_list.getAdapter();
							adapter_temp.add(search.getSearch_topic_list().get(i).getTopic_name());
							adapter_temp.notifyDataSetChanged();
						}
					}
				}
				else
				{
					@SuppressWarnings("unchecked")
					ArrayAdapter<String> adapter_temp = (ArrayAdapter<String>) listview_search_list.getAdapter();
					adapter_temp.add("Search Not Found!! Search is Case Sensitive");
					adapter_temp.notifyDataSetChanged();
				}				
			}
		});

		listview_search_list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				if(position<search.getSearch_subject_list().size())
				{
					bundle = new Bundle();
					bundle.putInt("subject_id_db", search.getSearch_subject_list().get(position).getSubject_id());
					bundle.putString("subject_name_db", search.getSearch_subject_list().get(position).getSubject_name());
					Intent subject_screen = new Intent(getApplicationContext(),ReviseChapterActivity.class);
					subject_screen.putExtras(bundle);
//					search_datasource.close();
					listview_search_list.setVisibility(View.INVISIBLE);
					edit_search_text.setText("");
					startActivity(subject_screen);

				}
				else if((position>=search.getSearch_subject_list().size())&&(position<(search.getSearch_subject_list().size() + search.getSearch_chapter_list().size())))
				{
					if(search.getSearch_subject_list().size()>0)
					{
						position = position-search.getSearch_subject_list().size();
					}
					Search search_subject = search_datasource.get_subject(search.getSearch_chapter_list().get(position).getChapter_id());					
					bundle = new Bundle();
					bundle.putInt("subject_id_db", search_subject.getSearch_subject().getSubject_id());
					bundle.putString("subject_name_db", search_subject.getSearch_subject().getSubject_name());
					bundle.putInt("chapter_id_db",search.getSearch_chapter_list().get(position).getChapter_id());
					bundle.putString("chapter_name_db", search.getSearch_chapter_list().get(position).getChapter_name());
					Intent topic_screen = new Intent(getApplicationContext(),ReviseTopicActivity.class);
					topic_screen.putExtras(bundle);
//					search_datasource.close();
					listview_search_list.setVisibility(View.INVISIBLE);
					edit_search_text.setText("");
					startActivity(topic_screen);
				}
				else if((position>=(search.getSearch_subject_list().size() + search.getSearch_chapter_list().size()))&&(position<(search.getSearch_subject_list().size() + search.getSearch_chapter_list().size() + search.getSearch_topic_list().size())))
				{
					if(search.getSearch_subject_list().size()>0)
					{
						position = position-search.getSearch_subject_list().size();
					}
					if(search.getSearch_chapter_list().size()>0)
					{
						position = position-search.getSearch_chapter_list().size();
					}

					Search  search_resultSearch = search_datasource.get_subject_chapter(search.getSearch_topic_list().get(position).getTopic_id());
			        bundle = new Bundle();
			        bundle.putInt("subject_id_db", search_resultSearch.getSearch_subject().getSubject_id());
			        bundle.putString("subject_name_db", search_resultSearch.getSearch_subject().getSubject_name());
			        bundle.putInt("chapter_id_db", search_resultSearch.getSearch_chapter().getChapter_id());
			        bundle.putString("chapter_name_db", search_resultSearch.getSearch_chapter().getChapter_name());
			        bundle.putInt("topic_id", search.getSearch_topic_list().get(position).getTopic_id());
			        bundle.putString("topic_name", search.getSearch_topic_list().get(position).getTopic_name());
			        bundle.putString("topic_data_uri", search.getSearch_topic_list().get(position).getTopic_data());
					Intent data_screen = new Intent(getApplicationContext(),ReviseDataActivity.class);
			        data_screen.putExtras(bundle);
//			        search_datasource.close();
					listview_search_list.setVisibility(View.INVISIBLE);
					edit_search_text.setText("");
			        startActivity(data_screen);					
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
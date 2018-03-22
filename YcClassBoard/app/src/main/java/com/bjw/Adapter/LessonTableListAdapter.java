package com.bjw.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bjw.R;

import java.util.ArrayList;

public class LessonTableListAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<ArrayList<String>> lists;
	
	public LessonTableListAdapter(Context context, ArrayList<ArrayList<String>> lists) {
		super();
		this.context = context;
		this.lists = lists;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ArrayList<String> list = lists.get(index);
		if(view == null){
			view = inflater.inflate(R.layout.item_right_table_list, null);
		}
		view.setBackgroundColor(Color.WHITE);
		TextView textView1 = (TextView) view.findViewById(R.id.text1);
		TextView textView2 = (TextView) view.findViewById(R.id.text2);
		TextView textView3 = (TextView) view.findViewById(R.id.text3);
		TextView textView4 = (TextView) view.findViewById(R.id.text4);
		TextView textView5 = (TextView) view.findViewById(R.id.text5);
		TextView textView6 = (TextView) view.findViewById(R.id.text6);
		if(list.size()==3) {
			textView1.setTextColor(Color.BLACK);
			textView2.setTextColor(Color.BLACK);
			textView3.setTextColor(Color.BLACK);
			textView4.setVisibility(View.GONE);
			textView5.setVisibility(View.GONE);
			textView6.setVisibility(View.GONE);
			textView1.setText(list.get(0));
			textView2.setText(list.get(1));
			textView3.setText(list.get(2));
		}
		else if(list.size()==4)
		{
			textView1.setTextColor(Color.BLACK);
			textView2.setTextColor(Color.BLACK);
			textView3.setTextColor(Color.BLACK);
			textView4.setTextColor(Color.BLACK);
			textView5.setVisibility(View.GONE);
			textView6.setVisibility(View.GONE);
			textView1.setText(list.get(0));
			textView2.setText(list.get(1));
			textView3.setText(list.get(2));
			textView4.setText(list.get(3));
		}
		if(list.size()==6) {
			textView1.setTextColor(Color.BLACK);
			textView2.setTextColor(Color.BLACK);
			textView3.setTextColor(Color.BLACK);
			textView4.setTextColor(Color.BLACK);
			textView5.setTextColor(Color.BLACK);
			textView6.setTextColor(Color.BLACK);
			textView1.setText(list.get(0));
			textView2.setText(list.get(1));
			textView3.setText(list.get(2));
			textView4.setText(list.get(3));
			textView5.setText(list.get(4));
			textView6.setText(list.get(5));
		}
		if(index == 0){
			if(list.size()==3) {
				view.setBackgroundColor(Color.parseColor("gray"));
				textView1.setTextColor(Color.WHITE);
				textView2.setTextColor(Color.WHITE);
				textView3.setTextColor(Color.WHITE);
			}
			else if(list.size()==4)
			{
				view.setBackgroundColor(Color.parseColor("gray"));
				textView1.setTextColor(Color.WHITE);
				textView2.setTextColor(Color.WHITE);
				textView3.setTextColor(Color.WHITE);
				textView4.setTextColor(Color.WHITE);
			}
			else if(list.size()==6) {
				view.setBackgroundColor(Color.parseColor("gray"));
				textView1.setTextColor(Color.WHITE);
				textView2.setTextColor(Color.WHITE);
				textView3.setTextColor(Color.WHITE);
				textView4.setTextColor(Color.WHITE);
				textView5.setTextColor(Color.WHITE);
				textView6.setTextColor(Color.WHITE);
			}
		}else{
			if(index%2 != 0){
				view.setBackgroundColor(Color.argb(250 ,  255 ,  255 ,  255 )); 
			}else{
				view.setBackgroundColor(Color.argb(250 ,  224 ,  243 ,  250 ));    
			}
		}
		return view;
	}

}

package com.example.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bean.Book;
import com.example.biz.Const;
import com.example.biz.HttpSender;
import com.example.bookapp.R;
import com.example.bookapp.R.drawable;

public class MyBookAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private List<Map<String, String>> list;
	public MyBookAdapter(Context context,List<Map<String,String>> list){
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(null == convertView){
		holder = new ViewHolder();
		convertView = inflater.inflate(R.layout.booklist_item, null);
		holder.iv = (ImageView) convertView.findViewById(R.id.boolist_image);
		holder.tv_title = (TextView) convertView.findViewById(R.id.boolist_title);
		holder.tv_content = (TextView) convertView.findViewById(R.id.boolist_content);
		holder.tv_author = (TextView) convertView.findViewById(R.id.boolist_author);
		convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Map<String,String> map = list.get(position);
		holder.tv_title.setText(map.get("title"));
		holder.tv_content.setText(map.get("content"));
		holder.tv_author.setText(map.get("author"));
		holder.iv.setTag(Const.IMAGEPATH_URL+map.get("image"));
//		holder.iv.setImageResource(drawable.d1);
		HttpSender.getInstance().downImage(context, holder.iv,convertView, Const.IMAGEPATH_URL+map.get("image"), map.get("image"));
//		holder.iv.setImageResource(drawable.d1);
		return convertView;
	}
	class ViewHolder{
		private ImageView iv;
	    private TextView tv_title;
        private TextView tv_content;
        private TextView tv_author;
	}

}

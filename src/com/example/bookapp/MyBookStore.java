package com.example.bookapp;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bean.Book;
import com.example.biz.Const;
import com.example.biz.HttpSender;
import com.example.biz.HttpTaskListener;
import com.example.bookapp.R.drawable;
import com.example.bookapp.borrow.BorrowBook;
 

//import android.app.ActionBar;

public class MyBookStore extends Activity implements HttpTaskListener {
	private String username;
	private int id;
	// ActionBar actionBar;
	Bundle savedInstanceState;
	private String[] BookTypes = { "还没借出的书籍", "已借出的书籍"};
	private List<Map<String,String>> list,clist;
	private Map<Integer,List<Map<String,String>>> bgroup;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybookstore);
		this.savedInstanceState = savedInstanceState;
		// actionBar = getActionBar();
		// // �����Ƿ���ʾӦ�ó���ͼ��
		// actionBar.setDisplayShowHomeEnabled(false);
		// // ��Ӧ�ó���ͼ������Ϊ�ɵ���İ�ť
		// actionBar.setHomeButtonEnabled(true);
		// // ��Ӧ�ó���ͼ������Ϊ�ɵ���İ�ť������ͼ������������ͷ
		// actionBar.setDisplayHomeAsUpEnabled(true);
		// actionBar.setIcon(R.drawable.ic_launcher);
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		// actionBar.show();
		// ����һ��BaseExpandableListAdapter����
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		list = new ArrayList<Map<String,String>>();
		clist = new ArrayList<Map<String,String>>();
		bgroup  = new HashMap<Integer,List<Map<String,String>>>();
		sendHttp();

	}
	private void sendHttp(){
		Map<String,String> map_url = new HashMap<String, String>();
		Map<String,String> map_param = new HashMap<String, String>();
		map_url.put("url", Const.QUERYBOOKBYUSERNAME);
		map_param.put("username", username);
		HttpSender.getInstance().sendQuery(map_url, map_param, this);
	}
	public void init(){
		ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
			// int[] logos = new int[]
			// {
			// R.drawable.androidligangpng,
			// R.drawable.androidrumenpng,
			// R.drawable.androidsecure,
			// };

			// ��ȡָ����λ�á�ָ�����б�������б������
			@Override
			public Object getChild(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				return bgroup.get(groupPosition).size();
			}

			private TextView getTextView() {
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 64);
				TextView textView = new TextView(MyBookStore.this);
				textView.setLayoutParams(lp);
				textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
				textView.setPadding(36, 0, 0, 0);
				textView.setTextSize(20);
				 
				return textView;
			}

			// �÷�������ÿ����ѡ������
			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				 
//				TextView textView = getTextView();
//				textView.setText(getChild(groupPosition, childPosition)
//						.toString());
//				return textView;
				ViewHolder holder = null;
				if(null == convertView){
				holder = new ViewHolder();
				LayoutInflater inflater = LayoutInflater.from(getApplicationContext()); 
				convertView = inflater.inflate(R.layout.booklist_item, null);
				holder.iv = (ImageView) convertView.findViewById(R.id.boolist_image);
				holder.tv_title = (TextView) convertView.findViewById(R.id.boolist_title);
				holder.tv_content = (TextView) convertView.findViewById(R.id.boolist_content);
				holder.tv_author = (TextView) convertView.findViewById(R.id.boolist_author);
				convertView.setTag(holder);
				}else{
					holder = (ViewHolder) convertView.getTag();
				}
				Map<String,String> map = new HashMap<String,String>();
				if(groupPosition==0){
					map = list.get(childPosition);
				}else{
					map = clist.get(childPosition);
				}
				holder.tv_title.setText(map.get("title"));
				holder.tv_content.setText(map.get("content"));
				holder.tv_author.setText(map.get("author"));
				holder.iv.setTag(Const.IMAGEPATH_URL+map.get("image"));
				holder.iv.setImageResource(drawable.d1);
				HttpSender.getInstance().downImage(getApplicationContext(), holder.iv,convertView, Const.IMAGEPATH_URL+map.get("image"), map.get("image"));
//				holder.iv.setImageResource(drawable.d1);
				return convertView;
			}

			// ��ȡָ����λ�ô��������
			@Override
			public Object getGroup(int groupPosition) {
				return BookTypes[groupPosition];
			}

			@Override
			public int getGroupCount() {
				return BookTypes.length;
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			// �÷�������ÿ����ѡ������
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				LinearLayout ll = new LinearLayout(MyBookStore.this);
				ll.setOrientation(0);
				TextView textView = getTextView();
				textView.setText(getGroup(groupPosition).toString());
				ll.addView(textView);
				return ll;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				return true;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

		};

		ExpandableListView expandListView = (ExpandableListView) findViewById(R.id.list);
		expandListView.setAdapter(adapter);
//		expandListView.setChildDivider(null);
		expandListView.setChildIndicator(null);
		expandListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					final int groupPosition, final int childPosition, long id) {
				// new
				// AlertDialog.Builder(MyBookStore.this).setPositiveButton("����",
				// new OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// // TODO Auto-generated method stub
				// Intent intent=new Intent(MyBookStore.this,BorrowBook.class);
				// startActivity(intent);
				//
				// }
				// }).show();
				 
				switch (groupPosition) {
				case 0:
					 final Map<String,String> map = list.get(childPosition);
					
					AlertDialog.Builder build = new AlertDialog.Builder(
							MyBookStore.this);
					build.setPositiveButton("是", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Map<String,String> map_url = new HashMap<String, String>();
							Map<String,String> map_param = new HashMap<String, String>();
							map_url.put("url", Const.CHANGISLEND);
							map_param.put("username", username);
							map_param.put("id", map.get("id")+"");
							list.clear();
							clist.clear();
							HttpSender.getInstance().sendQuery(map_url, map_param, MyBookStore.this);
						}
					}).setNegativeButton("否", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new AlertDialog.Builder(MyBookStore.this)
									.setCancelable(true);
						}
					}).setTitle("是否已经借出去").show();
					break;

				case 1:
					final Map<String,String> maps = clist.get(childPosition);
					AlertDialog.Builder builds = new AlertDialog.Builder(
							MyBookStore.this);
					builds.setPositiveButton("是", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Map<String,String> map_url = new HashMap<String, String>();
							Map<String,String> map_param = new HashMap<String, String>();
							map_url.put("url", Const.CHANGISNOTLEND);
							map_param.put("username", username);
							map_param.put("id", maps.get("id")+"");
							list.clear();
							clist.clear();
							HttpSender.getInstance().sendQuery(map_url, map_param, MyBookStore.this);
						}
					}).setNegativeButton("否", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new AlertDialog.Builder(MyBookStore.this)
									.setCancelable(true);
						}
					}).setTitle("是否已经还回来").show();
					break;
				}
//				AlertDialog.Builder build = new AlertDialog.Builder(
//						MyBookStore.this);
//				build.setPositiveButton("ȷ��", new OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent(MyBookStore.this,
//								BorrowBook.class);
//						startActivity(intent);
//					}
//				}).setNegativeButton("ȡ��", new OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						new AlertDialog.Builder(MyBookStore.this)
//								.setCancelable(true);
//					}
//				}).setTitle("��ȷ��Ҫ���Ȿ����").show();
				return true;
			}
		});
	}
	@Override
	public void onSuccess(List<Book> books) {
		
		if(books.size()==1&&books.get(0).getId()==0){
			Toast.makeText(getApplicationContext(), "您没有任何关于书的信息，敬请添加", Toast.LENGTH_SHORT).show();
			return;
		}
//		List<String> wtlend = new ArrayList<String>();
//		List<String> lended = new ArrayList<String>();
		for(Book book:books){
			Map<String,String> map = new HashMap<String, String>();
			map.put("title", book.getTitle());
			map.put("author", book.getAuthor());
			map.put("content", book.getContent());
			map.put("image", book.getImage());
			map.put("id", book.getId()+"");
			map.put("classify", book.getClassify()+"");
			map.put("username", book.getUsername());
			map.put("p_num", book.getP_num());
			switch (book.getIslend()) {
			
			case 0:
//				wtlend.add(book.getTitle());
				list.add(map);
				break;

			case 1:
//				lended.add(book.getTitle());
				clist.add(map);
				break;
			}
		}
		bgroup.put(0, list);
		bgroup.put(1, clist);
		BookTypes[0] = "还没借出的书籍 ("+list.size()+")";
		BookTypes[1] = "已借出的书籍 ("+clist.size()+")";
//		String [] wantLend = wtlend.toArray(new String[wtlend.size()]); 
//		String [] lendeded = lended.toArray(new String[lended.size()]);
//		detailbook = new String[][]{wantLend,lendeded};
		Toast.makeText(getApplicationContext(), "加载成功", Toast.LENGTH_SHORT).show();
//		for(Book book:books){
//			Map<String,String> map = new HashMap<String, String>();
//			map.put("title", book.getTitle());
//			map.put("author", book.getAuthor());
//			map.put("content", book.getContent());
//			map.put("image", book.getImage());
//			map.put("id", book.getId()+"");
//			map.put("classify", book.getClassify()+"");
//			map.put("username", book.getUsername());
//			map.put("p_num", book.getP_num());
//			list.add(map);
//		}
		init();
	}
	@Override
	public void onException() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "加载信息失败", Toast.LENGTH_SHORT).show();
	}
	class ViewHolder{
		private ImageView iv;
	    private TextView tv_title;
        private TextView tv_content;
        private TextView tv_author;
	}
	

}

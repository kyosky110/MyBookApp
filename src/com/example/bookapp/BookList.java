package com.example.bookapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.example.adapter.MyBookAdapter;
import com.example.bean.Book;
import com.example.biz.Const;
import com.example.biz.HttpSender;
import com.example.biz.HttpTaskListener;
import com.example.db.BookDao;
import com.example.view.XListView;
import com.example.view.XListView.IXListViewListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class BookList extends Activity implements HttpTaskListener,IXListViewListener,OnItemClickListener{
	private MyBookAdapter mAdapter;
	private List<Map<String,String>> list,clist;
	private String classify,index;
	private int num = 0;
	private ProgressDialog progressDialog;
	private XListView xlistview;
	private Handler mHandler;
	private boolean isRefresh = false;
	private boolean isLoad = false;
	private boolean isStopLoad = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		classify = intent.getStringExtra("classify").trim();
		Toast.makeText(getApplicationContext(), classify, Toast.LENGTH_SHORT).show();
		list = new ArrayList<Map<String,String>>();
		clist = new ArrayList<Map<String,String>>();
		mHandler = new Handler();
		setContentView(R.layout.booklist);
		progressDialog = ProgressDialog.show(this, "", "正在努力加载中...", true);
		sendHttp();
	}
	private void sendHttp() {
		Map<String,String> map_url = new HashMap<String, String>();
		Map<String,String> map_params = new HashMap<String, String>();
		map_url.put("url", Const.QUERYBOOK_URL);
		map_params.put("classify", classify);
		HttpSender.getInstance().sendQuery(map_url, map_params, this);
		
	}
	private void sendHttpByIndex() {
		Map<String,String> map_url = new HashMap<String, String>();
		Map<String,String> map_params = new HashMap<String, String>();
		index = num +"";
		map_url.put("url", Const.QUERYBOOK_URL);
		map_params.put("classify", classify);
		map_params.put("index", index);
		HttpSender.getInstance().sendQuery(map_url, map_params, this);
		
	}
	public void init(){
		mAdapter = new MyBookAdapter(BookList.this, list);
		xlistview = (XListView) findViewById(R.id.boolist_lv);
		xlistview.setPullLoadEnable(true);
		xlistview.setAdapter(mAdapter);
		xlistview.setXListViewListener(this);
		xlistview.setOnItemClickListener(this);
		if(isRefresh){
			isRefresh = false;
			onLoad();
		}
	}
	public void initFailure(){
		getData();
		mAdapter = new MyBookAdapter(this, list);
		xlistview = (XListView) findViewById(R.id.boolist_lv);
		xlistview.setAdapter(mAdapter);
		xlistview.setOnItemClickListener(this);
		if(isRefresh){
			isRefresh = false;
			onLoad();
		}
	}
	public void onClick(View v){
		if(v.getId()==R.id.booklist_back){
			finish();
		}
	}
	private void getData() {
		
//		for(int i= 0;i<10;i++){
//			Map<String,String> map = new HashMap<String, String>();
//			map.put("title", "Ӣ����");
//			map.put("author", "�Ʒɺ�");
//			map.put("content", "��˵�������д�úܳ��Ŀ϶������������Ƶķ��Ľ�������¼�ķ�����Ľ�������");
//			list.add(map);
//		}
		List<Book> books = BookDao.getInstance(getApplicationContext()).query(Integer.parseInt(classify));
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
			list.add(map);
		}
	}
	@Override
	public void onSuccess(List<Book> books) {
		progressDialog.dismiss();
		if(books.size()==1&&books.get(0).getId()==0){
			Toast.makeText(getApplicationContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
			initFailure();
		}else{
			if(isRefresh){
				clist.clear();
				for(Book book:books){
					Map<String,String> map = new HashMap<String, String>();
					map.put("title", book.getTitle());
					map.put("author", book.getAuthor());
					map.put("content", book.getContent());
					map.put("image", book.getImage());
					map.put("p_num", book.getP_num());
					clist.add(map);
				}
				if(clist.get(0).get("title").equals(list.get(0).get("title"))&&
						clist.get(0).get("content").equals(list.get(0).get("content"))){
					isRefresh = false;
					onLoad();
					return;
				}
				Toast.makeText(getApplicationContext(), "进入更新", Toast.LENGTH_SHORT).show();
				list.clear();
			}
			if(books.size()<10){
				isStopLoad = true ;
			}
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
				list.add(map);
			}
			if(isLoad){
				isLoad = false;
				mAdapter.notifyDataSetChanged();
				onLoad();
				return;
			}
			BookDao.getInstance(getApplicationContext()).delInfo(Integer.parseInt(classify));
			BookDao.getInstance(getApplicationContext()).insert(books);
		init();
		}
	}
	@Override
	public void onException() {
		progressDialog.dismiss();
		Toast.makeText(getApplicationContext(), "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
		if(isLoad){
			isLoad = false;
			onLoad();
		}
		initFailure();
	}
	
	private void onLoad() {
		xlistview.stopRefresh();
		xlistview.stopLoadMore();
		xlistview.setRefreshTime("某一个月黑风高的晚上");
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				isRefresh = true;
				sendHttp();
			}
		}, 2000);
	}
	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if(!isStopLoad){
				num++;
				isLoad = true;
				sendHttpByIndex();
				}else{
					Toast.makeText(getApplicationContext(), "小的没有数据给殿下了，请原谅，请添加", Toast.LENGTH_SHORT).show();
					onLoad();
				}
			}
		}, 2000);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(BookList.this,DetailInfoActivity.class);
		intent.putExtra("title", list.get((int)id).get("title"));
		intent.putExtra("author", list.get((int)id).get("author"));
		intent.putExtra("content", list.get((int)id).get("content"));
		intent.putExtra("image", list.get((int)id).get("image"));
		intent.putExtra("p_num", list.get((int)id).get("p_num"));
		startActivity(intent);
	}
}

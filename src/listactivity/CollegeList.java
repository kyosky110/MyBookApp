package listactivity;

import java.util.ArrayList;
import java.util.List;

 
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookapp.R;

public class CollegeList extends Activity {
	ListView listView;
	Boolean is_divpager;// 是否分页
	MyAdapter adapter;
	ProgressDialog dialog;

	// List<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collegelist);
		listView = (ListView) findViewById(R.id.collegelist);
		adapter = new MyAdapter();
		adapter.bindData(getData());
		listView.setAdapter(adapter);
		dialog = new ProgressDialog(CollegeList.this);
		dialog.setTitle("提示");
		dialog.setMessage("加载中...");
		

		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (is_divpager
						&& scrollState == OnScrollListener.SCROLL_STATE_FLING) {// 当用户滑到最低端，并且松开手指时，进行分页，加载数据
					// 开启异步任务，获取数据，更新UI
//                             task=new myTask();
					dialog.show();
					dialog.dismiss();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				is_divpager = (firstVisibleItem + visibleItemCount == totalItemCount);// 滑动到最低端
			}
		});
	}
	   
	   

	public List<String> getData() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			list.add("Book" + i);
		}
		return list;
	}
	 
	 
	class MyAdapter extends BaseAdapter {
		List<String> list;
		

		public MyAdapter() {
			// TODO Auto-generated constructor stub

		}

		public void bindData(List<String> list) {
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
			// TODO Auto-generated method stub
			TextView textView = null;
			if (convertView == null) {
				textView = new TextView(CollegeList.this);
				textView.setText(list.get(position));
				textView.setTextSize(20);

			} else {
				textView = (TextView) convertView;
			}
			return textView;
		}

	}

}

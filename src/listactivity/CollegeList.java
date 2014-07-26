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
	Boolean is_divpager;// �Ƿ��ҳ
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
		dialog.setTitle("��ʾ");
		dialog.setMessage("������...");
		

		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (is_divpager
						&& scrollState == OnScrollListener.SCROLL_STATE_FLING) {// ���û�������Ͷˣ������ɿ���ָʱ�����з�ҳ����������
					// �����첽���񣬻�ȡ���ݣ�����UI
//                             task=new myTask();
					dialog.show();
					dialog.dismiss();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				is_divpager = (firstVisibleItem + visibleItemCount == totalItemCount);// ��������Ͷ�
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

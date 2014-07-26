package listactivity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bookapp.R;
import com.webservice.WebServiceUtil;

public class EnglishList extends Activity {
	WebServiceUtil serviceUtilserviceUtil = new WebServiceUtil();
	ListView list_english;
	String sql = "select *from app_book";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.englishlist);
		list_english = (ListView) findViewById(R.id.englishlist);
		list_english.setAdapter(new MyAdapter());
		
		
		  
	}
	class MyAdapter extends BaseAdapter {
		List<String> list = WebServiceUtil.doSql(sql);
		

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
				textView = new TextView(EnglishList.this);
				textView.setText(list.get(position));
				textView.setTextSize(20);

			} else {
				textView = (TextView) convertView;
			}
			return textView;
		}

	}

}

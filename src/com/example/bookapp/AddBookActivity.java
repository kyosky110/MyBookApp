package com.example.bookapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.example.bean.Book;
import com.example.biz.Const;
import com.example.biz.DownService;
import com.example.biz.HttpSender;
import com.example.biz.HttpTaskListener;

public class AddBookActivity extends Activity implements HttpTaskListener{
	private static final String [] classifys = {"考研英语","考研数学","考研政治","大学教材","移动互联网","其它书籍"};
	/**��¼classify��ֵ*/
	private String classify_num;
	private String username;
	private Spinner spinner;
	private ProgressDialog progressDialog;
	private TextView tv;

	private ArrayAdapter<String> adapter;
	private EditText ed_title;
	private EditText ed_author;
	private EditText ed_content;
	private EditText ed_pnum;
	private ImageView image;

	private String path = null;
	private Uri imageUri;
	private static final int TAKE_PICTURE = 1;
	private static final int CHOOSE_PICTURE = 2;
	public static String imageDir = Const.SD_IMAGEDIR;
	public static String tempDir = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/Android/data/com.sky.mybook/";
	String name = "";
	String temp="temp.jpg";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbook);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		init();
		
	}
	public void init(){
		ed_title = (EditText) findViewById(R.id.addbook_title);
		ed_author = (EditText) findViewById(R.id.addbook_author);
		ed_content = (EditText) findViewById(R.id.addbook_content);
		ed_pnum = (EditText) findViewById(R.id.addbook_pnum);
		image = (ImageView) findViewById(R.id.addbook_photo);
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AddBookActivity.this)
						.setTitle("选择图片")
						.setItems(new String[] { "拍照", "本地选取" },
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Intent intent = null;
										// 0相机,1本地读取
										switch (which) {
										case 0:
											imageUri=Uri.fromFile(new File(tempDir,temp));
											intent = new Intent(
													MediaStore.ACTION_IMAGE_CAPTURE);
											 intent.putExtra(MediaStore.EXTRA_OUTPUT,
											 imageUri);
											startActivityForResult(intent,
													TAKE_PICTURE);
											break;
										case 1:
											intent = new Intent(
													intent.ACTION_GET_CONTENT,
													null);
											intent.setType("image/*");
											startActivityForResult(intent,
													CHOOSE_PICTURE);
											break;
										}
									}
								}).show();
			}
		});
		tv = (TextView) findViewById(R.id.addbook_tv);
		spinner = (Spinner) findViewById(R.id.addbook_classify);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,classifys);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}
	class MyOnItemSelectedListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			tv.setText("您选择书的类型是"+adapter.getItem(position));
			classify_num = position+"";
			parent.setVisibility(View.VISIBLE);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public void onClick(View v){
		if(v.getId()==R.id.addbook_quit){
			finish();
		}else if(v.getId()==R.id.addbook_ok){
			if(path==null){
				addDate();
				return;
			}
			
			File file = new File(path);
			if(!file.exists()){
				Toast.makeText(getApplicationContext(), "图片加载错误", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				try{
					HttpSender.getInstance().uploadImage(path, name);
					addDate();
				}catch (BCSServiceException e) {
					Toast.makeText(getApplicationContext(), "服务器端出错", 1000).show();
					finish();
				} catch (BCSClientException e) {
					Toast.makeText(getApplicationContext(), "客户端出错", 1000).show();
					finish();
				}
			}
			
		}
	}
	private void addDate() {
		String title = ed_title.getText().toString().trim();
		String content = ed_content.getText().toString().trim();
		String author = ed_author.getText().toString().trim();
		String p_num = ed_pnum.getText().toString().trim();
		if (TextUtils.isEmpty(username)
				|| TextUtils.isEmpty(title)||TextUtils.isEmpty(content)||TextUtils.isEmpty(author)
				||TextUtils.isEmpty(p_num)) {
			Toast.makeText(getApplicationContext(), "内容不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		Map<String,String> map_url = new HashMap<String, String>();
		map_url.put("url", Const.ADDBOOK_URL);
		Map<String,String> map_params = new HashMap<String, String>();
		map_params.put("username", username);
		map_params.put("title", title);
		map_params.put("content", content);
		map_params.put("author", author);
		map_params.put("image", name);
		map_params.put("classify", classify_num);
		map_params.put("p_num", p_num);
		progressDialog = ProgressDialog.show(this, "", "添加中...", true);
		HttpSender.getInstance().addBook(map_url, map_params, this);
	}
	@Override
	public void onSuccess(List<Book> list) {
		progressDialog.dismiss();
		Book book = list.get(0);
		if(book.getId()==0){
			Toast.makeText(getApplicationContext(), "对不起，添加失败", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
		}
		finish();
	}
	@Override
	public void onException() {
		progressDialog.dismiss();
		Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
		finish();
	}
	
	//压缩图片
		private Bitmap revitionImageSize(String path, int displayWidth,int displayHeight) throws IOException {  
	        // 取得图片   
	          
	        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();  
	        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化   
	        bitmapOptions.inJustDecodeBounds = true;  
	        // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）   
	        Bitmap bmp=BitmapFactory.decodeFile(path, bitmapOptions);  
	        // 关闭流   
	        
	  
	     // 编码后bitmap的宽高,bitmap除以屏幕宽度得到压缩比
	        int widthRatio = (int) FloatMath.ceil(bitmapOptions.outWidth
	        / (float) displayWidth);
	        int heightRatio = (int) FloatMath.ceil(bitmapOptions.outHeight
	        / (float) displayHeight);

	        if (widthRatio > 1 && heightRatio > 1) {
	        if (widthRatio > heightRatio) {
	        // 压缩到原来的(1/widthRatios)
	        bitmapOptions.inSampleSize = widthRatio;
	        } else {
	        bitmapOptions.inSampleSize = heightRatio;
	        }
	        }
	        bitmapOptions.inJustDecodeBounds = false;
	        bmp = BitmapFactory.decodeFile(path, bitmapOptions);
	        return bmp;
	        
		}
		//将图片存储在指定位置
		void saveFile(Bitmap bitmap){
			FileOutputStream b = null;
			/*
			 * File file = new File("/sdcard/myImage2/"); file.mkdirs();
			 * //按时间取名字存取 String name = new
			 * DateFormat().format("yyyyMMdd_hhmmss"
			 * ,Calendar.getInstance(Locale.CHINA)) + ".jpg"; String
			 * fileName = "/sdcard/myImage2/"+name;
			 */
			File file = new File(imageDir);//创建文件
			// "/sdcard/Android/data/demo3/downloadImage"
			if (!file.isFile()) {
				file.mkdirs();
			}
			name = new DateFormat().format("yyyyMMddhhmmss",
					Calendar.getInstance(Locale.CHINA))
					+ ".jpg";
			try {
				b = new FileOutputStream(imageDir + name);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, b);
				path = imageDir+"/"+name;
				Log.d("TestFile", "puch data now");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				File file1 = new File(imageDir, name);
				if (!file1.isFile()) {
					file1.delete();
					Toast.makeText(AddBookActivity.this, "压缩存储失败", 1).show();
				}
			} finally {
				try {
					b.flush();
					b.close();
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(AddBookActivity.this, "什么IO失败", 1).show();
				}
			}
		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (resultCode == RESULT_OK) {
				switch (requestCode) {
				case CHOOSE_PICTURE:
					Uri uri = data.getData();

					ContentResolver cr = this.getContentResolver();
					try {
						String[] pj = { MediaStore.Images.Media.DATA };//获取相册所选图片的路径
						Cursor cursor = managedQuery(uri, pj, null, null, null);
						int c_index = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						String path = cursor.getString(c_index);
						Toast.makeText(AddBookActivity.this, path, 1).show();
						
						Bitmap bitmap = revitionImageSize(path, 300,300);
						saveFile(bitmap);
						image.setImageBitmap(bitmap);

					} catch (FileNotFoundException e) {
						Log.e("Exception", e.getMessage(), e);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case TAKE_PICTURE:
					String sdStatus = Environment.getExternalStorageState();
					if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
						Log.d("TestFile",
								"SD card is not avaiable/writeable right now.");
						return;
					}
					/*
					// 读取数据，创建文件
					Bundle bundle = data.getExtras();
					final Bitmap bitmap = (Bitmap) bundle.get("data");
					saveFile(bitmap);
					Toast.makeText(RecordPhoto.this, imageDir + name, 1).show();
					image.setImageBitmap(bitmap);
					*/
					try {
						Bitmap bitmap = revitionImageSize(tempDir+temp, 300,300);
						saveFile(bitmap);
						image.setImageBitmap(bitmap);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
}

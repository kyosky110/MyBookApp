package com.example.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.bean.Book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BookDao {
	private static BookDao bookDao ;
	private static String DB_NAME = "bookInfo.db";//wecometosqlite.db
	    //��ݿ�汾
	private static int DB_VERSION = 2;
	private SqliteHelper helper; 
	private BookDao(Context context){
		helper = new SqliteHelper(context, DB_NAME, null, DB_VERSION);
	}
	
	
	public static BookDao getInstance(Context context){
		if(bookDao==null){
			bookDao = new BookDao(context);
		}
		
		return bookDao;
	}
	public void insert(List<Book> books){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		for(Book book:books){
			values.put("id", book.getId());
			values.put("title", book.getTitle());
			values.put("author", book.getAuthor());
			values.put("content", book.getContent());
			values.put("username", book.getUsername());
			values.put("image", book.getImage());
			values.put("classify", book.getClassify());
			values.put("p_num", book.getP_num());
			long uid = db.insert(SqliteHelper.TB_NAME_USER, null, values);
			System.out.println("insert :"+uid);
		}
		db.close();
	}
	public void delInfo(int classify){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(SqliteHelper.TB_NAME_USER, "classify "+ "= "+classify, null);
		System.out.println("删除 ");
		db.close();
	}
	public List<Book> query(int classify){
		SQLiteDatabase db = helper.getWritableDatabase();
		List<Book> lists = new ArrayList<Book>();
		 Cursor cursor=db.query(SqliteHelper.TB_NAME_USER, null, "classify="+classify, null, null, null, "id"+" DESC");
		 cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Book book = new Book();
			book.setId(cursor.getInt(0));
			book.setTitle(cursor.getString(1));
			book.setContent(cursor.getString(2));
			book.setAuthor(cursor.getString(3));
			book.setImage(cursor.getString(4));
			book.setUsername(cursor.getString(5));
			System.out.println("aaa"+cursor.getString(6));
			book.setClassify(cursor.getInt(6));
			book.setP_num(cursor.getString(7));
			lists.add(book);
			System.out.println("查询 ");
			cursor.moveToNext();
		}
		cursor.close();
		return lists;
	}
}

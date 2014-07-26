package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {
	 public static final String TB_NAME_USER="books";
	public SqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "+
				TB_NAME_USER+"("+
							"id integer primary key,"+
							"title varchar,"+
							"content varchar,"+
							"author varchar,"+
							"image varchar,"+
							"username varchar,"+
							"classify integer,"+
							"p_num varchar)"
						//	"date datetime,"+
							//"islend integer)"
							
				);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TB_NAME_USER);
        onCreate(db);
	}

}

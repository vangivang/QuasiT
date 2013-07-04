package com.example.quasit;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class StatusProvider extends ContentProvider {

	public static final String AUTHORITY = "content://com.example.quasit.provider";
	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);

	public static final String DB_NAME = "timeline.db";
	public static final String TABLE_NAME = "status";
	public static final int DB_VERSION = 1;

	public static final String C_ID = "_id";
	public static final String C_CREATED_AT = "created_at";
	public static final String C_USER = "user";
	public static final String C_TEXT = "status_text";

	SQLiteDatabase db;
	DbHelper dbHelper;

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		if (uri.getLastPathSegment() == null) {
			return "vnd.android.cursor.item/vnd.example.quasit.status";
		} else {
			return "vnd.android.cursor.dir/vnd.example.quasit.status";
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		db = dbHelper.getWritableDatabase();
		long id = db.insertWithOnConflict(TABLE_NAME, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		if (id != -1) {
			uri = Uri.withAppendedPath(uri, String.valueOf(id));
		}
		return uri;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String orderBy) {
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, projection, selection,
				selectionArgs, null, null, orderBy);
		return cursor;
	}

	class DbHelper extends SQLiteOpenHelper {

		private static final String TAG = "YO";

		public DbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = String
					.format("create table %s (%s int primary key, %s int, %s text, %s text)",
							TABLE_NAME, C_ID,
							C_CREATED_AT, C_USER,
							C_TEXT);
			Log.d(TAG, "onCreate() with SQL: " + sql);
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

}

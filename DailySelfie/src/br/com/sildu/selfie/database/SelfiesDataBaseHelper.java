package br.com.sildu.selfie.database;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import br.com.sildu.selfie.pojo.Selfie;

public class SelfiesDataBaseHelper extends SQLiteOpenHelper {

	public final static String		TABLE_NAME		= "selfie";
	public final static String		ID				= "_id";
	public final static String		SELFIE_NAME		= "name";
	public final static String		SELFIE_IMAGE	= "selfie";
	public final static String[]	columns			= { ID, SELFIE_NAME, SELFIE_IMAGE };
	public final String				createSql;

	private static String			DATABASE_NAME	= "selfies_db";
	private static Integer			VERSION			= 1;

	private Context					mContext;

	public SelfiesDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
		this.mContext = context;

		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ");
		builder.append(TABLE_NAME);
		builder.append(" (");
		builder.append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		builder.append(SELFIE_NAME).append(" TEXT NOT NULL, ");
		builder.append(SELFIE_IMAGE).append(" imageblob BLOB)");

		createSql = builder.toString();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// N/A
	}

	void deleteDatabase() {
		mContext.deleteDatabase(DATABASE_NAME);
	}

	public void insertSelfieInBD(Selfie selfie) {

		ContentValues values = new ContentValues();

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		selfie.getSelfie().compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();

		values.put(SelfiesDataBaseHelper.SELFIE_NAME, selfie.getName());
		values.put(SelfiesDataBaseHelper.SELFIE_IMAGE, byteArray);
		this.getWritableDatabase().insert(SelfiesDataBaseHelper.TABLE_NAME, null, values);

	}

	public List<Selfie> SelectAllSelfies() {
		List<Selfie> selfies = new ArrayList<Selfie>();
		Cursor c = readSelfies();
		while (c.moveToNext()) {
			byte[] image = c.getBlob(2);

			Selfie selfie = new Selfie();
			selfie.setName(c.getString(1));
			selfie.setSelfie(BitmapFactory.decodeByteArray(image, 0, image.length));

			selfies.add(selfie);

		}

		return selfies;
	}

	private Cursor readSelfies() {
		return this.getWritableDatabase().query(SelfiesDataBaseHelper.TABLE_NAME, SelfiesDataBaseHelper.columns, null, new String[] {}, null, null, null);
	}

	public void clearAll() {
		this.getWritableDatabase().delete(SelfiesDataBaseHelper.TABLE_NAME, null, null);
	}

}

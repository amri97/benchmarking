package io.blongho.github.greendao.database;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import io.blongho.github.greendao.model.DaoMaster;

public class DatabaseHelper extends DaoMaster.OpenHelper {
  private static final String TAG = "DatabaseHelper";

  public DatabaseHelper(Context context, String name) {
    super(context, name);
  }

  @Override
  public void onUpgrade(Database db, int oldVersion, int newVersion) {
    super.onUpgrade(db, oldVersion, newVersion);
    Log.d(TAG, "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
  }
}

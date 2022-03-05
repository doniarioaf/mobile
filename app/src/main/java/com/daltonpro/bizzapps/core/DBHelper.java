package com.daltonpro.bizzapps.core;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.daltonpro.bizzapps.util.FileUtil;

import java.io.File;

/**
 * Created by Deni Setiawan on 8/1/2018.
 */

public class DBHelper {
    private Context context;

    public DBHelper(Context context) {
        this.context = context;
    }

    public void exportDB() {
        try {
            File data = Environment.getDataDirectory();

            String currentDBPath = "/data/" + context.getPackageName() + "/databases/" + DaltonDatabase.getDatabaseName();

            File currentDB = new File(data, currentDBPath);
            File backupDB = FileUtil.getAbsoluteFile(context, "/DATA/", "DaltonData");

            if (!backupDB.exists()) {
                backupDB.mkdirs();
            }
            FileUtil.copy(currentDB, backupDB);

        } catch (Exception e) {
            Log.d("exporting", "export is called 5");
            Toast.makeText(context, "Backup Failed!" + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();

        }
        Log.d("exporting", "export is called 6");
    }

}

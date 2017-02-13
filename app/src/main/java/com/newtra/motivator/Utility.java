package com.newtra.motivator;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by sethugayu on 1/9/17.
 */

public class Utility {
    public static String getFilePathOnMobile(Context context, String name) {
        String filePath = "";
        File file = null;
//        if (context == null) {
//            context = HomeActivity.context;
//        }
        /*
         * if (isExternalStoragePresent()) { file = new
		 * File(Environment.getExternalStorageDirectory(), "theatro"); if
		 * (!file.exists()) { file.mkdirs(); } filePath = file +
		 * "/record_"+name+".wav"; } else {
		 */
        file = context.getDir("theatro", Context.MODE_PRIVATE);
        filePath = file + "/" + name + ".wav";
        // }

//        filePath = "/sdcard/theatro/" + name + ".wav";


        return filePath;
    }
    public static boolean checkExists(String directory, String file) {
        String path = Environment.getExternalStorageDirectory().toString() + "/Pictures";
        path = directory.toString();
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File files[] = f.listFiles();
        Log.d("Files", "Size: " + files.length);
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().equals(file)) {
                return true;
            }

        }

        return false;
    }
}


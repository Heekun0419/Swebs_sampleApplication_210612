package com.example.swebs_sampleapplication_210612.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilePathFinder {

    Context context;

    public FilePathFinder(Context context){
        this.context = context;
    }

    public File createMakefile() throws IOException {
        @SuppressLint("SimpleDateFormat")
        String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,".jpg",storageDir
        );
    }

    public String getPath(Uri uri) {
        String result;
        Cursor cursor = context.getContentResolver().query(uri,null, null, null, null);
        if(cursor == null){
            result = uri.getPath();
        }else{
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(columnIndex);
            cursor.close();
        }
        return result;
    }
}

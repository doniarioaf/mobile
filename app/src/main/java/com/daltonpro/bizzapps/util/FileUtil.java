package com.daltonpro.bizzapps.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
    public static File getAbsoluteFile(Context context, String folderName, String fileName) {

        File dir = getAbsoluteFolder(context, folderName);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return new File(dir, fileName);

    }


    public static void zip(String[] _files, String zipFileName) {
        try (FileOutputStream dest = new FileOutputStream(zipFileName); ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                dest))){


            byte data[] = new byte[2048];

            for (int i = 0; i < _files.length; i++) {
                Log.v("Compress", "Adding: " + _files[i]);
                try (FileInputStream fi = new FileInputStream(_files[i]); BufferedInputStream origin = new BufferedInputStream(fi, 2048)){
                    ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;

                    while ((count = origin.read(data, 0, 2048)) != -1) {
                        out.write(data, 0, count);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getAbsoluteFolder(Context context, String folderName) {
        return new File(context.getExternalFilesDir(null).getAbsolutePath() + folderName);
    }
}

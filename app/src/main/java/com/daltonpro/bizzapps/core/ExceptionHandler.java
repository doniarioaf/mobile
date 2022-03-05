package com.daltonpro.bizzapps.core;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.daltonpro.bizzapps.BuildConfig;
import com.daltonpro.bizzapps.util.FileUtil;

import java.io.PrintWriter;
import java.io.StringWriter;


public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Activity myContext;
    private final String LINE_SEPARATOR = "\n";

    public ExceptionHandler(Activity context) {
        myContext = context;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("************ CAUSE OF ERROR ************\n\n");
        errorReport.append(stackTrace.toString());

        errorReport.append("\n************ DEVICE INFORMATION ***********\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ FIRMWARE ************\n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);

        final DBHelper dbHelper = new DBHelper(myContext);
        dbHelper.exportDB();

        String SAMPLE_DB_NAME = "DaltonData";

        try {
            String[] stringArray = {FileUtil.getAbsoluteFile(myContext, "/DATA/", SAMPLE_DB_NAME).getAbsolutePath()};

            FileUtil.zip(stringArray, FileUtil.getAbsoluteFile(myContext, "/DATA/", "dalton.zip").getAbsolutePath());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("application/octet-stream");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"system.dalton@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Error Report DaltonPro " + BuildConfig.VERSION_NAME + " Code : " + BuildConfig.VERSION_CODE);

        i.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(myContext,
                BuildConfig.APPLICATION_ID + ".provider",
                FileUtil.getAbsoluteFile(myContext, "/DATA/", "dalton.zip")
        ));


        i.putExtra(Intent.EXTRA_TEXT, errorReport.toString());
        try {
            myContext.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(myContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

    }

    public void emailReport(){
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("Mohon untuk isi masalah anda : \n\n");
        errorReport.append("\n\n");

        errorReport.append("\n****** DEVICE INFORMATION ******\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);

        errorReport.append("\n****** FIRMWARE ******\n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);

        final DBHelper dbHelper = new DBHelper(myContext);
        dbHelper.exportDB();

        String SAMPLE_DB_NAME = "DaltonData";

        try {
            String[] stringArray = {FileUtil.getAbsoluteFile(myContext, "/DATA/", SAMPLE_DB_NAME).getAbsolutePath()};

            FileUtil.zip(stringArray, FileUtil.getAbsoluteFile(myContext, "/DATA/", "dalton.zip").getAbsolutePath());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("application/octet-stream");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"system.dalton@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Error Report DaltonPro " + BuildConfig.VERSION_NAME + " Code : " + BuildConfig.VERSION_CODE);

        i.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(myContext,
                BuildConfig.APPLICATION_ID + ".provider",
                FileUtil.getAbsoluteFile(myContext, "/DATA/", "dalton.zip")
        ));


        i.putExtra(Intent.EXTRA_TEXT, errorReport.toString());
        try {
            myContext.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(myContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

}

package com.daltonpro.bizzapps.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BitmapHelper {
    public static Bitmap addWatermark(Bitmap src, String watermarkRow1, String watermarkRow2, String watermarkRow3) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        if (w<200 && w>100){
            paint.setTextSize(12);
        }
        else if (w>200 && w<300){
            paint.setTextSize(14);
        }else if (w>400){
            paint.setTextSize(14);
        }else{
            paint.setTextSize(12);
        }

        paint.setAntiAlias(true);
        paint.setUnderlineText(false);
        canvas.drawText(watermarkRow1, w/12, h/1.30f, paint);
        canvas.drawText(watermarkRow2, w/12, h/1.20f, paint);
        canvas.drawText(watermarkRow3, w/12, h/1.10f, paint);


        return result;
    }

}

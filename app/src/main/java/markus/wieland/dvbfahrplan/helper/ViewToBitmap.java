package markus.wieland.dvbfahrplan.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ViewToBitmap {

    private ViewToBitmap() {
    }

    public static Bitmap getFromView(View view, int width) {
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmapFromMultipleViews(List<View> views, int width) {
        int totalHeight = 0;
        List<Bitmap> bitmaps = new ArrayList<>();

        for (View view : views) {
            bitmaps.add(getFromView(view, width));
            totalHeight += view.getMeasuredHeight();
        }

        Bitmap totalBitmap = Bitmap.createBitmap(width, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas totalCanvas = new Canvas(totalBitmap);

        Paint paint = new Paint();
        int currentHeight = 0;

        for (int i = 0; i < bitmaps.size(); i++) {
            Bitmap bitmap = bitmaps.get(i);
            totalCanvas.drawBitmap(bitmap, 0, currentHeight, paint);
            currentHeight += bitmap.getHeight();
        }

        return totalBitmap;
    }

}

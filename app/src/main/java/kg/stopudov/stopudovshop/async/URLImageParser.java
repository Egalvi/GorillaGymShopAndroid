package kg.stopudov.stopudovshop.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import java.io.InputStream;

public class URLImageParser implements Html.ImageGetter {
    Context c;
    TextView container;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     *
     * @param t
     * @param c
     */
    public URLImageParser(TextView t, Context c) {
        this.c = c;
        this.container = t;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();
//        RoundedBitmapDrawableFactory

        // get the actual source
        ImageGetterAsyncTask asyncTask =
                new ImageGetterAsyncTask(urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchScaledDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result == null) {
                return;
            }
            // set the correct bound according to the result from HTTP call
            urlDrawable.setBounds(0, 0, result.getIntrinsicWidth(), result.getIntrinsicHeight());

            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            URLImageParser.this.container.invalidate();
            container.setText(container.getText());
        }

        /***
         * Gets the Drawable from URL
         *
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = new java.net.URL(urlString).openStream();
                Drawable drawable = Drawable.createFromStream(is, "src");
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

        /***
         * Gets the Drawable from URL scaled to fit container width
         *
         * @param urlString
         * @return
         */
        public Drawable fetchScaledDrawable(String urlString) {
            try {
                Drawable drawable = fetchDrawable(urlString);
                Bitmap b = ((BitmapDrawable) drawable).getBitmap();
                double coef = (double) container.getWidth() / drawable.getIntrinsicWidth();
                int dstWidth = Double.valueOf(drawable.getIntrinsicWidth() * coef).intValue();
                int dstHeight = Double.valueOf(drawable.getIntrinsicHeight() * coef).intValue();
                Bitmap bitmapResized = Bitmap.createScaledBitmap(b, dstWidth, dstHeight, false);
                drawable = new BitmapDrawable(container.getResources(), bitmapResized);
                drawable.setBounds(0, 0, dstWidth, dstHeight);
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static class URLDrawable extends BitmapDrawable {
        // the drawable that you need to set, you could set the initial drawing
        // with the loading image if you need to
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }

        //required not to overlap text in text view
        @Override
        public int getIntrinsicWidth() {
            return drawable.getIntrinsicWidth();
        }

        //required not to overlap text in text view
        @Override
        public int getIntrinsicHeight() {
            return drawable.getIntrinsicHeight();
        }
    }
}
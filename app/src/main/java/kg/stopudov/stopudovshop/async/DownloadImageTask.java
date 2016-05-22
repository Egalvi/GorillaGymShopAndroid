package kg.stopudov.stopudovshop.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Bitmap result = null;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            result = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    protected void onPostExecute(Bitmap result) {
        if(result != null) {
            bmImage.setImageBitmap(result);
        } else {
            setDefaultImage(bmImage);
        }
    }

    private void setDefaultImage(ImageView bmImage) {

    }
}
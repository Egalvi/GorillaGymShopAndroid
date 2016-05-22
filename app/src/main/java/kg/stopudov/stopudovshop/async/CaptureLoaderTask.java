package kg.stopudov.stopudovshop.async;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;

import kg.gorillagym.shop.cart.GorillaGymCartService;
import kg.stopudov.stopudovshop.ContactDetails;
import kg.stopudov.stopudovshop.R;
import ru.egalvi.shop.Capture;

public class CaptureLoaderTask extends AsyncTask<Void, Void, Void> {
    Capture capture;
    ContactDetails contactDetailsActivity;

        public CaptureLoaderTask(ContactDetails contactDetailsActivity) {
            this.contactDetailsActivity = contactDetailsActivity;
        }

        @Override
        protected Void doInBackground(Void... params) {
            capture = new GorillaGymCartService().getCapture();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        contactDetailsActivity.findViewById(R.id.capture_image).setVisibility(View.GONE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ImageView image = (ImageView) contactDetailsActivity.findViewById(R.id.capture_image);
        image.setVisibility(View.VISIBLE);
        int width = contactDetailsActivity.findViewById(R.id.edit_verification_code).getWidth();
        BitmapDrawable drawable = getImageScaledToWidth(width);
        image.setImageDrawable(drawable);
        contactDetailsActivity.setToken(capture.getToken());
    }

    @NonNull
    private BitmapDrawable getImageScaledToWidth(int width) {
        Drawable src = Drawable.createFromStream(new ByteArrayInputStream(capture.getImagedata()), "src");
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) src).getBitmap(), width,
                Double.valueOf((double) width / src.getIntrinsicWidth() * src.getIntrinsicHeight()).intValue(), false);
        return new BitmapDrawable(contactDetailsActivity.getResources(), scaledBitmap);
    }
}

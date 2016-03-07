package kg.gorillagym.gorillagymshop.async;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;

import kg.gorillagym.gorillagymshop.ContactDetails;
import kg.gorillagym.gorillagymshop.R;
import kg.gorillagym.shop.cart.GorillaGymCartService;
import ru.egalvi.shop.Capture;

public class CaptureLoaderTask extends AsyncTask<Void, Void, Void> {
    Capture capture;
    ContactDetails cartActivity;

    public CaptureLoaderTask(ContactDetails cartActivity) {
        this.cartActivity = cartActivity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        capture = new GorillaGymCartService().getCapture();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        cartActivity.findViewById(R.id.capture_image).setVisibility(View.GONE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ImageView image = (ImageView) cartActivity.findViewById(R.id.capture_image);
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(Drawable.createFromStream(new ByteArrayInputStream(capture.getImagedata()), ""));
        cartActivity.setToken(capture.getToken());
    }
}

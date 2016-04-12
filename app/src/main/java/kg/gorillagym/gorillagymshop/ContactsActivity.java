package kg.gorillagym.gorillagymshop;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import kg.gorillagym.gorillagymshop.util.AppCompatActivityWithBackButton;

public class ContactsActivity extends AppCompatActivityWithBackButton {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ImageView map = (ImageView) findViewById(R.id.mapImage);
        map.setImageDrawable(getImageScaledToWidth(getResources().getDrawable(R.drawable.map), getWindowManager().getDefaultDisplay().getWidth()));
    }

    //TODO move to Utils some day
    private BitmapDrawable getImageScaledToWidth(Drawable src, int width) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) src).getBitmap(), width,
                Double.valueOf((double) width / src.getIntrinsicWidth() * src.getIntrinsicHeight()).intValue(), false);
        return new BitmapDrawable(getResources(), scaledBitmap);
    }

}

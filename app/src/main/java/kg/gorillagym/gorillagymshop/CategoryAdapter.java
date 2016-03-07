package kg.gorillagym.gorillagymshop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.List;

import ru.egalvi.shop.gorillagym.model.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Activity activity;

    public CategoryAdapter(Activity context, int resource, List<Category> objects) {
        super(context, resource, objects);
        activity = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_list_item, parent, false);
        }
        TextView text = (TextView) convertView.findViewById(R.id.category_title);
        ImageView image = (ImageView) convertView.findViewById(R.id.category_image);
        final Category category = getItem(position);
        int width = Double.valueOf(activity.getWindowManager().getDefaultDisplay().getWidth() * 0.35).intValue();
        BitmapDrawable scaledToWidth = getImageScaledToWidth(Drawable.createFromStream(new ByteArrayInputStream(category.getImageData()), "src"), width);
        image.setImageDrawable(scaledToWidth);
        text.setText(category.getName());
        text.setVisibility(View.GONE);
        return convertView;
    }

    //TODO move to Utils some day
    private BitmapDrawable getImageScaledToWidth(Drawable src, int width) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) src).getBitmap(), width,
                Double.valueOf((double) width / src.getIntrinsicWidth() * src.getIntrinsicHeight()).intValue(), false);
        return new BitmapDrawable(activity.getResources(), scaledBitmap);
    }
}

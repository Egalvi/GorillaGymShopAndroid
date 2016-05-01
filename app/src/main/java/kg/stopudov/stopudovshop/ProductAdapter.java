package kg.stopudov.stopudovshop;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Activity activity;

    private Category category;

    public ProductAdapter(Activity context, int resource, List<Product> objects, Category category) {
        super(context, resource, objects);
        activity = context;
        this.category = category;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_list_item, parent, false);
        }
        TextView text = (TextView) convertView.findViewById(R.id.product_title);
        final Product product = getItem(position);
        text.setText(product.getName());
        byte[] imageData = product.getImageData();
        ImageView image = (ImageView) convertView.findViewById(R.id.product_image);
        if (imageData != null && imageData.length > 0) {
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(), BitmapFactory.decodeByteArray(imageData, 0, imageData.length));
            roundedBitmapDrawable.setCircular(true);
            image.setImageDrawable(roundedBitmapDrawable);
        } else {
            image.setImageDrawable(null);
        }
        TextView price = (TextView) convertView.findViewById(R.id.product_price);
        price.setText(String.valueOf(product.getPrice()) + " " + activity.getString(R.string.currency));
        return convertView;
    }
}

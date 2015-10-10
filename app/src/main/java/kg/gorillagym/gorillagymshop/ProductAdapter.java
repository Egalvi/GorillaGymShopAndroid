package kg.gorillagym.gorillagymshop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import kg.gorillagym.gorillagymshop.navigation.Navigator;
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
        Button text = (Button) convertView.findViewById(R.id.product_title);
        ImageView image = (ImageView) convertView.findViewById(R.id.product_image);
        final Product product = getItem(position);
        text.setText(product.getName());
        image.setImageDrawable(activity.getDrawable(R.drawable.tst));
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.goToProductDetails(activity, category, product);
            }
        });
        return convertView;
    }
}

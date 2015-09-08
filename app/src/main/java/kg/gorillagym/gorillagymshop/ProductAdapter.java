package kg.gorillagym.gorillagymshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import ru.egalvi.shop.gorillagym.model.Product;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Activity activity;

    public ProductAdapter(Activity context, int resource, List<Product> objects) {
        super(context, resource, objects);
        activity = context;
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
                Intent intent = new Intent(activity, ProductDetails.class);
                intent.putExtra("PRODUCT", product);
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
}

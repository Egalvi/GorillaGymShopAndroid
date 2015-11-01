package kg.gorillagym.gorillagymshop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import kg.gorillagym.gorillagymshop.navigation.Navigator;
import ru.egalvi.shop.CartItem;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

//TODO change it
public class CartAdapter extends ArrayAdapter<CartItem> {
    private Activity activity;

    private Map<CartItem, Integer> productsInCart;

    public CartAdapter(Activity context, int resource, Map<CartItem, Integer> objects) {
        super(context, resource, new ArrayList<>(objects.keySet()));
        activity = context;
        productsInCart = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cart_list_item, parent, false);
        }
        Button text = (Button) convertView.findViewById(R.id.product_title);
        ImageView image = (ImageView) convertView.findViewById(R.id.product_image);
        final Product product = (Product) getItem(position); //TODO possibly unsafe cast
        text.setText(product.getName());
        image.setImageDrawable(activity.getResources().getDrawable(R.drawable.tst));
        TextView quantity = (TextView) convertView.findViewById(R.id.cart_quantity);
        Integer itemQuantity = productsInCart.get(product);
        quantity.setText(String.valueOf(itemQuantity));
        TextView pricePerItem = (TextView) convertView.findViewById(R.id.cart_price_per_item);
        pricePerItem.setText(String.valueOf(product.getPrice()));
        TextView price = (TextView) convertView.findViewById(R.id.cart_item_sum);
        price.setText(String.valueOf(product.getPrice() * itemQuantity) + " " + activity.getString(R.string.currency));
        return convertView;
    }
}

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import kg.stopudov.stopudovshop.R;
import kg.stopudov.stopudovshop.cart.CartHolder;
import kg.stopudov.stopudovshop.navigation.Navigator;
import ru.egalvi.shop.CartItem;
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
        TextView text = (TextView) convertView.findViewById(R.id.product_title);
        final Product product = (Product) getItem(position); //TODO possibly unsafe cast
        text.setText(product.getName());
        View.OnClickListener goToProductDetailsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.goToProductDetails(activity, null, product);
            }
        };
        text.setOnClickListener(goToProductDetailsClickListener);
        ImageView image = (ImageView) convertView.findViewById(R.id.product_image);
        image.setOnClickListener(goToProductDetailsClickListener);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(), BitmapFactory.decodeByteArray(product.getImageData(), 0, product.getImageData().length));
        roundedBitmapDrawable.setCircular(true);
        image.setImageDrawable(roundedBitmapDrawable);
//        MyNumberField quantity = (MyNumberField) convertView.findViewById(R.id.cart_quantity);
        Integer itemQuantity = productsInCart.get(product);
//        quantity.setValue(itemQuantity);
        TextView pricePerItem = (TextView) convertView.findViewById(R.id.cart_price_per_item);
        pricePerItem.setText(String.valueOf(product.getPrice()));
        final TextView price = (TextView) convertView.findViewById(R.id.cart_item_sum);
        price.setText(String.valueOf(product.getPrice() * itemQuantity) + " " + activity.getString(R.string.currency));

        final EditText value = getValueEdit(convertView);
        Button plusBtn = getPlusButton(convertView);
        Button minusBtn = getMinusButton(convertView);

        value.getText().clear();
        value.getText().append(itemQuantity.toString());
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer quantity = Integer.valueOf(value.getText().toString());
                value.getText().clear();
                value.getText().append(String.valueOf(quantity + 1));
                CartHolder.getCart().add(product, 1);
                ((CartActivity) activity).updateTotalPrice();
                price.setText(String.valueOf(product.getPrice() * productsInCart.get(product)) + " " + activity.getString(R.string.currency));
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer quantity = Integer.valueOf(value.getText().toString());
                value.getText().clear();
                int newQuantity = quantity - 1;
                value.getText().append(String.valueOf(newQuantity));
                CartHolder.getCart().remove(product, 1);
                if (newQuantity > 0) {
                    ((CartActivity) activity).updateTotalPrice();
                    price.setText(String.valueOf(product.getPrice() * productsInCart.get(product)) + " " + activity.getString(R.string.currency));
                } else {
                    ((CartActivity) activity).updateCartView();
                }
            }
        });

        return convertView;
    }

    private Button getMinusButton(View view) {
        return (Button) view.findViewById(R.id.minus_button);
    }

    private Button getPlusButton(View view) {
        return (Button) view.findViewById(R.id.plus_button);
    }

    private EditText getValueEdit(View view) {
        return (EditText) view.findViewById(R.id.int_value);
    }
}

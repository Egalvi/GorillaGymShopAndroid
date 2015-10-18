package kg.gorillagym.gorillagymshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import kg.gorillagym.gorillagymshop.cart.CartHolder;
import kg.gorillagym.gorillagymshop.navigation.Navigator;
import ru.egalvi.shop.CartItem;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;
import ru.egalvi.shop.gorillagym.service.ProductService;
import ru.egalvi.shop.service.impl.TestProductService;

public class CartActivity extends AppCompatActivity {


    //TODO check
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        TextView cartMessage = (TextView) findViewById(R.id.cartMessage);
        Button checkoutButton = (Button) findViewById(R.id.checkout_button);
        if (CartHolder.getCart().getOrder().isEmpty()) {
            checkoutButton.setVisibility(View.INVISIBLE);
            cartMessage.setVisibility(View.VISIBLE);
            cartMessage.setText("Cart is empty");//TODO i18n
        } else {
            checkoutButton.setVisibility(View.VISIBLE);
            cartMessage.setVisibility(View.INVISIBLE);
            final ListView lv = (ListView) findViewById(R.id.addedProductView);
            ArrayAdapter<CartItem> arrayAdapter = new CartAdapter(this,
                    R.layout.product_list_item,
                    CartHolder.getCart().getOrder());
            lv.setAdapter(arrayAdapter);
            TextView price = (TextView) findViewById(R.id.cart_total_price);
            price.setText(String.valueOf(CartHolder.getCart().getTotalPrice()) + " " + getString(R.string.currency));
            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigator.goToContactDetails(CartActivity.this);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_categories:
                Navigator.goToCategories(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

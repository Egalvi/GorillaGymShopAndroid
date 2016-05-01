package kg.stopudov.stopudovshop;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import kg.stopudov.stopudovshop.R;
import kg.stopudov.stopudovshop.async.CheckProductsAvailableTask;
import kg.stopudov.stopudovshop.cart.CartHolder;
import kg.stopudov.stopudovshop.navigation.Navigator;
import kg.stopudov.stopudovshop.util.AppCompatActivityWithBackButton;
import ru.egalvi.shop.CartItem;
import ru.egalvi.shop.gorillagym.model.Product;

public class CartActivity extends AppCompatActivityWithBackButton {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        updateCartView();
    }

    public void updateCartView() {
        TextView cartMessage = (TextView) findViewById(R.id.cartMessage);
        Button checkoutButton = (Button) findViewById(R.id.checkout_button);
        Button clearCartButton = (Button) findViewById(R.id.clear_cart_button);
        LinearLayout totalHolder = (LinearLayout) findViewById(R.id.layout_total_holder);
        final ListView lv = (ListView) findViewById(R.id.addedProductView);
        if (CartHolder.getCart().getOrder().isEmpty()) {
            checkoutButton.setVisibility(View.GONE);
            clearCartButton.setVisibility(View.GONE);
            totalHolder.setVisibility(View.GONE);
            cartMessage.setVisibility(View.VISIBLE);
            cartMessage.setHeight(50);
            cartMessage.setText(getString(R.string.cart_empty));
            lv.setAdapter(null);
        } else {
            checkoutButton.setVisibility(View.VISIBLE);
            clearCartButton.setVisibility(View.VISIBLE);
            totalHolder.setVisibility(View.VISIBLE);
            cartMessage.setVisibility(View.GONE);
            cartMessage.setHeight(0);
            ArrayAdapter<CartItem> arrayAdapter = new CartAdapter(this,
                    R.layout.product_list_item,
                    CartHolder.getCart().getOrder());
            lv.setAdapter(arrayAdapter);
            updateTotalPrice();

            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CheckProductsAvailableTask(CartActivity.this, CartHolder.getCart()).execute();
                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product itemAtPosition = (Product) parent.getItemAtPosition(position);
                    Navigator.goToProductDetails(CartActivity.this, null, itemAtPosition);
                }
            });

            clearCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(CartActivity.this)
                            .setTitle(getString(R.string.clear_cart))
                            .setMessage(getString(R.string.really_clear_cart))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    CartHolder.getCart().clear();
                                    updateCartView();
                                }
                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

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

    public void updateTotalPrice() {
        TextView price = (TextView) findViewById(R.id.cart_total_price);
        price.setText(String.valueOf(CartHolder.getCart().getTotalPrice()) + " " + getString(R.string.currency));
    }
}

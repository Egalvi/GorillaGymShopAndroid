package kg.gorillagym.gorillagymshop;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import kg.gorillagym.gorillagymshop.async.CategoryLoaderTask;
import kg.gorillagym.gorillagymshop.cache.CacheHolder;
import kg.gorillagym.gorillagymshop.cart.CartHolder;
import kg.gorillagym.gorillagymshop.navigation.Navigator;
import ru.egalvi.shop.gorillagym.model.Category;

public class MainActivity extends AppCompatActivity {

    public static final String CART_CACHE_NAME = "cart";
    public static final String PRODUCTS_CACHE_NAME = "products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CacheHolder.init(getApplicationContext());
        CartHolder.init(CacheHolder.getCache().getCart(CART_CACHE_NAME));
        setContentView(R.layout.activity_main);
        if (!isOnline()) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.no_internet_access_title))
                    .setMessage(getString(R.string.no_internet_access_message))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            Button cartButton = (Button) findViewById(R.id.cartButton);
            cartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigator.goToCart(MainActivity.this);
                }
            });
            Button contactsButton = (Button) findViewById(R.id.contactsButton);
            contactsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigator.goToContacts(MainActivity.this);
                }
            });
            final GridView lv = (GridView) findViewById(R.id.listView);
            new CategoryLoaderTask(MainActivity.this, lv).execute();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Category itemAtPosition = (Category) parent.getItemAtPosition(position);
                    Navigator.goToProducts(MainActivity.this, itemAtPosition);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cart:
                Navigator.goToCart(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

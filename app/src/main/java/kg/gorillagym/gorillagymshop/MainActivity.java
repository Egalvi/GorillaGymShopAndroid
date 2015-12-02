package kg.gorillagym.gorillagymshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import kg.gorillagym.gorillagymshop.async.CategoryLoaderTask;
import kg.gorillagym.gorillagymshop.cache.CacheHolder;
import kg.gorillagym.gorillagymshop.cart.CartHolder;
import kg.gorillagym.gorillagymshop.navigation.Navigator;

public class MainActivity extends AppCompatActivity {

    public static final String CART_CACHE_NAME = "cart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CacheHolder.init(getApplicationContext());
        CartHolder.init(CacheHolder.getCache().getCart(CART_CACHE_NAME));
        setContentView(R.layout.activity_main);
        Button cartButton = (Button) findViewById(R.id.cartButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.goToCart(MainActivity.this);
            }
        });
        final ListView lv = (ListView) findViewById(R.id.listView);
        new CategoryLoaderTask(MainActivity.this, lv).execute();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheHolder.getCache().putCart(CART_CACHE_NAME, CartHolder.getCart());
    }
}

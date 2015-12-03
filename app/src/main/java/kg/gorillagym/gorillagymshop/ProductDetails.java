package kg.gorillagym.gorillagymshop;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import kg.gorillagym.gorillagymshop.async.DownloadImageTask;
import kg.gorillagym.gorillagymshop.async.URLImageParser;
import kg.gorillagym.gorillagymshop.cache.CacheHolder;
import kg.gorillagym.gorillagymshop.cart.CartHolder;
import kg.gorillagym.gorillagymshop.navigation.Navigator;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

public class ProductDetails extends AppCompatActivity {
    private Menu menu;

    private Category category;
    private Product product;

    private static TextProcessor processor = BBProcessorFactory.getInstance().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        final TextView name = (TextView) findViewById(R.id.product_name);
        final TextView description = (TextView) findViewById(R.id.product_description);
        final TextView price = (TextView) findViewById(R.id.product_price);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            product = (Product) extras.getSerializable("PRODUCT");
            category = (Category) extras.getSerializable("CATEGORY");
            URLImageParser imageGetter = new URLImageParser(description, this);
            Spanned spanned = Html.fromHtml(processor.process(product.getText()).replaceAll("\\[center\\]", "").replaceAll("\\[/center\\]", ""), imageGetter, null);
            setTitle(product.getName());
            name.setText(product.getName());
            ImageView image = (ImageView) findViewById(R.id.product_image);
            image.setImageBitmap(BitmapFactory.decodeByteArray(product.getImageData(), 0, product.getImageData().length));
            description.setText(spanned);
            price.setText(product.getPrice() + " " + getString(R.string.currency));
        }

        Button addToCart = (Button) findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartHolder.getCart().add(product, 1);
                String text = String.format(getString(R.string.added_to_cart), product.getName());
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(ProductDetails.this, text, duration);
                toast.show();
                CacheHolder.getCache().putCart(MainActivity.CART_CACHE_NAME, CartHolder.getCart());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        this.menu = menu;
        if (category == null) {
            menu.removeItem(R.id.action_products);
        }
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
            case R.id.action_categories:
                Navigator.goToCategories(this);
                return true;
            case R.id.action_products:
                Navigator.goToProducts(this, category);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

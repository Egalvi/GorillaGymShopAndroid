package kg.stopudov.stopudovshop;

import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import kg.stopudov.stopudovshop.async.ProductDetailsLoaderTask;
import kg.stopudov.stopudovshop.async.URLImageParser;
import kg.stopudov.stopudovshop.navigation.Navigator;
import kg.stopudov.stopudovshop.util.AppCompatActivityWithBackButton;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

public class ProductDetails extends AppCompatActivityWithBackButton {
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
        final TextView oldPrice = (TextView) findViewById(R.id.product_old_price);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            product = (Product) extras.getSerializable("PRODUCT");
            category = (Category) extras.getSerializable("CATEGORY");
            URLImageParser imageGetter = new URLImageParser(description, this);
            Spanned spanned = Html.fromHtml(processor.process(product.getText()).replaceAll("\\[center\\]", "").replaceAll("\\[/center\\]", ""), imageGetter, null);
            setTitle(product.getName());
            name.setText(product.getName());
            byte[] imageData = product.getImageData();
            ImageView image = (ImageView) findViewById(R.id.product_image);
            if (imageData != null && imageData.length > 0) {
                image.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.length));
            } else {
                image.setImageDrawable(null);
            }
            description.setText(spanned);
            price.setText(product.getPrice() + " " + getString(R.string.currency));
            if (product.getOldprice() > 0) {
                oldPrice.setVisibility(View.VISIBLE);
                oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                oldPrice.setText(product.getOldprice() + " " + getString(R.string.currency));
            }
        }
        new ProductDetailsLoaderTask(product.getId(), this).execute();
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

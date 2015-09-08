package kg.gorillagym.gorillagymshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import ru.egalvi.shop.gorillagym.model.Product;

public class ProductDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        final TextView name = (TextView) findViewById(R.id.product_name);
        final ImageView image = (ImageView) findViewById(R.id.product_image);
        final TextView description = (TextView) findViewById(R.id.product_description);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Product product = (Product) extras.getSerializable("PRODUCT");
            name.setText(product.getName());
            //TODO to set image
            image.setImageDrawable(getDrawable(R.drawable.tst));
            description.setText(product.getDescription());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
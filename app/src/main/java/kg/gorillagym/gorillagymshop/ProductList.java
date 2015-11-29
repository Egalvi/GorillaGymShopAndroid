package kg.gorillagym.gorillagymshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import kg.gorillagym.gorillagymshop.navigation.Navigator;
import kg.gorillagym.shop.content.GorillaGymProductService;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;
import ru.egalvi.shop.gorillagym.service.ProductService;
//import ru.egalvi.shop.service.impl.TestProductService;

public class ProductList extends AppCompatActivity {

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        final ListView lv = (ListView) findViewById(R.id.productView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = (Category) extras.getSerializable("CATEGORY");
            ProductService productService = new GorillaGymProductService();
            List<Product> products = productService.getForCategory(category);
            ArrayAdapter<Product> arrayAdapter = new ProductAdapter(this,
                    R.layout.product_list_item,
                    products, category);

            lv.setAdapter(arrayAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

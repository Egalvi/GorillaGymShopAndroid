package kg.stopudov.stopudovshop;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import kg.stopudov.stopudovshop.async.ProductLoaderTask;
import kg.stopudov.stopudovshop.navigation.Navigator;
import kg.stopudov.stopudovshop.util.AppCompatActivityWithBackButton;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

public class ProductList extends AppCompatActivityWithBackButton {

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
        final ListView lv = (ListView) findViewById(R.id.productView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = (Category) extras.getSerializable("CATEGORY");
            setTitle(category.getName());
            new ProductLoaderTask(category, ProductList.this, lv).execute();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product itemAtPosition = (Product) parent.getItemAtPosition(position);
                    Navigator.goToProductDetails(ProductList.this, category, itemAtPosition);
                }
            });
        }
        Button cartButton = (Button) findViewById(R.id.cartButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.goToCart(ProductList.this);
            }
        });
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

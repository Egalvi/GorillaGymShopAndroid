package kg.gorillagym.gorillagymshop.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import kg.gorillagym.gorillagymshop.ProductAdapter;
import kg.gorillagym.gorillagymshop.R;
import kg.gorillagym.shop.content.GorillaGymProductService;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;
import ru.egalvi.shop.gorillagym.service.ProductService;

public class ProductLoaderTask extends AsyncTask<Void, Void, Void> {
    List<Product> products;
    Category category;
    Activity productActivity;
    ListView productListView;

    public ProductLoaderTask(Category category, Activity productActivity, ListView productListView) {
        this.category = category;
        this.productActivity = productActivity;
        this.productListView = productListView;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProductService productService = new GorillaGymProductService();
        try {
            products = productService.getForCategory(category);
        } catch (Exception e) {
            //TODO java.net.UnknownHostException: Unable to resolve host if internet is OFF
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ArrayAdapter<Product> arrayAdapter = new ProductAdapter(productActivity,
                R.layout.product_list_item, products, category);
        productListView.setAdapter(arrayAdapter);
//        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Application app = getApplication();
////                view.setBackgroundColor(android.R.color.background_light);
////                app.setCurrentItem(app.getSectionList().get(currentSelectedSection).getItems().get(position));
//                Intent productList = new Intent(productActivity, ProductList.class);
//                productActivity.startActivity(productList);
//            }
//        });
    }
}

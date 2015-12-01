package kg.gorillagym.gorillagymshop.async;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.View;
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
        productActivity.findViewById(R.id.loadingIndicator).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        productActivity.findViewById(R.id.loadingIndicator).setVisibility(View.GONE);
        if (products == null || products.isEmpty()) {
            new AlertDialog.Builder(productActivity)
                    .setTitle(productActivity.getString(R.string.no_products))
                    .setMessage(productActivity.getString(R.string.no_products_for_category))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            ArrayAdapter<Product> arrayAdapter = new ProductAdapter(productActivity,
                    R.layout.product_list_item, products, category);
            productListView.setAdapter(arrayAdapter);
        }
    }
}

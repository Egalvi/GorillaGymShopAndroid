package kg.gorillagym.gorillagymshop.async;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import kg.gorillagym.gorillagymshop.MainActivity;
import kg.gorillagym.gorillagymshop.ProductAdapter;
import kg.gorillagym.gorillagymshop.R;
import kg.gorillagym.gorillagymshop.cache.CacheHolder;
import kg.gorillagym.gorillagymshop.cache.impl.CacheImpl;
import kg.gorillagym.shop.content.cachedpicture.Cache;
import kg.gorillagym.shop.content.cachedpicture.GorillaGymProductService;
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
        CacheImpl.ProductCacheHolder productsCache = CacheHolder.getCache().getProducts(MainActivity.PRODUCTS_CACHE_NAME);
        if (productsCache != null && productsCache.get(category) != null && !productsCache.get(category).isEmpty()) {
            products = productsCache.get(category);
        } else {
            ProductService productService = new GorillaGymProductService(new Cache<byte[]>() {
                @Override
                public void put(String key, byte[] value) {
                    String fixedKey = fixKey(key);
                    CacheHolder.getCache().putImage(fixedKey, value);
                }

                @Nullable
                private String fixKey(String key) {
                    return (key == null) ? null : key.replace("http","").replace("www","").replace("/","").replace(".", "").replace(":","").toLowerCase();
                }

                @Override
                public byte[] get(String key) {
                    String fixedKey = fixKey(key);
                    return CacheHolder.getCache().getImage(fixedKey);
                }
            });
            try {
                products = productService.getForCategory(category);
            } catch (Exception e) {
                //TODO java.net.UnknownHostException: Unable to resolve host if internet is OFF
            }
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
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            CacheImpl.ProductCacheHolder productsCache = CacheHolder.getCache().getProducts(MainActivity.PRODUCTS_CACHE_NAME);
            productsCache = productsCache == null ? new CacheImpl.ProductCacheHolder() : productsCache;
            productsCache.add(category, products);
            ArrayAdapter<Product> arrayAdapter = new ProductAdapter(productActivity,
                    R.layout.product_list_item, products, category);
            productListView.setAdapter(arrayAdapter);
        }
    }
}

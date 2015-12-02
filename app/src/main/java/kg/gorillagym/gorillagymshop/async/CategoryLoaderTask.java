package kg.gorillagym.gorillagymshop.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kg.gorillagym.gorillagymshop.CategoryAdapter;
import kg.gorillagym.gorillagymshop.MainActivity;
import kg.gorillagym.gorillagymshop.R;
import kg.gorillagym.gorillagymshop.cache.CacheHolder;
import kg.gorillagym.gorillagymshop.cache.impl.CacheImpl;
import kg.gorillagym.shop.content.GorillaGymCategoryService;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.service.CategoryService;

public class CategoryLoaderTask extends AsyncTask<Void, Void, Void> {
    Collection<Category> categories;
    Activity categoryActivity;
    ListView categoryListView;

    public CategoryLoaderTask(Activity categoryActivity, ListView categoryListView) {
        this.categoryActivity = categoryActivity;
        this.categoryListView = categoryListView;
    }

    @Override
    protected Void doInBackground(Void... params) {
        CacheImpl.ProductCacheHolder products = CacheHolder.getCache().getProducts(MainActivity.PRODUCTS_CACHE_NAME);
        if (products != null && !products.getCategories().isEmpty()) {
            categories = products.getCategories();
        } else {
            CategoryService carrierService = new GorillaGymCategoryService();
            try {
                categories = carrierService.getAll();
            } catch (Exception e) {
                //TODO java.net.UnknownHostException: Unable to resolve host if internet is OFF
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        categoryActivity.findViewById(R.id.loadingIndicator).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        CacheImpl.ProductCacheHolder productsCache = CacheHolder.getCache().getProducts(MainActivity.PRODUCTS_CACHE_NAME);
        productsCache = productsCache == null ? new CacheImpl.ProductCacheHolder() : productsCache;
        for (Category category : categories) {
            if (!productsCache.getCategories().contains(category)) {
                productsCache.add(category, null);
            }
        }
        CacheHolder.getCache().putProducts(MainActivity.PRODUCTS_CACHE_NAME, productsCache);
        categoryActivity.findViewById(R.id.loadingIndicator).setVisibility(View.GONE);
        ArrayAdapter<Category> arrayAdapter = new CategoryAdapter(categoryActivity,
                R.layout.category_list_item, new ArrayList<>(categories));
        categoryListView.setAdapter(arrayAdapter);
    }
}

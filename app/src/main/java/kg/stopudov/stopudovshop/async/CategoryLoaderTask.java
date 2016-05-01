package kg.stopudov.stopudovshop.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kg.stopudov.stopudovshop.CategoryAdapter;
import kg.stopudov.stopudovshop.MainActivity;
import kg.stopudov.stopudovshop.R;
import kg.stopudov.stopudovshop.cache.CacheHolder;
import kg.stopudov.stopudovshop.cache.impl.CacheImpl;
import kg.gorillagym.shop.content.cachedpicture.Cache;
import kg.gorillagym.shop.content.cachedpicture.GorillaGymCategoryService;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.CategorySortComparator;
import ru.egalvi.shop.gorillagym.service.CategoryService;

public class CategoryLoaderTask extends AsyncTask<Void, Void, Void> {
    List<Category> categories;
    Activity categoryActivity;
    AbsListView categoryListView;

    public CategoryLoaderTask(Activity categoryActivity, AbsListView categoryListView) {
        this.categoryActivity = categoryActivity;
        this.categoryListView = categoryListView;
    }

    @Override
    protected Void doInBackground(Void... params) {
        CacheImpl.ProductCacheHolder products = CacheHolder.getCache().getProducts(MainActivity.PRODUCTS_CACHE_NAME);
        if (products != null && !products.getCategories().isEmpty()) {
            categories = new ArrayList<>(products.getCategories());
            Collections.sort(categories, new CategorySortComparator());
        } else {
            CategoryService carrierService = new GorillaGymCategoryService(new Cache<byte[]>() {
                @Override
                public void put(String key, byte[] value) {
                    String fixedKey = fixKey(key);
                    CacheHolder.getCache().putImage(fixedKey, value);
                }

                @Nullable
                private String fixKey(String key) {
                    return (key == null) ? null : key.replace("http","").replace("www","").replace("/","").replace(".","").replace(":","").toLowerCase();
                }

                @Override
                public byte[] get(String key) {
                    String fixedKey = fixKey(key);
                    return CacheHolder.getCache().getImage(fixedKey);
                }
            });
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
        categoryActivity.findViewById(R.id.chooseCategory).setVisibility(View.GONE);
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
        categoryActivity.findViewById(R.id.chooseCategory).setVisibility(View.VISIBLE);
        ArrayAdapter<Category> arrayAdapter = new CategoryAdapter(categoryActivity,
                R.layout.category_list_item, new ArrayList<>(categories));
        categoryListView.setAdapter(arrayAdapter);
    }
}

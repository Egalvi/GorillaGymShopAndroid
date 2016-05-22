package kg.stopudov.stopudovshop.cache;

import android.content.Context;

import kg.stopudov.stopudovshop.MainActivity;
import kg.stopudov.stopudovshop.cache.impl.CacheImpl;

//TODO get rid of this some time
public class CacheHolder {
    public static Cache cache;

    public static void init(Context context) {
        cache = cache == null ? new CacheImpl(context) : cache;
        if (cache.getProducts(MainActivity.PRODUCTS_CACHE_NAME) == null) {
            cache.putProducts(MainActivity.PRODUCTS_CACHE_NAME, new CacheImpl.ProductCacheHolder());
        }
    }

    public static Cache getCache() {
        return cache;
    }
}

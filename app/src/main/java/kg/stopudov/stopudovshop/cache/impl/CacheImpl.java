package kg.stopudov.stopudovshop.cache.impl;

import android.content.Context;

import com.vincentbrison.openlibraries.android.dualcache.lib.DualCache;
import com.vincentbrison.openlibraries.android.dualcache.lib.DualCacheBuilder;
import com.vincentbrison.openlibraries.android.dualcache.lib.DualCacheContextUtils;
import com.vincentbrison.openlibraries.android.dualcache.lib.SizeOf;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kg.stopudov.stopudovshop.cache.Cache;
import ru.egalvi.shop.Cart;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

//TODO move this impl to another module
public class CacheImpl implements Cache {

    private static final String PRODUCT_CACHE_NAME = "product_cache";
    private static final String CART_CACHE_NAME = "cart_cache";
    private static final String PICTURE_CACHE_NAME = "picture_cache";
    private static final int APP_VERSION = 0;
    private static final int RAM_MAX_SIZE = 128;
    private static final int DISK_MAX_SIZE = 512;

    private DualCache<ProductCacheHolder> productCache;

    private DualCache<Cart> cartCache;

    private DualCache<byte[]> pictureCache;

    public CacheImpl(Context context) {
        DualCacheContextUtils.setContext(context);
        productCache =
                new DualCacheBuilder<>(PRODUCT_CACHE_NAME, APP_VERSION, ProductCacheHolder.class)
                        .useReferenceInRam(RAM_MAX_SIZE, new SizeOfProductCacheHolder())
//                        .noRam()
                        .useDefaultSerializerInDisk(DISK_MAX_SIZE, true);
        cartCache =
                new DualCacheBuilder<>(CART_CACHE_NAME, APP_VERSION, Cart.class)
                        .useReferenceInRam(RAM_MAX_SIZE, new SizeOfCart())
//                        .noRam()
                        .useDefaultSerializerInDisk(DISK_MAX_SIZE, true);

        pictureCache =
                new DualCacheBuilder<>(PICTURE_CACHE_NAME, APP_VERSION, byte[].class)
                        .useReferenceInRam(RAM_MAX_SIZE, new SizeOfByteArray())
//                        .noRam()
                        .useDefaultSerializerInDisk(DISK_MAX_SIZE, true);
    }

    @Override
    public void putProducts(String key, ProductCacheHolder value) {
        productCache.put(key, value);
    }

    @Override
    public ProductCacheHolder getProducts(String key) {
        return productCache.get(key);
    }

    @Override
    public void putCart(String key, Cart value) {
        cartCache.put(key, value);
    }

    @Override
    public Cart getCart(String key) {
        return cartCache.get(key);
    }

    @Override
    public void putImage(String key, byte[] value) {
        pictureCache.put(key, value);
    }

    @Override
    public byte[] getImage(String key) {
        return pictureCache.get(key);
    }

    public static class ProductCacheHolder implements Serializable {
        private Map<Category, List<Product>> categoryToProductsMap = new HashMap<>();
        private Date creationDate;

        private static final long INVALIDATE_OFFSET = 24 * 60 * 60 * 1000; //24 hours in milliseconds

        public ProductCacheHolder() {
            this.creationDate = new Date();
        }

        public void add(Category category, List<Product> products) {
            invalidateCache();
            categoryToProductsMap.put(category, products);
        }

        public List<Product> get(Category category) {
            invalidateCache();
            return categoryToProductsMap.get(category);
        }

        public Set<Category> getCategories() {
            invalidateCache();
            return categoryToProductsMap.keySet();
        }

        private void invalidateCache() {
            if (new Date().getTime() - creationDate.getTime() > INVALIDATE_OFFSET) {
                categoryToProductsMap.clear();
                creationDate = new Date();
            }
        }
    }

    private static class SizeOfProductCacheHolder implements SizeOf<ProductCacheHolder> {
        @Override
        public int sizeOf(ProductCacheHolder object) {
            return object.categoryToProductsMap.size();
        }
    }

    private static class SizeOfCart implements SizeOf<Cart> {
        @Override
        public int sizeOf(Cart object) {
            return object.getOrder().size();
        }
    }

    private static class SizeOfByteArray implements SizeOf<byte[]> {
        @Override
        public int sizeOf(byte[] object) {
            return object.length;
        }
    }
}

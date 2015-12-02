package kg.gorillagym.gorillagymshop.cache;

import kg.gorillagym.gorillagymshop.cache.impl.CacheImpl;
import ru.egalvi.shop.Cart;

public interface Cache {
    void putProducts(String key, CacheImpl.ProductCacheHolder value);

    CacheImpl.ProductCacheHolder getProducts(String key);

    void putCart(String key, Cart value);

    Cart getCart(String key);
}

package kg.stopudov.stopudovshop.cache;

import kg.stopudov.stopudovshop.cache.impl.CacheImpl;
import ru.egalvi.shop.Cart;

public interface Cache {
    void putProducts(String key, CacheImpl.ProductCacheHolder value);

    CacheImpl.ProductCacheHolder getProducts(String key);

    void putCart(String key, Cart value);

    Cart getCart(String key);

    void putImage(String key, byte[] value);

    byte[] getImage(String key);
}

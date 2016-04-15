package kg.gorillagym.gorillagymshop.async;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import java.util.HashMap;
import java.util.Map;

import kg.gorillagym.gorillagymshop.CartActivity;
import kg.gorillagym.gorillagymshop.cache.CacheHolder;
import kg.gorillagym.gorillagymshop.navigation.Navigator;
import kg.gorillagym.shop.content.cachedpicture.Cache;
import kg.gorillagym.shop.content.cachedpicture.GorillaGymProductService;
import ru.egalvi.shop.Cart;
import ru.egalvi.shop.CartItem;
import ru.egalvi.shop.gorillagym.model.Product;
import ru.egalvi.shop.gorillagym.service.ProductService;

public class CheckProductsAvailableTask extends AsyncTask<Void, Void, Void> {
    private CartActivity activity;
    private Cart cart;
    private Map<Product, Integer> maxAvailableProducts = new HashMap<>();
    private ProductService productService;

    public CheckProductsAvailableTask(CartActivity activity, Cart cart) {
        productService = new GorillaGymProductService(new Cache<byte[]>() {
            @Override
            public void put(String key, byte[] value) {
                String fixedKey = fixKey(key);
                CacheHolder.getCache().putImage(fixedKey, value);
            }

            @Nullable
            private String fixKey(String key) {
                return (key == null) ? null : key.replace("http", "").replace("www", "").replace("/", "").replace(".", "").replace(":", "").toLowerCase();
            }

            @Override
            public byte[] get(String key) {
                String fixedKey = fixKey(key);
                return CacheHolder.getCache().getImage(fixedKey);
            }
        });
        this.activity = activity;
        this.cart = cart;
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (Map.Entry<CartItem, Integer> entry : cart.getOrder().entrySet()) {
            try {
                Product product = updateProductInfo((Product) entry.getKey());
                int maxAvailable = product != null ? Integer.parseInt(product.getNumber()) : 0;
                if (maxAvailable < entry.getValue()) {
                    maxAvailableProducts.put(product, maxAvailable);
                }
            } catch (Exception e) {
                //TODO java.net.UnknownHostException: Unable to resolve host if internet is OFF
            }
        }
        return null;
    }

    private Product updateProductInfo(Product key) {
        return productService.getProduct(key.getId());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (maxAvailableProducts.isEmpty()) {
            Navigator.goToContactDetails(activity);
        } else {
            new AlertDialog.Builder(activity)
                    .setTitle("Товар отсутствует")
                    .setMessage("Указанных товаров не хватает в наличии:\n" + constructMessage(maxAvailableProducts))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private String constructMessage(Map<Product, Integer> maxAvailableProducts) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : maxAvailableProducts.entrySet()) {
            sb.append(entry.getKey().getName()).append(" в наличии ").append(entry.getValue()).append(" штук; \n");
        }
        return sb.toString();
    }


}

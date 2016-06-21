package kg.stopudov.stopudovshop.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kg.stopudov.stopudovshop.MainActivity;
import kg.stopudov.stopudovshop.R;
import kg.stopudov.stopudovshop.cache.CacheHolder;
import kg.stopudov.stopudovshop.cart.CartHolder;
import kg.gorillagym.shop.content.cachedpicture.Cache;
import kg.gorillagym.shop.content.cachedpicture.GorillaGymProductService;
import kg.stopudov.stopudovshop.error.NoConnectionErrorDialog;
import ru.egalvi.shop.gorillagym.model.Product;
import ru.egalvi.shop.gorillagym.service.ProductService;

public class ProductDetailsLoaderTask extends AsyncTask<Void, Void, Void> {
    Product product;
    String id;
    Activity productActivity;

    public ProductDetailsLoaderTask(String id, Activity productActivity) {
        this.id = id;
        this.productActivity = productActivity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProductService productService = new GorillaGymProductService(new Cache<byte[]>() {
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
        try {
            product = productService.getProduct(id);
        } catch (Exception e) {
            product = null;
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
        if(product == null){
            NoConnectionErrorDialog.getInstance(productActivity);
            return;
        }
        productActivity.findViewById(R.id.loadingIndicator).setVisibility(View.GONE);
        Button addToCart = (Button) productActivity.findViewById(R.id.add_to_cart);
        if(Integer.parseInt(product.getNumber()) > 0) {
            addToCart.setText("Добавить в корзину");
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartHolder.getCart().add(product, 1);
                    String text = String.format(productActivity.getString(R.string.added_to_cart), product.getName());
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(productActivity, text, duration);
                    toast.show();
                    CacheHolder.getCache().putCart(MainActivity.CART_CACHE_NAME, CartHolder.getCart());
                }
            });
        } else {
            addToCart.setText("Нет в наличии");
        }
    }
}

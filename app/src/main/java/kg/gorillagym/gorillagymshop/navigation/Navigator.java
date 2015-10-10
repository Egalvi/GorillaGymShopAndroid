package kg.gorillagym.gorillagymshop.navigation;

import android.app.Activity;
import android.content.Intent;

import kg.gorillagym.gorillagymshop.MainActivity;
import kg.gorillagym.gorillagymshop.ProductDetails;
import kg.gorillagym.gorillagymshop.ProductList;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

public class Navigator {

    public static void goToProducts(Activity activity, Category category) {
        Intent intent = new Intent(activity, ProductList.class);
        intent.putExtra("CATEGORY", category);
        activity.startActivity(intent);
    }

    public static void goToCart(Activity activity) {

    }

    public static void goToCategories(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public static void goToProductDetails(Activity activity, Category category, Product product) {
        Intent intent = new Intent(activity, ProductDetails.class);
        intent.putExtra("PRODUCT", product);
        intent.putExtra("CATEGORY", category);
        activity.startActivity(intent);
    }
}

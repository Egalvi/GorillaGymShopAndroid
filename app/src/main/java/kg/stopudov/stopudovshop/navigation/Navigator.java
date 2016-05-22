package kg.stopudov.stopudovshop.navigation;

import android.app.Activity;
import android.content.Intent;

import kg.stopudov.stopudovshop.CartActivity;
import kg.stopudov.stopudovshop.ContactDetails;
import kg.stopudov.stopudovshop.ContactsActivity;
import kg.stopudov.stopudovshop.MainActivity;
import kg.stopudov.stopudovshop.ProductDetails;
import kg.stopudov.stopudovshop.ProductList;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

public class Navigator {

    public static void goToProducts(Activity activity, Category category) {
        Intent intent = new Intent(activity, ProductList.class);
        intent.putExtra("CATEGORY", category);
        activity.startActivity(intent);
    }

    public static void goToCart(Activity activity) {
        Intent intent = new Intent(activity, CartActivity.class);
        activity.startActivity(intent);
    }

    public static void goToCategories(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public static void goToContactDetails(Activity activity) {
        Intent intent = new Intent(activity, ContactDetails.class);
        activity.startActivity(intent);
    }

    public static void goToContacts(Activity activity) {
        Intent intent = new Intent(activity, ContactsActivity.class);
        activity.startActivity(intent);
    }

    public static void goToProductDetails(Activity activity, Category category, Product product) {
        Intent intent = new Intent(activity, ProductDetails.class);
        intent.putExtra("PRODUCT", product);
        intent.putExtra("CATEGORY", category);
        activity.startActivity(intent);
    }
}

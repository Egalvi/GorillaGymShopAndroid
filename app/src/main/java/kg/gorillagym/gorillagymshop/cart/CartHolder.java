package kg.gorillagym.gorillagymshop.cart;

import ru.egalvi.shop.Cart;
import ru.egalvi.shop.impl.CartImpl;

public class CartHolder {
    private static Cart cart = new CartImpl();

    public static void init(Cart cart) {
        CartHolder.cart = cart == null ? CartHolder.cart : cart;
    }

    public static Cart getCart() {
        return cart;
    }
}

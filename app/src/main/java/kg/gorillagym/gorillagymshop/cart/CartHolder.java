package kg.gorillagym.gorillagymshop.cart;

import ru.egalvi.shop.Cart;
import ru.egalvi.shop.impl.CartImpl;

public class CartHolder {
    private static final Cart cart = new CartImpl();

    public static Cart getCart(){
        return cart;
    }
}

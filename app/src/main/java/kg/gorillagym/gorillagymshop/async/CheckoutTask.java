package kg.gorillagym.gorillagymshop.async;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.View;

import kg.gorillagym.gorillagymshop.ContactDetails;
import kg.gorillagym.gorillagymshop.R;
import kg.gorillagym.gorillagymshop.cart.CartHolder;
import kg.gorillagym.gorillagymshop.navigation.Navigator;
import kg.gorillagym.shop.cart.GorillaGymCartService;
import ru.egalvi.shop.CheckoutResponse;
import ru.egalvi.shop.ClientData;

public class CheckoutTask extends AsyncTask<Void, Void, Void> {
    CheckoutResponse checkout;
    ContactDetails contactDetailsActivity;
    ClientData clientData;

    AlertDialog.Builder emptyFieldDialog;
    AlertDialog.Builder successfullCheckoutDialog;

    public CheckoutTask(final ContactDetails contactDetailsActivity, ClientData clientData) {
        this.contactDetailsActivity = contactDetailsActivity;
        this.clientData = clientData;
        emptyFieldDialog = new AlertDialog.Builder(contactDetailsActivity)
                .setTitle("Оформление заказа")
                .setMessage(contactDetailsActivity.getString(R.string.please_fill_the_field))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert);
        successfullCheckoutDialog = new AlertDialog.Builder(contactDetailsActivity)
                .setTitle("Оформление заказа")
                .setMessage(contactDetailsActivity.getString(R.string.please_fill_the_field))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Navigator.goToCategories(contactDetailsActivity);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert);
    }

    @Override
    protected Void doInBackground(Void... params) {
        checkout = new GorillaGymCartService().checkout(CartHolder.getCart(), clientData);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        contactDetailsActivity.findViewById(R.id.capture_image).setVisibility(View.GONE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(!"0".equals(checkout.getErrorCode())) {
            emptyFieldDialog.setMessage(checkout.getError()).setIcon(android.R.drawable.ic_dialog_alert).show();
        } else {
            CartHolder.getCart().clear();
            successfullCheckoutDialog.setMessage(checkout.getError()).setIcon(android.R.drawable.ic_dialog_info).show();
        }
    }

}

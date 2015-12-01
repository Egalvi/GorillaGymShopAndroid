package kg.gorillagym.gorillagymshop;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kg.gorillagym.gorillagymshop.cart.CartHolder;
import kg.gorillagym.gorillagymshop.navigation.Navigator;
import ru.egalvi.shop.Cart;
import ru.egalvi.shop.CartService;
import ru.egalvi.shop.ClientData;

public class ContactDetails extends AppCompatActivity {

    public static final String EMAIL_FIELD = "email";
    public static final String NAME_FIELD = "name";
    public static final String ADDRESS_FIELD = "address";
    public static final String PHONE_FIELD = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        final CartService cartService = new CartService() {
            @Override
            public void checkout(Cart cart, ClientData clientData) {

            }
        };

        final EditText emailField = (EditText) findViewById(R.id.edit_email);
        final EditText nameField = (EditText) findViewById(R.id.edit_name);
        final EditText addressField = (EditText) findViewById(R.id.edit_address);
        final EditText phoneField = (EditText) findViewById(R.id.edit_phone);

        SharedPreferences preferences = getPreferences(0);
        emailField.setText(preferences.getString(EMAIL_FIELD, ""));
        nameField.setText(preferences.getString(NAME_FIELD, ""));
        addressField.setText(preferences.getString(ADDRESS_FIELD, ""));
        phoneField.setText(preferences.getString(PHONE_FIELD, ""));

        Button sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailField.getText().toString();
                final String name = nameField.getText().toString();
                final String address = addressField.getText().toString();
                final String phone = phoneField.getText().toString();
                //TODO check fields are not empty
                //TODO check fields are not equal to previously saved
                new AlertDialog.Builder(ContactDetails.this)
                        .setTitle(getString(R.string.save_contact_data))
                        .setMessage(getString(R.string.really_save_contact_data))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences = getPreferences(0);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(EMAIL_FIELD, email);
                                editor.putString(NAME_FIELD, name);
                                editor.putString(ADDRESS_FIELD, address);
                                editor.putString(PHONE_FIELD, phone);
                                editor.apply();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                cartService.checkout(CartHolder.getCart(), new ClientData() {
                });
            }
        });
        Button clearButton = (Button) findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ContactDetails.this)
                        .setTitle(getString(R.string.clear_contact_data))
                        .setMessage(getString(R.string.really_clear_contact_data))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                CartHolder.getCart().clear();
                                emailField.setText("");
                                nameField.setText("");
                                addressField.setText("");
                                phoneField.setText("");
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_categories:
                Navigator.goToCategories(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

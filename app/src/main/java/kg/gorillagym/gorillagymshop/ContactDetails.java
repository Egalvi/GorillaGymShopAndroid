package kg.gorillagym.gorillagymshop;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
        emptyFieldDialog = new AlertDialog.Builder(ContactDetails.this)
                .setTitle(getString(R.string.field_empty))
                .setMessage(getString(R.string.please_fill_the_field))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
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
        final String savedEmail = preferences.getString(EMAIL_FIELD, "");
        emailField.setText(savedEmail);
        final String savedName = preferences.getString(NAME_FIELD, "");
        nameField.setText(savedName);
        final String savedAddress = preferences.getString(ADDRESS_FIELD, "");
        addressField.setText(savedAddress);
        final String savedPhone = preferences.getString(PHONE_FIELD, "");
        phoneField.setText(savedPhone);

        Button sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailField.getText().toString();
                final String name = nameField.getText().toString();
                final String address = addressField.getText().toString();
                final String phone = phoneField.getText().toString();
                //Validate for empty fields
                if (email.trim().isEmpty()) {
                    emptyFieldDialog.setMessage(String.format(getString(R.string.please_fill_the_field), "email")).show();
                    emailField.requestFocus();
                    return;
                }
                if (name.trim().isEmpty()) {
                    emptyFieldDialog.setMessage(String.format(getString(R.string.please_fill_the_field), "имя")).show();
                    nameField.requestFocus();
                    return;
                }
                if (address.trim().isEmpty()) {
                    emptyFieldDialog.setMessage(String.format(getString(R.string.please_fill_the_field), "адрес")).show();
                    addressField.requestFocus();
                    return;
                }
                if (phone.trim().isEmpty()) {
                    emptyFieldDialog.setMessage(String.format(getString(R.string.please_fill_the_field), "телефон")).show();
                    phoneField.requestFocus();
                    return;
                }
                //Check that fields are not equal to previously saved
                if (!savedEmail.equals(email) || !savedName.equals(name) || !savedAddress.equals(address) || !savedPhone.equals(phone)) {
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
                }
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

    AlertDialog.Builder emptyFieldDialog;
}

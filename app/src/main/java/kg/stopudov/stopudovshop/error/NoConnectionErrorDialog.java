package kg.stopudov.stopudovshop.error;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class NoConnectionErrorDialog {

    public static Dialog getInstance(Context context) {
        return new AlertDialog.Builder(context)
                .setTitle("Нет ответа от сервера")
                .setMessage("В настоящее время на сервере проводятся технические работы. " +
                        "Попробуйте запустить приложение позже. " +
                        "Если ошибка повторится, свяжитесь с нами через сайт 100pudov.kg")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}

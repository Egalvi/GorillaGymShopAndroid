package kg.gorillagym.gorillagymshop;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;

import kg.gorillagym.gorillagymshop.navigation.Navigator;
import ru.egalvi.shop.gorillagym.model.Category;

public class CategoryCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    private Activity activity;

    public CategoryCursorAdapter(Activity context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //obsolete
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.category_list_item, parent, false);
//        }
        Button text = (Button) view.findViewById(R.id.category_title);
        cursor.getString(cursor.getColumnIndex(Table.CONTENT))
        final Category category = getItem(position);
        text.setText(category.getName());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.goToProducts(activity, category);
            }
        });
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.category_list_item, parent, false);
    }

}
package kg.gorillagym.gorillagymshop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import kg.gorillagym.gorillagymshop.navigation.Navigator;
import ru.egalvi.shop.gorillagym.model.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Activity activity;

    public CategoryAdapter(Activity context, int resource, List<Category> objects) {
        super(context, resource, objects);
        activity = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_list_item, parent, false);
        }
        Button text = (Button) convertView.findViewById(R.id.category_title);
        final Category category = getItem(position);
        text.setText(category.getName());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.goToProducts(activity, category);
            }
        });
        return convertView;
    }
}

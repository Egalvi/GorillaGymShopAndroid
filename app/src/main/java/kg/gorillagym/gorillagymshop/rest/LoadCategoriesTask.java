package kg.gorillagym.gorillagymshop.rest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import kg.gorillagym.gorillagymshop.CategoryAdapter;
import kg.gorillagym.gorillagymshop.ProductList;
import kg.gorillagym.gorillagymshop.R;
import kg.gorillagym.shop.content.GorillaGymCategoryService;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.service.CategoryService;

public class LoadCategoriesTask extends AsyncTask<Object, String, String> {
    protected String doInBackground(Object... params) {

        ListView lv = (ListView) params[0];
        final Activity activity = (Activity) params[1];
        CategoryService categoryService = new GorillaGymCategoryService();

        List<Category> categoryList = categoryService.getAll();
        ArrayAdapter<Category> arrayAdapter = new CategoryAdapter(activity,
                R.layout.category_list_item,
                categoryList);

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Application app = getApplication();
//                view.setBackgroundColor(android.R.color.background_light);
//                app.setCurrentItem(app.getSectionList().get(currentSelectedSection).getItems().get(position));
                Intent productList = new Intent(activity, ProductList.class);
                activity.startActivity(productList);
            }
        });
    }

}

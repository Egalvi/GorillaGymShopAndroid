package kg.gorillagym.gorillagymshop.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import kg.gorillagym.gorillagymshop.CategoryAdapter;
import kg.gorillagym.gorillagymshop.R;
import kg.gorillagym.shop.content.GorillaGymCategoryService;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.service.CategoryService;

public class CategoryLoaderTask extends AsyncTask<Object, Void, Void> {
    List<Category> categories;
    Activity categoryActivity;
    ListView categoryListView;

    @Override
    protected Void doInBackground(Object... params) {//TODO can pass this parameters to constructor
        categoryActivity = (Activity) params[0];
        categoryListView = (ListView) params[1];
        CategoryService carrierService = new GorillaGymCategoryService();
        try {
            categories = carrierService.getAll();
        } catch (Exception e) {
            //TODO java.net.UnknownHostException: Unable to resolve host if internet is OFF
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ArrayAdapter<Category> arrayAdapter = new CategoryAdapter(categoryActivity,
                R.layout.category_list_item, categories);

        categoryListView.setAdapter(arrayAdapter);
    }
}

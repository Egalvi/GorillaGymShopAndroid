package kg.gorillagym.gorillagymshop;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.service.CategoryService;
import ru.egalvi.shop.service.impl.TestCategoryService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv = (ListView) findViewById(R.id.listView);

        CategoryService categoryService = new TestCategoryService();

        List<Category> categoryList = categoryService.getAll();

        ArrayAdapter<Category> arrayAdapter = new CategoryAdapter(this,
                R.layout.category_list_item,
                categoryList);

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Application app = getApplication();
//                view.setBackgroundColor(android.R.color.background_light);
//                app.setCurrentItem(app.getSectionList().get(currentSelectedSection).getItems().get(position));
                Intent productList = new Intent(MainActivity.this, ProductList.class);
                startActivity(productList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package assignment.prm.chartmarkapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import assignment.prm.chartmarkapplication.Adapter.GeneralProductAdapter;
import assignment.prm.chartmarkapplication.Model.GeneralProduct;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Spinner productCategory;
    private String searchCategory;
    private RecyclerView rvHistoryList;
    private TextView tvHistoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout linearLayout = findViewById(R.id.drawer_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        setMenu();
        initializeSpinner();

        rvHistoryList = findViewById(R.id.rvHistoryList);
        tvHistoryList = findViewById(R.id.tvHistoryList);

        List<GeneralProduct> tmpHistoryList = new ArrayList<>(((GlobalVariable) getApplication()).getHistoryList());
        if(tmpHistoryList.size() > 0){
            rvHistoryList = findViewById(R.id.rvHistoryList);
            rvHistoryList.setLayoutManager(new GridLayoutManager(this, 2));
            rvHistoryList.setAdapter(new GeneralProductAdapter(tmpHistoryList, this, new GeneralProductAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(GeneralProduct item) {
                    Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                    intent.putExtra("category", item.category);
                    intent.putExtra("id", item.ID + "");
                    intent.putExtra("brandId", item.brandId);
                    startActivity(intent);
                }
            }));
        } else {
            tvHistoryList.setVisibility(View.INVISIBLE);
        }
    }

    private void initializeSpinner(){
        productCategory = findViewById(R.id.spnCategory);
        String[] categories = new String[]{"Laptop", "Headphone", "Keyboard", "VGA", "Mouse", "CPU"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        productCategory.setAdapter(adapter);
        productCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchCategory = productCategory.getItemAtPosition(position).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setMenu() {
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();
                        String category = "laptop";
                        switch (menuItem.getItemId()){
                            case R.id.nav_home:
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.nav_laptop:
                                category = "laptop";
                                break;
                            case R.id.nav_cpu:
                                category = "cpu";
                                break;
                            case R.id.nav_headphone:
                                category = "headphone";
                                break;
                            case R.id.nav_keyboard:
                                category = "keyboard";
                                break;
                            case R.id.nav_mouse:
                                category = "mouse";
                                break;
                            case R.id.nav_vga:
                                category = "vga";
                                break;
                        }
                        Intent intent = new Intent(getBaseContext(), ProductListActivity.class);
                        intent.putExtra("category", category);
                        startActivity(intent);
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickToViewLoveList(View view) {
        Intent intent = new Intent(this, LoveListActivity.class);
        startActivity(intent);
    }

    public void clickToViewCompareList(View view) {
        Intent intent = new Intent(this, CompareActivity.class);
        startActivity(intent);
    }

    public void clickToSearch(View view) {
        EditText edtSearch = findViewById(R.id.edtSearch);
        String keyword = edtSearch.getText().toString().trim();
        if(keyword.isEmpty()){
            Toast.makeText(this, "Item can't be empty! Please enter something", Toast.LENGTH_SHORT).show();
        }else {
            loadSearchResultFragment(searchCategory, keyword);
        }

    }

    private void loadSearchResultFragment(String searchCategory, String keyword){
        SearchCategoryFragment fragment = new SearchCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("searchCategory", searchCategory);
        bundle.putString("keyword", keyword);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}

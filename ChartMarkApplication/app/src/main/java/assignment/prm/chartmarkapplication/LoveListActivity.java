package assignment.prm.chartmarkapplication;

import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import assignment.prm.chartmarkapplication.Adapter.GeneralProductAdapter;
import assignment.prm.chartmarkapplication.Model.GeneralProduct;

public class LoveListActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView rvLoveProducts;
    private TextView tvLoveList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_list);
        setMenu();

        tvLoveList = findViewById(R.id.txtLoveList);
        rvLoveProducts = findViewById(R.id.rvLoveProducts);
        rvLoveProducts.setLayoutManager(new GridLayoutManager(this, 2));

        final List<GeneralProduct> loveList =((GlobalVariable) getApplication()).getLoveList();
        if(loveList != null && loveList.size() > 0){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvLoveList.setText(loveList.size() + " product(s) in Love List.");
                    rvLoveProducts.setAdapter(new GeneralProductAdapter(loveList, LoveListActivity.this, new GeneralProductAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(GeneralProduct item) {
                            Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                            intent.putExtra("category", item.category);
                            intent.putExtra("id", item.ID+"");
                            startActivity(intent);
                        }
                    }));
                }
            });

        } else {
            tvLoveList.setText("There is no product in Love list.");
        }

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
}

package assignment.prm.chartmarkapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import assignment.prm.chartmarkapplication.Adapter.GeneralProductAdapter;
import assignment.prm.chartmarkapplication.Model.GeneralProduct;

public class CompareActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView rvCompareProduct;
    private TextView tvCompareList;
    private ImageButton btnEmptyCompare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        setMenu();
        ((GlobalVariable) getApplication()).isInCompareActivity = true;
        rvCompareProduct = findViewById(R.id.rvCompareProducts);
        tvCompareList = findViewById(R.id.txtCompareList);
        rvCompareProduct.setLayoutManager(new GridLayoutManager(this, 2));

        final List<GeneralProduct> compareList =((GlobalVariable) getApplication()).getCompareList();
        if(compareList != null && compareList.size() > 0){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvCompareList.setText(compareList.size() + " product(s) in Compare List.");
                    rvCompareProduct.setAdapter(new GeneralProductAdapter(compareList, CompareActivity.this, new GeneralProductAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(GeneralProduct item) {
                            Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                            intent.putExtra("brandId", item.brandId);
                            intent.putExtra("category", item.category);
                            intent.putExtra("id", item.ID+"");
                            startActivity(intent);
                        }
                    }));
                }
            });

        } else {
            tvCompareList.setText("There is no product in Compare list.");
        }
        btnEmptyCompare = (ImageButton) findViewById(R.id.btnEmptyCompare);
        btnEmptyCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Empty Action");
        builder.setMessage("Do you want to empty this list?");
        builder.setCancelable(true);
        builder.setPositiveButton("Do nothing!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(CompareActivity.this, "Remain Activity", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Empty Compare List", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clickToEmptyCompareList();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    public void clickToCompare(View view) {
        final List<GeneralProduct> compareList =((GlobalVariable) getApplication()).getCompareList();
        if(compareList.size() != 2){
            Toast.makeText(this, "You don't have enough item to compare", Toast.LENGTH_SHORT).show();
        }else if(compareList.size() == 2){
            Intent intent = new Intent(getApplicationContext(), CompareDetailActivity.class);
            //Lấy item trên vị trí
            GeneralProduct item1 = ((GlobalVariable) getApplication()).getCompareList().get(0);
            intent.putExtra("brandId1", item1.brandId);
            intent.putExtra("category1", item1.category);
            intent.putExtra("id1", item1.ID+"");

            GeneralProduct item2 = ((GlobalVariable) getApplication()).getCompareList().get(1);
            intent.putExtra("brandId2", item2.brandId);
            intent.putExtra("category2", item2.category);
            intent.putExtra("id2", item2.ID+"");
            try{
                startActivity(intent);
            }catch (Exception e){
                Log.e("Error: ", e.getMessage());
            }

        }

    }

    public void clickToEmptyCompareList() {
        String message = ((GlobalVariable) getApplication()).emptyCompareList();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CompareActivity.class);
        startActivity(intent);
        finish();
    }
}

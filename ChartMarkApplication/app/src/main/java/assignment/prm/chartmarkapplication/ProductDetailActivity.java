package assignment.prm.chartmarkapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.Key;
import java.util.List;

import assignment.prm.chartmarkapplication.Model.CPU;
import assignment.prm.chartmarkapplication.Model.GeneralProduct;
import assignment.prm.chartmarkapplication.Model.Headphone;
import assignment.prm.chartmarkapplication.Model.Keyboard;
import assignment.prm.chartmarkapplication.Model.Laptop;
import assignment.prm.chartmarkapplication.Model.Mouse;
import assignment.prm.chartmarkapplication.Model.VGA;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView tvCategory, tvName, tvBrand, tvPrice;
    private ImageView image1, image2;
    private String category, id;
    private ImageButton btnAddLove, btnAddCompare;
    private GeneralProduct generalProduct;
    private boolean isInLoveList = false, isInCompareList = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setMenu();
        initializeControl();

        Intent intent = getIntent();
        if(intent.hasExtra("category")){
            category = intent.getStringExtra("category");
        }
        if(intent.hasExtra("id")){
            id = intent.getStringExtra("id");
        }
        getAPIDataProduct(category, id);

        if(((GlobalVariable) this.getApplication()).checkInLoveList(generalProduct)){
            btnAddLove.setImageResource(R.drawable.icon_heart);
            isInLoveList = true;
        }
    }

    private void initializeControl(){
        tvBrand = findViewById(R.id.tv_brand);
        tvCategory = findViewById(R.id.tv_category);
        tvName = findViewById(R.id.tv_product_name);
        tvPrice = findViewById(R.id.tv_average_price);

        image1 = findViewById(R.id.iv_product_image1);
        image2 = findViewById(R.id.iv_product_image2);

        btnAddLove = findViewById(R.id.btn_add_love);
        btnAddCompare = findViewById(R.id.btn_add_compare);
    }

    private void getAPIDataProduct(String category, String id){

        switch (category){
            case "laptop":
                getLaptop( id);
                break;
            case "cpu":
                getCPU(id);
                break;
            case "vga":
                getVGA(id);
                break;
            case "headphone":
                getHeadphone(id);
                break;
            case "mouse":
                getMouse(id);
                break;
            case "keyboard":
                getKeyboard(id);
                break;
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

    public void clickToAddToCompareList(View view) {
        btnAddCompare.setImageResource(R.drawable.icon_added);
        Toast.makeText(this, "Product is added to Compare list.", Toast.LENGTH_SHORT).show();
    }

    public void clickToAddToLoveList(View view) {
        String message;
        if(isInLoveList){
            message = ((GlobalVariable) this.getApplication()).removeFromLoveList(generalProduct);
            isInLoveList = false;
            btnAddLove.setImageResource(R.drawable.icon_hollow_heart);
        } else {
            message = ((GlobalVariable) this.getApplication()).addToLoveList(generalProduct);
            isInLoveList = true;
            btnAddLove.setImageResource(R.drawable.icon_heart);
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    public void clickToSearchOnInternet(View view) {
        String searchURI = getResources().getString(R.string.google_search);
        String productName = tvName.getText().toString();
        String[] s = productName.split(" ");
        for(int i=0; i<s.length; i++){
            searchURI+=s[i];
            if(i!= s.length-1){
                searchURI += "+";
            }
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchURI));
        startActivity(Intent.createChooser(intent,"Open using:"));
    }

    private void getLaptop(String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Laptops/"+id).build();
        Type productType = Types.newParameterizedType(Laptop.class);
        final JsonAdapter<Laptop> jsonAdapter = moshi.adapter(productType);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final Laptop item = jsonAdapter.fromJson(json);
                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCategory.setText(item.category.toUpperCase());
                        tvBrand.setText(item.brandId);
                        tvName.setText("Laptop "+item.name);
                        tvPrice.setText(item.averagePrice + "VNĐ");
                        if(item.image1 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image1).into(image1);
                        } else{
                            image1.setVisibility(View.INVISIBLE);
                        }
                        if(item.image2 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image2).into(image2);
                        } else{
                            image2.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void getCPU( String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/CPUs/"+id).build();
        Type productType = Types.newParameterizedType(CPU.class);
        final JsonAdapter<CPU> jsonAdapter = moshi.adapter(productType);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final CPU item = jsonAdapter.fromJson(json);
                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCategory.setText(item.category.toUpperCase());
                        tvBrand.setText(item.brandId);
                        tvName.setText("CPU "+item.name);
                        tvPrice.setText(item.averagePrice + "VNĐ");
                        if(item.image1 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image1).into(image1);
                        } else{
                            image1.setVisibility(View.INVISIBLE);
                        }
                        if(item.image2 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image2).into(image2);
                        } else{
                            image2.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void getVGA( String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/VGAs/"+id).build();
        Type productType = Types.newParameterizedType(VGA.class);
        final JsonAdapter<VGA> jsonAdapter = moshi.adapter(productType);

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final VGA item = jsonAdapter.fromJson(json);
                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCategory.setText(item.category.toUpperCase());
                        tvBrand.setText(item.brandId);
                        tvName.setText("VGA "+item.name);
                        tvPrice.setText(item.averagePrice + "VNĐ");
                        if(item.image1 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image1).into(image1);
                        } else{
                            image1.setVisibility(View.INVISIBLE);
                        }
                        if(item.image2 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image2).into(image2);
                        } else{
                            image2.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void getHeadphone( String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Headphones/"+id).build();
        Type productType = Types.newParameterizedType(Headphone.class);
        final JsonAdapter<Headphone> jsonAdapter = moshi.adapter(productType);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final Headphone item = jsonAdapter.fromJson(json);
                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCategory.setText(item.category.toUpperCase());
                        tvBrand.setText(item.brandId);
                        tvName.setText("Headphone "+item.name);
                        tvPrice.setText(item.averagePrice + "VNĐ");
                        if(item.image1 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image1).into(image1);
                        } else{
                            image1.setVisibility(View.INVISIBLE);
                        }
                        if(item.image2 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image2).into(image2);
                        } else{
                            image2.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void getMouse( String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Mouses/"+id).build();
        Type productType = Types.newParameterizedType(Mouse.class);
        final JsonAdapter<Mouse> jsonAdapter = moshi.adapter(productType);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final Mouse item = jsonAdapter.fromJson(json);
                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCategory.setText(item.category.toUpperCase());
                        tvBrand.setText(item.brandId);
                        tvName.setText("Mouse "+item.name);
                        tvPrice.setText(item.averagePrice + "VNĐ");
                        if(item.image1 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image1).into(image1);
                        } else{
                            image1.setVisibility(View.INVISIBLE);
                        }
                        if(item.image2 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image2).into(image2);
                        } else{
                            image2.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void getKeyboard( String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Keyboards/"+id).build();
        Type productType = Types.newParameterizedType(Keyboard.class);
        final JsonAdapter<Keyboard> jsonAdapter = moshi.adapter(productType);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final Keyboard item = jsonAdapter.fromJson(json);
                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCategory.setText(item.category.toUpperCase());
                        tvBrand.setText(item.brandId);
                        tvName.setText("Keyboard "+item.name);
                        tvPrice.setText(item.averagePrice + "VNĐ");
                        if(item.image1 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image1).into(image1);
                        } else{
                            image1.setVisibility(View.INVISIBLE);
                        }
                        if(item.image2 != null){
                            Picasso.with(ProductDetailActivity.this).load(item.image2).into(image2);
                        } else{
                            image2.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }



}

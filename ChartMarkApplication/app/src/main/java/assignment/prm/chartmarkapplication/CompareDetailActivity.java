package assignment.prm.chartmarkapplication;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;

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

public class CompareDetailActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView tvCategory, tvName, tvBrand, tvPrice;
    private ImageView image1, image2;
    private String category, id, key, brandId, brandName;
    private GeneralProduct generalProduct;
    private boolean isInLoveList = false, isInCompareList = false;
    private TableLayout tbProductDetail, tbProductDetail2, tblProductAttribute;

    private void initializeControl() {
        tvBrand = findViewById(R.id.tv_brand);
        tvCategory = findViewById(R.id.tv_category);
        tvName = findViewById(R.id.tv_product_name);
        tvPrice = findViewById(R.id.tv_average_price);


        image1 = findViewById(R.id.imgCompareOne);
        image2 = findViewById(R.id.imgCompareTwo);

        tblProductAttribute = findViewById(R.id.tblProductAttribute);
        tbProductDetail = findViewById(R.id.tblProductCompare1);
        tbProductDetail2 = findViewById(R.id.tblProductCompare2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_detail);


    }

    private void checkCategory(String category) {

    }

    //Get data from specific item in compare list
    private void getLaptop(String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Laptops/" + id).build();
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
                        tvBrand.setText("Brand: " + brandName);
                        tvName.setText("Laptop " + item.name);
                        //Transfer Data for compare detail using


                        tvPrice.setText("Average Price: " + item.averagePrice + "VNĐ");
                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable(item);
                    }
                });
            }
        });
    }

    private void getCPU(String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/CPUs/" + id).build();
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
                        tvBrand.setText("Brand: " + brandName);
                        tvName.setText("CPU " + item.name);
                        //Transfer Data for compare detail using


                        tvPrice.setText("Average Price:" + item.averagePrice + "VNĐ");
                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable(item);
                    }
                });
            }
        });
    }

    private void getVGA(String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/VGAs/" + id).build();
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
                        tvBrand.setText("Brand: " + brandName);
                        tvName.setText("VGA " + item.name);
                        //Transfer Data for compare detail using


                        tvPrice.setText("Average Price: " + item.averagePrice + "VNĐ");
                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable(item);
                    }
                });
            }
        });
    }

    private void getHeadphone(String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Headphones/" + id).build();
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
                        tvBrand.setText("Brand:" + brandName);
                        tvName.setText("Headphone " + item.name);
                        //Transfer Data for compare detail using


                        tvPrice.setText("Average Price: " + item.averagePrice + "VNĐ");
                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable(item);
                    }
                });
            }
        });
    }

    private void getMouse(String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Mouses/" + id).build();
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
                        tvBrand.setText("Brand: " + brandName);
                        tvName.setText("Mouse " + item.name);
                        //Transfer Data for compare detail using


                        tvPrice.setText("Average Price:" + item.averagePrice + "VNĐ");
                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable(item);
                    }
                });
            }
        });
    }

    private void getKeyboard(String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Keyboards/" + id).build();
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
                        tvBrand.setText("Brand: " + brandName);
                        tvName.setText("Keyboard " + item.name);
                        //Transfer Data for compare detail using


                        tvPrice.setText("Average Price: " + item.averagePrice + "VNĐ");
                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable(item);
                    }
                });
            }
        });
    }


    public void clickToViewLoveList(View view) {
        Intent intent = new Intent(this, LoveListActivity.class);
        startActivity(intent);
    }

    public void clickToReturnHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void initializeProductAttribute(Object product) {
        if (product instanceof Laptop) {
            Laptop item = (Laptop) product;
            addTableProductAttribute("Category");
            addTableProductAttribute("Name");
            addTableProductAttribute("Brand");
            addTableProductAttribute("Type");
            addTableProductAttribute("Produce year");
            addTableProductAttribute("Screen size");
            addTableProductAttribute("Weight");
            addTableProductAttribute("Chip");
            addTableProductAttribute("RAM");
            addTableProductAttribute("ROM");
            addTableProductAttribute("Webcam");
            addTableProductAttribute("Wifi");
            addTableProductAttribute("Operating System");
            addTableProductAttribute("Battery");
            addTableProductAttribute("Average price");
        }
        if (product instanceof CPU) {
            CPU item = (CPU) product;
            addTableProductAttribute("Category");
            addTableProductAttribute("Name");
            addTableProductAttribute("Brand");
            addTableProductAttribute("Socket");
            addTableProductAttribute("TDP");
            addTableProductAttribute("Thread");
            addTableProductAttribute("Clock Speed");
            addTableProductAttribute("Weight");
            addTableProductAttribute("Average price");
        }
        if (product instanceof Headphone) {
            Headphone item = (Headphone) product;
            addTableProductAttribute("Category");
            addTableProductAttribute("Name");
            addTableProductAttribute("Brand");
            addTableProductAttribute("Type");
            addTableProductAttribute("Micro");
            addTableProductAttribute("Jack");
            addTableProductAttribute("Frequency range");
            addTableProductAttribute("Bluetooth");
            addTableProductAttribute("Length");
            addTableProductAttribute("Average price");
        }
        if (product instanceof Mouse) {
            Mouse item = (Mouse) product;
            addTableProductAttribute("Category");
            addTableProductAttribute("Name");
            addTableProductAttribute("Brand");
            addTableProductAttribute("Type");
            addTableProductAttribute("Wireless");
            addTableProductAttribute("Bluetooth");
            addTableProductAttribute("Weight");
            addTableProductAttribute("Average price");
        }
        if (product instanceof Keyboard) {
            Keyboard item = (Keyboard) product;
            addTableProductAttribute("Category");
            addTableProductAttribute("Name");
            addTableProductAttribute("Brand");
            addTableProductAttribute("Connection");
            addTableProductAttribute("Bluetooth");
            addTableProductAttribute("Height");
            addTableProductAttribute("Length");
            addTableProductAttribute("Width");
            addTableProductAttribute("Average price");
        }
        if (product instanceof VGA) {
            VGA item = (VGA) product;
            addTableProductAttribute("Category");
            addTableProductAttribute("Name");
            addTableProductAttribute("Brand");
            addTableProductAttribute("Standard memory");
            addTableProductAttribute("Max Screen Resolution");
            addTableProductAttribute("Weight");
            addTableProductAttribute("Size");
            addTableProductAttribute("Average price");
        }
    }

    private void initializeProductDetailTable(Object product) {

        if (product instanceof Laptop) {
            Laptop item = (Laptop) product;
            addTableProductDetail(item.category);
            addTableProductDetail(item.name);
            addTableProductDetail(brandName);
            addTableProductDetail(item.type);
            addTableProductDetail(item.year + "");
            addTableProductDetail(item.screenSize + " inches");
            addTableProductDetail(item.weight + " kg");
            addTableProductDetail(item.chip);
            addTableProductDetail(item.ram);
            addTableProductDetail(item.rom);
            addTableProductDetail(item.webcam);
            addTableProductDetail(item.wifi);
            addTableProductDetail(item.OS);
            addTableProductDetail(item.battery);
            addTableProductDetail(item.averagePrice + " VND");
        }
        if (product instanceof CPU) {
            CPU item = (CPU) product;
            addTableProductDetail(item.category);
            addTableProductDetail(item.name);
            addTableProductDetail(brandName);
            addTableProductDetail(item.socket + "");
            addTableProductDetail(item.TDP + "");
            addTableProductDetail(item.thread + "");
            addTableProductDetail(item.clockSpeed + "");
            addTableProductDetail(item.weight + "");
            addTableProductDetail(item.averagePrice + " VND");
        }
        if (product instanceof Headphone) {
            Headphone item = (Headphone) product;
            addTableProductDetail(item.category);
            addTableProductDetail(item.name);
            addTableProductDetail(brandName);
            addTableProductDetail(item.type);
            addTableProductDetail(item.micro);
            addTableProductDetail(item.jack);
            addTableProductDetail(item.frequencyRange);
            addTableProductDetail(item.bluetooth);
            addTableProductDetail(item.length + "m");
            addTableProductDetail(item.averagePrice + " VND");
        }
        if (product instanceof Mouse) {
            Mouse item = (Mouse) product;
            addTableProductDetail(item.category);
            addTableProductDetail(item.name);
            addTableProductDetail(brandName);
            addTableProductDetail(item.type);
            addTableProductDetail(item.wireless);
            addTableProductDetail(item.bluetooth);
            addTableProductDetail(item.weight + "kg");
            addTableProductDetail(item.averagePrice + " VND");
        }
        if (product instanceof Keyboard) {
            Keyboard item = (Keyboard) product;
            addTableProductDetail(item.category);
            addTableProductDetail(item.name);
            addTableProductDetail(brandName);
            addTableProductDetail(item.connect);
            addTableProductDetail(item.bluetooth);
            addTableProductDetail(item.height + "cm");
            addTableProductDetail(item.length + " cm");
            addTableProductDetail(item.width + " cm");
            addTableProductDetail(item.averagePrice + " VND");
        }
        if (product instanceof VGA) {
            VGA item = (VGA) product;
            addTableProductDetail(item.category);
            addTableProductDetail(item.name);
            addTableProductDetail(brandName);
            addTableProductDetail(item.standardMemory + "GB");
            addTableProductDetail(item.maxScreenResolution);
            addTableProductDetail(item.weight + "kg");
            addTableProductDetail(item.size);
            addTableProductDetail(item.averagePrice + " VND");
        }
    }

    private void addTableProductAttribute(String key) {
        TextView col1 = new TextView(this);
        TableRow row = new TableRow(this);

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.4f);
        col1.setLayoutParams(lp);
        col1.setPadding(10, 1, 10, 1);


        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col1.setText(key);

        row.addView(col1);
        tblProductAttribute.addView(row);
    }

    private void addTableProductDetail(String value) {
        TextView col2 = new TextView(this);
        TableRow row = new TableRow(this);

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.6f);
        col2.setLayoutParams(lp);
        col2.setPadding(10, 1, 10, 1);

        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col2.setText(value);

        row.addView(col2);

        //Gia dinh
        tbProductDetail.addView(row);
        tbProductDetail2.addView(row);
    }
}

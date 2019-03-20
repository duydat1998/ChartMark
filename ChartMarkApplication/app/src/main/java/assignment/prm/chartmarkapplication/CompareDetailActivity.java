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

    private TextView tvCategory, tvName, tvBrand, tvPrice;
    private ImageView image1, image2;
    private String category1, id1, key, brandId1, brandName1;
    private String category2, id2, key2, brandId2, brandName2;
    private GeneralProduct generalProduct;
    private boolean isInLoveList = false, isInCompareList = false;
    private TableLayout tbProductDetail, tbProductDetail2, tblProductAttribute;

    private void initializeControl() {

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

        initializeControl();
        Intent intent = getIntent();
        if (intent.hasExtra("category1")) {
            category1 = intent.getStringExtra("category1");
        }
        if (intent.hasExtra("id1")) {
            id1 = intent.getStringExtra("id1");
        }
        if (intent.hasExtra("brandId1")){
            brandId1 = intent.getStringExtra("brandId1");
        }


        if (intent.hasExtra("category2")) {
            category2 = intent.getStringExtra("category2");
        }
        if (intent.hasExtra("id2")) {
            id2 = intent.getStringExtra("id2");
        }
        if (intent.hasExtra("brandId2")){
            brandId2 = intent.getStringExtra("brandId2");
        }

        checkCategory1(category1, id1, id2, brandId1, brandId2);
    }

    private void checkCategory1(String category1, String id1, String id2, String brandId1, String brandId2) {
        brandName1 = ((GlobalVariable) getApplication()).getBrandName(brandId1);
        brandName2 = ((GlobalVariable) getApplication()).getBrandName(brandId2);
        switch (category1) {
            case "laptop":
                getLaptop1(id1);
                getLaptop2(id2);
                break;
            case "cpu":
                getCPU1(id1);
                getCPU2(id2);
                break;
            case "vga":
                getVGA1(id1);
                getVGA2(id2);
                break;
            case "headphone":
                getHeadphone1(id1);
                getHeadphone2(id2);
                break;
            case "mouse":
                getMouse1(id1);
                getMouse2(id2);
                break;
            case "keyboard":
                getKeyboard1(id1);
                getKeyboard2(id2);
                break;
        }
    }


    //Get data from specific item in compare list
    private void getLaptop1(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable1(item);
                    }
                });
            }
        });
    }
    private void getLaptop2(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
                        } else {
                            image2.setVisibility(View.INVISIBLE);
                        }
                        initializeProductDetailTable2(item);
                    }
                });
            }
        });
    }

    private void getCPU1(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable1(item);
                    }
                });
            }
        });
    }
    private void getCPU2(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
                        } else {
                            image2.setVisibility(View.INVISIBLE);
                        }
                        initializeProductDetailTable2(item);
                    }
                });
            }
        });
    }

    private void getVGA1(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable1(item);
                    }
                });
            }
        });
    }
    private void getVGA2(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
                        } else {
                            image2.setVisibility(View.INVISIBLE);
                        }
                        initializeProductDetailTable2(item);
                    }
                });
            }
        });
    }

    private void getHeadphone1(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable1(item);
                    }
                });
            }
        });
    }
    private void getHeadphone2(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
                        } else {
                            image2.setVisibility(View.INVISIBLE);
                        }
                        initializeProductDetailTable1(item);
                    }
                });
            }
        });
    }

    private void getMouse1(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable1(item);
                    }
                });
            }
        });
    }
    private void getMouse2(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
                        } else {
                            image2.setVisibility(View.INVISIBLE);
                        }
                        initializeProductDetailTable1(item);
                    }
                });
            }
        });
    }

    private void getKeyboard1(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
                        } else {
                            image1.setVisibility(View.INVISIBLE);
                        }
                        initializeProductAttribute(item);
                        initializeProductDetailTable1(item);
                    }
                });
            }
        });
    }
    private void getKeyboard2(String id) {
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

                        if (item.image1 != null) {
                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
                        } else {
                            image2.setVisibility(View.INVISIBLE);
                        }
                        initializeProductDetailTable1(item);
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

    private void initializeProductDetailTable1(Object product) {

        if (product instanceof Laptop) {
            Laptop item = (Laptop) product;
            addTableProductDetail1(item.category);
            addTableProductDetail1(item.name);
            addTableProductDetail1(brandName1);
            addTableProductDetail1(item.type);
            addTableProductDetail1(item.year + "");
            addTableProductDetail1(item.screenSize + " inches");
            addTableProductDetail1(item.weight + " kg");
            addTableProductDetail1(item.chip);
            addTableProductDetail1(item.ram);
            addTableProductDetail1(item.rom);
            addTableProductDetail1(item.webcam);
            addTableProductDetail1(item.wifi);
            addTableProductDetail1(item.OS);
            addTableProductDetail1(item.battery);
            addTableProductDetail1(item.averagePrice + " VND");
        }
        if (product instanceof CPU) {
            CPU item = (CPU) product;
            addTableProductDetail1(item.category);
            addTableProductDetail1(item.name);
            addTableProductDetail1(brandName1);
            addTableProductDetail1(item.socket + "");
            addTableProductDetail1(item.TDP + "");
            addTableProductDetail1(item.thread + "");
            addTableProductDetail1(item.clockSpeed + "");
            addTableProductDetail1(item.weight + "");
            addTableProductDetail1(item.averagePrice + " VND");
        }
        if (product instanceof Headphone) {
            Headphone item = (Headphone) product;
            addTableProductDetail1(item.category);
            addTableProductDetail1(item.name);
            addTableProductDetail1(brandName1);
            addTableProductDetail1(item.type);
            addTableProductDetail1(item.micro);
            addTableProductDetail1(item.jack);
            addTableProductDetail1(item.frequencyRange);
            addTableProductDetail1(item.bluetooth);
            addTableProductDetail1(item.length + "m");
            addTableProductDetail1(item.averagePrice + " VND");
        }
        if (product instanceof Mouse) {
            Mouse item = (Mouse) product;
            addTableProductDetail1(item.category);
            addTableProductDetail1(item.name);
            addTableProductDetail1(brandName1);
            addTableProductDetail1(item.type);
            addTableProductDetail1(item.wireless);
            addTableProductDetail1(item.bluetooth);
            addTableProductDetail1(item.weight + "kg");
            addTableProductDetail1(item.averagePrice + " VND");
        }
        if (product instanceof Keyboard) {
            Keyboard item = (Keyboard) product;
            addTableProductDetail1(item.category);
            addTableProductDetail1(item.name);
            addTableProductDetail1(brandName1);
            addTableProductDetail1(item.connect);
            addTableProductDetail1(item.bluetooth);
            addTableProductDetail1(item.height + "cm");
            addTableProductDetail1(item.length + " cm");
            addTableProductDetail1(item.width + " cm");
            addTableProductDetail1(item.averagePrice + " VND");
        }
        if (product instanceof VGA) {
            VGA item = (VGA) product;
            addTableProductDetail1(item.category);
            addTableProductDetail1(item.name);
            addTableProductDetail1(brandName1);
            addTableProductDetail1(item.standardMemory + "GB");
            addTableProductDetail1(item.maxScreenResolution);
            addTableProductDetail1(item.weight + "kg");
            addTableProductDetail1(item.size);
            addTableProductDetail1(item.averagePrice + " VND");
        }
    }
    private void initializeProductDetailTable2(Object product) {

        if (product instanceof Laptop) {
            Laptop item = (Laptop) product;
            addTableProductDetail2(item.category);
            addTableProductDetail2(item.name);
            addTableProductDetail2(brandName2);
            addTableProductDetail2(item.type);
            addTableProductDetail2(item.year + "");
            addTableProductDetail2(item.screenSize + " inches");
            addTableProductDetail2(item.weight + " kg");
            addTableProductDetail2(item.chip);
            addTableProductDetail2(item.ram);
            addTableProductDetail2(item.rom);
            addTableProductDetail2(item.webcam);
            addTableProductDetail2(item.wifi);
            addTableProductDetail2(item.OS);
            addTableProductDetail2(item.battery);
            addTableProductDetail2(item.averagePrice + " VND");
        }
        if (product instanceof CPU) {
            CPU item = (CPU) product;
            addTableProductDetail2(item.category);
            addTableProductDetail2(item.name);
            addTableProductDetail2(brandName2);
            addTableProductDetail2(item.socket + "");
            addTableProductDetail2(item.TDP + "");
            addTableProductDetail2(item.thread + "");
            addTableProductDetail2(item.clockSpeed + "");
            addTableProductDetail2(item.weight + "");
            addTableProductDetail2(item.averagePrice + " VND");
        }
        if (product instanceof Headphone) {
            Headphone item = (Headphone) product;
            addTableProductDetail2(item.category);
            addTableProductDetail2(item.name);
            addTableProductDetail2(brandName2);
            addTableProductDetail2(item.type);
            addTableProductDetail2(item.micro);
            addTableProductDetail2(item.jack);
            addTableProductDetail2(item.frequencyRange);
            addTableProductDetail2(item.bluetooth);
            addTableProductDetail2(item.length + "m");
            addTableProductDetail2(item.averagePrice + " VND");
        }
        if (product instanceof Mouse) {
            Mouse item = (Mouse) product;
            addTableProductDetail2(item.category);
            addTableProductDetail2(item.name);
            addTableProductDetail2(brandName2);
            addTableProductDetail2(item.type);
            addTableProductDetail2(item.wireless);
            addTableProductDetail2(item.bluetooth);
            addTableProductDetail2(item.weight + "kg");
            addTableProductDetail2(item.averagePrice + " VND");
        }
        if (product instanceof Keyboard) {
            Keyboard item = (Keyboard) product;
            addTableProductDetail2(item.category);
            addTableProductDetail2(item.name);
            addTableProductDetail2(brandName2);
            addTableProductDetail2(item.connect);
            addTableProductDetail2(item.bluetooth);
            addTableProductDetail2(item.height + "cm");
            addTableProductDetail2(item.length + " cm");
            addTableProductDetail2(item.width + " cm");
            addTableProductDetail2(item.averagePrice + " VND");
        }
        if (product instanceof VGA) {
            VGA item = (VGA) product;
            addTableProductDetail2(item.category);
            addTableProductDetail2(item.name);
            addTableProductDetail2(brandName2);
            addTableProductDetail2(item.standardMemory + "GB");
            addTableProductDetail2(item.maxScreenResolution);
            addTableProductDetail2(item.weight + "kg");
            addTableProductDetail2(item.size);
            addTableProductDetail2(item.averagePrice + " VND");
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

    private void addTableProductDetail1(String value) {
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
    }
    private void addTableProductDetail2(String value) {
        TextView col2 = new TextView(this);
        TableRow row = new TableRow(this);

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.8f);
        col2.setLayoutParams(lp);
        col2.setPadding(10, 1, 10, 1);

        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col2.setText(value);

        row.addView(col2);

        //Gia dinh
        tbProductDetail2.addView(row);
    }
}

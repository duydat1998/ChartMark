package assignment.prm.chartmarkapplication;

import android.content.Intent;
import android.graphics.Typeface;
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
import java.util.concurrent.CountDownLatch;

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

    private ImageView image1, image2;
    private String category1, id1, brandId1, brandName1;
    private String category2, id2, brandId2, brandName2;
    private Laptop lap1, lap2;
    private Keyboard keyb1, keyb2;
    private CPU cpu1, cpu2;
    private VGA vga1, vga2;
    private Headphone hp1, hp2;
    private Mouse mouse1, mouse2;
//    private TableLayout tbProductDetail, tbProductDetail2, tblProductAttribute;

    private TableLayout tblCompareDetail;
    private void initializeControl() {

        image1 = findViewById(R.id.imgCompareOne);
        image2 = findViewById(R.id.imgCompareTwo);

//        tblProductAttribute = findViewById(R.id.tblProductAttribute);
//        tbProductDetail = findViewById(R.id.tblProductCompare1);
//        tbProductDetail2 = findViewById(R.id.tblProductCompare2);
        tblCompareDetail = findViewById(R.id.tblCompareDetail);
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

        initialzizeProduct(category1);
//        checkCategory1(category1, id1, id2, brandId1, brandId2);
    }

    private void initialzizeProduct(String category1){
        switch (category1) {
            case "laptop":
                getLaptop(1,id1);
                getLaptop(2,id2);
                initializeCompareTable(category1,lap1, lap2);
                break;
            case "cpu":
                getCPU(1,id1);
                getCPU(2,id2);
                initializeCompareTable(category1,cpu1, cpu2);
                break;
            case "vga":
                getVGA(1,id1);
                getVGA(2,id2);
                initializeCompareTable(category1, vga1, vga2);
                break;
            case "headphone":
                getHeadphone(1,id1);
                getHeadphone(2,id2);
                initializeCompareTable(category1, hp1, hp2);
                break;
            case "mouse":
                getMouse(1,id1);
                getMouse(2,id2);
                initializeCompareTable(category1, mouse1, mouse2);
                break;
            case "keyboard":
                getKeyboard(1,id1);
                getKeyboard(2,id2);
                initializeCompareTable(category1, keyb1, keyb2);
                break;
        }

    }

    private void initializeCompareTable(String category1, Object obj1, Object obj2){
        switch (category1) {
            case "laptop":
                Laptop lap1 = (Laptop) obj1;
                Laptop lap2 = (Laptop) obj2;

                addTableRow("Category", lap1.category, lap2.category);
                addTableRow("Name", lap1.name, lap2.name);
                addTableRow("Brand", brandName1, brandName2);
                addTableRow("Type", lap1.type, lap2.type);
                addTableRow("Produce year", lap1.year+"", lap2.year+"");
                addTableRow("Screen size", lap1.screenSize +" inches", lap2.screenSize +" inches");
                addTableRow("Weight", lap1.weight + " kg", lap2.weight + " kg");
                addTableRow("Chip", lap1.chip, lap2.chip);
                addTableRow("RAM", lap1.ram, lap2.ram);
                addTableRow("ROM", lap1.rom, lap2.rom);
                addTableRow("Webcam", lap1.webcam, lap2.webcam);
                addTableRow("Wifi", lap1.wifi, lap2.wifi);
                addTableRow("Operating System", lap1.OS, lap2.OS);
                addTableRow("Battery", lap1.battery, lap2.battery);
                addTableRow("Average price", lap1.averagePrice + " VND", lap2.averagePrice + " VND");
                break;
            case "cpu":

                CPU cpu1 = (CPU) obj1;
                CPU cpu2 = (CPU) obj2;

                addTableRow("Category", cpu1.category, cpu2.category);
                addTableRow("Name", cpu1.name, cpu2.name);
                addTableRow("Brand", brandName1, brandName2);
                addTableRow("Socket", cpu1.socket + "", cpu2.socket + "");
                addTableRow("TDP",cpu1.TDP + "", cpu1.TDP + "");
                addTableRow("Thread", cpu1.thread+"", cpu2.thread +"");
                addTableRow("Clock Speed", cpu1.clockSpeed+"", cpu2.clockSpeed+"");
                addTableRow("Weight", cpu1.weight + "kg", cpu2.weight + "kg");
                addTableRow("Average price", cpu1.averagePrice + " VND", cpu2.averagePrice + " VND");

                break;
            case "vga":
                VGA vga1 = (VGA) obj1;
                VGA vga2 = (VGA) obj2;

                addTableRow("Category", vga1.category, vga2.category);
                addTableRow("Name", vga1.name, vga2.name);
                addTableRow("Brand", brandName1, brandName2);
                addTableRow("Standard memory", vga1.standardMemory + " GB", vga2.standardMemory + " GB");
                addTableRow("Max Screen Resolution", vga1.maxScreenResolution, vga2.maxScreenResolution);
                addTableRow("Weight", vga1.weight + " kg", vga2.weight + " kg");
                addTableRow("Size", vga1.size, vga2.size);
                addTableRow("Average price", vga1.averagePrice + " VND", vga2.averagePrice+ " VND");
                break;
            case "headphone":
                Headphone hp1 = (Headphone) obj1;
                Headphone hp2 = (Headphone) obj2;

                addTableRow("Category", hp1.category, hp1.category);
                addTableRow("Name", hp1.name, hp2.name);
                addTableRow("Brand", brandName1, brandName2);
                addTableRow("Type", hp1.type, hp2.type);
                addTableRow("Micro", hp1.micro, hp2.micro);
                addTableRow("Jack", hp1.jack, hp2.jack);
                addTableRow("Frequency range", hp1.frequencyRange, hp2.frequencyRange);
                addTableRow("Bluetooth", hp1.bluetooth, hp2.bluetooth);
                addTableRow("Length", hp1.length + " m", hp2.length + " m");
                addTableRow("Average price", hp1.averagePrice+" VND", hp2.averagePrice + " VND");
                break;
            case "mouse":
                Mouse mouse1 = (Mouse) obj1;
                Mouse mouse2 = (Mouse) obj2;

                addTableRow("Category", mouse1.category, mouse2.category);
                addTableRow("Name", mouse1.name, mouse2.name);
                addTableRow("Brand", brandName1, brandName2);
                addTableRow("Type", mouse1.type, mouse2.type);
                addTableRow("Wireless", mouse1.wireless, mouse2.wireless);
                addTableRow("Bluetooth", mouse1.bluetooth, mouse2.bluetooth);
                addTableRow("Weight", mouse1.weight + " g", mouse2.weight + " g");
                addTableRow("Average price", mouse1.averagePrice+" VND", mouse2.averagePrice + " VND");
                break;
            case "keyboard":
                Keyboard keyb1 = (Keyboard) obj1;
                Keyboard keyb2 = (Keyboard) obj2;

                addTableRow("Category", keyb1.category, keyb2.category);
                addTableRow("Name", keyb1.name, keyb2.name);
                addTableRow("Brand", brandName1, brandName2);
                addTableRow("Connection", keyb1.connect, keyb2.connect);
                addTableRow("Bluetooth", keyb1.bluetooth, keyb2.bluetooth);
                addTableRow("Height", keyb1.height +"cm", keyb2.height +"cm");
                addTableRow("Length", keyb1.length + "cm", keyb2.length+"cm");
                addTableRow("Width", keyb1.width+"cm", keyb2.width+"cm");
                addTableRow("Average price", keyb1.averagePrice+" VND", keyb2.averagePrice + " VND");
                break;
        }
    }

    private void addTableRow(String in1, String in2, String in3){
        TextView col1 = new TextView(this);
        TextView col2 = new TextView(this);
        TextView col3 = new TextView(this);
        TableRow row = new TableRow(this);

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.2f);
        col1.setLayoutParams(lp);
        col1.setPadding(10, 1, 10, 1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.4f);
        col2.setLayoutParams(lp);
        col2.setPadding(10, 1, 10, 1);

        col3.setLayoutParams(lp);
        col3.setPadding(10, 1, 10, 1);

        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col1.setText(in1);
        col1.setTypeface(col1.getTypeface(), Typeface.BOLD_ITALIC);
        col2.setText(in2);
        col3.setText(in3);

        row.addView(col1);
        row.addView(col2);
        row.addView(col3);

        tblCompareDetail.addView(row);
    }

    public void clickToViewLoveList(View view) {
        Intent intent = new Intent(this, LoveListActivity.class);
        startActivity(intent);
    }

    public void clickToReturnHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickToViewCompareList(View view) {
        Intent intent = new Intent(this, CompareActivity.class);
        startActivity(intent);
    }

    private void getLaptop(final int index, String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Laptops/" + id).build();
        Type productType = Types.newParameterizedType(Laptop.class);
        final JsonAdapter<Laptop> jsonAdapter = moshi.adapter(productType);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if(index == 1){
                    lap1 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( lap1 != null && lap1.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(lap1.image1).into(image1);
                            } else {
                                image1.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else if(index == 2){
                    lap2 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( lap2 != null && lap2.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(lap2.image1).into(image2);
                            } else {
                                image2.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                countDownLatch.countDown();
            }
        });
        try{
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getCPU(final int index, String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/CPUs/" + id).build();
        Type productType = Types.newParameterizedType(CPU.class);
        final JsonAdapter<CPU> jsonAdapter = moshi.adapter(productType);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if(index == 1){
                    cpu1 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( cpu1 != null && cpu1.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(cpu1.image1).into(image1);
                            } else {
                                image1.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else if(index == 2){
                    cpu2 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( cpu2 != null && cpu2.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(cpu2.image1).into(image2);
                            } else {
                                image2.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                countDownLatch.countDown();
            }
        });
        try{
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getVGA(final int index, String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();
        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/VGAs/" + id).build();
        Type productType = Types.newParameterizedType(VGA.class);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final JsonAdapter<VGA> jsonAdapter = moshi.adapter(productType);

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if(index == 1){
                    vga1 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( vga1 != null && vga1.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(vga1.image1).into(image1);
                            } else {
                                image1.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else if(index == 2){
                    vga2 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( vga2 != null && vga2.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(vga2.image1).into(image2);
                            } else {
                                image2.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                countDownLatch.countDown();
            }
        });
        try{
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getHeadphone(final int index, String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Headphones/" + id).build();
        Type productType = Types.newParameterizedType(Headphone.class);
        final JsonAdapter<Headphone> jsonAdapter = moshi.adapter(productType);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if(index == 1){
                    hp1 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( hp1 != null && hp1.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(hp1.image1).into(image1);
                            } else {
                                image1.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else if(index == 2){
                    hp2 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( hp2 != null && hp2.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(hp2.image1).into(image2);
                            } else {
                                image2.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                countDownLatch.countDown();
            }
        });
        try{
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getMouse(final int index, String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Mouses/" + id).build();
        Type productType = Types.newParameterizedType(Mouse.class);
        final JsonAdapter<Mouse> jsonAdapter = moshi.adapter(productType);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if(index == 1){
                    mouse1 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( mouse1 != null && mouse1.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(mouse1.image1).into(image1);
                            } else {
                                image1.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else if(index == 2){
                    mouse2 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( mouse2 != null && mouse2.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(mouse2.image1).into(image2);
                            } else {
                                image2.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                countDownLatch.countDown();
            }
        });
        try{
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getKeyboard(final int index, String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        String domain = getResources().getString(R.string.virtual_api);
        Request request = new Request.Builder()
                .url(domain + "api/Keyboards/" + id).build();
        Type productType = Types.newParameterizedType(Keyboard.class);
        final JsonAdapter<Keyboard> jsonAdapter = moshi.adapter(productType);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if(index == 1){
                    keyb1 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( keyb1 != null && keyb1.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(keyb1.image1).into(image1);
                            } else {
                                image1.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else if(index == 2){
                    keyb2 = jsonAdapter.fromJson(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( keyb2 != null && keyb2.image1 != null) {
                                Picasso.with(CompareDetailActivity.this).load(keyb2.image1).into(image2);
                            } else {
                                image2.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                countDownLatch.countDown();
            }
        });
        try{
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


//    private void initializeProductAttribute(Object product) {
//        if (product instanceof Laptop) {
//            addTableRow("Category");
//            addTableRow("Name");
//            addTableRow("Brand");
//            addTableRow("Type");
//            addTableRow("Produce year");
//            addTableRow("Screen size");
//            addTableRow("Weight");
//            addTableRow("Chip");
//            addTableRow("RAM");
//            addTableRow("ROM");
//            addTableRow("Webcam");
//            addTableRow("Wifi");
//            addTableRow("Operating System");
//            addTableRow("Battery");
//            addTableRow("Average price");
//        }
//        if (product instanceof CPU) {
//            addTableRow("Category");
//            addTableRow("Name");
//            addTableRow("Brand");
//            addTableRow("Socket");
//            addTableRow("TDP");
//            addTableRow("Thread");
//            addTableRow("Clock Speed");
//            addTableRow("Weight");
//            addTableRow("Average price");
//        }
//        if (product instanceof Headphone) {
//            Headphone item = (Headphone) product;
//            addTableRow("Category");
//            addTableRow("Name");
//            addTableRow("Brand");
//            addTableRow("Type");
//            addTableRow("Micro");
//            addTableRow("Jack");
//            addTableRow("Frequency range");
//            addTableRow("Bluetooth");
//            addTableRow("Length");
//            addTableRow("Average price");
//        }
//        if (product instanceof Mouse) {
//            addTableRow("Category");
//            addTableRow("Name");
//            addTableRow("Brand");
//            addTableRow("Type");
//            addTableRow("Wireless");
//            addTableRow("Bluetooth");
//            addTableRow("Weight");
//            addTableRow("Average price");
//        }
//        if (product instanceof Keyboard) {
//            addTableRow("Category");
//            addTableRow("Name");
//            addTableRow("Brand");
//            addTableRow("Connection");
//            addTableRow("Bluetooth");
//            addTableRow("Height");
//            addTableRow("Length");
//            addTableRow("Width");
//            addTableRow("Average price");
//        }
//        if (product instanceof VGA) {
//            addTableRow("Category");
//            addTableRow("Name");
//            addTableRow("Brand");
//            addTableRow("Standard memory");
//            addTableRow("Max Screen Resolution");
//            addTableRow("Weight");
//            addTableRow("Size");
//            addTableRow("Average price");
//        }
//    }

//    private void initializeProductDetailTable1(Object product) {
//
//        if (product instanceof Laptop) {
//            Laptop item = (Laptop) product;
//            addTableProductDetail1(item.category);
//            addTableProductDetail1(item.name);
//            addTableProductDetail1(brandName1);
//            addTableProductDetail1(item.type);
//            addTableProductDetail1(item.year + "");
//            addTableProductDetail1(item.screenSize + " inches");
//            addTableProductDetail1(item.weight + " kg");
//            addTableProductDetail1(item.chip);
//            addTableProductDetail1(item.ram);
//            addTableProductDetail1(item.rom);
//            addTableProductDetail1(item.webcam);
//            addTableProductDetail1(item.wifi);
//            addTableProductDetail1(item.OS);
//            addTableProductDetail1(item.battery);
//            addTableProductDetail1(item.averagePrice + " VND");
//        }
//        if (product instanceof CPU) {
//            CPU item = (CPU) product;
//            addTableProductDetail1(item.category);
//            addTableProductDetail1(item.name);
//            addTableProductDetail1(brandName1);
//            addTableProductDetail1(item.socket + "");
//            addTableProductDetail1(item.TDP + "");
//            addTableProductDetail1(item.thread + "");
//            addTableProductDetail1(item.clockSpeed + "");
//            addTableProductDetail1(item.weight + "");
//            addTableProductDetail1(item.averagePrice + " VND");
//        }
//        if (product instanceof Headphone) {
//            Headphone item = (Headphone) product;
//            addTableProductDetail1(item.category);
//            addTableProductDetail1(item.name);
//            addTableProductDetail1(brandName1);
//            addTableProductDetail1(item.type);
//            addTableProductDetail1(item.micro);
//            addTableProductDetail1(item.jack);
//            addTableProductDetail1(item.frequencyRange);
//            addTableProductDetail1(item.bluetooth);
//            addTableProductDetail1(item.length + "m");
//            addTableProductDetail1(item.averagePrice + " VND");
//        }
//        if (product instanceof Mouse) {
//            Mouse item = (Mouse) product;
//            addTableProductDetail1(item.category);
//            addTableProductDetail1(item.name);
//            addTableProductDetail1(brandName1);
//            addTableProductDetail1(item.type);
//            addTableProductDetail1(item.wireless);
//            addTableProductDetail1(item.bluetooth);
//            addTableProductDetail1(item.weight + "kg");
//            addTableProductDetail1(item.averagePrice + " VND");
//        }
//        if (product instanceof Keyboard) {
//            Keyboard item = (Keyboard) product;
//            addTableProductDetail1(item.category);
//            addTableProductDetail1(item.name);
//            addTableProductDetail1(brandName1);
//            addTableProductDetail1(item.connect);
//            addTableProductDetail1(item.bluetooth);
//            addTableProductDetail1(item.height + "cm");
//            addTableProductDetail1(item.length + " cm");
//            addTableProductDetail1(item.width + " cm");
//            addTableProductDetail1(item.averagePrice + " VND");
//        }
//        if (product instanceof VGA) {
//            VGA item = (VGA) product;
//            addTableProductDetail1(item.category);
//            addTableProductDetail1(item.name);
//            addTableProductDetail1(brandName1);
//            addTableProductDetail1(item.standardMemory + "GB");
//            addTableProductDetail1(item.maxScreenResolution);
//            addTableProductDetail1(item.weight + "kg");
//            addTableProductDetail1(item.size);
//            addTableProductDetail1(item.averagePrice + " VND");
//        }
//    }
//    private void initializeProductDetailTable2(Object product) {
//
//        if (product instanceof Laptop) {
//            Laptop item = (Laptop) product;
//            addTableProductDetail2(item.category);
//            addTableProductDetail2(item.name);
//            addTableProductDetail2(brandName2);
//            addTableProductDetail2(item.type);
//            addTableProductDetail2(item.year + "");
//            addTableProductDetail2(item.screenSize + " inches");
//            addTableProductDetail2(item.weight + " kg");
//            addTableProductDetail2(item.chip);
//            addTableProductDetail2(item.ram);
//            addTableProductDetail2(item.rom);
//            addTableProductDetail2(item.webcam);
//            addTableProductDetail2(item.wifi);
//            addTableProductDetail2(item.OS);
//            addTableProductDetail2(item.battery);
//            addTableProductDetail2(item.averagePrice + " VND");
//        }
//        if (product instanceof CPU) {
//            CPU item = (CPU) product;
//            addTableProductDetail2(item.category);
//            addTableProductDetail2(item.name);
//            addTableProductDetail2(brandName2);
//            addTableProductDetail2(item.socket + "");
//            addTableProductDetail2(item.TDP + "");
//            addTableProductDetail2(item.thread + "");
//            addTableProductDetail2(item.clockSpeed + "");
//            addTableProductDetail2(item.weight + "");
//            addTableProductDetail2(item.averagePrice + " VND");
//        }
//        if (product instanceof Headphone) {
//            Headphone item = (Headphone) product;
//            addTableProductDetail2(item.category);
//            addTableProductDetail2(item.name);
//            addTableProductDetail2(brandName2);
//            addTableProductDetail2(item.type);
//            addTableProductDetail2(item.micro);
//            addTableProductDetail2(item.jack);
//            addTableProductDetail2(item.frequencyRange);
//            addTableProductDetail2(item.bluetooth);
//            addTableProductDetail2(item.length + "m");
//            addTableProductDetail2(item.averagePrice + " VND");
//        }
//        if (product instanceof Mouse) {
//            Mouse item = (Mouse) product;
//            addTableProductDetail2(item.category);
//            addTableProductDetail2(item.name);
//            addTableProductDetail2(brandName2);
//            addTableProductDetail2(item.type);
//            addTableProductDetail2(item.wireless);
//            addTableProductDetail2(item.bluetooth);
//            addTableProductDetail2(item.weight + "kg");
//            addTableProductDetail2(item.averagePrice + " VND");
//        }
//        if (product instanceof Keyboard) {
//            Keyboard item = (Keyboard) product;
//            addTableProductDetail2(item.category);
//            addTableProductDetail2(item.name);
//            addTableProductDetail2(brandName2);
//            addTableProductDetail2(item.connect);
//            addTableProductDetail2(item.bluetooth);
//            addTableProductDetail2(item.height + "cm");
//            addTableProductDetail2(item.length + " cm");
//            addTableProductDetail2(item.width + " cm");
//            addTableProductDetail2(item.averagePrice + " VND");
//        }
//        if (product instanceof VGA) {
//            VGA item = (VGA) product;
//            addTableProductDetail2(item.category);
//            addTableProductDetail2(item.name);
//            addTableProductDetail2(brandName2);
//            addTableProductDetail2(item.standardMemory + "GB");
//            addTableProductDetail2(item.maxScreenResolution);
//            addTableProductDetail2(item.weight + "kg");
//            addTableProductDetail2(item.size);
//            addTableProductDetail2(item.averagePrice + " VND");
//        }
//    }

//    private void addTableRow(String key) {
//        TextView col1 = new TextView(this);
//        TableRow row = new TableRow(this);
//
//        TableRow.LayoutParams lp = new TableRow.LayoutParams();
//        lp.weight = 1; //column weight
//        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.2f);
//        col1.setLayoutParams(lp);
//        col1.setPadding(10, 1, 10, 1);
//
//
//        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//
//        col1.setText(key);
//
//        row.addView(col1);
//        tblProductAttribute.addView(row);
//    }

//    private void addTableProductDetail1(String value) {
//        TextView col2 = new TextView(this);
//        TableRow row = new TableRow(this);
//
//        TableRow.LayoutParams lp = new TableRow.LayoutParams();
//        lp.weight = 1; //column weight
//
//        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.4f);
//        col2.setLayoutParams(lp);
//        col2.setPadding(10, 1, 10, 1);
//
//        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//
//        col2.setText(value);
//
//        row.addView(col2);
//
//        //Gia dinh
//        tbProductDetail.addView(row);
//    }
//    private void addTableProductDetail2(String value) {
//        TextView col2 = new TextView(this);
//        TableRow row = new TableRow(this);
//
//        TableRow.LayoutParams lp = new TableRow.LayoutParams();
//        lp.weight = 1; //column weight
//
//        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 04f);
//        col2.setLayoutParams(lp);
//        col2.setPadding(10, 1, 10, 1);
//
//        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//
//        col2.setText(value);
//
//        row.addView(col2);
//
//        //Gia dinh
//        tbProductDetail2.addView(row);
//    }


    //    private void checkCategory1(String category1, String id1, String id2, String brandId1, String brandId2) {
//        brandName1 = ((GlobalVariable) getApplication()).getBrandName(brandId1);
//        brandName2 = ((GlobalVariable) getApplication()).getBrandName(brandId2);
//        switch (category1) {
//            case "laptop":
//                getLaptop1(id1);
//                getLaptop2(id2);
//                break;
//            case "cpu":
//                getCPU1(id1);
//                getCPU2(id2);
//                break;
//            case "vga":
//                getVGA1(id1);
//                getVGA2(id2);
//                break;
//            case "headphone":
//                getHeadphone1(id1);
//                getHeadphone2(id2);
//                break;
//            case "mouse":
//                getMouse1(id1);
//                getMouse2(id2);
//                break;
//            case "keyboard":
//                getKeyboard1(id1);
//                getKeyboard2(id2);
//                break;
//        }
//    }

    //Get data from specific item in compare list
//    private void getLaptop1(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/Laptops/" + id).build();
//        Type productType = Types.newParameterizedType(Laptop.class);
//        final JsonAdapter<Laptop> jsonAdapter = moshi.adapter(productType);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final Laptop item1 = jsonAdapter.fromJson(json);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if ( item1.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item1.image1).into(image1);
//                        } else {
//                            image1.setVisibility(View.INVISIBLE);
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    private void getLaptop2(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/Laptops/" + id).build();
//        Type productType = Types.newParameterizedType(Laptop.class);
//        final JsonAdapter<Laptop> jsonAdapter = moshi.adapter(productType);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final Laptop item = jsonAdapter.fromJson(json);
//                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
//                        } else {
//                            image2.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductDetailTable2(item);
//                    }
//                });
//            }
//        });
//    }
//
//    private void getCPU1(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/CPUs/" + id).build();
//        Type productType = Types.newParameterizedType(CPU.class);
//        final JsonAdapter<CPU> jsonAdapter = moshi.adapter(productType);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final CPU item = jsonAdapter.fromJson(json);
//                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
//                        } else {
//                            image1.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductAttribute(item);
//                        initializeProductDetailTable1(item);
//                    }
//                });
//            }
//        });
//    }
//    private void getCPU2(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/CPUs/" + id).build();
//        Type productType = Types.newParameterizedType(CPU.class);
//        final JsonAdapter<CPU> jsonAdapter = moshi.adapter(productType);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final CPU item = jsonAdapter.fromJson(json);
//                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
//                        } else {
//                            image2.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductDetailTable2(item);
//                    }
//                });
//            }
//        });
//    }
//
//    private void getVGA1(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/VGAs/" + id).build();
//        Type productType = Types.newParameterizedType(VGA.class);
//        final JsonAdapter<VGA> jsonAdapter = moshi.adapter(productType);
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final VGA item = jsonAdapter.fromJson(json);
//                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
//                        } else {
//                            image1.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductAttribute(item);
//                        initializeProductDetailTable1(item);
//                    }
//                });
//            }
//        });
//    }
//    private void getVGA2(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/VGAs/" + id).build();
//        Type productType = Types.newParameterizedType(VGA.class);
//        final JsonAdapter<VGA> jsonAdapter = moshi.adapter(productType);
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final VGA item = jsonAdapter.fromJson(json);
//                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
//                        } else {
//                            image2.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductDetailTable2(item);
//                    }
//                });
//            }
//        });
//    }
//
//    private void getHeadphone1(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/Headphones/" + id).build();
//        Type productType = Types.newParameterizedType(Headphone.class);
//        final JsonAdapter<Headphone> jsonAdapter = moshi.adapter(productType);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final Headphone item = jsonAdapter.fromJson(json);
//                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
//                        } else {
//                            image1.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductAttribute(item);
//                        initializeProductDetailTable1(item);
//                    }
//                });
//            }
//        });
//    }
//    private void getHeadphone2(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/Headphones/" + id).build();
//        Type productType = Types.newParameterizedType(Headphone.class);
//        final JsonAdapter<Headphone> jsonAdapter = moshi.adapter(productType);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final Headphone item = jsonAdapter.fromJson(json);
//                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
//                        } else {
//                            image2.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductDetailTable2(item);
//                    }
//                });
//            }
//        });
//    }
//
//    private void getMouse1(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//
////        String domain = getResources().getString(R.string.home_api);
////        String domain = getResources().getString(R.string.school_api);
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/Mouses/" + id).build();
//        Type productType = Types.newParameterizedType(Mouse.class);
//        final JsonAdapter<Mouse> jsonAdapter = moshi.adapter(productType);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final Mouse item = jsonAdapter.fromJson(json);
//                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
//                        } else {
//                            image1.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductAttribute(item);
//                        initializeProductDetailTable1(item);
//                    }
//                });
//            }
//        });
//    }
//    private void getMouse2(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/Mouses/" + id).build();
//        Type productType = Types.newParameterizedType(Mouse.class);
//        final JsonAdapter<Mouse> jsonAdapter = moshi.adapter(productType);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final Mouse item = jsonAdapter.fromJson(json);
//                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
//                        } else {
//                            image2.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductDetailTable2(item);
//                    }
//                });
//            }
//        });
//    }
//
//    private void getKeyboard1(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/Keyboards/" + id).build();
//        Type productType = Types.newParameterizedType(Keyboard.class);
//        final JsonAdapter<Keyboard> jsonAdapter = moshi.adapter(productType);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final Keyboard item = jsonAdapter.fromJson(json);
//                generalProduct = new GeneralProduct(item.ID, item.category, item.name, item.brandId, item.image1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image1);
//                        } else {
//                            image1.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductAttribute(item);
//                        initializeProductDetailTable1(item);
//                    }
//                });
//            }
//        });
//    }
//    private void getKeyboard2(String id) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Moshi moshi = new Moshi.Builder().build();
//
//        String domain = getResources().getString(R.string.virtual_api);
//        Request request = new Request.Builder()
//                .url(domain + "api/Keyboards/" + id).build();
//        Type productType = Types.newParameterizedType(Keyboard.class);
//        final JsonAdapter<Keyboard> jsonAdapter = moshi.adapter(productType);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                final Keyboard item = jsonAdapter.fromJson(json);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (item.image1 != null) {
//                            Picasso.with(CompareDetailActivity.this).load(item.image1).into(image2);
//                        } else {
//                            image2.setVisibility(View.INVISIBLE);
//                        }
//                        initializeProductDetailTable2(item);
//                    }
//                });
//            }
//        });
//    }





//


}

package assignment.prm.chartmarkapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import assignment.prm.chartmarkapplication.Model.GeneralProduct;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GlobalVariable extends Application {
    private List<GeneralProduct> compareList;
    private List<GeneralProduct> loveList;
    private String compareCategory;
    private String brandName;
    public boolean isInCompareActivity = false, isInLoveActivity = false;


    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }

    public void loadCompareList() {
        SharedPreferences sharedPreferences = getSharedPreferences("assignment.prm.chartmarkapplication_preferences", MODE_PRIVATE);
        String compareCategory = sharedPreferences.getString("compareCategory", null);
        String json = sharedPreferences.getString("compareList", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<GeneralProduct>>() {
            }.getType();
            compareList = gson.fromJson(json, type);
        } else {
            compareList = new ArrayList<>();
        }
    }

    public String addToCompareList(GeneralProduct product) {
        String message = "";
        try {
            if (compareList == null) {
                compareList = new ArrayList<>();
                compareList.add(product);
                compareCategory = product.category;
            } else {
                if (compareList.isEmpty()) {
                    compareList.add(product);
                    compareCategory = product.category;

                    return "Product is added to Compare list";
                } else {
                    if (compareList.size() == 2) {
                        return "Add no more than 2 products to Compare List. Add FAIL";
                    }
                    if (compareCategory.equals(product.category)) {
                        if (checkInCompareList(product)) {
                            return "Product is already in compare list";
                        } else {
                            compareList.add(product);
                            message = "Product is added to Compare list";
                        }
                    } else {
                        return "This product is not a " + compareCategory + " to be added to Compare List. Add FAIL";
                    }
                }
            }
            saveCompareList();
            loadCompareList();
        } catch (Exception e) {
            e.printStackTrace();
            message = "Something go wrong, please try again";
        }
        return message;
    }

    public void saveCompareList() {
        SharedPreferences sharedPreferences = getSharedPreferences("assignment.prm.chartmarkapplication_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(compareList);
        editor.remove("compareList");
        editor.putString("compareList", json);
        editor.remove("compareCategory");
        editor.putString("compareCategory", compareCategory);
        editor.commit();
    }

    public String removeFromCompareList(GeneralProduct product) {
        String message;
        try {
            if (compareList != null) {
                if (compareList.contains(product)) {
                    compareList.remove(product);
                }
                if (compareList.isEmpty()) {
                    compareCategory = null;
                }
            }
            saveCompareList();
            loadCompareList();
            message = "Product is removed from Compare list";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Something go wrong, please try again";
        }
        return message;
    }

    public String emptyCompareList() {
        String message;
        try {
            if (compareList != null) {
                if (compareList.isEmpty()) {
                    compareCategory = null;
                } else {
                    compareList.clear();
                    compareCategory = null;
                }
            }
            saveCompareList();
            loadCompareList();
            message = "Compare list is cleared";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Something go wrong, please try again";
        }
        return message;
    }

    public void loadLoveList() {
        SharedPreferences sharedPreferences = getSharedPreferences("assignment.prm.chartmarkapplication_preferences", MODE_PRIVATE);

        String json = sharedPreferences.getString("lovelist", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<GeneralProduct>>() {
            }.getType();
            loveList = gson.fromJson(json, type);
        } else {
            loveList = new ArrayList<>();
        }
    }

    public String removeFromLoveList(GeneralProduct product) {
        String message;
        try {
            if (loveList != null) {
                if (loveList.contains(product)) {
                    loveList.remove(product);
                }
            }
            saveLoveList();
            loadLoveList();
            message = "Product is removed from Love list";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Something go wrong, please try again";
        }
        return message;
    }

    public String addToLoveList(GeneralProduct product) {
        String message;
        try {
            if (loveList == null) {
                loveList = new ArrayList<>();
            }
            if (checkInLoveList(product)) {
                return "Product is already in love list";
            }
            loveList.add(product);
            saveLoveList();
            loadLoveList();
            message = "Product is added to Love list";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Something go wrong, please try again";
        }
        return message;
    }

    public void saveLoveList() {
        SharedPreferences sharedPreferences = getSharedPreferences("assignment.prm.chartmarkapplication_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loveList);
        editor.remove("lovelist");
        editor.putString("lovelist", json);
        editor.commit();
    }

    public boolean checkInLoveList(GeneralProduct product) {
        if (loveList == null) {
            return false;
        }
        return loveList.contains(product);
    }

    public boolean checkInCompareList(GeneralProduct product) {
        if (compareList == null) {
            return false;
        }
        return compareList.contains(product);
    }

    public List<GeneralProduct> getLoveList() {
        return loveList;
    }

    public List<GeneralProduct> getCompareList() {
        return compareList;
    }

    public String getBrandName(String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/Brands/name/" + id;
        Request request = new Request.Builder().url(url).build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Get data API Error: ", e.getMessage());
                countDownLatch.countDown();
                brandName = "";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                brandName = response.body().string();
                brandName = brandName.substring(1);
                brandName = brandName.substring(0, brandName.length() - 1);
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brandName;
    }

}

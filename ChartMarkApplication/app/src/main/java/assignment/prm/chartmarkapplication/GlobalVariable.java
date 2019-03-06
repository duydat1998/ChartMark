package assignment.prm.chartmarkapplication;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import assignment.prm.chartmarkapplication.Model.GeneralProduct;


public class GlobalVariable extends Application {
    private List<Object> compareList;
    private List<GeneralProduct> loveList;
    private String compareCategory;

    public String getCompareCategory() {
        return compareCategory;
    }

    public void setCompareCategory(String compareCategory) {
        this.compareCategory = compareCategory;
    }

    public void loadCompareList(){

    }
    public String addToCompareList(){
        String message = "";
        return message;
    }

    public void loadLoveList(){
        SharedPreferences sharedPreferences = getSharedPreferences("assignment.prm.chartmarkapplication_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("lovelist","");
        if(!json.isEmpty()){
            loveList = gson.fromJson(json, List.class);
        } else {
            loveList = new ArrayList<>();
        }
    }

    public String removeFromLoveList(GeneralProduct product){
        String message;
        try{
            if(loveList != null){
                if(loveList.contains(product)){
                    loveList.remove(product);
                }
            }
            saveLoveList();
            loadLoveList();
            message = "Product is removed from Love list";
        } catch (Exception e){
            e.printStackTrace();
            message = "Something go wrong, please try again";
        }
        return message;
    }
    public String addToLoveList(GeneralProduct product){
        String message;
        try{
            if(loveList == null){
                loveList = new ArrayList<>();
            }
            loveList.add(product);
            saveLoveList();
            loadLoveList();
            message = "Product is added to Love list";
        } catch (Exception e){
            e.printStackTrace();
            message = "Something go wrong, please try again";
        }
        return message;
    }

    public void saveLoveList(){
        SharedPreferences sharedPreferences = getSharedPreferences("assignment.prm.chartmarkapplication_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loveList);
        editor.putString("lovelist", json);
        editor.commit();
    }

    public boolean checkInLoveList(GeneralProduct product){
        return loveList.contains(product);
    }

}

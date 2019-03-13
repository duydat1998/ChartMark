package assignment.prm.chartmarkapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import assignment.prm.chartmarkapplication.Adapter.GeneralProductAdapter;
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

public class SearchCategoryFragment extends Fragment {
    //Decalre for using findId in Fragment
    private View searchProduct;

    private DrawerLayout drawerLayout;

    private TextView txtCategory;
    private RecyclerView rvSearchProducts;
    private Spinner productCategory;

    //No matter
    public SearchCategoryFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Button btnSearch = getActivity().findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productCategory = searchProduct.findViewById(R.id.spnCategory);
                //category = productCategory.getSelectedItem().toString();

                EditText productName = searchProduct.findViewById(R.id.edtSearch);
                //getAPIDataProduct(category, productName);
                if((productCategory.toString() == "laptop" || productCategory.toString() == "headphone"
                        || productCategory.toString() == "keyboard" || productCategory.toString() == "vga"
                        || productCategory.toString() == "mouse" || productCategory.toString() == "cpu")
                        && !searchProduct.toString().trim().isEmpty()
                ){
                    showProducts(productName);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchProduct = inflater.inflate(R.layout.fragment_search_category, container, false);
        //initializeControl();
        return searchProduct;
    }


    public void clickToViewLoveList(View view) {
        Intent intent = new Intent(getActivity(), LoveListActivity.class);
        startActivity(intent);
    }

    public void clickToViewCompareList(View view) {
        Intent intent = new Intent(getActivity(), CompareActivity.class);
        startActivity(intent);
    }

    private void showProducts(EditText productName) {
        rvSearchProducts = searchProduct.findViewById(R.id.rvSearchProducts);
        rvSearchProducts.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type productType = Types.newParameterizedType(List.class, GeneralProduct.class);
        final JsonAdapter<List<GeneralProduct>> jsonAdapter = moshi.adapter(productType);

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request;

        switch (productCategory.toString()){
            case "laptop":
                request = new Request.Builder()
                        .url(domain + "api/Laptops/Search/" + productName).build();
                break;
            case "cpu":
                request = new Request.Builder()
                        .url(domain + "api/CPUs/Search/" + productName).build();
                break;
            case "vga":
                request = new Request.Builder()
                        .url(domain + "api/VGAs/Search/" + productName).build();
                break;
            case "headphone":
                request = new Request.Builder()
                        .url(domain + "api/HeadPhones/Search/" + productName).build();
                break;
            case "mouse":
                request = new Request.Builder()
                        .url(domain + "api/Mouses/Search/" + productName).build();
                break;
            case "keyboard":
                request = new Request.Builder()
                        .url(domain + "api/Keyboards/Search/" + productName).build();
                break;
            default:
                request = new Request.Builder()
                        .url(domain + "api/Laptops/Search").build();
        }

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Get data API Error: ", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                final List<GeneralProduct> generalProducts = jsonAdapter.fromJson(json);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvSearchProducts.setAdapter(new GeneralProductAdapter(generalProducts, getContext(), new GeneralProductAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(GeneralProduct item) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), ProductDetailActivity.class);
                                intent.putExtra("category", item.category);
                                intent.putExtra("id", item.ID+"");
                                startActivity(intent);
                            }
                        }));
                    }
                });
            }
        });
    }

}

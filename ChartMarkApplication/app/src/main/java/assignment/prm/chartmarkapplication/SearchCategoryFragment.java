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

    private RecyclerView rvSearchProducts;
    private TextView tvSearchResult;
    private String searchCategory, keyword;


    public SearchCategoryFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_category, container, false);
        rvSearchProducts = view.findViewById(R.id.rvSearchProducts);
        rvSearchProducts.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        tvSearchResult = view.findViewById(R.id.tvSearchResult);
        Bundle bundle = getArguments();
        searchCategory = bundle.getString("searchCategory", "laptop");
        keyword = bundle.getString("keyword", "");
        showProducts();
        return view;
    }


    private void showProducts() {

        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type productType = Types.newParameterizedType(List.class, GeneralProduct.class);
        final JsonAdapter<List<GeneralProduct>> jsonAdapter = moshi.adapter(productType);

//        String domain = getResources().getString(R.string.home_api);
//        String domain = getResources().getString(R.string.school_api);
        String domain = getResources().getString(R.string.virtual_api);
        Request request;

        switch (searchCategory){
            case "laptop":
                request = new Request.Builder()
                        .url(domain + "api/Laptops/Search/" + keyword).build();
                break;
            case "cpu":
                request = new Request.Builder()
                        .url(domain + "api/CPUs/Search/" + keyword).build();
                break;
            case "vga":
                request = new Request.Builder()
                        .url(domain + "api/VGAs/Search/" + keyword).build();
                break;
            case "headphone":
                request = new Request.Builder()
                        .url(domain + "api/HeadPhones/Search/" + keyword).build();
                break;
            case "mouse":
                request = new Request.Builder()
                        .url(domain + "api/Mouses/Search/" + keyword).build();
                break;
            case "keyboard":
                request = new Request.Builder()
                        .url(domain + "api/Keyboards/Search/" + keyword).build();
                break;
            default:
                request = new Request.Builder()
                        .url(domain + "api/Laptops/Search/").build();
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
                if(generalProducts.size() == 0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSearchResult.setText("No products found!");
                        }
                    });

                } else {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSearchResult.setText("Search results: "+ generalProducts.size()+" product(s)");
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

            }
        });
    }

}

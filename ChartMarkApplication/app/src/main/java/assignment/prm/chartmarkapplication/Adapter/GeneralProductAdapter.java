package assignment.prm.chartmarkapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import assignment.prm.chartmarkapplication.Model.GeneralProduct;
import assignment.prm.chartmarkapplication.R;

public class GeneralProductAdapter extends RecyclerView.Adapter<GeneralProductAdapter.GeneralProductItemViewHolder> {
    private List<GeneralProduct> generalProducts;
    private Context context;

    public GeneralProductAdapter(List<GeneralProduct> generalProducts, Context context){
        this.generalProducts = generalProducts;
        this.context = context;
    }
    @NonNull
    @Override
    public GeneralProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);
        return new GeneralProductItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralProductItemViewHolder view, int i) {
        GeneralProduct generalProduct = generalProducts.get(i);
        Picasso.with(context)
                .load(generalProduct.image1)
                .into(view.ivImageProduct);
        view.tvName.setText(generalProduct.name);
        view.tvBrand.setText("Brand: " + generalProduct.brandId);
    }

    @Override
    public int getItemCount() {
        return generalProducts.size();
    }

    public static class GeneralProductItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvBrand;
        public ImageView ivImageProduct;

        public GeneralProductItemViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tv_product_name);
            tvBrand =  itemView.findViewById(R.id.tv_brand);
            ivImageProduct = itemView.findViewById(R.id.iv_image);
        }
    }
}

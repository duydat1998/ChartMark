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
    public interface OnItemClickListener {
        void onItemClick(GeneralProduct item);
    }

    private List<GeneralProduct> generalProducts;
    private Context context;
    private final OnItemClickListener listener;

    public GeneralProductAdapter(List<GeneralProduct> generalProducts, Context context, OnItemClickListener listener){
        this.generalProducts = generalProducts;
        this.context = context;
        this.listener = listener;
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
//        view.tvBrand.setText("Brand: " + BrandAdapter.getBrandName(generalProduct.brandId, context));
        view.tvBrand.setText("Brand: "+ generalProduct.brandId);
        view.tvCategory.setText(generalProduct.category);
        view.tvID.setText(generalProduct.ID + "");
        view.bind(generalProducts.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return generalProducts.size();
    }

    public static class GeneralProductItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvBrand;
        public ImageView ivImageProduct;
        public TextView tvID;
        public TextView tvCategory;

        public GeneralProductItemViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tv_product_name);
            tvBrand =  itemView.findViewById(R.id.tv_brand);
            ivImageProduct = itemView.findViewById(R.id.iv_image);
            tvID = itemView.findViewById(R.id.tv_ID);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }

        public void bind(final GeneralProduct item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}

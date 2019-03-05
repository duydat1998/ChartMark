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

import assignment.prm.chartmarkapplication.Model.Headphone;
import assignment.prm.chartmarkapplication.R;

public class HeadphoneAdapter extends RecyclerView.Adapter<HeadphoneAdapter.HeadphoneItemViewHolder>{
    private List<Headphone> headphones;
    private Context context;

    @NonNull
    @Override
    public HeadphoneItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);
        return new HeadphoneItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadphoneItemViewHolder view, int i) {
        Headphone headphone = headphones.get(i);
        Picasso.with(context)
                .load(headphone.image1)
                .into(view.ivImageProduct);
        view.tvName.setText(headphone.name);
        view.tvBrand.setText(headphone.brandId);
    }

    @Override
    public int getItemCount() {
        return headphones.size();
    }

    public static class HeadphoneItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvBrand;
        public ImageView ivImageProduct;

        public HeadphoneItemViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tv_product_name);
            tvBrand =  itemView.findViewById(R.id.tv_brand);
            ivImageProduct = itemView.findViewById(R.id.iv_image);
        }
    }
}

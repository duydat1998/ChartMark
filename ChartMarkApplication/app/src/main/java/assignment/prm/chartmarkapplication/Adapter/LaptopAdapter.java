package assignment.prm.chartmarkapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import assignment.prm.chartmarkapplication.Model.Laptop;
import assignment.prm.chartmarkapplication.R;

public class LaptopAdapter extends RecyclerView.Adapter<LaptopAdapter.LaptopItemViewHolder> {
    private List<Laptop> laptops;
    private Context context;

    public LaptopAdapter(List<Laptop> laptops, Context c){
        this.laptops = laptops;
        this.context = c;
    }
    @Override
    public LaptopItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);
        return new LaptopItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( LaptopItemViewHolder laptopItemViewHolder, int i) {
        Laptop laptop = laptops.get(i);
        Picasso.with(context)
                .load(laptop.image1)
                .into(laptopItemViewHolder.ivImageProduct);
        laptopItemViewHolder.tvName.setText(laptop.name);
        laptopItemViewHolder.tvBrand.setText(laptop.brandId);
    }


    @Override
    public int getItemCount() {
        return laptops.size();
    }

    public static class LaptopItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvBrand;
        public ImageView ivImageProduct;

        public LaptopItemViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tv_product_name);
            tvBrand =  itemView.findViewById(R.id.tv_brand);
            ivImageProduct = itemView.findViewById(R.id.iv_image);
        }
    }
}


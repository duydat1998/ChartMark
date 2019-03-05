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

import assignment.prm.chartmarkapplication.Model.Mouse;
import assignment.prm.chartmarkapplication.Model.VGA;
import assignment.prm.chartmarkapplication.R;

public class VGAAdapter extends  RecyclerView.Adapter<VGAAdapter.VGAItemViewHolder> {
    private List<VGA> vgas;
    private Context context;

    public VGAAdapter(List<VGA> listItem, Context c){
        this.vgas = listItem;
        this.context = c;
    }

    @NonNull
    @Override
    public VGAItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);
        return new VGAItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VGAItemViewHolder view, int i) {
        VGA vga = vgas.get(i);
        Picasso.with(context)
                .load(vga.image1)
                .into(view.ivImageProduct);
        view.tvName.setText(vga.name);
        view.tvBrand.setText(vga.brandId);
    }

    @Override
    public int getItemCount() {
        return vgas.size();
    }

    public static class VGAItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvBrand;
        public ImageView ivImageProduct;

        public VGAItemViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tv_product_name);
            tvBrand =  itemView.findViewById(R.id.tv_brand);
            ivImageProduct = itemView.findViewById(R.id.iv_image);
        }
    }
}

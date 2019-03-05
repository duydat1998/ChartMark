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
import assignment.prm.chartmarkapplication.Model.Mouse;
import assignment.prm.chartmarkapplication.R;

public class MouseAdapter extends  RecyclerView.Adapter<MouseAdapter.MouseItemViewHolder>{
    private List<Mouse> mouses;
    private Context context;

    public MouseAdapter(List<Mouse> mouses, Context c){
        this.mouses = mouses;
        this.context = c;
    }
    @Override
    public MouseItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);
        return new MouseItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MouseItemViewHolder view, int i) {
        Mouse mouse = mouses.get(i);
        Picasso.with(context)
                .load(mouse.image1)
                .into(view.ivImageProduct);
        view.tvName.setText(mouse.name);
        view.tvBrand.setText(mouse.brandId);
    }


    @Override
    public int getItemCount() {
        return mouses.size();
    }

    public static class MouseItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvBrand;
        public ImageView ivImageProduct;

        public MouseItemViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tv_product_name);
            tvBrand =  itemView.findViewById(R.id.tv_brand);
            ivImageProduct = itemView.findViewById(R.id.iv_image);
        }
    }
}

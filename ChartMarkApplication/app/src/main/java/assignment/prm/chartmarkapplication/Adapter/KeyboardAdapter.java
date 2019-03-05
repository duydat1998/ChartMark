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

import assignment.prm.chartmarkapplication.Model.Keyboard;
import assignment.prm.chartmarkapplication.R;

public class KeyboardAdapter extends  RecyclerView.Adapter<KeyboardAdapter.KeyboardItemViewHolder> {
    private List<Keyboard> keyboards;
    private Context context;

    public KeyboardAdapter(List<Keyboard> keyboards, Context c){
        this.keyboards = keyboards;
        this.context = c;
    }
    @Override
    public KeyboardItemViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);
        return new KeyboardItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KeyboardItemViewHolder view, int i) {
        Keyboard keyboard = keyboards.get(i);
        Picasso.with(context)
                .load(keyboard.image1)
                .into(view.ivImageProduct);
        view.tvName.setText(keyboard.name);
        view.tvBrand.setText(keyboard.brandId);
    }

    @Override
    public int getItemCount() {
        return keyboards.size();
    }

    public static class KeyboardItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvBrand;
        public ImageView ivImageProduct;

        public KeyboardItemViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tv_product_name);
            tvBrand =  itemView.findViewById(R.id.tv_brand);
            ivImageProduct = itemView.findViewById(R.id.iv_image);
        }
    }
}

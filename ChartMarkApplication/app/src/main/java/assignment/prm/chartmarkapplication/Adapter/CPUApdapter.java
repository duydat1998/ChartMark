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

import assignment.prm.chartmarkapplication.Model.CPU;
import assignment.prm.chartmarkapplication.R;

public class CPUApdapter extends RecyclerView.Adapter<CPUApdapter.CPUItemViewHolder> {
    private List<CPU> cpus;
    private Context context;

    public CPUApdapter(List<CPU> list, Context c){
        this.cpus = list;
        this.context = c;
    }
    @NonNull
    @Override
    public CPUItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);
        return new CPUItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CPUItemViewHolder view, int i) {
        CPU cpu = cpus.get(i);
        Picasso.with(context)
                .load(cpu.image1)
                .into(view.ivImageProduct);
        view.tvName.setText(cpu.name);
        view.tvBrand.setText(cpu.brandId);
    }

    @Override
    public int getItemCount() {
        return cpus.size();
    }

    public static class CPUItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvBrand;
        public ImageView ivImageProduct;

        public CPUItemViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tv_product_name);
            tvBrand =  itemView.findViewById(R.id.tv_brand);
            ivImageProduct = itemView.findViewById(R.id.iv_image);
        }
    }
}

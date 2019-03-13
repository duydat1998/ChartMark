package assignment.prm.chartmarkapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import assignment.prm.chartmarkapplication.GlobalVariable;
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
        final GeneralProduct generalProduct = generalProducts.get(i);
        Picasso.with(context)
                .load(generalProduct.image1)
                .into(view.ivImageProduct);
        view.tvName.setText(generalProduct.name);
//        view.tvBrand.setText("Brand: " + BrandAdapter.getBrandName(generalProduct.brandId, context));
        view.tvBrand.setText("Brand: "+ generalProduct.brandId);
        view.tvCategory.setText(generalProduct.category);
        view.tvID.setText(generalProduct.ID + "");
        final Context context = GlobalVariable.getAppContext();
        final boolean isInLoveList = ((GlobalVariable) context.getApplicationContext()).checkInLoveList(generalProduct);
        final boolean isInCompareList = ((GlobalVariable) context.getApplicationContext()).checkInCompareList(generalProduct);
        if(isInLoveList){
            view.btnAddLove.setImageResource(R.drawable.heart_small_icon);
        }
        if(isInCompareList){
            view.btnAddCompare.setImageResource(R.drawable.icon_added);
        }
        view.btnAddCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((GlobalVariable) context.getApplicationContext()).checkInCompareList(generalProduct)){
                    String message = ((GlobalVariable) context.getApplicationContext()).removeFromCompareList(generalProduct);
                    if(!message.contains("wrong")){
                        ImageButton ib = (ImageButton) v;
                        ib.setImageResource(R.drawable.icon_add); }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } else {
                    String message = ((GlobalVariable) context.getApplicationContext()).addToCompareList(generalProduct);
                    if(!message.contains("Add FAIL")&&!message.contains("wrong")){
                        ImageButton ib = (ImageButton) v;
                        ib.setImageResource(R.drawable.added_small_icon);
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.btnAddLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((GlobalVariable) context.getApplicationContext()).checkInLoveList(generalProduct)){
                    String message = ((GlobalVariable) context.getApplicationContext()).removeFromLoveList(generalProduct);
                    if(!message.contains("wrong")){
                        ImageButton ib = (ImageButton) v;
                        ib.setImageResource(R.drawable.icon_hollow_heart);
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } else {
                    String message = ((GlobalVariable) context.getApplicationContext()).addToLoveList(generalProduct);
                    if(!message.contains("wrong")){
                        ImageButton ib = (ImageButton) v;
                        ib.setImageResource(R.drawable.heart_small_icon);
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        public ImageButton btnAddLove;
        public ImageButton btnAddCompare;

        public GeneralProductItemViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tv_product_name);
            tvBrand =  itemView.findViewById(R.id.tv_brand);
            ivImageProduct = itemView.findViewById(R.id.iv_image);
            tvID = itemView.findViewById(R.id.tv_ID);
            tvCategory = itemView.findViewById(R.id.tv_category);
            btnAddLove = itemView.findViewById(R.id.btn_add_love);
            btnAddCompare = itemView.findViewById(R.id.btn_add_compare);
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

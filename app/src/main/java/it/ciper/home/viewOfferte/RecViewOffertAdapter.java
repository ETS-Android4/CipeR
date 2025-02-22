package it.ciper.home.viewOfferte;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.ciper.MainActivity;
import it.ciper.R;
import it.ciper.data.DataCenter;
import it.ciper.data.dataClasses.product.ProductAndPriceAPI;
import it.ciper.home.viewProdotto.CreationOnCllickProductSheet;

public class RecViewOffertAdapter extends RecyclerView.Adapter<RecViewOffertAdapter.ViewHolder>{

    protected List<ProductAndPriceAPI> topFiveOfferts ;

    protected Activity activity;
    protected Context context;
    protected MainActivity mainActivity;
    protected DataCenter dataCenter;


    public void setParams(Activity activity, Context context, MainActivity mainActivity, DataCenter dataCenter){
        this.activity = activity;
        this.context = context;
        this.mainActivity = mainActivity;
        this.dataCenter = dataCenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 1:
                View viewMoreInfo = LayoutInflater.from(parent.getContext()).inflate(R.layout.offert_more_info_item, parent, false);
                ViewHolder holderMoreInfo = new ViewHolder(viewMoreInfo);
                return holderMoreInfo;
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offert_item, parent, false);
                ViewHolder holder = new ViewHolder(view);
                return holder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder.getItemViewType()==0) {
            holder.productName.setText(topFiveOfferts.get(position).getSummaryname());
            holder.oldPrice.setText(topFiveOfferts.get(position).getPrice().getPrice() + "€");
            holder.newPrice.setText(topFiveOfferts.get(position).getPrice().getOffertprice() + "€");

            Glide.with(holder.itemView)
                    .load(topFiveOfferts.get(position).getSrcimage())
                    .into(holder.productImage);

            Glide.with(holder.itemView)
                    .load(dataCenter.getShopAPI(topFiveOfferts.get(position).getPrice().getSellercod()).getSrclogo())
                    .into(holder.shopLogo);


            holder.offertItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayProduct(position);
                }
            });
        }
    }

    private void displayProduct(int position){
        CreationOnCllickProductSheet creationOnCllickProductSheet = new CreationOnCllickProductSheet();
        creationOnCllickProductSheet.setParams(activity,context,mainActivity,dataCenter,topFiveOfferts.get(position).getProduct());
        creationOnCllickProductSheet.display();
    }

    @Override
    public int getItemCount() {
        return topFiveOfferts.size()+1;
    }
    @Override
    public int getItemViewType(int position) {
        if (position<topFiveOfferts.size())
            return 0;
        return 1;
    }

    public void setTopFiveOfferts(List<ProductAndPriceAPI> offerts, DataCenter dataCenter) {
        topFiveOfferts = offerts;
        this.dataCenter=dataCenter;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView productName, oldPrice, newPrice;
        private ImageView productImage, shopLogo;
        private ConstraintLayout offertItem;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            offertItem = itemView.findViewById(R.id.offertItem);
            productName = itemView.findViewById(R.id.productName);
            oldPrice = itemView.findViewById(R.id.oldPrice);
            newPrice = itemView.findViewById(R.id.newPrice);
            productImage = itemView.findViewById(R.id.productImage);
            shopLogo = itemView.findViewById(R.id.shopLogoOffert);
        }
    }

    public class ViewHolderMoreInfo extends RecViewOffertAdapter.ViewHolder{

        public ViewHolderMoreInfo(@NonNull View itemView) {
            super(itemView);
        }
    }
}

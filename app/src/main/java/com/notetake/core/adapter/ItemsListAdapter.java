package com.notetake.core.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.notetake.core.dashdoors.R;
import com.notetake.core.db.FavoritesDataSource;
import com.notetake.core.model.RestaurantRespModel;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */


public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ViewHolder> {
    ArrayList<RestaurantRespModel> data;

    private WeakReference<Context> mContext;
    private List<Long> allFavs;
    private FavoritesDataSource favDataSource;
    private boolean isClickAllowed;

    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(mContext.get(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_single, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RestaurantRespModel restaurantRespModel = data.get(position);

        holder.itemTitle.setText(restaurantRespModel.name);
        holder.status.setText(restaurantRespModel.status);
        if (!TextUtils.isEmpty(restaurantRespModel.coverImageUrl)) {
            Picasso.with(mContext.get()).load(restaurantRespModel.coverImageUrl).error(R.drawable.ic_menu_gallery).
                    placeholder(R.drawable.ic_menu_gallery).
                    //transform(new CircleTransform()).
                            into(holder.thumbnailImage);
        } else {
            holder.thumbnailImage.setImageResource(R.drawable.ic_menu_gallery);
        }
        if(restaurantRespModel.isFavorite || allFavs.contains(Long.valueOf(restaurantRespModel.id))) {
            restaurantRespModel.isFavorite = true;
            holder.fav.setImageResource(R.mipmap.ic_favorite_black_24dp);
        } else {
            holder.fav.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
        }

        if(isClickAllowed) {
            holder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.fav.getDrawable().getConstantState().equals(//it is not a favorite
                            ContextCompat.getDrawable(v.getContext(),
                                    R.mipmap.ic_favorite_border_black_24dp).getConstantState())) {
                        data.get(position).isFavorite = true;
                        holder.fav.setImageResource(R.mipmap.ic_favorite_black_24dp);
                        if(favDataSource != null) {
                            favDataSource.createFavorite(data.get(position));
                        }

                    } else {
                        data.get(position).isFavorite = false;
                        holder.fav.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
                        if(favDataSource != null) {
                            favDataSource.deleteFavorite(data.get(position).id);
                        }
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public ItemsListAdapter(Context mContext, ArrayList<RestaurantRespModel> data, List<Long> allFavIds, FavoritesDataSource favDataSource, boolean isClickAllowed) {
        this.data = data;
        this.mContext = new WeakReference<>(mContext);
        this.allFavs = allFavIds;
        this.favDataSource = favDataSource;
        this.isClickAllowed = isClickAllowed;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle;
        public ImageView thumbnailImage;
        public View nameHolder;
        public TextView status;
        public ImageView fav;

        ViewHolder(View v) {
            super(v);
            itemTitle = (TextView) v
                    .findViewById(R.id.name);
            thumbnailImage = (ImageView) v.findViewById(R.id.image);
            nameHolder = v.findViewById(R.id.nameHolder);
            status = (TextView) v.findViewById(R.id.time);
            fav = (ImageView) v.findViewById(R.id.fav);
        }
    }
}


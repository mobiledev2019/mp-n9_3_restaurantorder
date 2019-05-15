package com.e15cn2.restaurantorder.screen.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;

import java.util.List;

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private List<T> mData;
    private @LayoutRes
    int mLayoutRes;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;

    public BaseAdapter(Context context, int layoutRes) {
        mLayoutRes = layoutRes;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(mInflater, mLayoutRes, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mView.setVariable(BR.item, mData.get(i));
        viewHolder.mView.setVariable(BR.listener, mListener);
        viewHolder.mView.setVariable(BR.position, i);
        viewHolder.mView.executePendingBindings();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mView;


        public ViewHolder(@NonNull ViewDataBinding itemView) {
            super(itemView.getRoot());
            mView = itemView;
        }

        public void removeItem() {
            try {
                mData.remove(getAdapterPosition());
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnItemClickListener {

    }
}

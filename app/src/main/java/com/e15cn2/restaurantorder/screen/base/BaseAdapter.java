package com.e15cn2.restaurantorder.screen.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.android.databinding.library.baseAdapters.BR;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.model.Item;
import com.e15cn2.restaurantorder.data.model.Menu;
import com.e15cn2.restaurantorder.data.model.Table;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder>
        implements Filterable {
    private List<T> mData;
    private @LayoutRes
    int mLayoutRes;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;
    private List<T> mSearchData;
    private MyFilter mFilter;

    public BaseAdapter(Context context, int layoutRes) {
        mLayoutRes = layoutRes;
        mInflater = LayoutInflater.from(context);
        mFilter = new MyFilter();
    }

    public void setData(List<T> data) {
        mData = data;
        mSearchData = data;
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
        return mSearchData == null ? 0 : mSearchData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mView.setVariable(BR.item, mSearchData.get(i));
        viewHolder.mView.setVariable(BR.listener, mListener);
        viewHolder.mView.setVariable(BR.position, i);
        viewHolder.mView.executePendingBindings();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
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

    public class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<T> listResults = new ArrayList<>();
            for (T t : mData) {
                if (t instanceof Item) {
                    if (((Item) t).getName().toLowerCase().contains(constraint.toString().toLowerCase())
                            || ((Item) t).getDescription().toLowerCase().contains(constraint.toString().toLowerCase())
                            || String.valueOf(((Item) t).getPrice()).toLowerCase().contains(constraint.toString().toLowerCase())) {
                        listResults.add(t);
                    }
                }
                if (t instanceof Table) {
                    if (((Table) t).getNumber().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        listResults.add(t);
                    }
                }

                if (t instanceof Menu) {
                    if (((Menu) t).getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        listResults.add(t);
                    }
                }
                if (t instanceof Cart) {
                    if (((Cart) t).getUser().getName().toLowerCase().contains(constraint.toString().toLowerCase())
                            || ((Cart) t).getTable().getNumber().contains(constraint.toString())) {
                        listResults.add(t);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.count = listResults.size();
            filterResults.values = listResults;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mSearchData = (List<T>) results.values;
            notifyDataSetChanged();
        }

    }

    public interface OnItemClickListener {

    }
}

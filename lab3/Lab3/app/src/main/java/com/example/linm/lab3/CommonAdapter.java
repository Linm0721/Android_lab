package com.example.linm.lab3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.example.linm.lab3.ViewHolder;
import java.util.List;

/**
 * Created by ACER on 2017/10/23.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    private OnItemClickListener mOnItemClickListener = null;
    public CommonAdapter(Context context, int layoutId, List<T> datas){
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position){
        convert(holder, mDatas.get(position));

        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener( new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }

    }
    public abstract void convert(ViewHolder holder, T t);


    @Override
    public int getItemCount(){
        return mDatas.size();
    }


    public void removeItem(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
    public interface  OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
}

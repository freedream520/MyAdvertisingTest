package com.hbln.myadvertisingtest.FrameWork;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015/10/29.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter<T>.MyViewHolder> {
    /**
     * 数据源
     */
    protected List<T> mDatas = null;

    /**
     * 上下文对象
     */
    protected Context context = null;

    /**
     * item布局文件的资源ID
     */
    protected int itemLayoutResId = 0;

    public BaseRecyclerAdapter(Context context, List<T> mDatas, int itemLayoutResId) {
        this.context = context;
        this.mDatas = mDatas;
        this.itemLayoutResId = itemLayoutResId;
    }

    public void setData(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return this.mDatas;
    }

    public T getData(int position) {
        return this.mDatas.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(itemLayoutResId, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        convert(holder, mDatas.get(position), position);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(int position, T item) {
        mDatas.add(position, item);
        notifyItemInserted(position);
    }

    public void addDataLast(T item) {
        int position = mDatas.size();
        mDatas.add(position, item);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 开发者实现该方法，进行业务处理
     */
    public abstract void convert(MyViewHolder viewHolder, T item, int position);

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * 存储item中所用控件引用的容器
         * <p/>
         * Key - 资源ID Value - 控件的引用
         */
        private SparseArray<View> views = null;
        private View convertView = null;

        public MyViewHolder(View view) {
            super(view);
            this.views = new SparseArray<>();
            this.convertView = view;
        }

        public View getConvertView() {
            return convertView;
        }

        /**
         * 【核心部分】 根据控件的资源ID，获取控件
         *
         * @param viewResId 控件的资源ID
         * @return 控件的引用
         */
        public <E extends View> E getView(int viewResId) {
            View view = views.get(viewResId);
            if (view == null) {
                view = convertView.findViewById(viewResId);
                views.put(viewResId, view);
            }
            return (E) view;
        }

        public MyViewHolder setText(int viewResId, String text) {
            TextView tv = getView(viewResId);
            tv.setText(text);
            return this;
        }

        public MyViewHolder setImageResource(int viewResId, int resId) {
            ImageView iv = getView(viewResId);
            iv.setImageResource(resId);
            return this;
        }

        public MyViewHolder setImageWithUrl(int viewResId, String url) {
            ImageView iv = getView(viewResId);
            UtilFactory.getImageLoadUtil().display(url, iv);
            return this;
        }
    }
}


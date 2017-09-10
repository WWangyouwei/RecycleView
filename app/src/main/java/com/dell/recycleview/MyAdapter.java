package com.dell.recycleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.List;

/**
 * 姓名：王有为
 * 时间：2017/9/9.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public List<Bean.DataBean> list;
    public Context context;
    private MainActivity mainActivity;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    public MyAdapter( Context context,List<Bean.DataBean> list) {
        this.list = list;
        this.context = context;
        mainActivity=(MainActivity) context;
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                        .createDefault(context);

                //将configuration配置到imageloader中
                imageLoader= ImageLoader.getInstance();
                imageLoader.init(configuration);

                options=new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .showImageOnLoading(R.mipmap.ic_launcher)
                        .showImageForEmptyUri(R.mipmap.ic_launcher)
                        .showImageOnFail(R.mipmap.ic_launcher)
                        .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Bean.DataBean dataBean = list.get(position);

            viewHolder.checkBox.setChecked(dataBean.isChecked());
            imageLoader.displayImage(list.get(position).getImage_url(),viewHolder.image2,options);
            viewHolder.mTextname.setText(dataBean.getTitle());
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //先判断是否选中
                    Bean.DataBean currentDataBean = list.get(position);
                    boolean currentChecked = currentDataBean.isChecked();
                    currentDataBean.setChecked(!currentChecked);
                    notifyDataSetChanged();

                    if (viewHolder.checkBox.isChecked()) {
                        //去判断其它按钮是否都选中
                        mainActivity.setCb(isAllChecked());

                    } else {
                        //如果点击的按钮取消掉，即未选中状态，则去判断全选按钮是否是选中状态，如果是则取消选中
                        mainActivity.setCb(false);
                    }
                }
            });
    }
    private boolean isAllChecked() {
        for (int i = 0; i < list.size(); i++) {
            Bean.DataBean dataBeans = list.get(i);
            if (!dataBeans.isChecked()) {
                return false;
            }
        }
        return true;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextname;
        public CheckBox checkBox;
        public ImageView image2;
        public ViewHolder(View view){
            super(view);
            mTextname = (TextView) view.findViewById(R.id.name);
            checkBox=(CheckBox)view.findViewById(R.id.check1);
            image2=(ImageView)view.findViewById(R.id.image2);
        }
    }
    public void selectedAll(){

        for (int i = 0; i < list.size() ; i++){
            Bean.DataBean bean = list.get(i);
            bean.setChecked(true);
        }
        notifyDataSetChanged();
    }
    public void cancleAll() {
        for (int i = 0; i < list.size(); i++) {
            Bean.DataBean dataBean = list.get(i);
            dataBean.setChecked(false);
        }
        notifyDataSetChanged();
    }
}

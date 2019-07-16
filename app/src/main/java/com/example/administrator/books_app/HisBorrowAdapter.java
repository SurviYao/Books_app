package com.example.administrator.books_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.books_app.utils.HttpUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HisBorrowAdapter extends RecyclerView.Adapter<HisBorrowAdapter.ViewHolder> {
    private Context context;
    private List<BookJYInfo> infos;
    private LayoutInflater inflater;
    private View.OnClickListener listener;

    public HisBorrowAdapter(Context context, List<BookJYInfo> infos, View.OnClickListener listener) {
        this.context = context;
        this.infos = infos;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_his_borrow_item, parent, false);
        return new ViewHolder(view);
    }

    public void deleteItem(int position) {
        infos.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvBookaddress.setText(infos.get(position).getStartdate());
        holder.tvBookname.setText(infos.get(position).getBookname());
        holder.tvStartdate.setText(infos.get(position).getEnddate());
        holder.tvState.setText("删除");
        holder.tvState.setTextColor(0xffff0000);
        holder.tvState.setTag(position);
        holder.tvState.setOnClickListener(listener);
        ImageLoader.getInstance()
                .displayImage(HttpUtil.getInstance()
                                .getUrl() + "img/" +
                                infos.get(position).getImage(),
                        holder.ivBorrow, MyApp.options);
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_borrow)
        ImageView ivBorrow;
        @BindView(R.id.tv_bookname)
        TextView tvBookname;
        @BindView(R.id.tv_bookaddress)
        TextView tvBookaddress;
        @BindView(R.id.tv_startdate)
        TextView tvStartdate;
        @BindView(R.id.tv_state)
        TextView tvState;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

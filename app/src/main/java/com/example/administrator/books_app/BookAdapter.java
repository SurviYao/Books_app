package com.example.administrator.books_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.books_app.utils.HttpUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context context;
    private List<Book> books;
    private LayoutInflater inflater;
    private View.OnClickListener listener;

    public BookAdapter(Context context, List<Book> books,View.OnClickListener listener) {
        this.context = context;
        this.books = books;
        this.inflater = LayoutInflater.from(context);
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvBook.setText(books.get(position).getBookname());
        ImageLoader.getInstance().displayImage
                (HttpUtil.getInstance().getUrl()+"img/"
                        +books.get(position).getImage(),
                        holder.ivImage,MyApp.options);
        holder.llItem.setTag(position);
        holder.llItem.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_book)
        TextView tvBook;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

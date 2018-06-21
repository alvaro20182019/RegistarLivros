package pt.ipg.registarlivros;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ÁlvaroSF on 19/06/2018.
 */

public class BooksCursorAdapter extends RecyclerView.Adapter<BooksCursorAdapter.bookViewHolder> {
    private  Context context;

    private Cursor cursor=null;

    public BooksCursorAdapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override

    public bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_book,parent,false);
        return new bookViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull bookViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class bookViewHolder extends RecyclerView.ViewHolder{
        public bookViewHolder(View intemView) {
            super(intemView);
        }
    }
}

package pt.ipg.registarlivros;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ÁlvaroSF on 19/06/2018.
 */

public class BooksCursorAdapter extends RecyclerView.Adapter<BooksCursorAdapter.bookViewHolder> {


    @NonNull
    @Override
    public bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
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

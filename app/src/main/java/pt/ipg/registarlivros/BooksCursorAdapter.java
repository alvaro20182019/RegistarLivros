package pt.ipg.registarlivros;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ÁlvaroSF on 19/06/2018.
 */

public class BooksCursorAdapter extends RecyclerView.Adapter<BooksCursorAdapter.bookViewHolder> {
    private  Context context;
    private Cursor cursor=null;
    private View.OnClickListener viewHolderClickListener = null;
    private int lastBookClicked = -1;
    public BooksCursorAdapter(Context context){
        this.context=context;
    }

    public void atualizarDados(Cursor cursor){
        if(this.cursor!=cursor){
            this.cursor=cursor;
            notifyDataSetChanged();
        }
    }

    public void setViewHolderClickListener(View.OnClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
    }

    public int getLastBookClicked() {

        return lastBookClicked;
    }

    @NonNull
    @Override
    public bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_book,parent,false);
        return new bookViewHolder(item);
    }


    @Override
    public void onBindViewHolder(@NonNull bookViewHolder holder, int position) {
        cursor.moveToPosition(position);
        Book book = DbTableBookss.getCurrentBookFromCursor(cursor);
        holder.setBook(book);
    }

    @Override
    public int getItemCount() {
      if(cursor==null) {
          return 0;
      }
      return cursor.getCount();
    }

    public class bookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewTitle;
        private TextView textViewState;
        private int id_book;
        public bookViewHolder(View itemView) {
            super(itemView);
            textViewState= (TextView) itemView.findViewById(R.id.textViewEstado);
            textViewTitle= (TextView) itemView.findViewById(R.id.textViewTitle);
            itemView.setOnClickListener(this);
        }

        public void setBook(Book book){
            textViewTitle.setText(book.getTitle());
            textViewState.setText(book.getState());
            id_book=book.getId();
        }

        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position == RecyclerView.NO_POSITION) {
                return;
            }

            if (viewHolderClickListener != null) {
                lastBookClicked = id_book;
                viewHolderClickListener.onClick(v);
            }
        }
    }
}

package info.androidhive.sqlite.view;

/**
 * Created by Diego Costa
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import info.androidhive.sqlite.R;
import info.androidhive.sqlite.database.model.Livro;

public class LivrosAdapter extends RecyclerView.Adapter<LivrosAdapter.MyViewHolder> {

    private Context context;
    private List<Livro> livrosList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView livro;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            livro = view.findViewById(R.id.livro);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }


    public LivrosAdapter(Context context, List<Livro> livrosList) {
        this.context = context;
        this.livrosList = livrosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Livro livro = livrosList.get(position);

        holder.livro.setText(livro.getLivro());

        // exibe ponto na tela
        holder.dot.setText(Html.fromHtml("&#910021;"));

        // Formata e exibe a data
        holder.timestamp.setText(formatDate(livro.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return livrosList.size();
    }

    /**
     * Formatando a data
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}

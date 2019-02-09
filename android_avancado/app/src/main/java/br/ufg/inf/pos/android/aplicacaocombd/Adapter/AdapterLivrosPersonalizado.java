package br.ufg.inf.pos.android.aplicacaocombd.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.ufg.inf.pos.android.aplicacaocombd.R;
import br.ufg.inf.pos.android.aplicacaocombd.model.Livro;

public class AdapterLivrosPersonalizado extends BaseAdapter {

    private final List<Livro> livros;
    private final Activity activity;

    public AdapterLivrosPersonalizado(List<Livro> livros, Activity activity) {
        this.livros = livros;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.livros.size();
    }

    @Override
    public Object getItem(int position) {
        return this.livros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.livros.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.activity.getLayoutInflater().inflate(R.layout.lista_livros_personalizada, parent, false);
        Livro livro = livros.get(position);

        TextView txtViewTitulo = (TextView) view.findViewById(R.id.lista_livro_personalizada_titulo);
        TextView txtViewAutor = (TextView) view.findViewById(R.id.lista_livro_personalizada_autor);
        TextView txtViewEditora = (TextView) view.findViewById(R.id.lista_livro_personalizada_editora);

        Log.e("erro",livro.toString());
        txtViewTitulo.setText(livro.getTitulo());
        txtViewAutor.setText(livro.getAutor());
        txtViewEditora.setText(livro.getEditora());

        return view;
    }
}

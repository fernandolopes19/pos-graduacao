package br.ufg.inf.pos.android.aplicacaocombd.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pos.android.aplicacaocombd.Adapter.AdapterLivrosPersonalizado;
import br.ufg.inf.pos.android.aplicacaocombd.R;
import br.ufg.inf.pos.android.aplicacaocombd.controller.BDController;
import br.ufg.inf.pos.android.aplicacaocombd.model.Livro;

public class ListagemLivrosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_livros);

        ListView listaLivros = (ListView) findViewById(R.id.listaLivros);
        BDController bdController = new BDController(getBaseContext());

        List<Livro> livros = bdController.listarLivros();

        AdapterLivrosPersonalizado adapter = new AdapterLivrosPersonalizado(livros, this);
        listaLivros.setAdapter(adapter);
    }
}

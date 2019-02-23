package br.ufg.inf.pos.android.aplicacaocombd.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pos.android.aplicacaocombd.Adapter.AdapterLivrosPersonalizado;
import br.ufg.inf.pos.android.aplicacaocombd.R;
import br.ufg.inf.pos.android.aplicacaocombd.controller.BDController;
import br.ufg.inf.pos.android.aplicacaocombd.model.Livro;

public class ListagemLivrosActivity extends AppCompatActivity {

    private List<Livro> livros = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_livros);

        final ListView listaLivros = (ListView) findViewById(R.id.listaLivros);
        BDController bdController = new BDController(getBaseContext());

        this.livros = bdController.listarLivros();

        AdapterLivrosPersonalizado adapter = new AdapterLivrosPersonalizado(livros, this);
        listaLivros.setAdapter(adapter);

        listaLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TAG", "usuário clicou no item:" + listaLivros.getItemAtPosition(position).toString());
                Toast.makeText(getApplicationContext(), "Você clicou em: " + livros.get(position).toString(), Toast.LENGTH_LONG).show();
                showActionsDialog(position);
            }
        });



    }

    public void carregarTelaAdicaoLivro(View view) {
        Intent intent = new Intent(this, CadastroLivroActivity.class);
        startActivity(intent);
    }

    public void removeLivro() {

    }

    public void updateLivro() {

    }


    /**
     * Dialogo de edicao -  OPCAO DELETAR
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Editar", "Apagar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha um opcao!!!");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    carregarTelaAdicaoLivro(null);
                } else {
                    BDController bdController = new BDController(getApplicationContext());
                    livros.remove(position);
                    bdController.removerLivro(livros.get(position));
                }
            }
        });
        builder.show();
    }

}

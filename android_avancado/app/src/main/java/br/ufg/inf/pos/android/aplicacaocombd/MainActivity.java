package br.ufg.inf.pos.android.aplicacaocombd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.ufg.inf.pos.android.aplicacaocombd.view.CadastroLivroActivity;
import br.ufg.inf.pos.android.aplicacaocombd.view.ListagemLivrosActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void carregarTelaCadastro(View view){
        Intent intent = new Intent(this, CadastroLivroActivity.class);
        startActivity(intent);
    }

    public void carregarTelaListagem(View view ){
        Intent intent = new Intent(this, ListagemLivrosActivity.class);
        startActivity(intent);
    }
}

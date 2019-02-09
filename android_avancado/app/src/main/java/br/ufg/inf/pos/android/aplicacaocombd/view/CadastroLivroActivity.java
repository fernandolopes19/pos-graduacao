package br.ufg.inf.pos.android.aplicacaocombd.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufg.inf.pos.android.aplicacaocombd.R;
import br.ufg.inf.pos.android.aplicacaocombd.controller.BDController;

public class CadastroLivroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        Button btnCadastro = (Button) findViewById(R.id.btnCadastro);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDController crud = new BDController(getBaseContext());

                EditText titulo = (EditText) findViewById(R.id.edtTitulo);
                EditText autor = (EditText) findViewById(R.id.edtAutor);
                EditText editora = (EditText) findViewById(R.id.edtEditora);

                String tituloStr = titulo.getText().toString();
                String autorStr = autor.getText().toString();
                String editoraStr = editora.getText().toString();

                String resultado;

                resultado = crud.insereDado(tituloStr, autorStr, editoraStr);
                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
            }
        });

    }
}

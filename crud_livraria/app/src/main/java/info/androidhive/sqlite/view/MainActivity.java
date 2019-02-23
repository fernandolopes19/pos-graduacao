package info.androidhive.sqlite.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.sqlite.R;
import info.androidhive.sqlite.database.DatabaseHelper;
import info.androidhive.sqlite.database.model.Livro;
import info.androidhive.sqlite.utils.MyDividerItemDecoration;
import info.androidhive.sqlite.utils.RecyclerTouchListener;

public class MainActivity extends AppCompatActivity {
    private LivrosAdapter mAdapter;
    private List<Livro> livrosList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noLivrosView;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        recyclerView = findViewById(R.id.recycler_view);
        noLivrosView = findViewById(R.id.empty_livros_view);

        db = new DatabaseHelper(this);

        livrosList.addAll(db.getAllLivros());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLivroDialog(false, null, -1);
            }
        });

        mAdapter = new LivrosAdapter(this, livrosList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyLivros();

        /**
         * On long press on RecyclerView item, open alert dialog
         * */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    /**
     * Insere livro e atualiza a lista
     */
    private void createLivro(String livro) {
        // insere no banco e obtem a nova linha
        long id = db.insertLivro(livro);

        // get the newly inserted note from db
        Livro n = db.getLivro(id);

        if (n != null) {
            // add na lista a partir da posicao 0
            livrosList.add(0, n);

            // atualiza lista
            mAdapter.notifyDataSetChanged();

            toggleEmptyLivros();
        }
    }

    /**
     * Update no banco e atualiza indice na lista
     */
    private void updateLivro(String livro, int position) {
        Livro n = livrosList.get(position);
        // updating note text
        n.setLivro(livro);

        // atualiza no bd
        db.updateLivro(n);

        // atualiza lista
        livrosList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyLivros();
    }

    /**
     * Deleting livro no BD e atualiza posicao
     */
    private void deleteLivro(int position) {
        // apaga livro db
        db.deleteLivro(livrosList.get(position));

        // remove da lista
        livrosList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyLivros();
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
                    showLivroDialog(true, livrosList.get(position), position);
                } else {
                    deleteLivro(position);
                }
            }
        });
        builder.show();
    }


    /**
     * Alert de inserir ou editar
     * recupera dados ja salvos
     */
    private void showLivroDialog(final boolean shouldUpdate, final Livro livro, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.note_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputLivro = view.findViewById(R.id.livro);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_livro_title) : getString(R.string.lbl_edit_livro_title));

        if (shouldUpdate && livro != null) {
            inputLivro.setText(livro.getLivro());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "atualizar" : "salvar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mostra mensagem quando nao tem nenhum texto digitado
                if (TextUtils.isEmpty(inputLivro.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Digite o nome do livro!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // checa se o livro foi atualizado
                if (shouldUpdate && livro != null) {
                    // atualiza pelo ID
                    updateLivro(inputLivro.getText().toString(), position);
                } else {
                    // cria novo
                    createLivro(inputLivro.getText().toString());
                }
            }
        });
    }

    /**
     * lista vazia
     */
    private void toggleEmptyLivros() {
        // you can check notesList.size() > 0

        if (db.getLivrosCount() > 0) {
            noLivrosView.setVisibility(View.GONE);
        } else {
            noLivrosView.setVisibility(View.VISIBLE);
        }
    }
}

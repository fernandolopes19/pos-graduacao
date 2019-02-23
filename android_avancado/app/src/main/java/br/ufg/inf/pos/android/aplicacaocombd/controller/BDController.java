package br.ufg.inf.pos.android.aplicacaocombd.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pos.android.aplicacaocombd.dao.SQLConnection;
import br.ufg.inf.pos.android.aplicacaocombd.model.Livro;

public class BDController {
    private SQLiteDatabase db;
    private SQLConnection banco;

    public BDController(Context context){
        this.banco = new SQLConnection(context);
    }

    public String insereDado(String titulo, String autor, String editora){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(SQLConnection.TITULO,titulo);
        valores.put(SQLConnection.AUTOR, autor);
        valores.put(SQLConnection.EDITORA, editora);

        resultado = db.insert(SQLConnection.TABELA, null, valores);
        db.close();

        if(resultado == -1){
            return "Erro ao inserir livro";
        } else {
            return "Livro inserido com sucesso";
        }
    }

    public List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + SQLConnection.TABELA;

        db = banco.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            String id = cursor.getString(0);
            String titulo = cursor.getString(1);
            String autor = cursor.getString(2);
            String editora = cursor.getString(3);
            livros.add( new Livro(id, titulo, autor, editora));
        }
        while(cursor.moveToNext()){
            String id = cursor.getString(0);
            String titulo = cursor.getString(1);
            String autor = cursor.getString(2);
            String editora = cursor.getString(3);
            livros.add( new Livro(id, titulo, autor, editora) );
        }
        cursor.close();
        return livros;
    }

    public String atualizarLivro(Livro livro){
        db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLConnection.TITULO, livro.getTitulo());
        values.put(SQLConnection.AUTOR, livro.getAutor());
        values.put(SQLConnection.EDITORA, livro.getEditora());

        int resultado = db.update(SQLConnection.TABELA, values,
                SQLConnection.ID + " = ?", new String[]{String.valueOf(livro.getId())});

        if(resultado == -1){
            return "Erro ao atualizar o livro";
        } else {
            return "Livro atualizado com sucesso";
        }
    }



    public String removerLivro(Livro livro){
        db = banco.getWritableDatabase();

        int resultado = db.delete(SQLConnection.TABELA, SQLConnection.ID + " = ?", new String[]{String.valueOf(livro.getId())});
        String resposta;
        if (resultado == -1){
            return "Erro ao remover o livro";
        } else {
            return "Livro removido com sucesso";
        }
    }


}

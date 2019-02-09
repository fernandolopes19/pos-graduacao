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
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
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



    public String removerDado(String titulo){
        ContentValues valor;
        long resultado;

        db = banco.getWritableDatabase();
        valor = new ContentValues();
        valor.remove(titulo);

        //resultado = db.delete(SQLConnection.TABELA, "titulo", );
        return null;
    }


}

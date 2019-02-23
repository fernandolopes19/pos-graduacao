package info.androidhive.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.sqlite.database.model.Livro;

/**
 * Created by Diego Costa
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Versao da base de dados
    private static final int DATABASE_VERSION = 1;

    // Nome da base
    private static final String DATABASE_NAME = "livros_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Criando tabelas
    @Override
    public void onCreate(SQLiteDatabase db) {

        // criando livros na tabela
        db.execSQL(Livro.CREATE_TABLE);
    }

    // Atualizando database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Apaga a tabela caso exista
        db.execSQL("DROP TABLE IF EXISTS " + Livro.TABLE_NAME);

        // Recria a tabela
        onCreate(db);
    }

    //INSERT
    public long insertLivro(String livro) {

        // obtem o banco que queremos gravar os dados
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` e `timestamp` sao inseridos automaticamente

        values.put(Livro.COLUMN_LIVRO, livro);

        // insere linha
        long id = db.insert(Livro.TABLE_NAME, null, values);

        // fecha a conexao do banco
        db.close();

        // retorna id da linha inserida
        return id;
    }

    // OBTEM LIVRO ESPECIFICO
    public Livro getLivro(long id) {
        // obtem o banco de dados legivel
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Livro.TABLE_NAME,
                new String[]{Livro.COLUMN_ID, Livro.COLUMN_LIVRO, Livro.COLUMN_TIMESTAMP},
                Livro.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // PREPARA OS OBJETOS
        Livro livro = new Livro(
                cursor.getInt(cursor.getColumnIndex(Livro.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Livro.COLUMN_LIVRO)),
                cursor.getString(cursor.getColumnIndex(Livro.COLUMN_TIMESTAMP)));

        // fecha conexao
        cursor.close();

        return livro;
    }

    // OBTEM TODOS LIVROS
    public List<Livro> getAllLivros() {
        List<Livro> livros = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Livro.TABLE_NAME + " ORDER BY " +
                Livro.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // add tudo na lista, linha a linha.
        if (cursor.moveToFirst()) {
            do {
                Livro livro = new Livro();
                livro.setId(cursor.getInt(cursor.getColumnIndex(Livro.COLUMN_ID)));
                livro.setLivro(cursor.getString(cursor.getColumnIndex(Livro.COLUMN_LIVRO)));
                livro.setTimestamp(cursor.getString(cursor.getColumnIndex(Livro.COLUMN_TIMESTAMP)));

                livros.add(livro);
            } while (cursor.moveToNext());
        }

        // fecha conexao
        db.close();

        // retorna lista de livros
        return livros;
    }

    public int getLivrosCount() {
        String countQuery = "SELECT  * FROM " + Livro.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return qtd
        return count;
    }

    // UPDATE
    public int updateLivro(Livro livro) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Livro.COLUMN_LIVRO, livro.getLivro());
//        values.put(Livro.COLUMN_DESCRICAO, livro.getDescricao());
//        values.put(Livro.COLUMN_PRECO, livro.getPreco());

        // Atualiza linha
        return db.update(Livro.TABLE_NAME, values, Livro.COLUMN_ID + " = ?",
                new String[]{String.valueOf(livro.getId())});
    }
    // DELETE
    public void deleteLivro(Livro livro) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Livro.TABLE_NAME, Livro.COLUMN_ID + " = ?",
                new String[]{String.valueOf(livro.getId())});
        db.close();
    }
}

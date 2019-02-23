package info.androidhive.sqlite.database.model;

/**
 * Created by Diego Costa
 */

public class Livro {
    public static final String TABLE_NAME = "livros";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LIVRO = "livro";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_PRECO = "preco";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String livro;
    private String timestamp;


    // Cria tabela "livros" no DB
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LIVRO + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Livro() {
    }

    public Livro(int id, String livro, String timestamp) {
        this.id = id;
        this.livro = livro;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

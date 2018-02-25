package br.com.genesis.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.genesis.model.Cliente;

/**
 * Created by JAZON on 17/02/2018.
 */

public class BancoOpenHelper extends SQLiteOpenHelper{
    //TUTORIAL
    // https://www.youtube.com/watch?v=a87CqD1qm18

    private static final String NOME_BANCO = "bd_clientes";
    private static final int VERSAO_BANCO = 1;

    public static final String TBL_CLIENTES = "tbl_clientes";
    public static final String CLIENTES_COL_CODIGO = "id_cliente";
    public static final String CLIENTES_COL_NOME = "st_nome";
    public static final String CLIENTES_COL_TELFFONE = "st_telefone";
    public static final String CLIENTES_COL_EMAIL = "st_email";

  //  private SQLiteDatabase banco;

    public BancoOpenHelper(Context context) {
        super(context , NOME_BANCO, null, VERSAO_BANCO );
        Log.i("GENESIS", "construtor do banco" );
    }

    @Override
    public void onCreate(SQLiteDatabase db){
       // this.banco = db;
        Log.i("GENESIS", "onCreate do banco" );
        String sql  = " CREATE TABLE "+TBL_CLIENTES+" ( "+
        "id_cliente INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
        "st_nome TEXT NOT NULL DEFAULT(''), "+
        "st_telefone TEXT NOT NULL DEFAULT(''), "+
        "st_email TEXT NOT NULL DEFAULT('') )";
//        StringBuilder sql = new StringBuilder();
//        //sql.append("CREATE TABLE IF NOT EXISTS TBL_CLIENTES ( ");
//        sql.append("CREATE TABLE TBL_CLIENTES ( ");
//        sql.append("id_cliente INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
//        sql.append("st_nome TEXT NOT NULL DEFAULT(''), ");
//        sql.append("st_telefone TEXT NOT NULL DEFAULT(''), ");
//        sql.append("st_email TEXT NOT NULL DEFAULT(''))");
        Log.i("GENESIS", "show_script: " + sql);
        db.execSQL(sql);
        Log.i("GENESIS","Criado a tabela clientes");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){}


    private void criarConexao() {
        try {
//           dbOpenHelper  = new BancoOpenHelper(this);
//            conexao = dbOpenHelper.getWritableDatabase();
//          //  Snackbar.make(Vi);
        } catch (SQLException e) {
        }
    }
    /* CRUD */
    public  void insertCliente(Cliente cliente){
        try {
            Log.i("GENESIS", "insertCliente do banco" );
            ContentValues values = new ContentValues();
         //   values.put(CLIENTES_COL_CODIGO, cliente.getCodigo());
            values.put(CLIENTES_COL_NOME, cliente.getNome());
            values.put(CLIENTES_COL_TELFFONE, cliente.getTelefone());
            values.put(CLIENTES_COL_EMAIL, cliente.getEmail());

            SQLiteDatabase db = getWritableDatabase();
           int result = (int) db.insert(TBL_CLIENTES, null, values);
            if(db !=null){
                db.close();
            }else {
                Log.i("GENESIS", "falha na inserção" );
            }
        }catch (SQLException e){
            throw new SQLException("Falha para salvar o registro");
        }
    }
    public void deleteCliente(Cliente c){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_CLIENTES,CLIENTES_COL_CODIGO+"= ?",new String[]{String.valueOf(c.getCodigo())});
        db.close();
    }
    public void updateCliente(Cliente cliente){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLIENTES_COL_NOME,cliente.getNome());
        values.put(CLIENTES_COL_TELFFONE,cliente.getTelefone());
        values.put(CLIENTES_COL_EMAIL, cliente.getEmail());
        db.update(TBL_CLIENTES, values,CLIENTES_COL_CODIGO+ " = ?",
                new String[]{String.valueOf(cliente.getCodigo())});
    }
    public Cliente findById(Integer id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_CLIENTES, new String[]{CLIENTES_COL_CODIGO,CLIENTES_COL_NOME,CLIENTES_COL_TELFFONE,
                CLIENTES_COL_EMAIL},CLIENTES_COL_CODIGO + " = ?",
                new String[]{String.valueOf(id)}, null,null,null,null) ;
        if(cursor != null){
            cursor.moveToFirst();
        }
        Cliente cliente = new Cliente(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return cliente;
    }
    public List<Cliente> getAllClientes(){
        List<Cliente> lista = new ArrayList<Cliente>();
        String query = "SELECT * FROM "+ TBL_CLIENTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        //if na primeira posição é porque tem resultado
        if (cursor.moveToFirst()){
            do{
                Cliente cliente = new Cliente();
                cliente.setCodigo(Integer.parseInt(cursor.getString(0)));
                cliente.setNome(cursor.getString(1));
                cliente.setTelefone(cursor.getString(2));
                cliente.setEmail(cursor.getString(3));
                lista.add(cliente);
            }while (cursor.moveToNext());
        }
        return lista;
    }
}

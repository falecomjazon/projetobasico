package br.com.genesis.banco;

import android.util.Log;

/**
 * Created by JAZON on 18/02/2018.
 */

public class ScriptDDL {
    public static final int VERSAO_BANCO = 1;
    public static final String NOME_BANCO = "bd_clientes";

    public static final String TBL_CLIENTES = "tbl_clientes";
    public static final String COLUNA_CODIGO = "id_cliente";
    public static final String COLUNA_NOME = "st_nome";
    public static final String COLUNA_TELFFONE = "st_telefone";
    public static final String COLUNA_EMAIL = "st_email";

    public static String getCreateTableCliente() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS " + NOME_BANCO + " ( ");
        sql.append(COLUNA_CODIGO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append(COLUNA_NOME + " TEXT NOT NULL DEFAULT(''), ");
        sql.append(COLUNA_TELFFONE + " TEXT NOT NULL DEFAULT(''), ");
        sql.append(COLUNA_EMAIL + " TEXT NOT NULL DEFAULT(''))");

        Log.i("GENESIS", "show_ScriptDLL" + sql.toString());
        return sql.toString();
    }
}

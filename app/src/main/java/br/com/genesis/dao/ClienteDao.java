package br.com.genesis.dao;

import android.content.Context;
import android.util.Log;

import br.com.genesis.banco.BancoOpenHelper;
import br.com.genesis.model.Cliente;

/**
 * Created by JAZON on 20/02/2018.
 */

public class ClienteDao extends GenericDao {
    //  private SQLiteDatabase conexao;
    private BancoOpenHelper banco;
    private Cliente c;

    public ClienteDao(Context context) {
        this.c = new Cliente();
        banco = new BancoOpenHelper(context);
    }

    //@Override
    public void insert(Cliente c) {


            this.c.setNome(c.getNome());
            this.c.setTelefone(c.getTelefone());
            this.c.setEmail(c.getEmail());
            try {
                banco.insertCliente(this.c);
            Log.i("GENESIS", "registro inserido");

        } catch (Exception e) {
            Log.i("GENESIS", "erro para inserir o registro " + e.getMessage());
        }
    }




}

package br.com.genesis;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


import br.com.genesis.banco.BancoOpenHelper;
import br.com.genesis.model.Cliente;

public class MainActivity extends AppCompatActivity {

    EditText et_codigo, et_nome, et_telefone, et_email;
    Button btnLimpar, btnSalvar, btnExluir;
    ListView lv_clientes;

    BancoOpenHelper banco = new BancoOpenHelper(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("GENESIS", "construtor");

        et_codigo = (EditText) findViewById(R.id.et_codigo);
        et_nome = (EditText) findViewById(R.id.et_nome);
        et_telefone = (EditText) findViewById(R.id.et_telefone);
        et_email = (EditText) findViewById(R.id.et_email);

        btnLimpar = (Button) findViewById(R.id.btnLimpar);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnExluir = (Button) findViewById(R.id.btnExcluir);

        lv_clientes = (ListView) findViewById(R.id.lv_clientes);
        inputMethodManager = (InputMethodManager)  this.getSystemService(Service.INPUT_METHOD_SERVICE);
        listarClientes();
        limparCampos();
        lv_clientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String conteudo = (String) lv_clientes.getItemAtPosition(position);
                //Toast.makeText(MainActivity.this, "Select: "+conteudo,Toast.LENGTH_SHORT).show();
                String codigo = conteudo.substring(0, conteudo.indexOf("-"));
                Cliente cliente = banco.findById(Integer.parseInt(codigo));
                limparCampos();
                et_codigo.setText(String.valueOf(cliente.getCodigo()));
                et_nome.setText(cliente.getNome());
                et_telefone.setText(cliente.getTelefone());
                et_email.setText(cliente.getEmail());
            }
        });
    }

    public void limparCliente(View view) {
        this.limparCampos();
    }

    private void esconderTeclado(){
        inputMethodManager.hideSoftInputFromWindow(et_nome.getWindowToken(),0);
    }
    public void addCliente(View view) {
        if(et_nome.getText().toString().isEmpty()){
            et_nome.setError("Este campo é obrigatório");
        }
        if (this.isCamposObrigatorioPreenchidos()) {

            String codigo = et_codigo.getText().toString();

            Cliente cliente = new Cliente();
            cliente.setNome(et_nome.getText().toString());
            cliente.setTelefone(et_telefone.getText().toString());
            cliente.setEmail(et_email.getText().toString());

            if (codigo.isEmpty()) {
                banco.insertCliente(cliente);
                Toast.makeText(MainActivity.this, "Registro cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                cliente.setCodigo(Integer.parseInt(codigo));
                banco.updateCliente(cliente);
                Toast.makeText(MainActivity.this, "Registro atualizado com sucesso", Toast.LENGTH_SHORT).show();
            }
            limparCampos();
            listarClientes();
            esconderTeclado();
        } else {
            Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isCamposObrigatorioPreenchidos() {
        return  (et_nome.getText().toString().isEmpty() ||
                et_telefone.getText().toString().isEmpty() ||
                et_email.getText().toString().isEmpty()? false: true);
    }

    public void deleteCliente(View view) {

        String codigo = et_codigo.getText().toString();
        if (!TextUtils.isEmpty(codigo) && TextUtils.isDigitsOnly(codigo)) {
            Cliente c = new Cliente();
            c.setCodigo(Integer.parseInt(codigo));
            c.setNome(et_nome.getText().toString());
            c.setTelefone(et_telefone.getText().toString());
            c.setEmail(et_email.getText().toString());
            //banco
            banco.deleteCliente(c);
            limparCampos();
            listarClientes();
            Toast.makeText(MainActivity.this, "Cliente removido com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Selecione um item!", Toast.LENGTH_SHORT).show();
        }
    }

    private void limparCampos() {
        et_codigo.setText("");
        et_nome.setText("");
        et_telefone.setText("");
        et_email.setText("");

        et_nome.requestFocus();
    }

    private void listarClientes() {
        List<Cliente> clientes = banco.getAllClientes();

        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);

        lv_clientes.setAdapter(adapter);

        for (Cliente c : clientes) {
            Log.d("Lista: ", "\nID : " + c.getCodigo() + ", Nome: " + c.getNome());
            arrayList.add(c.getCodigo() + "-" + c.getNome());
            adapter.notifyDataSetChanged();
        }
    }
}

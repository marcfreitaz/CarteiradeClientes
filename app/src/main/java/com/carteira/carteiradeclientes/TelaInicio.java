package com.carteira.carteiradeclientes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.carteira.carteiradeclientes.dao.ClienteDao;
import com.carteira.carteiradeclientes.modelo.Cliente;

import java.util.ArrayList;

public class TelaInicio extends AppCompatActivity {

    ListView listaVisivel;
    Button botaoCadastro;
    Cliente cliente;
    ClienteDao clienteDao;
    ArrayList<Cliente> arrayListCliente;
    ArrayAdapter<Cliente> arrayAdapterCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicio);
        listaVisivel = (ListView) findViewById(R.id.listaClientes);
        registerForContextMenu(listaVisivel);

        botaoCadastro = (Button) findViewById(R.id.botaoCadastro);
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TelaInicio.this,TelaCadastro.class);
                startActivity(i);
            }
        });

        listaVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente clienteEnviado = (Cliente) arrayAdapterCliente.getItem(position);

                        Intent i = new Intent(TelaInicio.this,TelaCadastro.class);
                        i.putExtra("cliente-enviado",clienteEnviado);
                        startActivity(i);

            }
        });

        listaVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cliente = arrayAdapterCliente.getItem(position);
                return false;
            }
        });
    }

    public void popLista() {
        clienteDao = new ClienteDao(TelaInicio.this);

        arrayListCliente = clienteDao.selectAllCliente();
        clienteDao.close();

        if (listaVisivel != null) {
            arrayAdapterCliente = new ArrayAdapter<Cliente>(TelaInicio.this,
                    android.R.layout.simple_list_item_1, arrayListCliente);
            listaVisivel.setAdapter(arrayAdapterCliente);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        popLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Excluir Registro");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornoDB;
                clienteDao = new ClienteDao(TelaInicio.this);
                retornoDB = clienteDao.excluirCliente(cliente);
                clienteDao.close();

                if (retornoDB == -1) {
                    alert("Erro ao excluir");
                } else {
                    alert("Registro excluido");
                }

                popLista();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}

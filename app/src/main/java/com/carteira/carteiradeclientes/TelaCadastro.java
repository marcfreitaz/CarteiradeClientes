package com.carteira.carteiradeclientes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carteira.carteiradeclientes.dao.ClienteDao;
import com.carteira.carteiradeclientes.modelo.Cliente;

public class TelaCadastro extends AppCompatActivity {

    EditText campoNome, campoTelefone, campoEndereco, campoEmail;
    Button botaoVariavel;
    Cliente cliente, altcliente;
    ClienteDao clienteDao;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);

        Intent intent = getIntent();
        altcliente = (Cliente) intent.getSerializableExtra("cliente-enviado");
        cliente = new Cliente();
        clienteDao = new ClienteDao(TelaCadastro.this);

        campoNome = (EditText) findViewById(R.id.campoNome);
        campoTelefone = (EditText) findViewById(R.id.campoTelefone);
        campoEndereco = (EditText) findViewById(R.id.campoEndereco);
        campoEmail = (EditText) findViewById(R.id.campoEmail);
        botaoVariavel = findViewById(R.id.botaoVariavel);

        if (altcliente != null) {
            botaoVariavel.setText("Alterar");
            campoNome.setText(altcliente.getNome());
            campoTelefone.setText(altcliente.getTelefone());
            campoEndereco.setText(altcliente.getEndereco());
            campoEmail.setText(altcliente.getEmail());

            cliente.setId(altcliente.getId());
        } else {
            botaoVariavel.setText("Salvar");
        }

        botaoVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cliente.setNome(campoNome.getText().toString());
                cliente.setTelefone(campoTelefone.getText().toString());
                cliente.setEndereco(campoEndereco.getText().toString());
                cliente.setEmail(campoEmail.getText().toString());

                if (botaoVariavel.getText().toString().equals("Salvar")) {
                    retornoDB = clienteDao.salvarCliente(cliente);
                    clienteDao.close();
                    if (retornoDB == -1) {
                        alert("Erro ao cadastrar");
                    } else {
                        alert("Cadastro realizado");
                    }
                } else {
                    retornoDB = clienteDao.alterarCliente(cliente);
                    clienteDao.close();
                    if (retornoDB == -1) {
                        alert("Erro ao alterar");
                    } else {
                        alert("Alteração realizada");
                    }

                }

                finish();
            }

        });

    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
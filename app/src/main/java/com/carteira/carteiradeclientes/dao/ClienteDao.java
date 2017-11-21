package com.carteira.carteiradeclientes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.carteira.carteiradeclientes.modelo.Cliente;

import java.util.ArrayList;

/**
 * Created by Marcelino on 15/11/2017.
 */

public class ClienteDao extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "DBCliente.sqLiteDatabase";
    private static final int VERSION = 1;
    private static final String TABELA = "cliente";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String TELEFONE = "telefone";
    private static final String ENDERECO = "endereco";
    private static final String EMAIL = "email";

    public ClienteDao(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+" ( " +
                " "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " "+NOME+" TEXT, "+TELEFONE+" TEXT, " +
                " "+ENDERECO+" TEXT, "+EMAIL+" TEXT );";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IS EXISTS " +TABELA;
        db.execSQL(sql);
        onCreate(db);

    }

    public long salvarCliente (Cliente c){
        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(NOME,c.getNome());
        values.put(TELEFONE,c.getTelefone());
        values.put(ENDERECO,c.getEndereco());
        values.put(EMAIL,c.getEmail());

        retornoDB = getWritableDatabase().insert(TABELA,null,values);

        return retornoDB;

    }

    public long alterarCliente (Cliente c){
        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(NOME,c.getNome());
        values.put(TELEFONE,c.getTelefone());
        values.put(ENDERECO,c.getEndereco());
        values.put(EMAIL,c.getEmail());

        String[] args = {String.valueOf(c.getId())};
        retornoDB = getWritableDatabase().update(TABELA,values,"id=?",args);

        return retornoDB;

    }

    public long excluirCliente (Cliente c){
        long retornoDB;

        String[] args = {String.valueOf(c.getId())};
        retornoDB = getWritableDatabase().delete(TABELA,ID+"=?",args);

        return retornoDB;

    }

    public ArrayList<Cliente> selectAllCliente(){
        String[] coluns = {ID,NOME,TELEFONE,ENDERECO,EMAIL};

        Cursor cursor = getWritableDatabase().query(TABELA,coluns,
                null,null,null,null, "upper(nome)",null);

        ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();

        while (cursor.moveToNext()){
            Cliente c = new Cliente();

            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setTelefone(cursor.getString(2));
            c.setEndereco(cursor.getString(3));
            c.setEmail(cursor.getString(4));

            listaClientes.add(c);
        }

        return listaClientes;
    }
}

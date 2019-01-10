package migra.br.smart.ActivityCliente;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.cliente.ClienteRN;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitRN;
import migra.br.smart.utils.Utils;

public class ClienteListView extends ListActivity {
    /***********************************************************************************************
     *rTheselectCli parâmetro de Intent para informar que deve retornar quando um cliente for
     * selecionado se o conteúdo for "yes"
     **********************************************************************************************/
    ArrayList<Cliente> list = null;
    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.fullScreen(getWindow());
        super.onCreate(savedInstanceState);

        it = getIntent();

        long codRota = getIntent().getLongExtra("codRota", 0);
        try {
            if(codRota != 0){
                list = new SeqVisitRN(this).getClientes(codRota);
            }else{
                list = new ClienteRN(this).pesquisar(new Cliente());
            }
            setListAdapter(new ClienteAdapter(this, list));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        Cliente cli = (Cliente) getListAdapter().getItem(position);
        if(it != null){
            if(it.getStringExtra("rTheselectCli").equals("yes")){
                it.putExtra("codCli", String.valueOf(cli.getCodigo()));
                it.putExtra("nomeCli", cli.getFantasia());
                setResult(0, it);
                finish();
            }
        }
    }
}
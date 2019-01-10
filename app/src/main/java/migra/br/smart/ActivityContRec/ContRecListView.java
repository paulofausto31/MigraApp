package migra.br.smart.ActivityContRec;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.contasReceber.ContReceb;
import migra.br.smart.manipulaBanco.entidades.contasReceber.ContRecebRN;
import migra.br.smart.utils.Utils;

public class ContRecListView extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.fullScreen(getWindow());
        super.onCreate(savedInstanceState);
        Intent it = getIntent();

        ArrayList<ContReceb> lista = null;
        try {
            if(it != null){
                if(getIntent().getStringExtra("codCli").equals("yes")){//filtrar por codigo do cliente
                    lista = new ContRecebRN(this).searchForCli(CurrentInfo.codCli);
                }
            }else{
                lista = new ContRecebRN(this).pesquisar(new ContReceb());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setListAdapter(new ContRecAdapter(this, lista));
    }

    protected void onStart(){
        super.onStart();
        Log.i("CONT_RECEB", "START");
        try {
            Utils.verifiLicence(ContRecListView.this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void onResume(){
        super.onResume();
        Log.i("CONT_START", "RESUME");
        try {
            Utils.verifiLicence(ContRecListView.this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void onRestart(){
        super.onRestart();
        Log.i("CONT_START", "RESTART");
        try {
            Utils.verifiLicence(ContRecListView.this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

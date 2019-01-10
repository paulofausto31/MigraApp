package migra.br.smart.atvConfig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.R;
import migra.br.smart.TaskNetWork.TaskConnectNetWork;
import migra.br.smart.TaskNetWork.transferOperations.Operations;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.dbAccess.DBAccess;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocal;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocalRN;
import migra.br.smart.manipulaBanco.entidades.empresas.Empresa;
import migra.br.smart.manipulaBanco.entidades.empresas.EmpresaRN;
import migra.br.smart.manipulaBanco.entidades.registro.Registro;
import migra.br.smart.manipulaBanco.entidades.registro.RegistroRN;
import migra.br.smart.utils.Utils;

public class Config extends AppCompatActivity {

    EditText edTxIp, edTxPort, edtCnpjEmpresa, edtAtvConfK3, edtAtvConfK2, edtAtvConfK1, edtEmailDestPed;
    Button btSaveConfig, btPortalOnlineConfig, btBackup;
    CheckBox chMultBanco;

    private Registro deviceRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_config);

        edTxIp = (EditText) findViewById(R.id.edTxIp);
        edTxPort = (EditText) findViewById(R.id.edTxPort);
        edtCnpjEmpresa = (EditText) findViewById(R.id.edtCnpjEmpresa);
        edtAtvConfK3 = (EditText) findViewById(R.id.edtAtvConfK3);
        edtAtvConfK2 = (EditText) findViewById(R.id.edtAtvConfK2);
        edtAtvConfK1 = (EditText) findViewById(R.id.edtAtvConfK1);

        edtEmailDestPed = (EditText) findViewById(R.id.edtEmailDestPed);

        chMultBanco = (CheckBox) findViewById(R.id.chMultBanco);

        btBackup = (Button) findViewById(R.id.btBackup);
        btBackup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent itBackup = new Intent("ATV_BACKUP");
                startActivity(itBackup);
            }
        });

        btPortalOnlineConfig = (Button) findViewById(R.id.btPortalOnlineConfig);
        btPortalOnlineConfig.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    if(new Utils().verifiIpAndPort(Config.this) && Utils.verifiCnpjAndKey(Config.this)){
                        //TaskConnectNetWork task = new TaskConnectNetWork(Config.this, btPortalOnlineConfig, null);
                        TaskConnectNetWork task = new TaskConnectNetWork(Config.this, null, null);
                        task.execute(String.valueOf(Operations.CONSUL_LICENCA_CLI), null);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                /*
                Intent it = new Intent("ATV_PORTAL_WEB");
                Intent it = new Intent("ATV_INSERE_ALTERA_ITEM_PEDIDO");
                startActivity(it);
                */
            }
        });

        try {
            ArrayList<ConfigLocal> list =  new DBAccess(this).pesquisar(new ConfigLocal());

            ConfigLocal con = null;
            if(list.size() > 0){
                con = list.get(0);
                edTxIp.setText(con.getIp());
                edTxPort.setText(String.valueOf(con.getPorta()));
                edtEmailDestPed.setText(con.getEmailDestino());
            }
            deviceRegister = new RegistroRN(this).getRegistro();
            if(!deviceRegister.getCnpjEmpresa().equals("")){
                edtCnpjEmpresa.setText(deviceRegister.getCnpjEmpresa());
                if(!deviceRegister.getKey().equals("") && deviceRegister.getKey().split("-").length == 3) {
                    String[] key = deviceRegister.getKey().split("-");
                    edtAtvConfK1.setText(key[0]);
                    edtAtvConfK2.setText(key[1]);
                    edtAtvConfK3.setText(key[2]);
                }
                CurrentInfo.cnpjEmpresa = deviceRegister.getCnpjEmpresa();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btSaveConfig = (Button) findViewById(R.id.btSaveConfig);
        btSaveConfig.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
            ArrayList<ConfigLocal> list;
            DBAccess dbAccess = new DBAccess(Config.this);
            ConfigLocal con = new ConfigLocal();
            Empresa emp = new Empresa();
            con.setId(1);

            try {
                if(!edtEmailDestPed.equals("") && !edTxIp.getText().toString().equals("") && !edTxPort.getText().toString().equals("")
                    && !edtCnpjEmpresa.getText().toString().equals("") && !edtAtvConfK1.getText().toString().equals("")
                    && !edtAtvConfK2.getText().toString().equals("") && !edtAtvConfK3.getText().toString().equals("")){
                    if(chMultBanco.isChecked()){
                        con.setMultBanco(1);
                    }else{
                        con.setMultBanco(0);
                    }

                    emp.setCnpj(edtCnpjEmpresa.getText().toString());
                    emp.setTipo("m");

                    con.setIp(edTxIp.getText().toString());
                    con.setPorta(Long.parseLong(edTxPort.getText().toString()));

                    con.setEmailDestino(edtEmailDestPed.getText().toString());

                    list = new ConfigLocalRN(Config.this).pesquisar(new ConfigLocal());
                    if(list.size() > 0){
                        if(!list.get(0).getEmailDestino().equals(edtEmailDestPed.getText().toString()) || !list.get(0).getIp().equals(edTxIp.getText().toString())
                           ||list.get(0).getPorta() != Integer.parseInt(edTxPort.getText().toString())){
                            list.get(0).setIp(edTxIp.getText().toString());
                            list.get(0).setPorta(Integer.parseInt(edTxPort.getText().toString()));
                            list.get(0).setEmailDestino(edtEmailDestPed.getText().toString());
                            new ConfigLocalRN(Config.this).atualizar(list.get(0));
                            Toast.makeText(Config.this, "IP E/OU PORTA MODIFICADO(S)", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        new ConfigLocalRN(Config.this).salvar(con);
                        new EmpresaRN(Config.this).save(emp);
                        Toast.makeText(Config.this, "IP E/OU PORTA MODIFICADO(S)", Toast.LENGTH_SHORT).show();
                        //funciona TaskConnectNetWork task = new TaskConnectNetWork(Config.this, con, btSaveConfig, null);
                        //funciona task.execute(String.valueOf(Operations.CONSUL_KEY));
                    }

                    if(deviceRegister.getCnpjEmpresa() == null || (!edtCnpjEmpresa.getText().toString().equals(deviceRegister.getCnpjEmpresa())
                            || !deviceRegister.getKey().equals(edtAtvConfK1.getText().toString()+"-"+edtAtvConfK2.getText()+"-"+edtAtvConfK3.getText()))){
                        //Registro r = new Registro();
                        deviceRegister.setCnpjEmpresa(edtCnpjEmpresa.getText().toString());
                        deviceRegister.setKey(edtAtvConfK1.getText()+"-"+edtAtvConfK2.getText()+"-"+edtAtvConfK3.getText());
                        Calendar c = Calendar.getInstance();
                        deviceRegister.setUltima_data(c.getTimeInMillis());
                        c.add(Calendar.DAY_OF_MONTH, 30);
                        deviceRegister.setData_expira(c.getTimeInMillis());
                        TaskConnectNetWork task = new TaskConnectNetWork(Config.this, deviceRegister, btSaveConfig, null);
                        task.execute(String.valueOf(Operations.CONSUL_KEY));
                    }

                }else{
                    Utils.showMsg(Config.this, "ERRO", "PREENCHA TODOS OS CAMPOS", R.drawable.dialog_error);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            }
        });

    }

    public void onResume(){
        super.onResume();

        try {
            Utils.verifiLicence(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
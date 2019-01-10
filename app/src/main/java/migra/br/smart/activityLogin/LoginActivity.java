package migra.br.smart.activityLogin;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import migra.br.smart.R;
import migra.br.smart.TaskNetWork.TaskConnectNetWork;
import migra.br.smart.TaskNetWork.transferOperations.Operations;
import migra.br.smart.activityLogin.dgOpDownTables.DgOpDownTablesAdap;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.dbAccess.DBAccess;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocal;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocalRN;
import migra.br.smart.manipulaBanco.entidades.configuracao.Configuracao;
import migra.br.smart.manipulaBanco.entidades.configuracao.ConfiguracaoRN;
import migra.br.smart.manipulaBanco.entidades.empresas.Empresa;
import migra.br.smart.manipulaBanco.entidades.empresas.EmpresaRN;
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.manipulaBanco.entidades.registro.Registro;
import migra.br.smart.manipulaBanco.entidades.registro.RegistroRN;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;
import migra.br.smart.manipulaBanco.entidades.rota.RotaRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.permissions.PermissionUtils;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private String key = "jpr0gr@mfringe1974";
    private String retransmite = "";

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText edtLoginCodVend;
    private EditText edtPassword;

    private Button btLogin;
    //private FloatingActionButton imgBtConfig;
    //private FloatingActionButton btLoginDownload, btAtvLoginUpload;
    private ImageButton btLoginDownload, btAtvLoginUpload, imgBtConfig;

    private View mProgressView;
    private View mLoginFormView;

    private TaskConnectNetWork task;

    private static final int RETURN_CONFIG = 0;//RETORNO DA TELA DE CONFIGURAÇÕES

    private boolean clearStatCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.fullScreen(getWindow());
        setContentView(R.layout.activity_login);
        /*
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        boolean ok = PermissionUtils.validate(this, 0, permissions);
        */
        /*if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }, 0);
            }
        }*/

        Date hoje = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("a dd/MM/yyyy HH:mm:ss");
        Log.i("DATA", sdf.format(hoje).split(" ")[2]);

        //funciona new Manutencao(LoginActivity.this).copyDb(new File("/data/data/smart.br.migra.migraapp/databases"), new File("/mnt/sdcard/meu_banco_dump.db"));

        edtLoginCodVend = (EditText) findViewById(R.id.codVend);
        edtPassword = (EditText) findViewById(R.id.password);
        btLogin = (Button) findViewById(R.id.btLogin);

        imgBtConfig = (ImageButton) findViewById(R.id.fabConfig);
        btLoginDownload = (ImageButton) findViewById(R.id.btLoginDownload);
        btAtvLoginUpload = (ImageButton) findViewById(R.id.btAtvLoginUpload);

        Button btKeyBeta = (Button)findViewById(R.id.btKeyBeta);

        try {
            if(!verifIpAndPort() || !Utils.verifiCnpjAndKey(this)){
                //  imgBtConfig.setVisibility(View.INVISIBLE);
                btLoginDownload.setVisibility(View.INVISIBLE);
                btAtvLoginUpload.setVisibility(View.INVISIBLE);
                btLogin.setVisibility(View.GONE);
                Utils.showMsg(this, "ERRO","VÁ EM CONFIGURAÇÕES, VERIFIQUE IP, PORTA, CNPJ E/OU CHAVE DE ACESSO", R.drawable.dialog_error);
            }else{
                btLoginDownload.setVisibility(View.VISIBLE);
                btAtvLoginUpload.setVisibility(View.VISIBLE);
                btLogin.setVisibility(View.VISIBLE);
                if(!Utils.verifiLicence(this)){
                    //  imgBtConfig.setVisibility(View.INVISIBLE);
                    btLoginDownload.setVisibility(View.GONE);
                    btAtvLoginUpload.setVisibility(View.GONE);
                    btLogin.setVisibility(View.GONE);
                }else{
                    //   imgBtConfig.setVisibility(View.VISIBLE);
                    btLoginDownload.setVisibility(View.VISIBLE);
                    btAtvLoginUpload.setVisibility(View.VISIBLE);
                    btLogin.setVisibility(View.VISIBLE);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*try {
            if(!Utils.verifiCnpjAndKey(this)){
                Utils.showMsg(this, "ERRO","VÁ EM CONFIGURAÇÕES VERIFIQUE CNPJ, CHVE, IP OU PORTA", R.drawable.dialog_error);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        btAtvLoginUpload.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Log.e("ENTER_D", "fgsdffddfgdfg");
                try {
                    if(Utils.verifiLicence(LoginActivity.this)){

                        boolean boo = Utils.verifiNetConnection(LoginActivity.this);
                        if(boo){
                            Toast.makeText(LoginActivity.this, "ok", Toast.LENGTH_SHORT).show();
                        }

                        if(verifiFields()){
                            Utils.showMsg(LoginActivity.this, "ERRO", "EXISTE ALGUM CAMPO VAZIO", R.drawable.dialog_error);
                        }else if (!Utils.codVendedorExists(LoginActivity.this, edtLoginCodVend.getText().toString())) {
                            Utils.showMsg(LoginActivity.this, "ERRO", "ESTE VENDEDOR NÃO EXISTE NA BASE DE DADOS", R.drawable.dialog_error);
                        } else {
                            //CurrentInfo.codVendedor = edtLoginCodVend.getText().toString();
                            /*
                            Pedido ped = new Pedido();
                            if(ControlFragment.isActiveHistoriFg()) {//reenvia se estiver no histórico
                                setRetransmite("reenviar");
                                ped.setStatus("Transmitido");
                                ped.setDel("s");
                            }else {
                                //Pedido ped = new Pedido();
                                ped.setStatus("Fechado");
                            }*/

                            AlertDialog.Builder albClearStat = new AlertDialog.Builder(LoginActivity.this);
                            albClearStat.setTitle("MANTER STATUS APÓS TRNASMISSÃO?");
                            albClearStat.setPositiveButton("SIM", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        setClearStatCliente(false);
                                        showMenuTransfer();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            albClearStat.setNegativeButton("NÃO", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which){
                                    try {
                                        setClearStatCliente(true);
                                        showMenuTransfer();
                                        setClearStatCliente(false);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            albClearStat.show();
                            /*
                            ArrayList<Pedido> lPed = new PedidoRN(LoginActivity.this).filtrar(ped);
                            Negativacao ne = new Negativacao();
                            ne.setStatus("Espera");
                            ArrayList<Negativacao> lNegativ = new NegativacaoRN(LoginActivity.this).getWithClients(ne);
                            if (lPed.size() > 0 || lNegativ.size() > 0) {//HÁ PEDIDO PARA TRANSMITIR

                                        HashMap<String, String> hasTbDown = new HashMap<String, String>();
                                        /*hasTbDown.put("P", "Produto");//produto
                                        hasTbDown.put("L", "Linhas");//linhas de produto
                                        hasTbDown.put("C", "Clientes");// clientes
                                        hasTbDown.put("F", "Formas de Pagamento");//formas de pagamento
                                        hasTbDown.put("V", "Vendas");//vendedores
                                        hasTbDown.put("B", "Rotas");//rotas
                                        hasTbDown.put("Z", "Configurações");//configurações
                                        hasTbDown.put("E", "Comodato");//comodato
                                        hasTbDown.put("O", "Fornecedores");//fornecedores
                                        hasTbDown.put("J", "Erro");//justificativas
                                        hasTbDown.put("R", "Contas a Receber");//contas a receber
                                        hasTbDown.put("D", "Sequência de Visitas");//sequencia de visitas

                                        *//*
                                        LayoutInflater inflater = (LayoutInflater) LoginActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View vdg = inflater.inflate(R.layout.dg_opt_down_tables, null);

                                        ListView lvDgOpDownTab = (ListView)vdg.findViewById(R.id.lvDgOptDownTables);
                                        Button btDgOpDownTbWeb = (Button) vdg.findViewById(R.id.btDgOpDowntbOnline);
                                        Button btDgOpDowntbLocal = (Button) vdg.findViewById(R.id.btDgOpDowntbLocal);
                                        Button btDgUpInterno = (Button) vdg.findViewById(R.id.btDgUpInterno);

                                        AlertDialog.Builder alb = new AlertDialog.Builder(LoginActivity.this);
                                        alb.setView(vdg);
                                        final AlertDialog alert = alb.show();//263
                                        btDgOpDownTbWeb.setOnClickListener(new OnClickListener(){
                                            @Override
                                            public void onClick(View v) {
                                                alert.dismiss();
                                                try {
                                                    if(new Utils().verifiIpAndPort(LoginActivity.this) && Utils.verifiCnpjAndKey(LoginActivity.this)){
                                                        btAtvLoginUpload.setEnabled(false);
                                                        //TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, btAtvLoginUpload, null);
                                                        TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, null, null);
                                                        task.execute(String.valueOf(Operations.EXPORT_PEDIDOS_INTERNET), getRetransmite());
                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        btDgOpDowntbLocal.setOnClickListener(new OnClickListener(){
                                            public void onClick(View v){
                                                try {
                                                    if(new Utils().verifiIpAndPort(LoginActivity.this) && Utils.verifiCnpjAndKey(LoginActivity.this)){
                                                        btAtvLoginUpload.setEnabled(false);
                                                        //TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, btAtvLoginUpload, null);
                                                        TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, null, null);
                                                        task.execute(String.valueOf(Operations.UPLOAD_TO_SERV_OFF), getRetransmite());
                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                //btDgUpInterno = (Button) vdg.findViewById(R.id.btDgUpInterno);
                                btDgUpInterno.setOnClickListener(new Button.OnClickListener(){
                                    public void onClick(View v){
                                        try {
                                            if(new Utils().verifiIpAndPort(LoginActivity.this) && Utils.verifiCnpjAndKey(LoginActivity.this)){
                                                btAtvLoginUpload.setEnabled(false);
                                                //TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, btAtvLoginUpload, null);
                                                TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, null, null);
                                                task.execute(String.valueOf(Operations.UPLOAD_INTERNO), getRetransmite());
                                            }
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                                        lvDgOpDownTab.setAdapter(new DgOpDownTablesAdap(LoginActivity.this, hasTbDown));

                            } else {
                                Utils.showMsg(LoginActivity.this, "ERRO", "NÃO HÁ PEDIDOS PARA TRANSMITIR", R.drawable.dialog_error);
                            }*/
                        }
                    }

                } catch(SQLException e){
                        e.printStackTrace();
                }
            }
        });

        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        btLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(Utils.verifiLicence(LoginActivity.this)) {
                        if (verifiFields()) {//se true não está vazio
                            Utils.showMsg(LoginActivity.this, "ERRO", "EXISTE ALGUM CAMPO VAZIO", R.drawable.dialog_error);
                        }else{
                            corrigeCodVend();
                            if (!Utils.codVendedorExists(LoginActivity.this, edtLoginCodVend.getText().toString())) {
                                Utils.showMsg(LoginActivity.this, "ERRO", "ESTE VENDEDOR NÃO EXISTE, " +
                                        "TENTE REALIZAR DOWNLOAD ONLINE OU USE OUTRO CODIGO", R.drawable.dialog_error);
                            } else {
                            /*TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, null, 0, btLogin, null);
                            task.execute(String.valueOf(Operations.CONSUL_KEY));*/
                                ArrayList<ConfigLocal> confLocal = new ConfigLocalRN(LoginActivity.this).pesquisar(new ConfigLocal());
                                if (confLocal != null && confLocal.size() > 0) {
                                    //if (confLocal.get(0).getKey().split("-").length == 3) {
                                    CurrentInfo.codVendedor = edtLoginCodVend.getText().toString();

                                    Intent it = new Intent(Operations.ACTIVITY_CONTAINER_FRAGMENTS);
                                    it.putExtra("openFrag", "rotaFg");//identifica o fragment a exibir
                                    startActivity(it);


                                    //}
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        imgBtConfig.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                        //CurrentInfo.codVendedor = edtLoginCodVend.getText().toString();
                        Intent it = new Intent(Operations.ABRIR_CONFIG);
                        startActivityForResult(it, RETURN_CONFIG);

            }
        });

        btLoginDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hasTbDown = new HashMap<String, String>();
               /* hasTbDown.put("P", "Produto");//produto
                hasTbDown.put("L", "Linhas");//linhas de produto
                hasTbDown.put("C", "Clientes");// clientes
                hasTbDown.put("F", "Formas de Pagamento");//formas de pagamento
                hasTbDown.put("V", "Vendas");//vendedores
                hasTbDown.put("B", "Rotas");//rotas
                hasTbDown.put("Z", "Configurações");//configurações
                hasTbDown.put("E", "Comodato");//comodato
                hasTbDown.put("O", "Fornecedores");//fornecedores
                hasTbDown.put("J", "Erro");//justificativas
                hasTbDown.put("R", "Contas a Receber");//contas a receber
                hasTbDown.put("D", "Sequência de Visitas");//sequencia de visitas
*/

                LayoutInflater inflater = (LayoutInflater) LoginActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vdg = inflater.inflate(R.layout.dg_opt_down_tables, null);

                //ListView lvDgOpDownTab = (ListView)vdg.findViewById(R.id.lvDgOptDownTables);
                Button btDgOpDownTbWeb = (Button) vdg.findViewById(R.id.btDgOpDowntbOnline);
                Button btDgOpDowntbLocal = (Button) vdg.findViewById(R.id.btDgOpDowntbLocal);

                AlertDialog.Builder alb = new AlertDialog.Builder(LoginActivity.this);
                alb.setView(vdg);
                final AlertDialog alert = alb.show();//263
                btDgOpDownTbWeb.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        downLogin(String.valueOf(Operations.DOWNLOAD_INTERNET));
                    }
                });
                btDgOpDowntbLocal.setOnClickListener(new OnClickListener(){
                    public void onClick(View v){
                        alert.dismiss();
                        downLogin(String.valueOf(Operations.downloadLocal));
                    }
                });
                Button btDgOpDowntbMarcDesmarc = (Button) vdg.findViewById(R.id.btDgUpInterno);

                //lvDgOpDownTab.setAdapter(new DgOpDownTablesAdap(LoginActivity.this, hasTbDown));
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public boolean isClearStatCliente() {
        return clearStatCliente;
    }

    public void setClearStatCliente(boolean clearStatCliente) {
        this.clearStatCliente = clearStatCliente;
    }

    private void showMenuTransfer() throws SQLException {
        CurrentInfo.codVendedor = edtLoginCodVend.getText().toString();
        Pedido ped = new Pedido();
        if(ControlFragment.isActiveHistoriFg()) {//reenvia se estiver no histórico
            setRetransmite("reenviar");
            ped.setStatus("Transmitido");
            ped.setDel("s");
        }else {
            //Pedido ped = new Pedido();
            ped.setStatus("Fechado");
        }
        ArrayList<Pedido> lPed = new PedidoRN(LoginActivity.this).filtrar(ped);
        Negativacao ne = new Negativacao();
        ne.setStatus("Espera");
        ArrayList<Negativacao> lNegativ = new NegativacaoRN(LoginActivity.this).getWithClients(ne);
        if (lPed.size() > 0 || lNegativ.size() > 0) {//HÁ PEDIDO PARA TRANSMITIR

            HashMap<String, String> hasTbDown = new HashMap<String, String>();
                                        /*hasTbDown.put("P", "Produto");//produto
                                        hasTbDown.put("L", "Linhas");//linhas de produto
                                        hasTbDown.put("C", "Clientes");// clientes
                                        hasTbDown.put("F", "Formas de Pagamento");//formas de pagamento
                                        hasTbDown.put("V", "Vendas");//vendedores
                                        hasTbDown.put("B", "Rotas");//rotas
                                        hasTbDown.put("Z", "Configurações");//configurações
                                        hasTbDown.put("E", "Comodato");//comodato
                                        hasTbDown.put("O", "Fornecedores");//fornecedores
                                        hasTbDown.put("J", "Erro");//justificativas
                                        hasTbDown.put("R", "Contas a Receber");//contas a receber
                                        hasTbDown.put("D", "Sequência de Visitas");//sequencia de visitas

*/
            LayoutInflater inflater = (LayoutInflater) LoginActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View vdg = inflater.inflate(R.layout.dg_opt_down_tables, null);

            //ListView lvDgOpDownTab = (ListView)vdg.findViewById(R.id.lvDgOptDownTables);
            Button btDgOpDownTbWeb = (Button) vdg.findViewById(R.id.btDgOpDowntbOnline);
            Button btDgOpDowntbLocal = (Button) vdg.findViewById(R.id.btDgOpDowntbLocal);
            Button btDgUpInterno = (Button) vdg.findViewById(R.id.btDgUpInterno);

            final Spinner spEmpresas = (Spinner) vdg.findViewById(R.id.spEmpresas);//lista de cnpj das empresas

            try {
                ArrayList<Empresa> alEmp = new EmpresaRN(LoginActivity.this).list(new Empresa());
                String[] arSpempr = new String[alEmp.size()+1];
                arSpempr[0] = "0-TODAS";
                for(int i = 1; i < alEmp.size()+1; i++){
                    arSpempr[i] = alEmp.get(i-1).getId()+"-"+alEmp.get(i-1).getFantasia();
                }
                ArrayAdapter<String> arAdpspEmp = new ArrayAdapter<String>(LoginActivity.this, R.layout.spin_ped_adapter_cod_ped, arSpempr);
                spEmpresas.setAdapter(arAdpspEmp);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            AlertDialog.Builder alb = new AlertDialog.Builder(LoginActivity.this);
            alb.setView(vdg);
            final AlertDialog alert = alb.show();//263
            btDgOpDownTbWeb.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                    try {
                        if(new Utils().verifiIpAndPort(LoginActivity.this) && Utils.verifiCnpjAndKey(LoginActivity.this)){
                            btAtvLoginUpload.setEnabled(false);
                            //TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, btAtvLoginUpload, null);
                            TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, null, null);
                            task.setApagaStatus(isClearStatCliente());//limpar o status do cliente P, N, A
                            task.execute(String.valueOf(Operations.EXPORT_PEDIDOS_INTERNET), getRetransmite(), spEmpresas.getSelectedItem().toString().split("-")[0]);
                            //setClearStatCliente(false);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            btDgOpDowntbLocal.setOnClickListener(new OnClickListener(){
                public void onClick(View v){
                    try {
                        if(new Utils().verifiIpAndPort(LoginActivity.this) && Utils.verifiCnpjAndKey(LoginActivity.this)){
                            alert.dismiss();
                            btAtvLoginUpload.setEnabled(false);
                            //TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, btAtvLoginUpload, null);
                            TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, null, null);
                            task.setApagaStatus(isClearStatCliente());//limpar o status do cliente P, N, A
                            task.execute(String.valueOf(Operations.UPLOAD_TO_SERV_OFF), getRetransmite(), spEmpresas.getSelectedItem().toString().split("-")[0]);
                           // setClearStatCliente(false);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            //btDgUpInterno = (Button) vdg.findViewById(R.id.btDgUpInterno);
            btDgUpInterno.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                    try {
                        if(new Utils().verifiIpAndPort(LoginActivity.this) && Utils.verifiCnpjAndKey(LoginActivity.this)){
                            alert.dismiss();
                            btAtvLoginUpload.setEnabled(false);
                            //TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, btAtvLoginUpload, null);
                            TaskConnectNetWork task = new TaskConnectNetWork(LoginActivity.this, null, null);
                            task.execute(String.valueOf(Operations.UPLOAD_INTERNO), getRetransmite(), spEmpresas.getSelectedItem().toString().split("-")[0]);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });
            spEmpresas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                    //ljfdkfj
                }
            });

            //lvDgOpDownTab.setAdapter(new DgOpDownTablesAdap(LoginActivity.this, hasTbDown));

        } else {
            Utils.showMsg(LoginActivity.this, "ERRO", "NÃO HÁ PEDIDOS PARA TRANSMITIR", R.drawable.dialog_error);
        }
    }

    private boolean status = true;//status para cancelar task

    private void downLogin(String operation){
        try {
            //new DropCreateDatabaseDirectAccess().dropTables(LoginActivity.this, new DropCreateDatabase());
            if(Utils.verifiLicence(LoginActivity.this)) {
                if (verifiFields()) {
                    Utils.showMsg(LoginActivity.this, "ERRO", "EXISTE ALGUM CAMPO VAZIO", R.drawable.dialog_error);
                } else {
                    corrigeCodVend();
                    CurrentInfo.codVendedor = edtLoginCodVend.getText().toString();

                    if (new Utils().verifiIpAndPort(LoginActivity.this) && Utils.verifiCnpjAndKey(LoginActivity.this)) {//se cnpj, chave e configuração de rede estiver ok
                            //task = new TaskConnectNetWork(LoginActivity.this/*Utils.getConfigLocal().getPorta()*/, btLoginDownload, CurrentInfo.codVendedor);
                        task = new TaskConnectNetWork(LoginActivity.this/*Utils.getConfigLocal().getPorta()*/, null, CurrentInfo.codVendedor);
                            task.execute(operation);
                    } else {
                        Utils.showMsg(LoginActivity.this, "ERRO", "VÁ EM CONFIGURAÇÕES E INSIRA O CNPJ DA SUA EMPRESA", R.drawable.dialog_error);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(edtLoginCodVend, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Use an AsyncTask to fetch the user's email addresses on a background thread, and update
     * the email text field with results on the main UI thread.
     */
    class SetupEmailAutoCompleteTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            ArrayList<String> emailAddressCollection = new ArrayList<>();

            // Get all emails from the user's contacts and copy them to a list.
            ContentResolver cr = getContentResolver();
            Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    null, null, null);
            while (emailCur.moveToNext()) {
                String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract
                        .CommonDataKinds.Email.DATA));
                emailAddressCollection.add(email);
            }
            emailCur.close();

            return emailAddressCollection;
        }

        @Override
        protected void onPostExecute(List<String> emailAddressCollection) {

        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                edtPassword.setError(getString(R.string.error_incorrect_password));
                edtPassword.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * @return true se algum campo estiver vazio
     */
    private boolean verifiFields(){
        boolean status = false;

        if(TextUtils.isEmpty(edtLoginCodVend.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())){
            status = true;
        }

        return status;
    }

    private boolean corrigeCodVend(){//retorna true quando não tem codigo do vendedor
        final boolean[] faltaCodVendedor = {false};
        if(edtLoginCodVend.length() < 4){
            StringBuilder builder = new StringBuilder(edtLoginCodVend.getText());
            edtLoginCodVend.setText(builder.toString());
            faltaCodVendedor[0] = false;
            CurrentInfo.codVendedor = String.valueOf(Long.parseLong(edtLoginCodVend.getText().toString()));
        }else if(edtLoginCodVend.length() == 4){
            CurrentInfo.codVendedor = String.valueOf(Long.parseLong(edtLoginCodVend.getText().toString()));
        }
        return faltaCodVendedor[0];
    }

    private boolean verifiSalesMan() throws SQLException {
        Log.i("COD VENDEDOR", CurrentInfo.codVendedor);
        boolean status = false;
        ArrayList<Rota> alRota = new RotaRN(LoginActivity.this).getRouteForSalesMan(CurrentInfo.codVendedor);
        if(alRota.size() == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
            alert.setTitle("ESTE VENDEDOR NÃO EXISTE NA BASE DE DADOS, OU NÃO EXISTE ROTA PARA O MESMO");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();
        }else{
            status = true;
        }
        return status;
    }

    /**
     * VERIFICA CONFIGURACAO DE REDE
     * @return true se houver ip e porta, false caso contrario
     * @throws SQLException
     */
    private boolean verifIpAndPort() throws SQLException {
        boolean status = false;//true quando ip ou porta
        ArrayList<ConfigLocal> listConfigLocal = new DBAccess(LoginActivity.this).pesquisar(new ConfigLocal());
        if(listConfigLocal.size() >  0){
            ConfigLocal configLocal = listConfigLocal.get(0);
            if(!configLocal.getIp().equals("") && configLocal.getPorta() != 0){
                status = true;
                //CurrentInfo.cnpjEmpresa = configLocal.getCnpjEmpresa();
            }
        }

        return status;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RETURN_CONFIG){
            try {
                if(!verifIpAndPort()){
                    Utils.showMsg(LoginActivity.this, "ERRO", "VÁ EM CONFIGURAÇÕES E INSIRA O CNPJ DA SUA EMPRESA E/OU A CHAVE DE PERMISSÃO ", R.drawable.dialog_error);
                }else{

                        btLoginDownload.setVisibility(View.VISIBLE);
                        btAtvLoginUpload.setVisibility(View.VISIBLE);
                        btLogin.setVisibility(View.VISIBLE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean verifiLicence() throws SQLException{
        boolean status = false;
        Calendar cExpira = Calendar.getInstance();
        cExpira.set(2017, 5, 1);//expira em

        //ArrayList<Configuracao> lConf = new ConfiguracaoRN(this).filtrar(new Configuracao());
        Registro r = new RegistroRN(this).getRegistro();
        if(r != null){
            if(r.getKey().equals("")) {
                Utils.showMsg(this, "ERRO", "INSIRA A CHAVE BETA", R.drawable.dialog_error);
            }else if(r.getUltima_data() == 0){
                r.setUltima_data(Calendar.getInstance().getTimeInMillis());//primeira execução
                new RegistroRN(this).update(r);
                verifiLicence();
            }else if(r.getUltima_data() >= cExpira.getTimeInMillis()){
                AlertDialog.Builder ald = new AlertDialog.Builder(this);
                ald.setTitle("ERRO");
                ald.setCancelable(false);
                ald.setMessage("A LICENÇA EXPIROU, POR FAVOR SOLICITE UMA NOVA");
                ald.setIcon(R.drawable.dialog_error);
                ald.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int wich){
                        finish();
                    }
                });
                ald.show();
            }else if(r.getUltima_data() > Calendar.getInstance().getTimeInMillis()) {//ultima data menor que data atual
                AlertDialog.Builder ald = new AlertDialog.Builder(this);
                ald.setTitle("ERRO");
                ald.setCancelable(false);
                ald.setMessage("DATA INCORRETA");
                ald.setIcon(R.drawable.dialog_error);
                ald.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int wich){
                        finish();
                    }
                });
                ald.show();
            }else if(Calendar.getInstance().getTimeInMillis() >= cExpira.getTimeInMillis()){
                AlertDialog.Builder ald = new AlertDialog.Builder(this);
                ald.setTitle("ERRO");
                ald.setCancelable(false);
                ald.setMessage("A LICENÇA EXPIROU, POR FAVOR SOLICITE UMA NOVA");
                ald.setIcon(R.drawable.dialog_error);
                ald.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int wich){
                        finish();
                    }
                });
                ald.show();
            }else if(r.getKey().equals(key)){
                r.setUltima_data(Calendar.getInstance().getTimeInMillis());//primeira execução
                new RegistroRN(this).update(r);//atualiza ultima data
                //verifiLicence();
                Log.i("dataUltima", r.getUltima_data()+"");
                Log.i("dataUltima", Calendar.getInstance().getTimeInMillis()+"");
                Log.i("dataUltima", cExpira.getTimeInMillis()+"");
                status = true;
            }
        }
        return status;
    }

    public String getRetransmite() {
        return retransmite;
    }

    public void setRetransmite(String retransmite) {
        this.retransmite = retransmite;
    }

    protected void onResume(){
        super.onResume();
        Log.i("LOGIN", "RESUME");
        try {
            Utils.verifiLicence(LoginActivity.this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void onRestart(){
        super.onRestart();
        Log.i("LOGIN", "RESTART");
        try {
            Utils.verifiLicence(LoginActivity.this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
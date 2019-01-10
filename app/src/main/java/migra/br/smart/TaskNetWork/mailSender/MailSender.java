package migra.br.smart.TaskNetWork.mailSender;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import migra.br.smart.manipulaBanco.dbAccess.DBAccess;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocal;
import migra.br.smart.utils.compactador.Compactador;

/**
 * Created by r2d2 on 3/27/18.
 */

public class MailSender {

    private String[] destino;//emails de destino
    private String assunto;
    private String mensagem;

    private Context context;

    private File p, pDest ;

    public MailSender(Context ctx){
        setAssunto("PEDIDO VENDEDOR");
        setDestino(new String[]{""});
        setMensagem("PEDIDOS EM ANEXO");
        setContext(ctx);

    }


    /**
     * Configura para enviar o e-mail por gmail
     * @author Dainel
     */
    public void startMail(){
        p = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getParent()+"/migra/pedido");
        pDest = new File(p.getParent()+"/"+p.getName()+".zip");
        String pathTemp = "";
        try {
            Log.i("FOLDER", p.getAbsolutePath()+"----"+p.getParent()+"/"+p.getName()+".zip");
            Compactador.zipFolder(p.getAbsolutePath(), pDest.getAbsolutePath());
        } catch (Exception ex) {
            Log.i("ERRO", ex.getMessage());
        }

        String[] TO = {"jdanielobj@gmail.com"};
        String[] CC = {""};
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setData(Uri.parse("mailto:"));
        email.setType("text/plain");
        email.putExtra(Intent.EXTRA_EMAIL, getDestino());
        email.putExtra(Intent.EXTRA_CC, CC);
        email.putExtra(Intent.EXTRA_SUBJECT, getAssunto());
        email.putExtra(Intent.EXTRA_TEXT, getMensagem());
        File file = getAttachment(pDest.getAbsolutePath());

        Log.i("F_FINAL", pDest.getAbsolutePath());
        email.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pDest));

        getContext().startActivity(Intent.createChooser(email, "teste"));
    }

    public void removePedidos(){
        //boolean status = false;

        for(File f: p.listFiles()){
            f.delete();
        }
        //pDest.delete();

        //return status;
    }

    private File getAttachment(String f) {
        return new File(Environment.getExternalStorageDirectory()+File.separator+"", f);
    }

    public String[] getDestino() {
        return destino;
    }

    public void setDestino(String[] destino) {
        this.destino = destino;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
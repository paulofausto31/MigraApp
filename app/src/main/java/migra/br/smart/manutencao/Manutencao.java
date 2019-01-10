package migra.br.smart.manutencao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by root on 04/09/17.
 */

public class Manutencao {
    Context ctx;
    boolean status = false;

    public Manutencao(Context ctx){
        this.ctx = ctx;
    }

    public boolean copyDb(File origemDb, File destinoDb){
        Log.i("path", origemDb.listFiles()[0].toString());
        FileInputStream fis=null;
        FileOutputStream fos=null;

        BufferedOutputStream bfOut = null;
        BufferedInputStream bfIn = null;

        Log.i("SIZE", new File("/data/data/").getFreeSpace()+"");

        if(new File("/mnt/sdcard/").getFreeSpace() > 0) {
            try {
                bfIn = new BufferedInputStream(new FileInputStream(origemDb));
                bfOut = new BufferedOutputStream(new FileOutputStream(destinoDb));
                byte block[] = new byte[512];
                while (bfIn.read(block) != -1) {
                    bfOut.write(block);
                }
                Toast.makeText(ctx, "DB dump OK", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ctx, "DB dump ERROR", Toast.LENGTH_LONG).show();
            } finally {
                try {
                    bfOut.close();
                    bfIn.close();
                } catch (IOException ioe) {
                }
            }
        }
        return status;
    }
}

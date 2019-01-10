package migra.br.smart.manipulaBanco.importar;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ydxpaj on 21/07/2016.
 */
public class Importar {
    ArrayList<Object> entrada;
    Context ctx;

    public Importar(Context ctx){
        this.ctx = ctx;
    }

    public ArrayList<Object> getEntrada() {
        return entrada;
    }

    public void setEntrada(ArrayList<Object> entrada) {
        this.entrada = entrada;
    }

    public void importar(){
        //new ItemListaRN(ctx).salvar(entrada);
    }
}

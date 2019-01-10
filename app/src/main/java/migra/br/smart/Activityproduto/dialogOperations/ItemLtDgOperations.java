package migra.br.smart.Activityproduto.dialogOperations;

import migra.br.smart.R;

/**
 * Created by droidr2d2 on 16/01/2017.
 */
public class ItemLtDgOperations {
    private int src;
    private int background;
    private String titulo;

    public ItemLtDgOperations(){
        setBackground(R.drawable.circulo);
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

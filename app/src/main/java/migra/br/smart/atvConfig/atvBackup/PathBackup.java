package migra.br.smart.atvConfig.atvBackup;

import java.io.File;

/**
 * Created by r2d2 on 3/15/18.
 */

public class PathBackup {
    private File path;
    private String tipo;//P=PASTA, A=ARQUIVO

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

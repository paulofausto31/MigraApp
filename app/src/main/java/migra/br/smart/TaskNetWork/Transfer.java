package migra.br.smart.TaskNetWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by ydxpaj on 06/07/2016.
 */
public class Transfer {

    public static final int T_INT = 1;
    public static final int T_FLOAT = 2;
    public static final int T_DOUBLE = 3;
    public static final int T_STRING = 4;
    public static final int T_LONG = 5;
    public static final int T_DATE = 6;
    public static final int T_NULL = 7;


    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private InputStreamReader inputStreamReader;
    private ObjectOutputStream outputStream;

    private String msg;

    private File interno;


    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setInput(DataInputStream input) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(input));
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public String getMsg() {
        return msg;
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    /*public void setSocket(Socket socket) {
        this.socket = socket;
    }
*/
    public BufferedReader getInput() {
        return bufferedReader;
    }

    public BufferedWriter getOutput() {
        return bufferedWriter;
    }

    public void setOutput(OutputStream output) {

        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(output));
    }

    public void send(String msg) throws IOException {
        getOutput().write(msg);
        getOutput().flush();
    }

    public String readKind() throws IOException {
        return getInput().readLine();
    }

    public void closeConnect() throws IOException{
        getOutput().close();
        getInput().close();
        getSocket().close();
    }

}

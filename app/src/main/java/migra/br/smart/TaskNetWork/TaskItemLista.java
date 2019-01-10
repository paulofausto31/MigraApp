package migra.br.smart.TaskNetWork;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by droidr2d2 on 13/01/2017.
 */
public class TaskItemLista extends AsyncTask<String, String, Void> {

    Context ctx;

    public TaskItemLista(Context ctx){
        this.ctx = ctx;
    }

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected void onPostExecute(Void result){
        super.onPostExecute(result);
    }
    @Override
    protected Void doInBackground(String... params) {



        return null;
    }
}

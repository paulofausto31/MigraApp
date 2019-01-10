package migra.br.smart.atvConfig.atvBackup;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import migra.br.smart.R;

import static android.os.Environment.getExternalStorageDirectory;

//import smart.br.migra.migraapp.R;

public class BackupActivity extends AppCompatActivity {

    private ListView lvPathBkp;
    private ImageButton imgBtBack;
    private File[] files;
    private ArrayList<File> currentFolders;//armazena a pasta atual
    private String rootFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        currentFolders = new ArrayList<File>();
        //setRootFolder(Environment.getExternalStorageDirectory().getAbsolutePath());
        setRootFolder("//");

        imgBtBack = (ImageButton) findViewById(R.id.imgBtBack);
        lvPathBkp = (ListView) findViewById(R.id.lvPathBkp);

        imgBtBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(currentFolders.size() > 0) {
                    int index = currentFolders.size()-1;
                    fillListView(currentFolders.get(index).getAbsolutePath());
                    Toast.makeText(BackupActivity.this, currentFolders.get(index).getAbsolutePath(), Toast.LENGTH_LONG).show();
                    currentFolders.remove(index);
                }else{
                    currentFolders.add(new File(getRootFolder()));

                    setRootFolder(currentFolders.get(0).getParent());
                    Log.e("ERR_FOL", currentFolders.get(0).getParent());
                    Toast.makeText(BackupActivity.this, currentFolders.get(0).getAbsolutePath(), Toast.LENGTH_LONG).show();
                    fillListView(currentFolders.get(0).getParent());

                    currentFolders.remove(0);
                }
            }
        });

        fillListView(getRootFolder());

        lvPathBkp.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PathBackup pBkp = (PathBackup) lvPathBkp.getAdapter().getItem(position);
                Toast.makeText(BackupActivity.this, pBkp.getPath().getParent(), Toast.LENGTH_LONG).show();
                if(pBkp.getPath().isDirectory()) {
                    currentFolders.add(new File(pBkp.getPath().getParent()));//primeira pasta
                    fillListView(pBkp.getPath().getAbsolutePath());
                }
            }
        });
    }

    private void fillListView(String pathFile){
        ArrayList<PathBackup> list = new ArrayList<PathBackup>();
        files = new File(pathFile).listFiles();
        for(File f: files){
            PathBackup pathBackup = new PathBackup();
            pathBackup.setPath(f);
            if(f.isDirectory()) {
                pathBackup.setTipo("P");
            }else{
                pathBackup.setTipo("F");
            }

            list.add(pathBackup);
        }
        lvPathBkp.setAdapter(new BackupAtvAdp(this, list));
    }

    public String getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }
}
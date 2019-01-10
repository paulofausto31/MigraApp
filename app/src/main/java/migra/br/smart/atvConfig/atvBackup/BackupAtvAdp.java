package migra.br.smart.atvConfig.atvBackup;

import android.content.Context;
import android.graphics.Path;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import migra.br.smart.R;

/**
 * Created by r2d2 on 3/15/18.
 */

public class BackupAtvAdp extends BaseAdapter {
    private List<PathBackup> list;
    private Context ctx;

    public BackupAtvAdp(Context ctx, List<PathBackup> list){
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inlfate = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inlfate.inflate(R.layout.atv_adp_list_backup, null);

        PathBackup pBkp = (PathBackup) getItem(position);

        ImageView imgFolderFile = (ImageView) v.findViewById(R.id.imgFolderFile);
        TextView tvBackupPath = (TextView) v.findViewById(R.id.tvBackupPath);

        tvBackupPath.setText(pBkp.getPath().getAbsolutePath());
        switch(pBkp.getTipo()){
            case "P":
                imgFolderFile.setImageResource(R.drawable.folder);
                break;
            case "F":
                imgFolderFile.setImageResource(R.drawable.file);
                break;
        }

        return v;
    }
}

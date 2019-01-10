package migra.br.smart.activityLogin.dgOpDownTables;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import migra.br.smart.R;

/**
 * Created by r2d2 on 26/05/17.
 */

public class DgOpDownTablesAdap extends BaseAdapter {
    private Context ctx;
    private HashMap<String, String> list;
    private String[] keySet = null;

    public DgOpDownTablesAdap(Context ctx, HashMap<String, String> list){
        this.ctx = ctx;
        this.list = list;
        this.keySet = list.values().toArray(new String[list.size()]);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dg_opt_down_tables_adp, null);
        final CheckBox chDgOpDowTabAdp = (CheckBox) v.findViewById(R.id.chDgOpDowTabAdp);
        final TextView tvDgOpTbAdpNomeTable = (TextView) v.findViewById(R.id.tvDgOpTbAdpNomeTable);

        tvDgOpTbAdpNomeTable.setText(keySet[position]);
        return v;
    }
}

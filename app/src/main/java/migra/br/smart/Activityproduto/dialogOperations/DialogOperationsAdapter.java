package migra.br.smart.Activityproduto.dialogOperations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import migra.br.smart.R;

/**
 * Created by droidr2d2 on 14/01/2017.
 */
public class DialogOperationsAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<ItemLtDgOperations> list;

    public DialogOperationsAdapter(Context ctx, ArrayList<ItemLtDgOperations> list){
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public ItemLtDgOperations getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflate = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.dialog_operaions_adapter, null);

        ItemLtDgOperations item = getItem(position);

        ImageView imgDgOperations = (ImageView)v.findViewById(R.id.imgDgOperations);
        TextView tvDgOperations = (TextView)v.findViewById(R.id.tvDgOperations);

        imgDgOperations.setBackgroundResource(item.getBackground());
        imgDgOperations.setImageResource(item.getSrc());

        tvDgOperations.setText(item.getTitulo());

        return v;
    }
}
package migra.br.smart.mainActivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by ydxpaj on 19/08/2016.
 */
public class GridAdapter extends BaseAdapter {

    Context ctx;
    private int[] imagens;


    public GridAdapter(Context ctx, int[] imagens){
        this.ctx = ctx;
        this.imagens = imagens;

    }

    @Override
    public int getCount() {
        return imagens.length;
    }

    @Override
    public Object getItem(int position) {
        return imagens[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img = new ImageView(ctx);
        img.setImageResource(imagens[position]);
      //  img.setAdjustViewBounds(true);
        //img.setLayoutParams(params);
        return img;
    }
}

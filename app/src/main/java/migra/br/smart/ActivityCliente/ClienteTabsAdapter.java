package migra.br.smart.ActivityCliente;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import migra.br.smart.ActivityCliente.fragmentComodato.ComodatoFragment;
import migra.br.smart.ActivityContRec.ContRecTabFragment;
import migra.br.smart.R;
import migra.br.smart.pedidoFragment.PedidoFragment;

/**
 * Created by droidr2d2 on 22/02/2017.
 */
public class ClienteTabsAdapter extends FragmentPagerAdapter {
    private Context context;
    private Bundle bundle;
    private FragmentManager fm;
    public ClienteTabsAdapter(Bundle bundle, Context context, FragmentManager fm) {
        super(fm);
        this.fm = fm;
        this.context = context;
        this.bundle = bundle;
    }

    public CharSequence getPageTitle(int position){
        if(position == 1){
            return context.getString(R.string.tab_conta_recebe);
        }else if(position == 0){
            return context.getString(R.string.tab_pedido);
        }else if(position == 2){
            return context.getString(R.string.tab_comodato);
            //return context.getString(R.string.tab_justifica);
        }/*else if(position == 3){
            return context.getString(R.string.tab_historico);
            //return context.getString(R.string.tab_comodato);
        }/*else if(position == 4){
            //return context.getString(R.string.tab_historico);
        }*/

        return context.getString(R.string.tab_pedido);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        if(position == 1){
            f = new ContRecTabFragment();
        }else if(position == 0){
            f = new PedidoFragment();
            if(this.bundle != null){
                f.setArguments(this.bundle);
            }
        }else if(position == 2){
            f = new ComodatoFragment();
            //f = new NegativacaoFragment();
        }/*else if(position == 3){
            f = new HistoricoFragment();
            //f = new ComodatoFragment();
        }/*else if(position == 4){
            //f = new HistoricoFragment();
        }*/
        return f;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
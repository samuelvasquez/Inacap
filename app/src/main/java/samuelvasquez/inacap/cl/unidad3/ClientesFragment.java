package samuelvasquez.inacap.cl.unidad3;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClientesFragment extends Fragment {

    public static final String ARG_ITEM_ID = "clientes";
    private Activity activity;
    private int id_vendedor;
    //public static final String ARG_VENDEDOR_ID = "id_vendedor";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        id_vendedor = ((MainActivity) activity).GetIdVendedor();

        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getText(R.string.title_fragment_clientes));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clientes, container, false);

        Log.d("onCreateView", "onCreateView init");

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.clientes_fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) activity).goToAddCliente();
                }
            });
            Log.d("onCreate", "fab ok");
        }

        if (rootView.findViewById(R.id.cliente_detail_container) != null) {
            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            Log.d("onCreate", "detail init");
            ((ClientesListFragment) getChildFragmentManager() //getSupportFragmentManager()
                    .findFragmentById(R.id.clientes_list))
                    .setActivateOnItemClick(true);
            Log.d("onCreate", "detail ok");
        }


        Log.d("onCreateView", "onCreateView ok");

        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_cliente, menu);
    }


}
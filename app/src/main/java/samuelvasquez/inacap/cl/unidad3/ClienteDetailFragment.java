package samuelvasquez.inacap.cl.unidad3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOCliente;
import samuelvasquez.inacap.cl.unidad3.datamodel.Cliente;


public class ClienteDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "cliente_detail";
    //public static final String ARG_EDITAR = "editar";
    public static final String ARG_CLIENTE_ID = "id_cliente";
    //public static final String ARG_VENDEDOR_ID = "id_vendedor";

    /**
     * The content this fragment is presenting.
     */
    private Cliente mItem;
    private Activity activity;
    private DAOCliente clienteDAO;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClienteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        clienteDAO = new DAOCliente(activity);

        setHasOptionsMenu(true);

        if (getArguments().containsKey(ARG_CLIENTE_ID)) {
            mItem = clienteDAO.getCliente(getArguments().getInt(ARG_CLIENTE_ID));

            if (mItem != null) {
                Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
                if (toolbar != null) {
                    toolbar.setTitle(mItem.nombre);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cliente_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.txt_cliente_nombre)).setText(mItem.nombre);
            ((TextView) rootView.findViewById(R.id.txt_cliente_direccion)).setText(mItem.direccion);
            ((TextView) rootView.findViewById(R.id.txt_cliente_telefono)).setText(mItem.telefono);
            ((TextView) rootView.findViewById(R.id.txt_cliente_latitud)).setText(String.valueOf(mItem.latitud));
            ((TextView) rootView.findViewById(R.id.txt_cliente_longuitud)).setText(String.valueOf(mItem.longuitud));
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.clientes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit:
                editarCliente();
                return true;
            case R.id.action_delete:
                confirmarBorrado();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editarCliente() {
        if (mItem != null) {
            ((MainActivity) activity).goToEditCliente(mItem.id);
        }
    }

    /**
     * Se pregunta al usuario si realmente desea eliminar usuario
     */
    private void confirmarBorrado() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(activity);
        dialogo1.setTitle("Confirmacion");
        dialogo1.setMessage("Esta seguro de eliminar este cliente?");
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                borrarCliente();
            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                return;
            }
        });
        dialogo1.show();
    }

    /**
     * Se realiza la eliminacion de un cliente
     */
    private void borrarCliente() {
        if (mItem != null) {
            clienteDAO.delete(mItem);
            ((MainActivity) activity).goToClientes();
        }
    }


}

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
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOPedido;
import samuelvasquez.inacap.cl.unidad3.datamodel.DetallePedido;
import samuelvasquez.inacap.cl.unidad3.datamodel.Pedido;

public class PedidoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "pedido_detail";
    public static final String ARG_PEDIDO_ID = "id_pedido";

    /**
     * The content this fragment is presenting.
     */
    private Pedido mItem;
    private Activity activity;
    private DAOPedido pedidoDAO;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PedidoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        pedidoDAO = new DAOPedido(activity);

        setHasOptionsMenu(true);

        if (getArguments().containsKey(ARG_PEDIDO_ID)) {
            mItem = pedidoDAO.getPedido(getArguments().getInt(ARG_PEDIDO_ID));

            if (mItem != null) {
                Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
                if (toolbar != null) {
                    toolbar.setTitle(mItem.toString());
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pedido_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

            ((TextView) rootView.findViewById(R.id.txt_cliente_nombre)).setText(mItem.nombre_cliente);
            ((TextView) rootView.findViewById(R.id.txt_fecha)).setText(dateFormatter.format(mItem.fecha_entrega));
            ((TextView) rootView.findViewById(R.id.txt_estado)).setText(mItem.entregado ? "Entregado" : "Pendiente");
            TableLayout detalle = (TableLayout) rootView.findViewById(R.id.detalle);

            ArrayList<DetallePedido> detalleList = mItem.detalles;

            for (int i = 0; i < detalleList.size(); i++) {
                TableRow row = new TableRow(activity);
                row.setLayoutParams(new TableRow.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT));

                TextView tv01 = new TextView(activity);
                tv01.setLayoutParams(new TableRow.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
                tv01.setText(detalleList.get(i).nombre_producto);

                TextView tv02 = new TextView(activity);
                tv02.setLayoutParams(new TableRow.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
                tv02.setText(String.valueOf(detalleList.get(i).cantidad));

                TextView tv03 = new TextView(activity);
                tv03.setLayoutParams(new TableRow.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
                tv03.setText(String.valueOf(detalleList.get(i).precio));

                TextView tv04 = new TextView(activity);
                tv04.setLayoutParams(new TableRow.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
                tv04.setText(String.valueOf(detalleList.get(i).total()));

                row.addView(tv01);
                row.addView(tv02);
                row.addView(tv03);
                row.addView(tv04);

                detalle.addView(row);
            }

        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.pedidos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit:
                editarPedido();
                return true;
            case R.id.action_delete:
                confirmarBorrado();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editarPedido() {
        if (mItem != null) {
            ((MainActivity) activity).goToEditPedido(mItem.id);
        }
    }

    /**
     * Se pregunta al usuario si realmente desea eliminar usuario
     */
    private void confirmarBorrado() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(activity);
        dialogo1.setTitle("Confirmacion");
        dialogo1.setMessage("Esta seguro de eliminar este pedido?");
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                borrarPedido();
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
    private void borrarPedido() {
        if (mItem != null) {
            pedidoDAO.delete(mItem);
            ((MainActivity) activity).goToPedidos();
        }
    }


}

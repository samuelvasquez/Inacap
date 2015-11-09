package samuelvasquez.inacap.cl.unidad3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import samuelvasquez.inacap.cl.unidad3.dataaccess.ClienteListAdapter;
import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOCliente;
import samuelvasquez.inacap.cl.unidad3.datamodel.Cliente;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientesListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientesListFragment extends Fragment {
    public static final String ARG_ITEM_ID = "clientes_list";

    private static final String ARG_VENDEDOR_ID = "id_vendedor";

    private int id_vendedor;

    Activity activity;
    ArrayList<Cliente> clientes;
    Cliente cliente;
    ClienteListAdapter clienteListAdapter;
    DAOCliente clienteDAO;
    private GetClienteTask task;
    private ActionMode mActionMode;

    // UI references
    ListView list_cliente;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClientesListFragment.
     */
    public static ClientesListFragment newInstance(int id_vendedor) {
        ClientesListFragment fragment = new ClientesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VENDEDOR_ID, String.valueOf(id_vendedor));
        fragment.setArguments(args);
        return fragment;
    }

    public ClientesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        clienteDAO = new DAOCliente(activity);
        if (getArguments() != null) {
            id_vendedor = getArguments().getInt(ARG_VENDEDOR_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_cliente, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_clientes_list, container, false);
        findViewsById(rootView);

        task = new GetClienteTask(activity);
        task.execute((Void) null);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                ((MainActivity)activity).goToAddCliente();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class GetClienteTask extends AsyncTask<Void, Void, ArrayList<Cliente>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetClienteTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Cliente> doInBackground(Void... arg0) {
            ArrayList<Cliente> clienteList = clienteDAO.getClientesByIdVendedor(id_vendedor);
            return clienteList;
        }

        @Override
        protected void onPostExecute(ArrayList<Cliente> clienteList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                Log.d("cliente", clienteList.toString());
                clientes = clienteList;
                if (clienteList != null) {
                    if (clienteList.size() != 0) {
                        clienteListAdapter = new ClienteListAdapter(activity,
                                clienteList);
                        list_cliente.setAdapter(clienteListAdapter);
                        clienteListAdapter.notifyDataSetChanged();

                        // ListView Item Click Listener
                        list_cliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                cliente = (Cliente)list_cliente.getItemAtPosition(position);
                                if(mActionMode == null)
                                {
                                    mActionMode = activity.startActionMode(mActionModeCallback);
                                    view.setSelected(true);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(activity, "Sin clientes",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.clientes, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    editarCliente();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                case R.id.action_delete:
                    confirmarBorrado();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode  = null;
        }
    };

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.title_fragment_clientes);
        //getActivity().getActionBar().setTitle(R.string.title_fragment_clientes);
        super.onResume();
    }


    private void findViewsById(View view) {
        list_cliente = (ListView) view.findViewById(R.id.list_cliente);
    }


    /*
     * This method is invoked from MainActivity onFinishDialog() method. It is
     * called from CustomEmpDialogFragment when an employee record is updated.
     * This is used for communicating between fragments.
     */
    public void updateView() {
        task = new GetClienteTask(activity);
        task.execute((Void) null);
    }

    private void editarCliente()
    {
        if(cliente != null)
        {
            ((MainActivity)activity).goToEditCliente(cliente.id);
        }
    }

    /**
     * Se pregunta al usuario si realmente desea eliminar usuario
     * */
    private void confirmarBorrado()
    {
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
     * */
    private void borrarCliente()
    {
        if(cliente != null)
        {
            if(cliente.id == id_vendedor)
            {
                Toast.makeText(activity, "No se puede eliminar el usuario Logeado", Toast.LENGTH_SHORT).show();
                return;
            }
            clienteDAO.delete(cliente);
            clienteListAdapter.remove(cliente);
        }
    }

}

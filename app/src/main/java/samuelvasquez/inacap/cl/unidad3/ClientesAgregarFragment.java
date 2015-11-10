package samuelvasquez.inacap.cl.unidad3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOCliente;
import samuelvasquez.inacap.cl.unidad3.datamodel.Cliente;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientesAgregarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientesAgregarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientesAgregarFragment extends Fragment {
    public static final String ARG_ITEM_ID = "clientes_agregar";

    public static final String ARG_EDITAR = "editar";
    public static final String ARG_CLIENTE_ID = "id_cliente";
    public static final String ARG_VENDEDOR_ID = "id_vendedor";
    Activity activity;
    private boolean editar = false;
    private int id_cliente;
    private int id_vendedor;
    private DAOCliente dao;

    // UI references
    private EditText etxt_nombre;
    private EditText etxt_direccion;
    private EditText etxt_telefono;
    private EditText etxt_latitud;
    private EditText etxt_longuitud;

    private OnFragmentInteractionListener mListener;

    public ClientesAgregarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClientesAgregarFragment.
     */
    public static ClientesAgregarFragment newInstance(Boolean editar, int id_cliente, int id_vendedor) {
        ClientesAgregarFragment fragment = new ClientesAgregarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EDITAR, String.valueOf(editar));
        args.putString(ARG_CLIENTE_ID, String.valueOf(id_cliente));
        args.putString(ARG_VENDEDOR_ID, String.valueOf(id_vendedor));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        dao = new DAOCliente(getActivity());
        if (getArguments() != null) {
            editar = getArguments().getBoolean(ARG_EDITAR);
            id_cliente = getArguments().getInt(ARG_CLIENTE_ID);
            id_vendedor = getArguments().getInt(ARG_VENDEDOR_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form_cliente, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_clientes_agregar, container, false);

        View rootView = inflater.inflate(R.layout.fragment_clientes_agregar, container, false);
        findViewsById(rootView);
        if(editar)
        {
            Cliente cliente = dao.getCliente(id_cliente);
            if(cliente != null)
            {
                etxt_nombre.setText(cliente.nombre);
                etxt_direccion.setText(cliente.direccion);
                etxt_telefono.setText(cliente.telefono);
                etxt_latitud.setText(String.valueOf(cliente.latitud));
                etxt_longuitud.setText(String.valueOf(cliente.longuitud));
            }
        }
        etxt_nombre.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etxt_nombre);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        etxt_direccion.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etxt_direccion);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        etxt_telefono.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etxt_telefono);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        etxt_latitud.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.isDouble(etxt_latitud, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        etxt_longuitud.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.isDouble(etxt_longuitud, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                if (checkValidation())
                    confirmarRegistro();
                else
                    Toast.makeText(activity, "Debe corregir los errores para continuar", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_revert:
                ((MainActivity) activity).goToClientes();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        if(editar)
        {
            getActivity().setTitle(R.string.update_cliente);
            //getActivity().getActionBar().setTitle(R.string.update_cliente);
        }
        else
        {
            getActivity().setTitle(R.string.add_cliente);
            //getActivity().getActionBar().setTitle(R.string.add_cliente);
        }
        super.onResume();
    }

    private void findViewsById(View rootView) {
        etxt_nombre = (EditText) rootView.findViewById(R.id.etxt_nombre);
        etxt_direccion = (EditText) rootView.findViewById(R.id.etxt_direccion);
        etxt_telefono = (EditText) rootView.findViewById(R.id.etxt_telefono);
        etxt_latitud = (EditText) rootView.findViewById(R.id.etxt_latitud);
        etxt_longuitud = (EditText) rootView.findViewById(R.id.etxt_longuitud);
    }

    protected void resetAllFields() {
        etxt_nombre.setText("");
        etxt_direccion.setText("");
        etxt_telefono.setText("");
        etxt_latitud.setText("");
        etxt_longuitud.setText("");
    }

    private Cliente setCliente() {
        Cliente cliente = new Cliente();
        cliente.id = id_cliente;
        cliente.id_vendedor = id_vendedor;
        cliente.nombre = etxt_nombre.getText().toString();
        cliente.direccion = etxt_direccion.getText().toString();
        cliente.telefono = etxt_telefono.getText().toString();
        cliente.latitud = Double.parseDouble(etxt_latitud.getText().toString());
        cliente.longuitud = Double.parseDouble(etxt_longuitud.getText().toString());
        return cliente;
    }

    private void confirmarRegistro()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this.activity);
        dialogo1.setTitle("Confirmacion");
        if(!editar){
            dialogo1.setMessage("Esta seguro de agregar este cliente?");
        }
        else
        {
            dialogo1.setMessage("Esta seguro de modificar este cliente?");
        }
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                validarCliente();
            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                return;
            }
        });
        dialogo1.show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(etxt_nombre)) ret = false;
        if (!Validation.hasText(etxt_direccion)) ret = false;
        if (!Validation.hasText(etxt_telefono)) ret = false;
        if (!Validation.isDouble(etxt_latitud, true)) ret = false;
        if (!Validation.isDouble(etxt_longuitud, true)) ret = false;

        return ret;
    }

    /**
     * Se realiza la validacion de los datos ingresados y se registra el nuevo usuario
     **/
    private void validarCliente() {
        if (etxt_nombre.getText().toString().trim().equals("")) {
            Toast.makeText(activity, "Debe ingresar un nombre", Toast.LENGTH_LONG).show();
            etxt_nombre.requestFocus();
            return;
        }

        if (etxt_direccion.getText().toString().trim().equals("")) {
            Toast.makeText(activity, "Debe ingresar direccion", Toast.LENGTH_LONG).show();
            etxt_direccion.requestFocus();
            return;
        }

        if (etxt_telefono.getText().toString().trim().equals("")) {
            Toast.makeText(activity, "Debe ingresar telefono", Toast.LENGTH_LONG).show();
            etxt_telefono.requestFocus();
            return;
        }

        if (etxt_latitud.getText().toString().trim().equals("")) {
            Toast.makeText(activity, "Debe ingresar latitud", Toast.LENGTH_LONG).show();
            etxt_latitud.requestFocus();
            return;
        }

        if (etxt_longuitud.getText().toString().trim().equals("")) {
            Toast.makeText(activity, "Debe ingresar longuitud", Toast.LENGTH_LONG).show();
            etxt_longuitud.requestFocus();
            return;
        }

        Cliente cliente = setCliente();
        if (!editar) {
            dao.save(cliente);
            Toast.makeText(activity, "Cliente agregado correctamente", Toast.LENGTH_LONG).show();
        } else {
            dao.update(cliente);
            Toast.makeText(activity, "Cliente modificado correctamente", Toast.LENGTH_LONG).show();
        }

        ((MainActivity) activity).goToClientes();
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
        void onFragmentInteraction(Uri uri);
    }
}

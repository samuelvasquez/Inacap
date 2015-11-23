package samuelvasquez.inacap.cl.unidad3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOCliente;
import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOPedido;
import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOProducto;
import samuelvasquez.inacap.cl.unidad3.datamodel.Cliente;
import samuelvasquez.inacap.cl.unidad3.datamodel.DetallePedido;
import samuelvasquez.inacap.cl.unidad3.datamodel.Pedido;
import samuelvasquez.inacap.cl.unidad3.datamodel.Producto;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PedidosAgregarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PedidosAgregarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidosAgregarFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_ITEM_ID = "pedidos_agregar";

    public static final String ARG_EDITAR = "editar";
    public static final String ARG_PEDIDO_ID = "id_pedido";
    // public static final String ARG_VENDEDOR_ID = "id_vendedor";
    Activity activity;
    private boolean editar = false;
    private int id_pedido;
    private int id_vendedor;
    private SimpleDateFormat dateFormatter;

    private DAOPedido daoPedido;
    private DAOCliente daoCliente;
    private DAOProducto daoProducto;

    private ArrayList<Cliente> clienteList;
    private ArrayList<Producto> productoList;
    private ArrayList<DetallePedido> detalleList;

    // UI references
    private Spinner sel_cliente;
    private EditText txt_fecha;
    private CheckBox check_entregado;
    private DatePickerDialog fechaDatePickerDialog;
    private Spinner sel_producto;
    private EditText txt_cantidad;
    private EditText txt_precio;
    private Button btn_add;
    private TableLayout detalle;

    private OnFragmentInteractionListener mListener;

    public PedidosAgregarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PedidosAgregarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PedidosAgregarFragment newInstance(Boolean editar, int id_pedido) {
        PedidosAgregarFragment fragment = new PedidosAgregarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EDITAR, String.valueOf(editar));
        args.putString(ARG_PEDIDO_ID, String.valueOf(id_pedido));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        id_vendedor = ((MainActivity) activity).GetIdVendedor();
        daoCliente = new DAOCliente(activity);
        daoPedido = new DAOPedido(activity);
        daoProducto = new DAOProducto(activity);
        detalleList = new ArrayList<DetallePedido>();
        if (getArguments() != null) {
            editar = getArguments().getBoolean(ARG_EDITAR);
            id_pedido = getArguments().getInt(ARG_PEDIDO_ID);
        }
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            if (editar) {
                toolbar.setTitle(getText(R.string.update_pedido));
            } else {
                toolbar.setTitle(getText(R.string.add_pedido));
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.form_cliente, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_pedidos_agregar, container, false);
        View rootView = inflater.inflate(R.layout.fragment_pedidos_agregar, container, false);
        id_vendedor = ((MainActivity) activity).GetIdVendedor();

        dateFormatter = ((SimpleDateFormat) DateFormat.getDateInstance());

        findViewsById(rootView);
        setDateTimeField();

        clienteList = daoCliente.getClientesByIdVendedor(id_vendedor);
        List<String> nombresClientes = new ArrayList<String>();
        for (int i = 0; i < clienteList.size(); i++) {
            nombresClientes.add(clienteList.get(i).nombre);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, nombresClientes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sel_cliente.setAdapter(spinnerAdapter);

        productoList = daoProducto.getAllProductos();
        List<String> nombresProductos = new ArrayList<String>();
        for (int i = 0; i < productoList.size(); i++) {
            nombresProductos.add(productoList.get(i).nombre);
        }
        ArrayAdapter<String> spinnerProductoAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, nombresProductos);
        spinnerProductoAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sel_producto.setAdapter(spinnerProductoAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidationDetalle())
                    confirmarRegistroDetalle();
                else
                    Toast.makeText(activity, getText(R.string.hay_errores), Toast.LENGTH_LONG).show();
            }
        });

        if(editar)
        {
            Pedido pedido = daoPedido.getPedido(id_pedido);
            if(pedido != null)
            {
                int spinnerPosition = 0;
                for (int i = 0; i < clienteList.size(); i++) {
                    if(clienteList.get(i).id == pedido.id_cliente)
                    {
                        spinnerPosition = i;
                    }
                }
                sel_cliente.setSelection(spinnerPosition);
                txt_fecha.setText(dateFormatter.format(pedido.fecha_entrega));
                check_entregado.setChecked(pedido.entregado);

                detalleList = pedido.detalles;

                for (int i = 0; i < detalleList.size(); i++)
                {
                    TableRow row = new TableRow(activity);
                    row.setLayoutParams(new TableRow.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT,GridLayout.LayoutParams.WRAP_CONTENT));

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
        }

        txt_fecha.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(txt_fecha);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
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
                    Toast.makeText(activity, getText(R.string.hay_errores), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_revert:
                ((MainActivity) activity).goToPedidos();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == txt_fecha) {
            fechaDatePickerDialog.show();
        }
    }

    @Override
    public void onResume() {
        if(editar)
        {
            getActivity().setTitle(R.string.update_pedido);
            //getActivity().getActionBar().setTitle(R.string.update_pedido);
        }
        else
        {
            getActivity().setTitle(R.string.add_pedido);
            //getActivity().getActionBar().setTitle(R.string.add_pedido);
        }
        super.onResume();
    }

    protected void resetAllFields() {
        sel_cliente.setSelection(0);
        txt_fecha.setText("");
        check_entregado.setChecked(false);
        sel_producto.setSelection(0);
        txt_cantidad.setText("");
        txt_precio.setText("");
    }

    private Pedido setPedido() {
        Pedido pedido = new Pedido();
        pedido.id = id_pedido;
        pedido.id_vendedor = id_vendedor;
        pedido.id_cliente = clienteList.get(sel_cliente.getSelectedItemPosition()).id;
        try
        {
            pedido.fecha_entrega = dateFormatter.parse(txt_fecha.getText().toString());
        }
        catch(ParseException ex)
        {}
        pedido.entregado = check_entregado.isChecked();

        pedido.detalles = detalleList;

        return pedido;
    }

    private void findViewsById(View rootView) {
        sel_cliente = (Spinner)rootView.findViewById(R.id.sel_cliente);
        txt_fecha = (EditText)rootView.findViewById(R.id.txt_fecha);
        txt_fecha.setInputType(InputType.TYPE_NULL);

        check_entregado = (CheckBox)rootView.findViewById(R.id.check_entregado);

        sel_producto = (Spinner)rootView.findViewById(R.id.sel_producto);
        txt_cantidad = (EditText)rootView.findViewById(R.id.txt_cantidad);
        txt_precio = (EditText)rootView.findViewById(R.id.txt_precio);
        btn_add = (Button)rootView.findViewById(R.id.btn_add);

        detalle = (TableLayout)rootView.findViewById(R.id.detalle);
    }

    private void setDateTimeField() {
        txt_fecha.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fechaDatePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txt_fecha.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private void confirmarRegistro()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this.activity);
        dialogo1.setTitle(getText(R.string.confirmacion));
        if(!editar){
            dialogo1.setMessage(getText(R.string.pedido_agregar));
        }
        else
        {
            dialogo1.setMessage(getText(R.string.pedido_modificar));
        }
        dialogo1.setPositiveButton(getText(R.string.si), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                validarPedido();
            }
        });
        dialogo1.setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                return;
            }
        });
        dialogo1.show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(txt_fecha)) ret = false;

        return ret;
    }

    private void validarPedido()
    {
        if(txt_fecha.getText().toString().trim().equals(""))
        {
            Toast.makeText(activity, getText(R.string.pedido_fecha_requerido), Toast.LENGTH_LONG).show();
            txt_fecha.requestFocus();
            return;
        }

        Pedido pedido = setPedido();
        if(!editar)
        {
            daoPedido.save(pedido);
            Toast.makeText(activity, getText(R.string.pedido_agregar_ok), Toast.LENGTH_LONG).show();
        }
        else
        {
            daoPedido.update(pedido);
            Toast.makeText(activity, getText(R.string.pedido_modificar_ok), Toast.LENGTH_LONG).show();
        }

        ((MainActivity)activity).goToPedidos();
    }

    private void confirmarRegistroDetalle()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this.activity);
        dialogo1.setTitle(getText(R.string.confirmacion));
        dialogo1.setMessage(getText(R.string.pedido_detalle_agregar));

        dialogo1.setPositiveButton(getText(R.string.si), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                validarPedidoDetalle();
            }
        });
        dialogo1.setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                return;
            }
        });
        dialogo1.show();
    }

    private boolean checkValidationDetalle() {
        boolean ret = true;

        if (!Validation.isNumber(txt_cantidad, true)) ret = false;
        if (!Validation.isNumber(txt_precio, true)) ret = false;

        return ret;
    }

    private DetallePedido setDetallePedido() {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.id_producto = productoList.get(sel_producto.getSelectedItemPosition()).id;
        detallePedido.nombre_producto = productoList.get(sel_producto.getSelectedItemPosition()).nombre;
        detallePedido.cantidad = Integer.parseInt(txt_cantidad.getText().toString());
        detallePedido.precio  = Integer.parseInt(txt_precio.getText().toString());
        return detallePedido;
    }

    private void validarPedidoDetalle()
    {
        if(txt_cantidad.getText().toString().trim().equals(""))
        {
            Toast.makeText(activity, getText(R.string.pedido_detalle_cantidad_requerido), Toast.LENGTH_LONG).show();
            txt_cantidad.requestFocus();
            return;
        }
        if(txt_precio.getText().toString().trim().equals(""))
        {
            Toast.makeText(activity, getText(R.string.pedido_detalle_precio_requerido), Toast.LENGTH_LONG).show();
            txt_precio.requestFocus();
            return;
        }
        DetallePedido detallePedido = setDetallePedido();
        detalleList.add(detallePedido);

        Log.d("validarPedidoDetalle", detallePedido.id_producto + " " + detallePedido.cantidad + " " + detallePedido.precio);
        Log.d("validarPedidoDetalle", "contador detalles = " + detalleList.size());

        TableRow row = new TableRow(activity);
        row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView tv01 = new TextView(activity);
        tv01.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv01.setText(detallePedido.nombre_producto);

        TextView tv02 = new TextView(activity);
        tv02.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv02.setText(String.valueOf(detallePedido.cantidad));

        TextView tv03 = new TextView(activity);
        tv03.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv03.setText(String.valueOf(detallePedido.precio));

        TextView tv04 = new TextView(activity);
        tv04.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv04.setText(String.valueOf(detallePedido.total()));

        row.addView(tv01);
        row.addView(tv02);
        row.addView(tv03);
        row.addView(tv04);

        detalle.addView(row);

        sel_producto.setSelection(0);
        txt_cantidad.setText("");
        txt_precio.setText("");
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

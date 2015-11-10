package samuelvasquez.inacap.cl.unidad3;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOProducto;
import samuelvasquez.inacap.cl.unidad3.dataaccess.ProductoListAdapter;
import samuelvasquez.inacap.cl.unidad3.datamodel.Producto;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductosListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductosListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductosListFragment extends Fragment {
    public static final String ARG_ITEM_ID = "productos_list";

    public static final String ARG_VENDEDOR_ID = "id_vendedor";
    Activity activity;
    ArrayList<Producto> productos;
    Producto producto;
    ProductoListAdapter productoListAdapter;
    DAOProducto productoDAO;
    // UI references
    ListView list_producto;
    private int id_vendedor;
    private GetProductosTask task;
    private ActionMode mActionMode;
    private OnFragmentInteractionListener mListener;

    public ProductosListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProductosListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductosListFragment newInstance(int id_vendedor) {
        ProductosListFragment fragment = new ProductosListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VENDEDOR_ID, String.valueOf(id_vendedor));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        productoDAO = new DAOProducto(activity);
        if (getArguments() != null) {
            id_vendedor = getArguments().getInt(ARG_VENDEDOR_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_producto, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_productos_list, container, false);
        View rootView = inflater.inflate(R.layout.fragment_productos_list, container, false);
        findViewsById(rootView);

        task = new GetProductosTask(activity);
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

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.title_fragment_productos);
        //getActivity().getActionBar().setTitle(R.string.title_fragment_productos);
        super.onResume();
    }

    private void findViewsById(View view) {
        list_producto = (ListView) view.findViewById(R.id.list_producto);
    }

    /*
         * This method is invoked from MainActivity onFinishDialog() method. It is
	     * called from CustomEmpDialogFragment when an employee record is updated.
	     * This is used for communicating between fragments.
	     */
    public void updateView() {
        task = new GetProductosTask(activity);
        task.execute((Void) null);
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

    public class GetProductosTask extends AsyncTask<Void, Void, ArrayList<Producto>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetProductosTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Producto> doInBackground(Void... arg0) {
            ArrayList<Producto> productoList = productoDAO.getAllProductos();
            return productoList;
        }

        @Override
        protected void onPostExecute(ArrayList<Producto> productoList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                Log.d("cliente", productoList.toString());
                productos = productoList;
                if (productoList != null) {
                    if (productoList.size() != 0) {
                        productoListAdapter = new ProductoListAdapter(activity,
                                productoList);
                        list_producto.setAdapter(productoListAdapter);
                    } else {
                        Toast.makeText(activity, "Sin productos",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }


}

package samuelvasquez.inacap.cl.unidad3;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOPedido;
import samuelvasquez.inacap.cl.unidad3.datamodel.ResumenCaja;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResumenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResumenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResumenFragment extends Fragment {
    public static final String ARG_ITEM_ID = "resumen";

    //public static final String ARG_VENDEDOR_ID = "id_vendedor";
    Activity activity;
    DAOPedido pedidoDAO;
    private int id_vendedor;
    private OnFragmentInteractionListener mListener;

    public ResumenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        pedidoDAO = new DAOPedido(activity);
        id_vendedor = ((MainActivity) activity).GetIdVendedor();

        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getText(R.string.title_fragment_resumen));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_resumen, container, false);
        View rootView = inflater.inflate(R.layout.fragment_resumen, container, false);

        id_vendedor = ((MainActivity) activity).GetIdVendedor();
        ResumenCaja resumen = pedidoDAO.getResumenByIdVendedor(id_vendedor);

        TextView lbl_valor_total_entregas = (TextView) rootView.findViewById(R.id.lbl_valor_total_entregas);
        TextView lbl_valor_total_pedidos = (TextView) rootView.findViewById(R.id.lbl_valor_total_pedidos);
        TextView lbl_valor_saldo = (TextView) rootView.findViewById(R.id.lbl_valor_saldo);

        lbl_valor_total_entregas.setText(String.valueOf(resumen.total_entregas));
        lbl_valor_total_pedidos.setText(String.valueOf(resumen.total_pedidos));
        lbl_valor_saldo.setText(String.valueOf(resumen.saldo));

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.title_fragment_resumen);
        //getActivity().getActionBar().setTitle(R.string.title_fragment_resumen);
        super.onResume();
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

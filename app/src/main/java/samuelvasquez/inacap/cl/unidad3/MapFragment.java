package samuelvasquez.inacap.cl.unidad3;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOCliente;
import samuelvasquez.inacap.cl.unidad3.datamodel.Cliente;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    public static final String ARG_ITEM_ID = "map";

    //public static final String ARG_VENDEDOR_ID = "id_vendedor";
    Activity activity;
    DAOCliente clienteDAO;
    MapView mMapView;
    private int id_vendedor;
    private GoogleMap googleMap;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClientesListFragment.
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        clienteDAO = new DAOCliente(activity);
        id_vendedor = ((MainActivity) activity).GetIdVendedor();


        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getText(R.string.title_fragment_map));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

        // latitude and longitude por defecto
        double latitude = -33.4691199;
        double longitude = -70.641997;
        LatLng posicionInicial = new LatLng(latitude, longitude);

        ArrayList<Cliente> lista = clienteDAO.getClientesPendientesByIdVendedor(id_vendedor);
        if (lista.size() == 0) {
            Toast.makeText(activity, getText(R.string.no_pedidos_pendientes),
                    Toast.LENGTH_LONG).show();

        } else {
            for (Cliente item : lista) {
                LatLng posicion = new LatLng(item.latitud, item.longuitud);

                // create marker
                MarkerOptions marker = new MarkerOptions().position(
                        posicion).title(item.nombre);

                // adding marker
                googleMap.addMarker(marker);
            }
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(posicionInicial).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}

package samuelvasquez.inacap.cl.unidad3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String ARG_VENDEDOR_ID = "id_vendedor";

    private int id_vendedor;

    private Fragment contentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null)
        {
            if (extra.containsKey("id_vendedor"))
            {
                id_vendedor = extra.getInt("id_vendedor");
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        FragmentManager fragmentManager = getFragmentManager();
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");

                if (content.equals(ClientesAgregarFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(ClientesAgregarFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.add_cliente);
                        contentFragment = fragmentManager.findFragmentByTag(ClientesAgregarFragment.ARG_ITEM_ID);
                    }
                }
                if (content.equals(ClientesListFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(ClientesListFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.title_fragment_clientes);
                        contentFragment = fragmentManager.findFragmentByTag(ClientesListFragment.ARG_ITEM_ID);
                    }
                }
                if (content.equals(PedidosAgregarFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(PedidosAgregarFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.add_pedido);
                        contentFragment = fragmentManager.findFragmentByTag(PedidosAgregarFragment.ARG_ITEM_ID);
                    }
                }
                if (content.equals(PedidosListFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(PedidosListFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.title_fragment_pedidos);
                        contentFragment = fragmentManager.findFragmentByTag(PedidosListFragment.ARG_ITEM_ID);
                    }
                }
                if (content.equals(ProductosListFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(ProductosListFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.title_fragment_productos);
                        contentFragment = fragmentManager.findFragmentByTag(ProductosListFragment.ARG_ITEM_ID);
                    }
                }
                if (content.equals(ResumenFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(ResumenFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.title_fragment_resumen);
                        contentFragment = fragmentManager.findFragmentByTag(ResumenFragment.ARG_ITEM_ID);
                    }
                }
                if (content.equals(MapFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(MapFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.title_fragment_map);
                        contentFragment = fragmentManager.findFragmentByTag(MapFragment.ARG_ITEM_ID);
                    }
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof ClientesAgregarFragment) {
            outState.putString("content", ClientesAgregarFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof ClientesListFragment) {
            outState.putString("content", ClientesListFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof PedidosAgregarFragment) {
            outState.putString("content", PedidosAgregarFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof PedidosListFragment) {
            outState.putString("content", PedidosListFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof ProductosListFragment) {
            outState.putString("content", ProductosListFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof ResumenFragment) {
            outState.putString("content", ResumenFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof MapFragment) {
            outState.putString("content", MapFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_clientes) {
            goToClientes();
        } else if (id == R.id.nav_pedidos) {
            goToPedidos();
        } else if (id == R.id.nav_productos) {
            goToListaProductos();
        } else if (id == R.id.nav_caja) {
            goToResumen();
        } else if (id == R.id.nav_ruta) {
            goToRuta();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);

            transaction.commit();
            contentFragment = fragment;
        }
    }

    public void goToClientes()
    {
        ClientesListFragment fragment1 = new ClientesListFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(ClientesListFragment.ARG_VENDEDOR_ID, id_vendedor);
        fragment1.setArguments(bundle1);
        switchContent(fragment1, ClientesListFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToAddCliente()
    {
        ClientesAgregarFragment fragment = new ClientesAgregarFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(ClientesAgregarFragment.ARG_VENDEDOR_ID, id_vendedor);
        fragment.setArguments(bundle1);
        switchContent(fragment, ClientesAgregarFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToEditCliente(int id_cliente)
    {
        ClientesAgregarFragment fragment = new ClientesAgregarFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putBoolean(ClientesAgregarFragment.ARG_EDITAR, true);
        bundle1.putInt(ClientesAgregarFragment.ARG_VENDEDOR_ID, id_vendedor);
        bundle1.putInt(ClientesAgregarFragment.ARG_CLIENTE_ID, id_cliente);
        fragment.setArguments(bundle1);
        switchContent(fragment, ClientesAgregarFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToPedidos()
    {
        PedidosListFragment fragment1 = new PedidosListFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(PedidosListFragment.ARG_VENDEDOR_ID, id_vendedor);
        fragment1.setArguments(bundle1);
        switchContent(fragment1, PedidosListFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToAddPedido()
    {
        PedidosAgregarFragment fragment = new PedidosAgregarFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(PedidosAgregarFragment.ARG_VENDEDOR_ID, id_vendedor);
        fragment.setArguments(bundle1);
        switchContent(fragment, PedidosAgregarFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToEditPedido(int id_pedido)
    {
        PedidosAgregarFragment fragment = new PedidosAgregarFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putBoolean(PedidosAgregarFragment.ARG_EDITAR, true);
        bundle1.putInt(PedidosAgregarFragment.ARG_VENDEDOR_ID, id_vendedor);
        bundle1.putInt(PedidosAgregarFragment.ARG_PEDIDO_ID, id_pedido);
        fragment.setArguments(bundle1);
        switchContent(fragment, PedidosAgregarFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToListaProductos()
    {
        ProductosListFragment fragment = new ProductosListFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(ProductosListFragment.ARG_VENDEDOR_ID, id_vendedor);
        fragment.setArguments(bundle1);

        switchContent(fragment, ProductosListFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToResumen()
    {
        ResumenFragment fragment3 = new ResumenFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt(ResumenFragment.ARG_VENDEDOR_ID, id_vendedor);
        fragment3.setArguments(bundle3);
        switchContent(fragment3, ResumenFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToRuta() {
        MapFragment fragment = new MapFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(MapFragment.ARG_VENDEDOR_ID, id_vendedor);
        fragment.setArguments(bundle1);

        switchContent(fragment, MapFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

}

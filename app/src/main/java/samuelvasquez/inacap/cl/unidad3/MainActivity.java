package samuelvasquez.inacap.cl.unidad3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , ClientesListFragment.Callbacks
        , PedidosListFragment.Callbacks {

    private static final String ARG_VENDEDOR_ID = "id_vendedor";
    //private Fragment contentFragment;
    ActionBarDrawerToggle toggle;
    private int id_vendedor;
    private boolean VistaClientesExtendida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null)
        {
            if (extra.containsKey(ARG_VENDEDOR_ID))
            {
                id_vendedor = extra.getInt(ARG_VENDEDOR_ID);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getText(R.string.app_name));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        //drawer.openDrawer(GravityCompat.START);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
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
        */
    }

/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
      if (contentFragment instanceof ClientesAgregarFragment) {
            outState.putString("content", ClientesAgregarFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof ClientesFragment) {
            outState.putString("content", ClientesFragment.ARG_ITEM_ID);
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
    */

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (toggle != null) {
            if (toggle.onOptionsItemSelected(item)) {
                return true;
            }
        }
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClienteItemSelected(int id) {
        if (findViewById(R.id.cliente_detail_container) != null) {
            Bundle arguments = new Bundle();
            arguments.putInt(ClienteDetailFragment.ARG_CLIENTE_ID, id);
            ClienteDetailFragment fragment = new ClienteDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cliente_detail_container, fragment)
                    .commit();

        } else {
            goToDetalleCliente(id);
        }
    }

    @Override
    public void onPedidoItemSelected(int id) {
        if (findViewById(R.id.pedido_detail_container) != null) {
            Bundle arguments = new Bundle();
            arguments.putInt(PedidoDetailFragment.ARG_PEDIDO_ID, id);
            PedidoDetailFragment fragment = new PedidoDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pedido_detail_container, fragment)
                    .commit();

        } else {
            goToDetallePedido(id);
        }
    }


    public void switchContent(Fragment fragment, String tag) {
        Log.d("switchContent", tag);
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame_main, fragment, tag);

            transaction.commit();
            //contentFragment = fragment;
        }
        Log.d("switchContent", tag + " ok");

    }

    public int GetIdVendedor() {
        return id_vendedor;
    }

    public void goToClientes()
    {
        ClientesFragment fragment1 = new ClientesFragment();
        switchContent(fragment1, ClientesFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToAddCliente()
    {
        ClientesAgregarFragment fragment = new ClientesAgregarFragment();
        switchContent(fragment, ClientesAgregarFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToEditCliente(int id_cliente)
    {
        ClientesAgregarFragment fragment = new ClientesAgregarFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putBoolean(ClientesAgregarFragment.ARG_EDITAR, true);
        bundle1.putInt(ClientesAgregarFragment.ARG_CLIENTE_ID, id_cliente);
        fragment.setArguments(bundle1);
        switchContent(fragment, ClientesAgregarFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToDetalleCliente(int id_cliente) {
        ClienteDetailFragment fragment = new ClienteDetailFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(ClienteDetailFragment.ARG_CLIENTE_ID, id_cliente);
        fragment.setArguments(bundle1);
        switchContent(fragment, ClienteDetailFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToPedidos()
    {
        PedidosFragment fragment1 = new PedidosFragment();
        switchContent(fragment1, PedidosFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToDetallePedido(int id_pedido) {
        PedidoDetailFragment fragment = new PedidoDetailFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(PedidoDetailFragment.ARG_PEDIDO_ID, id_pedido);
        fragment.setArguments(bundle1);
        switchContent(fragment, PedidoDetailFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToAddPedido()
    {
        PedidosAgregarFragment fragment = new PedidosAgregarFragment();
        switchContent(fragment, PedidosAgregarFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToEditPedido(int id_pedido)
    {
        PedidosAgregarFragment fragment = new PedidosAgregarFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putBoolean(PedidosAgregarFragment.ARG_EDITAR, true);
        bundle1.putInt(PedidosAgregarFragment.ARG_PEDIDO_ID, id_pedido);
        fragment.setArguments(bundle1);
        switchContent(fragment, PedidosAgregarFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToListaProductos()
    {
        ProductosListFragment fragment = new ProductosListFragment();
        switchContent(fragment, ProductosListFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToResumen()
    {
        ResumenFragment fragment = new ResumenFragment();
        switchContent(fragment, ResumenFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

    public void goToRuta() {
        MapFragment fragment = new MapFragment();
        switchContent(fragment, MapFragment.ARG_ITEM_ID);
        invalidateOptionsMenu();
    }

}

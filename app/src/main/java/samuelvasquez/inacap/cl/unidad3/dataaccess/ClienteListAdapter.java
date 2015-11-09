package samuelvasquez.inacap.cl.unidad3.dataaccess;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import samuelvasquez.inacap.cl.unidad3.R;
import samuelvasquez.inacap.cl.unidad3.datamodel.*;

public class ClienteListAdapter extends ArrayAdapter<Cliente> {
    private Context context;
    private List<Cliente> listaClientes;

    public ClienteListAdapter(Context _context, List<Cliente> _listaClientes) {
        super(_context, R.layout.list_item_cliente, _listaClientes);
        context = _context;
        listaClientes = _listaClientes;
    }

    class ViewHolder {
        TextView txt_cliente_id;
        TextView txt_cliente_nombre;
        TextView txt_cliente_direccion;
        TextView txt_cliente_telefono;
        TextView txt_cliente_latitud;
        TextView txt_cliente_longuitud;
    }

    public int getCount() {
        return listaClientes.size();
    }

    public Cliente getItem(int position) {
        return listaClientes.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_item_cliente, null);
            holder = new ViewHolder();
            holder.txt_cliente_id = (TextView)convertView.findViewById(R.id.txt_cliente_id);
            holder.txt_cliente_nombre = (TextView)convertView.findViewById(R.id.txt_cliente_nombre);
            holder.txt_cliente_direccion = (TextView)convertView.findViewById(R.id.txt_cliente_direccion);
            holder.txt_cliente_telefono = (TextView)convertView.findViewById(R.id.txt_cliente_telefono);
            holder.txt_cliente_latitud = (TextView)convertView.findViewById(R.id.txt_cliente_latitud);
            holder.txt_cliente_longuitud = (TextView)convertView.findViewById(R.id.txt_cliente_longuitud);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Cliente cliente = (Cliente)getItem(position);

        holder.txt_cliente_id.setText(String.valueOf(cliente.id));
        holder.txt_cliente_nombre.setText(cliente.nombre);
        holder.txt_cliente_direccion.setText(cliente.direccion);
        holder.txt_cliente_telefono.setText(cliente.telefono);
        holder.txt_cliente_latitud.setText(String.valueOf(cliente.latitud));
        holder.txt_cliente_longuitud.setText(String.valueOf(cliente.longuitud));

        return convertView;
    }

    @Override
    public void add(Cliente cliente) {
        listaClientes.add(cliente);
        notifyDataSetChanged();
        super.add(cliente);
    }

    @Override
    public void remove(Cliente cliente) {
        listaClientes.remove(cliente);
        notifyDataSetChanged();
        super.remove(cliente);
    }

}

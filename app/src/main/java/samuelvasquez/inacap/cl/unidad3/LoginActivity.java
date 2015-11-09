package samuelvasquez.inacap.cl.unidad3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import samuelvasquez.inacap.cl.unidad3.dataaccess.DAOUsuario;
import samuelvasquez.inacap.cl.unidad3.datamodel.Usuario;

public class LoginActivity extends AppCompatActivity {

    EditText txt_usuario;
    EditText txt_contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_usuario = (EditText)findViewById(R.id.txt_usuario);
        txt_contrasena = (EditText)findViewById(R.id.txt_contrasena);

        // Obtiene refencia a boton Ingresar
        Button btn_login = (Button)findViewById(R.id.btn_login);
        // Asocia comportamiento a la accion OnClick del boton
        btn_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                if ( checkValidation () )
                    ValidarLoginUsuario();
                else
                    Toast.makeText(LoginActivity.this, "Debe corregir los errores para continuar", Toast.LENGTH_LONG).show();
            }} );

        txt_usuario.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(txt_usuario);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        txt_contrasena.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(txt_contrasena);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.login, menu);
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


    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(txt_usuario)) ret = false;
        if (!Validation.hasText(txt_contrasena)) ret = false;

        return ret;
    }

    private void ValidarLoginUsuario()
    {
        if(txt_usuario.getText().toString().trim().equals(""))
        {
            Toast.makeText(LoginActivity.this, "Debe ingresar un nombre de usuario", Toast.LENGTH_LONG).show();
            txt_usuario.requestFocus();
            return;
        }

        if(txt_contrasena.getText().toString().trim().equals(""))
        {
            Toast.makeText(LoginActivity.this, "Debe ingresar contraseña", Toast.LENGTH_LONG).show();
            txt_contrasena.requestFocus();
            return;
        }

        DAOUsuario daoUsuario = new DAOUsuario(LoginActivity.this);

        // valida si datos ingresados corresponden a un usuario
        if(daoUsuario.ValidarUsuario(txt_usuario.getText().toString(),
                txt_contrasena.getText().toString()))
        {
            // Muestra mensaje de exito
            Toast.makeText(LoginActivity.this,
                    "Usuario correcto",
                    Toast.LENGTH_SHORT).show();

            Usuario _usuario = daoUsuario.GetUsuario(txt_usuario.getText().toString());

            // Limpia formulario
            //txt_usuario.setText("");
            //txt_contrasena.setText("");

            // Redireccions al menu
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id_vendedor", _usuario.id);
            this.startActivity(intent);
        }
        else
        {
            // Muestra mensaje de error
            Toast.makeText(LoginActivity.this,
                    "Usuario y/o contrasena incorrecta",
                    Toast.LENGTH_SHORT).show();
        }
    }

}

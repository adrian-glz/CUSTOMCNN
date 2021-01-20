package mx.sounds.customssn;

import java.sql.ResultSet;

import mx.sounds.customssn.data.DatabasePostgres;
import mx.sounds.customssn.util.Base64Encoding;
import mx.sounds.customssn.util.TunnelSSHPostgres;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	public final static String EXTRA_ID = "-1";
	public static Boolean lsessionPg;
	public static Boolean lConnected;
	public static Boolean lerr;
	public static String[] img;
	public static String cMenError="";
	public static String cschema = "public";
	
	public void sndCapturar(View view) {
		Intent intent = new Intent(this, CapturarActivity.class);
		intent.putExtra(MainActivity.EXTRA_ID, "-1");
		startActivity(intent);
	}

	public void sndConsultar(View view) {
		Intent intent = new Intent(this, ConsultaActivity.class);
	    startActivity(intent);			
	}
	
	public void sndConsultarTotales(View view) {
		Intent intent = new Intent(this, ConsultaTotalesActivity.class);
	    startActivity(intent);			
	}
	
	public void sndSubirFotos(View view) {
		Intent intent = new Intent(this, SubirFotosActivity.class);
	    startActivity(intent);			
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        
        img = new String[4];
        img[0] = Base64Encoding.fDecode("YW5nZWxt");
        img[1] = Base64Encoding.fDecode("TWFkbGpkYTE=");
        img[2] = Base64Encoding.fDecode("Y3VzdG9tc3Nu");
        img[3] = Base64Encoding.fDecode("TWFkbGpkYTE=");

    	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())) {
    	    msbox("Error!", "No hay conexion de Red disponible!");
    	    return;
        }	

        
        try {
        	MainActivity.lsessionPg = false;
            MainActivity.lConnected = true;
            TunnelSSHPostgres.main();
            DatabasePostgres.isConnected();
            //AccessControl.BLogin();
            //return "ok";
        } catch (Exception e) {
    	    msbox("Error!", "No fue posible conectarse a la BD!");
    	    return;
        }
        
		setContentView(R.layout.activity_main);
	}

    public void msbox(String str,String str2)
    {
    	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle(str); 
        dlgAlert.setMessage(str2); 
        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                 finish(); 
            }
       });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void sndCerrar(View view) {
		if (MainActivity.lConnected) {
			DatabasePostgres.disconnect();
			TunnelSSHPostgres.disconnect();
		}
		this.finish();
	}
	
}

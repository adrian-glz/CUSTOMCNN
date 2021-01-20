package mx.sounds.customssn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.support.v7.app.ActionBarActivity;
import mx.sounds.customssn.data.DatabasePostgres;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConsultaActivity extends Activity {
	//public final static String EXTRA_ID = "mx.sounds.customssn.ID";
	//private ListView lista; 
	private String cSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_consulta);
        
		///////////////////////////////		
		try {
			cSql = "select * from customssn order by id desc";
			
            ResultSet rs = DatabasePostgres.getRS(cSql);
            ArrayList<List_Consulta> datos = new ArrayList<List_Consulta>(); 
            if (rs == null) {
            	datos.add(new List_Consulta("", "Reporte Sin Datos","","","","","",""));
            } else {
	            if ((MainActivity.lerr) || (rs.isAfterLast())) {
	            	datos.add(new List_Consulta("", "Reporte Sin Datos","","","","","",""));
	            } else {
		            while (!(rs.isAfterLast())) {
		            	String val1 = String.format(Locale.getDefault(), "%d", rs.getInt("id"));
		            	String val2 = rs.getString("factura");
		            	String val3 = rs.getString("marca");
		            	String val4 = rs.getString("modelo");
		            	String val5 = rs.getString("color");
		            	String val6 = rs.getString("origen");
		            	String val7 = rs.getString("serie");
		            	String val8 = rs.getString("foto");
		            	val8 = val8.trim();
		            	if (val8.length() > 0) {
		            		val8 = "SI";
		            	} else {
		            		val8 = "NO";
		            	}
		            	datos.add(new List_Consulta(val1, val2, val3, val4, val5, val6, val7, val8));
		            	rs.next();
		            }
		            rs.close();
	            }
            }
            rs = null;

			ListView lista = (ListView) findViewById(R.id.ListView_listado);
	        lista.setAdapter(new List_Adapter(this, R.layout.item_consulta, datos){
				@Override
				public void onEntrada(Object item_consulta, View view) {
			            TextView texto_1 = (TextView) view.findViewById(R.id.textView_1); 
			            texto_1.setText(((List_Consulta) item_consulta).get_texto1()); 
	
			            TextView texto_2 = (TextView) view.findViewById(R.id.textView_2); 
			            texto_2.setText(((List_Consulta) item_consulta).get_texto2()); 
			            
			            TextView texto_3 = (TextView) view.findViewById(R.id.textView_3); 
			            texto_3.setText(((List_Consulta) item_consulta).get_texto3()); 	
			            
			            TextView texto_4 = (TextView) view.findViewById(R.id.textView_4); 
			            texto_4.setText(((List_Consulta) item_consulta).get_texto4()); 
			            
			            TextView texto_5 = (TextView) view.findViewById(R.id.textView_5); 
			            texto_5.setText(((List_Consulta) item_consulta).get_texto5()); 
			            
			            TextView texto_6 = (TextView) view.findViewById(R.id.textView_6); 
			            texto_6.setText(((List_Consulta) item_consulta).get_texto6()); 

			            TextView texto_7 = (TextView) view.findViewById(R.id.textView_7); 
			            texto_7.setText(((List_Consulta) item_consulta).get_texto7()); 
			            
			            TextView texto_8 = (TextView) view.findViewById(R.id.textView_8); 
			            texto_8.setText(((List_Consulta) item_consulta).get_texto8()); 
			            
			            //ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
			            //imagen_entrada.setImageResource(((List_Sales1) entrada).get_idImagen());
				}
			});
	        
	        lista.setOnItemClickListener(new OnItemClickListener() { 
	            @Override
	            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
	            	List_Consulta elegido = (List_Consulta) pariente.getItemAtPosition(posicion); 
	            	
	                CharSequence texto = "Serie Seleccionada: " + elegido.get_texto6();
	                Toast toast = Toast.makeText(ConsultaActivity.this, texto, Toast.LENGTH_LONG);
	                toast.show();
	            	
	        		Intent intent = new Intent(ConsultaActivity.this , CapturarActivity.class);
	        		String message = elegido.get_texto1();
	        		intent.putExtra(MainActivity.EXTRA_ID, message);
	        		startActivity(intent);
	        		ConsultaActivity.this.finish();	                
	            }
	         });
	        
			///////////////////////////////
        } catch (SQLException ex) {
            Logger.getLogger(DatabasePostgres.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            System.gc();                
        }
    }
    
       
    public void buttonClose_onclick(View view) {
    	this.finish();
    }    
    
}

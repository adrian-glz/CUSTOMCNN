package mx.sounds.customssn;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import mx.sounds.customssn.data.DatabasePostgres;
import mx.sounds.customssn.util.SFTP;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CapturarActivity extends ActionBarActivity {
	public static String cSql;
	public static String mCurrentPhotoPath;
	static final int REQUEST_TAKE_PHOTO = 1;
	public String EXTRA_ID="-1"; 
	public int nDelReg=0;
	public int nQuery=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capturar);
		
		EXTRA_ID="-1"; 
		Intent intent = getIntent();
	    EXTRA_ID = intent.getStringExtra(MainActivity.EXTRA_ID);
	    
	    if (!EXTRA_ID.equals("-1")) { //Editar uno existente
			try {
				cSql = "select * from customssn where id = " + EXTRA_ID;
				ResultSet rs = DatabasePostgres.getRS(cSql);
				if (rs == null) {
	            	return;
	            } else {
		            if ((MainActivity.lerr) || (rs.isAfterLast())) {
		            	return;
		            } else {
			            if (!(rs.isAfterLast())) {
			            	String val1 = rs.getString("factura");
			            	EditText editText1 = (EditText) findViewById(R.id.editText1);
			            	editText1.setText(val1);
			            	String val2 = rs.getString("marca");
			            	EditText editText2 = (EditText) findViewById(R.id.editText2);
			            	editText2.setText(val2);			            	
			            	String val3 = rs.getString("modelo");
			            	EditText editText3 = (EditText) findViewById(R.id.editText3);
			            	editText3.setText(val3);			     
			            	String val4 = rs.getString("color");
			            	EditText editText4 = (EditText) findViewById(R.id.editText4);
			            	editText4.setText(val4);			     			            	
			            	String val5 = rs.getString("origen");
			            	EditText editText5 = (EditText) findViewById(R.id.editText5);
			            	editText5.setText(val5);			            	
			            	String val6 = rs.getString("serie");
			            	EditText editText6 = (EditText) findViewById(R.id.editText6);
			            	editText6.setText(val6);			            	
			            	String val7 = rs.getString("foto");
			            	EditText editText7 = (EditText) findViewById(R.id.editText7);
			            	editText7.setText(val7);			            	
			            }
			            rs.close();
		            }
	            }
	            rs = null;

			} catch (SQLException ex) {
	            Logger.getLogger(DatabasePostgres.class.getName()).log(Level.SEVERE, null, ex);
	        } finally{
	            System.gc();                
	        }	    	
	    } else { // Serie Nueva
			try {
				cSql = "select * from customssn order by id desc limit 1;";
				ResultSet rs = DatabasePostgres.getRS(cSql);
				if (rs == null) {
	            	return;
	            } else {
		            if ((MainActivity.lerr) || (rs.isAfterLast())) {
		            	return;
		            } else {
			            if (!(rs.isAfterLast())) {
			            	String val1 = rs.getString("factura");
			            	EditText editText1 = (EditText) findViewById(R.id.editText1);
			            	editText1.setText(val1);
			            	String val2 = rs.getString("marca");
			            	EditText editText2 = (EditText) findViewById(R.id.editText2);
			            	editText2.setText(val2);			            	
			            	String val3 = rs.getString("modelo");
			            	EditText editText3 = (EditText) findViewById(R.id.editText3);
			            	editText3.setText(val3);			            	
			            	String val4 = rs.getString("color");
			            	EditText editText4 = (EditText) findViewById(R.id.editText4);
			            	editText4.setText(val4);			            	
			            	String val5 = rs.getString("origen");
			            	EditText editText5 = (EditText) findViewById(R.id.editText5);
			            	editText5.setText(val5);		
			            	//String val6 = rs.getString("serie");
			            	EditText editText6 = (EditText) findViewById(R.id.editText6);
			            	editText6.setText("");			            	
			            	//String val7 = rs.getString("foto");
			            	EditText editText7 = (EditText) findViewById(R.id.editText7);
			            	editText7.setText("");			            	
			            }
			            rs.close();
		            }
	            }
	            rs = null;

			} catch (SQLException ex) {
	            Logger.getLogger(DatabasePostgres.class.getName()).log(Level.SEVERE, null, ex);
	        } finally{
	            System.gc();                
	        }	    	
	    }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.capturar, menu);
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
	
	public void sndLimpiarFactura(View view) {
		EditText editText = (EditText) findViewById(R.id.editText1);
		editText.setText("");
	}

	public void sndLimpiarMarca(View view) {
		EditText editText = (EditText) findViewById(R.id.editText2);
		editText.setText("");
	}

	public void sndLimpiarModelo(View view) {
		EditText editText = (EditText) findViewById(R.id.editText3);
		editText.setText("");
	}
	
	public void sndLimpiarColor(View view) {
		EditText editText = (EditText) findViewById(R.id.editText4);
		editText.setText("");
	}

	public void sndLimpiarOrigen(View view) {
		EditText editText = (EditText) findViewById(R.id.editText5);
		editText.setText("");
	}

	public void sndLimpiarSerie(View view) {
		EditText editText = (EditText) findViewById(R.id.editText6);
		editText.setText("");
	}

	public void sndLimpiarFoto(View view) {
		EditText editText = (EditText) findViewById(R.id.editText7);
		editText.setText("");
	}
	
	public void sndGrabar(View view) {
		String cfile="";
		Boolean lCapFoto=false;
		int nReg=0;
		EditText editText1 = (EditText) findViewById(R.id.editText1);	
		EditText editText2 = (EditText) findViewById(R.id.editText2);	
		EditText editText3 = (EditText) findViewById(R.id.editText3);	
		EditText editText4 = (EditText) findViewById(R.id.editText4);	
		EditText editText5 = (EditText) findViewById(R.id.editText5);	
		EditText editText6 = (EditText) findViewById(R.id.editText6);	
		EditText editText7 = (EditText) findViewById(R.id.editText7);	
		CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		TextView textview6 = (TextView) findViewById(R.id.textView6);
		
		if (editText1.getText().toString().length()==0)  {
    		Toast toast = Toast.makeText(CapturarActivity.this, "Capture Factura!", Toast.LENGTH_LONG);
            toast.show();
            finish();
            return;
    	}		
		if (editText2.getText().toString().length()==0)  {
    		Toast toast = Toast.makeText(CapturarActivity.this, "Capture Marca!", Toast.LENGTH_LONG);
            toast.show();
            finish();
            return;
    	}		
		if (editText3.getText().toString().length()==0)  {
    		Toast toast = Toast.makeText(CapturarActivity.this, "Capture Modelo!", Toast.LENGTH_LONG);
            toast.show();
            finish();
            return;
    	}		
		if (editText4.getText().toString().length()==0)  {
    		Toast toast = Toast.makeText(CapturarActivity.this, "Capture Color!", Toast.LENGTH_LONG);
            toast.show();
            finish();
            return;
    	}		
		if (editText5.getText().toString().length()==0)  {
    		Toast toast = Toast.makeText(CapturarActivity.this, "Capture Origen!", Toast.LENGTH_LONG);
            toast.show();
            finish();
            return;
    	}		
		if (editText6.getText().toString().length()==0)  {
    		Toast toast = Toast.makeText(CapturarActivity.this, "Capture Serie!", Toast.LENGTH_LONG);
            toast.show();
            finish();
            return;
    	}	
		lCapFoto=false;
		if (checkBox1.isChecked()) {
			if (editText7.getText().toString().length() > 0)  {
				try {
					cfile = editText7.getText().toString();
					SFTP.UploadSFTP(MainActivity.img[0], MainActivity.img[1], "sounds.mx", 7822, cfile, "/var/www/web2/sn_fotos/", true);
					lCapFoto=true;
				} catch (Exception ex) {
		            System.out.println("Problema para accesar: "+cfile);
		        }				
			}
		}
		if  (EXTRA_ID.equals("-1")) {
			nReg=0;
			try {
				nReg = 0;
				cSql = "select count(*) as reg from customssn where upper(serie) not in ('ILEGIBLE','SN') and upper(serie) = upper('" + 
			    editText6.getText().toString().trim() + "')";
				ResultSet rs = DatabasePostgres.getRS(cSql);
				if (rs == null) {
	            	return;
	            } else {
		            if ((MainActivity.lerr) || (rs.isAfterLast())) {
		            	return;
		            } else {
			            if (!(rs.isAfterLast())) {
			            	nReg = rs.getInt("reg");
			            }
			            rs.close();
		            }
	            }
	            rs = null;
			} catch (SQLException ex) {
	            Logger.getLogger(DatabasePostgres.class.getName()).log(Level.SEVERE, null, ex);
	        } finally{
	            System.gc();                
	        }		
			if (nReg > 0) {
				Toast toast = Toast.makeText(CapturarActivity.this, "Serie Duplicada!", Toast.LENGTH_LONG);
	            toast.show();
	            finish();
	            return;				
			}			
			if (lCapFoto) {
				cSql = "insert into customssn (factura, marca, modelo, color, origen, serie, foto, subida, fecha_subida) " +
						   " values( '" + editText1.getText().toString().trim().toUpperCase() + 
						   "','" + editText2.getText().toString().trim().toUpperCase() + 
						   "','" + editText3.getText().toString().trim().toUpperCase() + 
						   "','" + editText4.getText().toString().trim().toUpperCase() + 
						   "','" + editText5.getText().toString().trim().toUpperCase() +
						   "','" + editText6.getText().toString().trim().toUpperCase() + 
						   "','" + editText7.getText().toString().trim() + "', true, now());";
			} else {
				cSql = "insert into customssn (factura, marca, modelo, color, origen, serie, foto) " +
					   " values( '" + editText1.getText().toString().trim().toUpperCase() + 
					   "','" + editText2.getText().toString().trim().toUpperCase() + 
					   "','" + editText3.getText().toString().trim().toUpperCase() + 
					   "','" + editText4.getText().toString().trim().toUpperCase() + 
					   "','" + editText5.getText().toString().trim().toUpperCase() + 
					   "','" + editText6.getText().toString().trim().toUpperCase() + 
					   "','" + editText7.getText().toString().trim() + "');";
			}
		} else {
			if (lCapFoto) {
				cSql = "update customssn set factura = '" + editText1.getText().toString().trim().toUpperCase() + 
			   "',marca = '" + editText2.getText().toString().trim().toUpperCase() + 
			   "', modelo = '" + editText3.getText().toString().trim().toUpperCase() + 
			   "', color = '" + editText4.getText().toString().trim().toUpperCase() + 
			   "', origen = '" + editText5.getText().toString().trim().toUpperCase() + 
			   "', serie = '" + editText6.getText().toString().trim().toUpperCase() + 
			   "', foto = '" + editText7.getText().toString().trim() + 
			   "', subida = true, fecha_subida = now() where id = " + EXTRA_ID;
			} else {
				cSql = "update customssn set factura = '" + editText1.getText().toString().trim().toUpperCase() + 
			   "',marca = '" + editText2.getText().toString().trim().toUpperCase() + 
			   "', modelo = '" + editText3.getText().toString().trim().toUpperCase() + 
			   "', color = '" + editText4.getText().toString().trim().toUpperCase() + 
			   "', origen = '" + editText5.getText().toString().trim().toUpperCase() + 
			   "', serie = '" + editText6.getText().toString().trim().toUpperCase() + 
			   "', foto = '" + editText7.getText().toString().trim() + 
			   "', subida = false, fecha_subida = null where id = " + EXTRA_ID;
			}
		}
        Boolean lResult = mx.sounds.customssn.data.DatabasePostgres.execQuery(cSql);
        if (lResult) {
        	ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        	toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);         	
        	//editText6.setText("");
        	editText7.setText("");
        	try {
				nReg = 0;
				cSql = "select count(*) as reg from customssn where upper(factura) ='" + editText1.getText().toString().trim().toUpperCase() + "'" +
				" and upper(marca) ='"  + editText2.getText().toString().trim().toUpperCase() + "'" +
				" and upper(modelo) ='" + editText3.getText().toString().trim().toUpperCase() + "'" +
				" and upper(color) ='"  + editText4.getText().toString().trim().toUpperCase() + "'";				
				ResultSet rs = DatabasePostgres.getRS(cSql);
				if (rs == null) {
	            	return;
	            } else {
		            if ((MainActivity.lerr) || (rs.isAfterLast())) {
		            	return;
		            } else {
			            if (!(rs.isAfterLast())) {
			            	nReg = rs.getInt("reg");
			            }
			            rs.close();
		            }
	            }
	            rs = null;
			} catch (SQLException ex) {
	            Logger.getLogger(DatabasePostgres.class.getName()).log(Level.SEVERE, null, ex);
	        } finally{
	            System.gc();                
	        } 
        	textview6.setText("Serie     Total Capturada: " + nReg);
        } else {
        	msbox("Error!", "No fue posible Grabar Datos en la BD!");
        }
        if (!EXTRA_ID.equals("-1")) {
        	this.finish();
        }
	}
	
	public void sndBorrar(View view) {
		if  (!EXTRA_ID.equals("-1")) {
			QueryBoxDel("Borrar?", "Desea Borrar Registro en la BD ?");
		}
	}
	
	public void sndFoto(View view) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
	        }
	    }		
	}
	
	private File createImageFile() throws IOException {
		EditText editText7 = (EditText) findViewById(R.id.editText7);
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = timeStamp; // + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    editText7.setText(image.getAbsolutePath());
	    return image;
	}
	
	public void sndCerrar(View view) {
		this.finish();
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
	
	public void QueryBoxDel(String str,String str2)
    {	
    	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle(str); 
        dlgAlert.setMessage(str2); 
        dlgAlert.setPositiveButton("SI",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
				cSql = "delete from  customssn where id = " + EXTRA_ID;	
				Boolean lResult = mx.sounds.customssn.data.DatabasePostgres.execQuery(cSql);
				if (lResult) {
					ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
		        	toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);  
					msbox("Exito!", "Se borro Registro en la BD!");
				} else {
					msbox("Error!", "No fue posible Borrar Registro en la BD!");
				}
                finish(); 
            }
        });
        dlgAlert.setNegativeButton("NO",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish(); 
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    } 
	
}

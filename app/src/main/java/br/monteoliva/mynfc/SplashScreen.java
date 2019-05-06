package br.monteoliva.mynfc;

// imports da API do ANDROID
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Tela de SplashScreen
 * 
 * @author Claudio Monteoliva
 * @version 1.0
 * @copyright 2019 Monteoliva Developer
 *
 */
public class SplashScreen extends AppCompatActivity {
	private static final int NFC_SECONDS = 2000; // 2 segundos

	private static final int NFC_PERM = 200;

	// pega os Uses Permissions
	private static final String[] permissions = new String[] {
			Manifest.permission.NFC
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// seta o Layout padrao
		setContentView(R.layout.splashscreen);

		final int rc1 = ActivityCompat.checkSelfPermission(this, permissions[0]);
		if (rc1 == PackageManager.PERMISSION_GRANTED) { redireciona(); }
		else { requestCameraPermission(); }
	}

	/**
	 * Metodo que redireciona para as Categorias
	 */
	public void redireciona() {
    	// carrega
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// redireciona
				startActivity(new Intent(getBaseContext(), Principal_.class));
				finish();
			}
		}, NFC_SECONDS);
	}

	/**
     * Evento KeyDown
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { return true; }
        else                                  { return super.onKeyDown(keyCode, event); }
    }

	/*******************************************************************************
	 * Camera Permission
	 *******************************************************************************/
	private void requestCameraPermission() {
		ActivityCompat.requestPermissions(this, permissions, NFC_PERM);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == NFC_PERM) {
			if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				redireciona();
			}
		}
	}
}
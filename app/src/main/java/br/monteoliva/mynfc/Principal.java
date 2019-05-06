package br.monteoliva.mynfc;

// imports da API do ANDROID
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.TextView;
import android.content.Intent;

// imports da API MonteolivaLIB
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.monteoliva.monteolivalib.nfc.NFCScanner;
import br.monteoliva.monteolivalib.nfc.OnNFCListener;

/**
 * Tela Principal
 * 
 * @author Claudio Monteoliva
 * @version 1.0
 *
 * @copyright 2019 Monteoliva Developer
 */
@EActivity(R.layout.principal)
public class Principal extends AppCompatActivity implements OnNFCListener {
    private NFCScanner scanner;

    @ViewById(R.id.txtId)
    protected TextView txtId;

    protected ActionBar actionBar;

    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;

    @AfterViews
    protected void afterViews() {
        // toolbar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setSubtitle("");
        }

        // seta o NFC Scanner
        scanner = new NFCScanner(this, getIntent());
        scanner.setOnNFCListener(this);
    }

    @Click(R.id.btnClear)
    protected void btnClear() {
        txtId.setText("");
    }

    /**
     * Metodo de ciclo de vida onResume
     */
    @Override
    protected void onResume() {
        super.onResume();

        // onResume do NFC Scanner
        scanner.onResume();
    }

	/**
	 * Metodo de onPause
	 */
    @Override
    protected void onPause() {
    	super.onPause();

        // onPause do NFC Scanner
        scanner.onPause();
	}

    /**
     * Metodo para a Leitura do NFC
     *
     * @param intent
     */
    @Override
    public void onNewIntent(Intent intent) { scanner.resolveIntent(intent); }

    @Override
    public void onTagRead(String nrTag) {
        // coloca os dados
        txtId.setText(txtId.getText().toString() + nrTag + "\n");
    }

    /**
     * Evento KeyDown
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { finish(); return true; }
        else                                  { return super.onKeyDown(keyCode, event); }
    }
}
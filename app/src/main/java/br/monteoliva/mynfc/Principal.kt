package br.monteoliva.mynfc

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import br.monteoliva.monteolivalib.nfc.NFCScanner
import br.monteoliva.monteolivalib.nfc.OnNFCListener

/**
 * Tela Principal
 *
 * @author Claudio Monteoliva
 * @version 1.0
 *
 * @copyright 2020 Monteoliva Developer
 */
class Principal : AppCompatActivity(), OnNFCListener {
    private lateinit var scanner: NFCScanner
    private lateinit var txtId: TextView
    private lateinit var toolbar: Toolbar

    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal)

        txtId = findViewById(R.id.txtId)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        if (actionBar != null) {
            actionBar!!.title = ""
            actionBar!!.subtitle = ""
        }

        // seta o NFC Scanner
        scanner = NFCScanner(this, intent)
        scanner.setOnNFCListener(this)

        val btnClear = findViewById<Button>(R.id.btnClear)
            btnClear.setOnClickListener { txtId.setText("") }
    }

    override fun onResume() {
        super.onResume()

        // onResume do NFC Scanner
        scanner.onResume()
    }

    override fun onPause() {
        super.onPause()

        // onPause do NFC Scanner
        scanner.onPause()
    }

    /**
     * Metodo para a Leitura do NFC
     *
     * @param intent
     */
    public override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        scanner.resolveIntent(intent)
    }

    override fun onTagRead(nrTag: String) {
        txtId.text = "${txtId.text}$nrTag\n"
    }

    /**
     * Evento KeyDown
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }
}
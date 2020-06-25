package br.monteoliva.mynfc

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

/**
 * Tela de SplashScreen
 *
 * @author Claudio Monteoliva
 * @version 1.0
 * @copyright 2020 Monteoliva Developer
 */
class SplashScreen : AppCompatActivity() {
    private val NFC_SECONDS = 2000 // 2 segundos
    private val NFC_PERM = 200
    private val permissions = arrayOf(
            Manifest.permission.NFC
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splashscreen)

        val rc1 = ActivityCompat.checkSelfPermission(this, permissions[0])
        if (rc1 == PackageManager.PERMISSION_GRANTED) {
            redireciona()
        } else {
            requestCameraPermission()
        }
    }

    /**
     * Metodo que redireciona para as Categorias
     */
    fun redireciona() {
        Handler().postDelayed({
            startActivity(Intent(baseContext, Principal::class.java))
            finish()
        }, NFC_SECONDS.toLong())
    }

    /**
     * Evento KeyDown
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        }
        else {
            super.onKeyDown(keyCode, event)
        }
    }

    /*******************************************************************************
     * Camera Permission
     */
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, permissions, NFC_PERM)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NFC_PERM) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                redireciona()
            }
        }
    }
}
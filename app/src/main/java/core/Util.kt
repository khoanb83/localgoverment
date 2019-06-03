package core


import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import com.esri.arcgisruntime.geometry.Envelope
import com.esri.arcgisruntime.geometry.SpatialReference
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.security.UserCredential
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonStreamParser
import com.example.localgoverment.R

object Util {
    fun loadAppConfig(context:Context)
    {


        //symbol
        Config.gSymbolMarkerGPS = PictureMarkerSymbol(context.getDrawable(R.drawable.app_pin_current) as BitmapDrawable)
//        Config.gSymbolMarkerHighlight = PictureMarkerSymbol(context.getDrawable(R.drawable.app_pin_pink) as BitmapDrawable)
//        Config.gSymbolMarkerHighlight.offsetY=10f
//
//        Config.gSymbolLineHighlight= SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.MAGENTA,2f)
//        Config.gSymbolFillHighlight= SimpleFillSymbol(SimpleFillSymbol.Style.NULL, Color.MAGENTA,Config.gSymbolLineHighlight)
    }
    fun alert(context: Context, msg: String) {
        AlertDialog.Builder(context)
            .setTitle(R.string.system_msg)
            .setMessage(msg)
            .setPositiveButton(R.string.confirm_msg, null)
            .show()
    }

    fun confirm(context: Context, msg: String, listener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context)
            .setTitle(R.string.system_msg)
            .setMessage(msg)
            .setPositiveButton(R.string.confirm_msg, listener)
            .setNegativeButton(R.string.cancel_msg, listener).show()
    }

    fun showToastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showLoading(v: View) {
        v.visibility = View.VISIBLE
    }


    fun showProgress(context: Context, msg: String) {

//        _progress = ProgressDialog(context)
//        _progress.setIcon(R.drawable.ic_vpn_key_black_24dp)
//        _progress.setTitle("Thực hiện")
//        _progress.setMessage(msg)
//        _progress.show()
        val dialog = ProgressDialog(context)
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading")
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
    }
    fun dismissProgress() {

       // if (_progress != null)
            //_progress.dismiss()
    }
    fun clearUserPassword(context: Context) {
        val editor = context.getSharedPreferences("SAVE_CREDENTIAL", Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.commit()
    }

    fun saveUserPassword(context: Context, user: String,  pass: String,token: String, isSave: Boolean) {
        //val sharedPref = context.getSharedPreferences("SAVE_CREDENTIAL", Context.MODE_PRIVATE).edit()
        val sharedPref = context.getSharedPreferences("SAVE_CREDENTIAL", Context.MODE_P RIVATE)
        val editor = sharedPref ?: return
        with(editor.edit()) {
            putString("USER", user)
            putString("PASSWORD", pass)
            putString("TOKEN", token)
            editor.edit().putBoolean("IS_SAVE", isSave)
            apply()
        }
        }
    fun showError(context: Context, ex: Throwable) {
        val dlg = AlertDialog.Builder(context)
        dlg.setIcon(R.drawable.ic_report_black_24dp)
        dlg.setTitle("Lỗi")
        dlg.setMessage(ex.message)
        dlg.setPositiveButton("Thoát", null)
        dlg.show()
    }


}
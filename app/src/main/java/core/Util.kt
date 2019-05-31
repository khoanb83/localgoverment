package core

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import com.example.localgoverment.R

object Util {

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

    fun saveUserPassword(context: Context, user: String, password: String, isSave: Boolean) {
        val editor = context.getSharedPreferences("SAVE_CREDENTIAL", Context.MODE_PRIVATE).edit()
        editor.putString("USER", user)
        editor.putString("PASSWORD", password)
        editor.putBoolean("IS_SAVE", isSave)
        editor.commit()
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
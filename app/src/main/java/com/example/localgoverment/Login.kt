package com.example.localgoverment

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import core.Util

import com.esri.arcgisruntime.portal.Portal
import com.esri.arcgisruntime.security.UserCredential
import core.Config

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.concurrent.ListenableFuture
import com.esri.arcgisruntime.loadable.LoadStatus
import com.esri.arcgisruntime.loadable.LoadStatusChangedEvent
import com.esri.arcgisruntime.loadable.LoadStatusChangedListener
import com.esri.arcgisruntime.mapping.ArcGISMap

import com.esri.arcgisruntime.portal.PortalItem
import com.esri.arcgisruntime.portal.PortalQueryParameters
import com.esri.arcgisruntime.portal.PortalQueryResultSet
import com.google.gson.JsonObject
import core.service.ServiceCallback


public class Login : AppCompatActivity(), LoadStatusChangedListener, CompoundButton.OnCheckedChangeListener  {


   // khi bản đồ đã được load
    override fun loadStatusChanged(LoadStatusChangedEvent: LoadStatusChangedEvent?) {

       val status = LoadStatusChangedEvent?.getNewLoadStatus()
       when (status) {
           LoadStatus.LOADED -> {
               //get token
              var token = ( Config.myPortal?.getCredential() as UserCredential ).token.a()
               var user=findViewById<EditText>(R.id.txtNguoiDung).text.toString()
               var  pass=findViewById<EditText>(R.id.txtMatKhau).text.toString()
               //save user password, luu cau hinh
               if ((findViewById<View>(R.id.chkSaveUserPassword) as CheckBox).isChecked)
                   Util.saveUserPassword(
                       _context,
                       user.toString(),
                       pass.toString(),
                       token,
                       true
                   )
               Util.loadAppConfig(_context)
               val portal = LoadStatusChangedEvent?.getSource() as Portal
               val queryPortal = PortalQueryParameters()
               queryPortal.setQuery(PortalItem.Type.WEBMAP, Config.GROUP_ID, null)
               queryPortal.sortField = "title"
               queryPortal.sortOrder = PortalQueryParameters.SortOrder.DESCENDING
               val futurePortal = portal.findItemsAsync(queryPortal)
               startActivity(Intent(_context, MainActivity::class.java))
               futurePortal.addDoneListener {
                   try {
                       val results = futurePortal.get()
                       val size = results.totalResults

                       ArcGISRuntimeEnvironment.setLicense(portal.portalInfo.licenseInfo)


                       Config.glbMap?.addDoneLoadingListener({
                           Util.dismissProgress()
                           startActivity(Intent(_context, MainActivity::class.java))
                       })
                       Config.glbMap?.loadAsync()
                   } catch (e: Exception) {
                       Util.dismissProgress()
                       Util.showError(_context, e)
                   }
               }
           }
           LoadStatus.FAILED_TO_LOAD -> {
               Util.dismissProgress()
               Util.showError(_context, Exception("Sai người dùng hoặc mật khẩu"))
           }
       }

   }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

    }

    lateinit  var _context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        _context=this;
        if (!Config.initConfig(this)) {
            //txtConfig_onClick(null)
        }
    }
    fun btnLogin_click(v: View) {

        var user=findViewById<EditText>(R.id.txtNguoiDung).text.toString()
       var  pass=findViewById<EditText>(R.id.txtMatKhau).text.toString()
        if (user.isEmpty() || pass.isEmpty()){
            Util.alert(_context,"Sai mat khau");
        }
        //Đăng nhập hệ thống
        else{
            Config.glbCredential = UserCredential(user.toString(), pass.toString())
            // Create a Portal object
            Config.myPortal = Portal(Config.URL_PORTAL, true)
            Config.myPortal?.credential = Config.glbCredential
            Config.myPortal?.addLoadStatusChangedListener(this)
            Config.myPortal?.loadAsync()
            Util.showProgress(_context, "Kiểm tra tài khoản người dùng & mật khẩu...")
        }

    }


}

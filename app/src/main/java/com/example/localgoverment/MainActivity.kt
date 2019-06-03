package com.example.localgoverment

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.esri.arcgisruntime.mapping.view.MapView
import com.kotlinpermissions.KotlinPermissions
import core.Config
import core.Util
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var _mv: MapView
    lateinit  var _context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _context=this
        _mv=this.findViewById<MapView>(R.id.mapView)
        val map = ArcGISMap(Basemap.Type.TOPOGRAPHIC, 34.056295, -117.195800, 16)
        _mv.map = map

    }
    override fun onPause() {
        super.onPause()
        _mv.pause()
    }

    override fun onResume() {
        super.onResume()
        _mv.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        _mv.dispose()
    }
    //nut home
    fun cmdHome_onClick(v: View) {
        _mv.setViewpointAsync(_mv.map.getInitialViewpoint());
    }
    //thu nho
    fun cmdZoomIn_onClick(v: View) {
        _mv.setViewpointScaleAsync(_mv.getMapScale()/2);
    }
    //phong to
    fun cmdZoomOut_onClick(v: View) {
        _mv?.setViewpointScaleAsync(2*(_mv.getMapScale()))
    }
    //hoi thong tin
    fun btnToolIdentify_click(v: View) {

    }
    fun btnAddnew_Onclick(v:View)
    {

    }
    fun btnDanhSach_Onclick(v:View)
    {

    }
    //vi tri
    fun cmdLocation_onClick(v:View?) {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Util.confirm(_context,"This application need enable GPS in your device?", object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    if(which== DialogInterface.BUTTON_POSITIVE) {
                        val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(myIntent)
                    }
                }
            })
        }

        KotlinPermissions.with(this)
            .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .onAccepted { val locationDisplay = _mv.locationDisplay
                locationDisplay.defaultSymbol = Config.gSymbolMarkerGPS
                locationDisplay.autoPanMode = LocationDisplay.AutoPanMode.RECENTER
                _mv.locationDisplay.isShowLocation = true
                locationDisplay.startAsync() }
            .onDenied{Util.showToastMessage(this, "Permission Denied")}
            .ask()
    }
}

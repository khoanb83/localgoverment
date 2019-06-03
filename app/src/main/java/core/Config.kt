package core

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences

import com.esri.arcgisruntime.layers.ArcGISMapImageLayer
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer
import com.esri.arcgisruntime.layers.ArcGISSublayer
import com.esri.arcgisruntime.layers.SublayerList
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.LayerList
import com.esri.arcgisruntime.portal.Portal
import com.esri.arcgisruntime.portal.PortalItem
import com.esri.arcgisruntime.security.Credential
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol
import com.esri.arcgisruntime.util.ListenableList
import com.example.localgoverment.R
import java.util.ArrayList
import java.util.HashMap

object Config {
    var glbMap: ArcGISMap? = null
    var glbCredential: Credential? = null
    var myPortal: Portal? = null
    var URL_PORTAL: String? = null
    var URL_REPORT: String? = null
    var GROUP_ID: String? = null
    var MAP_ITEMS: HashMap<String, PortalItem>? = null
    var gToken: String? = null
    var gUser: String? = null
    var gUserId: String? = null
    var gPassword: String? = null
    lateinit var gSymbolMarkerGPS: PictureMarkerSymbol
    fun initConfig(context: Context): Boolean {
//        CARRIER = context.getString(R.string.CARRIER)
//        LYR_OVERVIEW = context.getString(R.string.LYR_OVERVIEW)
//        LYR_CELLSITE = context.getString(R.string.LYR_CELLSITE)
        URL_PORTAL = context.getString(R.string.URL_PORTAL)
        URL_REPORT = context.getString(R.string.URL_REPORT)
        GROUP_ID = context.getString(R.string.GROUP_ID)
        return URL_PORTAL != null && URL_REPORT != null && GROUP_ID != null
    }
}
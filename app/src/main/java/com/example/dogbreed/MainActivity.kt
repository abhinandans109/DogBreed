package com.example.dogbreed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {
    val list= mutableListOf<model>()
    lateinit var r:RecyclerView
    lateinit var toolbar:Toolbar
    lateinit var mDrawer:DrawerLayout
    lateinit var nvDrawer:NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = findViewById(R.id.main);
        nvDrawer = findViewById(R.id.navigation_menu);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        r=findViewById<RecyclerView>(R.id.rvbreedname)
        r.layoutManager=LinearLayoutManager(this)
        r.adapter=rvAdapter(this,list)
        val url = "https://dog.ceo/api/breeds/list/all"
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                var jsonObject= JSONObject(response)
                Log.e("latest",response)
                val jsonObject2=jsonObject.getJSONObject("message");
                val mp:Iterator<String> = jsonObject2.keys();
                while(mp.hasNext()){
                    var key=mp.next();
                    Log.e("key",key)
                    list.add(model(key,""))
                    r.adapter?.notifyDataSetChanged()
                }
            },
            Response.ErrorListener { error ->
                Log.e("responseerror",error.localizedMessage)
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun setupDrawerContent(nvDrawer: NavigationView?) {
        nvDrawer?.setNavigationItemSelectedListener(
            NavigationView.OnNavigationItemSelectedListener { menuItem ->
                selectDrawerItem(menuItem)
                true
            })

    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        if(menuItem.itemId==R.id.home){
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }else{
            startActivity(Intent(applicationContext,RandomImages::class.java))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.itemId) {
            android.R.id.home -> {
                mDrawer.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
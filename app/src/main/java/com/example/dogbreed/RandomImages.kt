package com.example.dogbreed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.HashMap

class RandomImages : AppCompatActivity() {
    lateinit var breed:String
    var list= mutableListOf<model>()
    lateinit var r: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breed_detail)
        r=findViewById(R.id.rvimage)
        r.layoutManager= LinearLayoutManager(this)
        r.adapter=rvAdapterimage(this,list)
        val url = "https://dog.ceo/api/breeds/image/random/10"
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                var jsonObject= JSONObject(response)
                Log.e("latest",response)
                val jsonArray=jsonObject.getJSONArray("message");
                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray[i].toString()
                    list.add(model("",item))
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
}
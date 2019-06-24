package com.developersian.webService.volley

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.developersian.base.App
import com.developersian.model.Pokemon

class ApiService {
	
	
	fun send() {
		val request = GsonRequest(Request.Method.GET, "https://pokeapi.co/api/v2/pokemon/ditto/",
		                          Response.Listener<Pokemon> {
		                          })
		
		
		Volley.newRequestQueue(App.context).add(request)
	}
	
	
}
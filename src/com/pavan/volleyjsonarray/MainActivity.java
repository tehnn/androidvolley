package com.pavan.volleyjsonarray;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

public class MainActivity extends ListActivity {

	String url = "http://restcountries.eu/rest/v1";
	ProgressDialog PD;

	ArrayList<String> countries;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		countries = new ArrayList<String>();

		PD = new ProgressDialog(this);
		PD.setMessage("Loading.....");
		PD.setCancelable(false);

		adapter = new ArrayAdapter(this, R.layout.items, R.id.tv, countries);
		setListAdapter(adapter);

		MakeJsonArrayReq();
	}

	private void MakeJsonArrayReq() {
		PD.show();

		//JsonArrayRequest jr=new JsonArrayRequest(url, listener, errorListener)
		
		JsonArrayRequest jreq = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {

						for (int i = 0; i < response.length(); i++) {
							try {
								JSONObject jo = response.getJSONObject(i);
								String name = jo.getString("name");
								countries.add(name);

							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						PD.dismiss();
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});

		MyApplication.getInstance().addToReqQueue(jreq, "jreq");
	}
}

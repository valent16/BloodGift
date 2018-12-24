package com.bloodgift.bloodgift.Controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bloodgift.bloodgift.Model.POI.CollectionPOI;
import com.bloodgift.bloodgift.R;
import com.bloodgift.bloodgift.Vue.MapsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapController {
    /**
     * Path of the Json file
     */
    private static final String JSON_PATH = "http://api.openeventdatabase.org/event/?what=health.blood.collect&when=nextweek&limit=1000";

    private MapsActivity activity;

    public MapController(MapsActivity activity){
        this.activity = activity;

        //Gets the refresh button from the activity layout
        Button buttonRefresh = activity.findViewById(R.id.refreshMap);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshJsonMap();
            }
        });
    }

    /**
     *  Method used to refresh the database with the download of the new JSON file
     */
    private void refreshJsonMap(){
        Log.i("infoBlood", "refresh JSON Map called");
        new GetJSONFile().execute();
    }

    /**
     * Class used to download the Json File.
     */
    public class GetJSONFile extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        private CollectionPOI collectionPOI;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            JsonParserPOI jsonParser = new JsonParserPOI(JSON_PATH, activity);

            collectionPOI = jsonParser.parse();

            if (collectionPOI == null){

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getApplicationContext(),
                                "Veuillez activer votre connexion internet!",
                                Toast.LENGTH_LONG)
                                .show();
                        //Log.i("infoBlood", "Couldn't get Json from server, Check logCat for possible errors");
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            activity.updateMarkers(collectionPOI);
            /**
             * Updating parsed JSON data into ListView
             * */
        }
    }
}
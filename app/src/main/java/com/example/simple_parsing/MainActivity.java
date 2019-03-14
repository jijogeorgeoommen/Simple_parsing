package com.example.simple_parsing;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient client;
    RequestParams params;

    ArrayList<String>details;
    ArrayList<String>string;

    ListView listview;
    LayoutInflater inflate;

    String url="http://srishti-systems.info/projects/ocr/view_string.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview=findViewById(R.id.lview);

        client=new AsyncHttpClient();
        params=new RequestParams();
        details=new ArrayList<>();
       // string=new ArrayList<>();

        Log.e ("In","ouut");

        client.get(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    Log.e("Inn","out");
                    JSONObject jobjmain=new JSONObject(content);
                    if (jobjmain.getString("status").equals("success")){

                        JSONArray jarray=jobjmain.getJSONArray("String_details");
                        for(int i=0;i<jarray.length();i++){
                            JSONObject jobj=jarray.getJSONObject(i);
                            String st=jobj.getString("string");
                            details.add(""+st);
                        }
                    }

                 adapter adp=new adapter();
                 listview.setAdapter(adp);


                }catch (Exception e){

                }
            }
        });

    }
    class adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return details.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            inflate=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflate.inflate(R.layout.historylview,null);

            TextView tview=convertView.findViewById(R.id.textview);
            tview.setText(details.get(position));
            return convertView;
        }
    }
}

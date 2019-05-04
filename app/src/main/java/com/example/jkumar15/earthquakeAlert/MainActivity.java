package com.example.jkumar15.earthquakeAlert;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView linearLayoutListView;


    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2018-01-01&limit=20&minmagnitude=6.0";
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutListView = findViewById(R.id.list);
        QuakeAsyncTask task = new QuakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    class QuakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Earthquake> result = Utils.fetchEarthquakeData(urls[0]);
            return result;
        }

        public void onPostExecute(final List<Earthquake> list) {
//            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,
//                   android.R.layout.simple_list_item_1, postExecuteResult);
//            linearLayoutListView = findViewById(R.id.list);
//            linearLayoutListView.setAdapter(arrayAdapter);

            linearLayoutListView.setAdapter(new CustomListAdapter(getApplicationContext(),R.layout.custom,list));
            linearLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(list.get(position).getUrl()));
                    startActivity(viewIntent);
                }
            });
        }
    }
}


class CustomListAdapter extends ArrayAdapter<Earthquake>{

    private int resourceLayout;
    private Context mContext;


    public CustomListAdapter(Context context, int resource, List<Earthquake>items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            view = vi.inflate(resourceLayout,null);
        }

        Earthquake p = getItem(position);

        if(p!=null){
            TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
            tv1.setText(p.toString());

            if (position % 2 ==0){
                tv1.setBackgroundColor(Color.parseColor("#33b5e5"));

            }else{
                tv1.setBackgroundColor(Color.parseColor("#ffbb33"));
            }
        }


//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.layout, null,true);
//
//        TextView tv1 = (TextView) rowView.findViewById(R.id.textView22);
//        tv1.setText(ar1[position] + " " + ar2[position]);

        return view;

    };
}

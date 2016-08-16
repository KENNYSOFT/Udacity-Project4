package kr.KENNYSOFT.Udacity.Project4;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import kr.KENNYSOFT.Udacity.Project4.AndroidLibrary.JokesActivity;
import kr.KENNYSOFT.Udacity.Project4.Backend.myApi.MyApi;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        //Step 1
        //Toast.makeText(this,new Joker().getJoke(),Toast.LENGTH_SHORT).show();

        //Step 2
        //startActivity(new Intent(this,JokesActivity.class).putExtra("jokes",new Joker().getJoke()));

        //Step 3
        new EndpointsAsyncTask().execute(this);
    }


}

class EndpointsAsyncTask extends AsyncTask<Context, Void, String>
{
    private static MyApi myApiService = null;
    private Context context;
    private EndpointsAsyncTaskListener mListener;

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            /*MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException
                        {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });*/
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://kennysoft-udacity-project4.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.sayJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context,JokesActivity.class).putExtra("jokes",result).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        if(this.mListener!=null)this.mListener.onComplete(result);
    }

    public EndpointsAsyncTask setListener(EndpointsAsyncTaskListener listener)
    {
        this.mListener=listener;
        return this;
    }

    public interface EndpointsAsyncTaskListener
    {
        void onComplete(String result);
    }
}
package eparon.sideloadlauncher;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private AbsListView mListView;

    @SuppressLint("StaticFieldLeak")
    private final AsyncTask<Void, Void, PackageInfo[]> mApplicationLoader = new AsyncTask<Void, Void, PackageInfo[]>() {

        @Override
        protected PackageInfo[] doInBackground (Void... params) {
            return PackageInfo.loadApplications(MainActivity.this).toArray(new PackageInfo[0]);
        }

        @Override
        protected void onPostExecute (PackageInfo[] apps) {
            mListView.setOnItemClickListener(MainActivity.this);
            mListView.setAdapter(new ApplicationAdapter(MainActivity.this, R.layout.grid_item, apps));
        }

    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.list);
        mApplicationLoader.execute();
    }

    @Override
    protected void onResume () {
        super.onResume();
        setFullScreen();
    }

    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        PackageInfo packageInfo = (PackageInfo)view.getTag();
        startPackage(packageInfo.getPackageName());
    }

    private void startPackage (String packageName) {
        try {
            startActivity(getPackageManager().getLaunchIntentForPackage(packageName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFullScreen () {
        try {
            View decorView = getWindow().getDecorView();

            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;

            decorView.setSystemUiVisibility(uiOptions);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER, WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

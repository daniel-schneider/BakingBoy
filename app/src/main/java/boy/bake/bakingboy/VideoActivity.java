package boy.bake.bakingboy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import boy.bake.bakingboy.fragments.VideoFragment;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = VideoActivity.class.getSimpleName();
    public static final String KEY_STEPS_DESC = "stepDescription";
    public static final String KEY_STEPS_URL = "stepURL";
    public static final String THUMBNAIL_IMAGE = "thumbnailUrl";
    public static final String KEY_ROTATION_VIDEO_ACTIVITY="rotationVideo";
    public static final String KEY_PANE_VID = "pane";
    public static final String KEY_VISIBILITY_EXO_PLAYER = "exoPlayerVisibility";
    public static final String KEY_VISIBILITY_PLACEHOLDER = "placeHolderVisibility";
    public static final String KEY_PLAY_WHEN_READY="playWhenReady";
    public static final String MEDIA_POS = "MEDIA_POSITION";



    private Bundle bundle;
    private boolean fragmentCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

//            TODO getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }
        if (savedInstanceState != null) {
            fragmentCreated = savedInstanceState.getBoolean(KEY_ROTATION_VIDEO_ACTIVITY);
        }
        if (!fragmentCreated) {
            //Only init when the bool is false and fragments need to be transacted
            //for preserving the ExoPlayer instance so that it resumes properly
            bundle = new Bundle();
            bundle = getIntent().getExtras();
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(bundle);
            fragmentCreated = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_fragment, videoFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //Back Button to navigate back to the details screen
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ROTATION_VIDEO_ACTIVITY, fragmentCreated);
    }
}

package boy.bake.bakingboy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import boy.bake.bakingboy.fragments.VideoFragment;
import boy.bake.bakingboy.util.SimpleIdlingResource;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    public static final String KEY_STEP_DATA = "key_step_data";
    public static final String KEY_INGREDIENT_DATA = "key_ingredient_data";
    public static final String KEY_PANE = "key_pane";
    public static final String KEY_STEPS = "steps";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_STEPS_JSON = "stepListJson";
    public static final String KEY_INGREDIENTS_JSON = "ingredientsListJson";
    public static final String KEY_ROTATION_DETAIL_ACTIVITY="rotationDetail";


    private static boolean mTwoPane;
    String mStepData;
    String mIngredientsData;
    boolean rotationDetails;

    @Nullable
    private SimpleIdlingResource mIdlingResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState != null) {
            rotationDetails = savedInstanceState.getBoolean(KEY_ROTATION_DETAIL_ACTIVITY);
        } else {
            mStepData = getIntent().getStringExtra(KEY_STEP_DATA);
            mIngredientsData = getIntent().getStringExtra(KEY_INGREDIENT_DATA);
            Bundle bundle = new Bundle();
            bundle.putString(KEY_STEP_DATA, mStepData);
            bundle.putString(KEY_INGREDIENT_DATA, mIngredientsData);
            bundle.putBoolean(KEY_PANE, mTwoPane);
        }

        if (findViewById(R.id.video_container) != null) {
            mTwoPane = true;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.video_container, new VideoFragment()).commit();
        }

        if (savedInstanceState == null) {
            //Only initialize when needed for preserving rotations states
            mStepData = getIntent().getStringExtra(KEY_STEPS);
            mIngredientsData = getIntent().getStringExtra(KEY_INGREDIENTS);
            Bundle bundle = new Bundle();
            bundle.putString(KEY_STEPS_JSON, mStepData);
            bundle.putString(KEY_INGREDIENTS_JSON, mIngredientsData);
            bundle.putBoolean(KEY_PANE, mTwoPane);
            Log.d(TAG, "Pane: " + mTwoPane);
            RecipeDetailFragment detailFragment = new RecipeDetailFragment();
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, detailFragment).commit();
            rotationDetails = true;
        }

    }
//TODO rename NoPane
    public static boolean getNoPane() {
        return mTwoPane;
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

}

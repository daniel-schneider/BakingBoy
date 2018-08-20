package boy.bake.bakingboy;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import boy.bake.bakingboy.models.Ingredient;
import boy.bake.bakingboy.models.Recipe;
import boy.bake.bakingboy.models.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private static final String SCROLL_POSITION_KEY = "scroll_position_key";
    public static final String SHARED_PREFERENCES = "MyPrefs";

    @BindView(R.id.ingredients_list_text_view)
    TextView ingredientsText;
    @BindView(R.id.step_recycler_view)
    RecyclerView stepRecyclerView;
    @BindView(R.id.fab_widget)
    FloatingActionButton widgetAddButton;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.back_button)
    ImageButton backButton;
    String mSteps, mIngredients;
    Gson mGson;
    boolean mTwoPane;
    LinearLayoutManager mLinearLayoutManager;
    private List<Step> mStepList;
    private List<Ingredient> mIngredientList;
    private Parcelable mListState;
    RecipeDetailAdapter mRecyclerAdapter;
    FloatingActionButton mWidgetButton;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mSteps = bundle.getString(RecipeDetailActivity.KEY_STEP_DATA);
        mIngredients = bundle.getString(RecipeDetailActivity.KEY_INGREDIENT_DATA);
        mGson = new Gson();
        mIngredientList = mGson.fromJson(mIngredients, new TypeToken<List<Ingredient>>() {}.getType());
        mStepList = mGson.fromJson(mSteps, new TypeToken<List<Step>>() {}.getType());
        mTwoPane = bundle.getBoolean(RecipeDetailActivity.KEY_PANE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        StringBuffer stringBuffer = new StringBuffer();
        for (Ingredient ingredient : mIngredientList) {
            stringBuffer.append("\u2022 " + ingredient.getQuantity() + " " +
                    ingredient.getIngredient() + " " + ingredient.getMeasure() + "\n");
        }
        setHasOptionsMenu(true);
        ingredientsText.setText(stringBuffer.toString());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stepRecyclerView.setLayoutManager(mLinearLayoutManager);

        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(SCROLL_POSITION_KEY);
        }
        Log.d(TAG, mStepList.size() + "");
        mRecyclerAdapter = new RecipeDetailAdapter(getActivity(), mStepList);
//        TODO make this click work
//        mRecyclerAdapter.setOnClick(this);
        stepRecyclerView.setAdapter(mRecyclerAdapter);
        mWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getActivity()
                        .getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
                Recipe recipe = mGson.fromJson(sharedPreferences.getString(RecipeAppWidgetProvider.WIDGET_RESULT, null), Recipe.class);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
                Bundle bundle = new Bundle();
                int appWidgetId = bundle.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                RecipeAppWidgetProvider.updateAppWidget(getActivity(), appWidgetManager, appWidgetId, recipe.getName(),
                        recipe.getIngredients());
                Toast.makeText(getActivity(), "Added " + recipe.getName() + " to Widget.", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListState != null) {
            mLinearLayoutManager.onRestoreInstanceState(mListState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //storing recycler view state
        outState.putParcelable(SCROLL_POSITION_KEY, mLinearLayoutManager.onSaveInstanceState());
    }
}

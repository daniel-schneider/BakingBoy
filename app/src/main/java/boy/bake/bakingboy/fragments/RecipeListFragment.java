package boy.bake.bakingboy.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import boy.bake.bakingboy.R;
import boy.bake.bakingboy.RecipeDetailActivity;
import boy.bake.bakingboy.RecipeDetailAdapter;
import boy.bake.bakingboy.RecipeDetailFragment;
import boy.bake.bakingboy.data.RecipeData;
import boy.bake.bakingboy.models.Recipe;
import boy.bake.bakingboy.util.SimpleIdlingResource;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends Fragment {


    private static final String TAG = RecipeDetailFragment.class.getSimpleName();
    @BindView(R.id.recipe_recycler_view)
    RecyclerView recipeRecyclerView;
    String resultJson;
    Gson gson;
    //  TODO  SimpleIdlingResource mIdlingResource;
    @BindView(R.id.double_progress_arc)
    ProgressBar mProgressBar;
    SimpleIdlingResource mIdlingResource;
    private List<Recipe> mRecipeList;
    private boolean mTwoPane;
    private RecipeDetailAdapter mRecipeAdapter;


    public RecipeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        mIdlingResource = (SimpleIdlingResource) ((RecipeDetailActivity) getActivity()).getIdlingResource();
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
        mProgressBar.setVisibility(View.VISIBLE);
        if (isNetworkConnected()) {
            mRecipeList = getRecipeList();
        } else {
            Snackbar.make(view, getActivity().getString(R.string.network_error), Snackbar.LENGTH_LONG).show();
        }
        gson = new Gson();
        return view;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    public List<Recipe> getRecipeList() {
        mRecipeList = gson.fromJson(RecipeData.recipe_data, new TypeToken<List<Recipe>>(){}.getType());
        mRecipeAdapter = new RecipeDetailAdapter(getActivity(), mRecipeList);
        mTwoPane = RecipeDetailActivity.getNoPane();
        if (mTwoPane) {
            //GridLayout
            GridLayoutManager gridLayoutManager = new
                    GridLayoutManager(getActivity(),  3);
            recipeRecyclerView.setLayoutManager(gridLayoutManager);
            recipeRecyclerView.setAdapter(mRecipeAdapter);
            mIdlingResource.setIdleState(true);
        } else {
            //LinearVerticalLayout
            LinearLayoutManager linearLayoutManager = new
                    LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recipeRecyclerView.setLayoutManager(linearLayoutManager);
            recipeRecyclerView.setAdapter(mRecipeAdapter);
            mIdlingResource.setIdleState(true);
        }
        mProgressBar.setVisibility(View.GONE);
        return mRecipeList;
    }


    private void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

}

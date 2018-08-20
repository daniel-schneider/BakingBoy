package boy.bake.bakingboy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeListActivity extends AppCompatActivity {

    private static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        if (findViewById(R.id.tabbed_recipe_list) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
    }

    public static boolean getPane() {
        return mTwoPane;
    }

}

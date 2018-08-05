package boy.bake.bakingboy.recipe;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class RecipeViewModel extends ViewModel {

    private String data;

    public RecipeViewModel(Context context) {
        try {
            InputStream is = context.getAssets().open("yourfilename.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            data = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

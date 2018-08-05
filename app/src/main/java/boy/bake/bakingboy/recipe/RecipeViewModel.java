package boy.bake.bakingboy.recipe;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeViewModel extends ViewModel {

    private String data;
    private Integer id;
    private String name;
    private List<Ingredient> ingredients = null;
    private List<Step> steps = null;
    private Integer servings;
    private String image;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public RecipeViewModel(Context context) {

        try {
            InputStream is = context.getAssets().open("recipe_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            data = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


//example showing how to use Ingredient and Step objects
//    public class Example {
//
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public List<Ingredient> getIngredients() {
//            return ingredients;
//        }
//
//        public void setIngredients(List<Ingredient> ingredients) {
//            this.ingredients = ingredients;
//        }
//
//        public List<Step> getSteps() {
//            return steps;
//        }
//
//        public void setSteps(List<Step> steps) {
//            this.steps = steps;
//        }
//
//        public Integer getServings() {
//            return servings;
//        }
//
//        public void setServings(Integer servings) {
//            this.servings = servings;
//        }
//
//        public String getImage() {
//            return image;
//        }
//
//        public void setImage(String image) {
//            this.image = image;
//        }
//
//        public Map<String, Object> getAdditionalProperties() {
//            return this.additionalProperties;
//        }
//
//        public void setAdditionalProperty(String name, Object value) {
//            this.additionalProperties.put(name, value);
//        }
//
//    }


}

package boy.bake.bakingboy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import boy.bake.bakingboy.models.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Recipe> mRecipeList;
//    TODO make callback
//    private ClickCallBack mCallBack;

    public RecipeDetailAdapter(Context context, List<Recipe> recipes) {
        mContext = context;
        mRecipeList = recipes;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String shortDescription = mRecipeList.get(position).getSteps().get(position).getShortDescription();
        holder.stepTextView.setText(shortDescription);

    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_name_text_view)
        TextView stepTextView;
        @BindView(R.id.card_video_list)
        CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

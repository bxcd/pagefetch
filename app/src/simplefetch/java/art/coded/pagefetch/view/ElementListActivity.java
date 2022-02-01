package art.coded.pagefetch.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.databinding.ActivityListBinding;

/**
 * Manages higher-level UI abstractions such as the content view and action bar
 */
public class ElementListActivity extends AppCompatActivity {

    private static final String LOG_TAG = ElementListActivity.class.getSimpleName();

    // Member variable
    private ActivityListBinding binding;

    // From inflated layouts, sets the content view containing a Fragment and sets a SupportActionBar
    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarList);
    }
}
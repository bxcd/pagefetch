package art.coded.pagefetch.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import art.coded.pagefetch.R;
import art.coded.pagefetch.databinding.FragmentListBinding;
import art.coded.pagefetch.model.entity.ElementComparator;
import art.coded.pagefetch.model.source.ElementDataSourceFactory;
import art.coded.pagefetch.view.adapter.ElementListAdapter;
import art.coded.pagefetch.viewmodel.ElementListViewModel;

/**
 * Manages the UI visuals and interactions inside the content view of ListActivity
 */
public class ElementListFragment
        extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    static final String LOG_TAG = ElementListFragment.class.getSimpleName();

    // Member variables
    private ElementListViewModel mListViewModel;
    private FragmentActivity mFragmentActivity;
    private SharedPreferences mSharedPreferences;
    private ElementListAdapter mPagedListAdapter;
    private InputMethodManager mMethodManager;
    private FragmentListBinding binding;
    private ConstraintLayout mRootView;
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private boolean mControlsFlipped;
    private Integer mPageSize;
    private Integer mTypeKey;

    // Inflates Views and defines UI objects and their interactions
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate root view
        binding = FragmentListBinding.inflate(inflater, container, false);
        mRootView = binding.getRoot();

        // Get Activity reference for context and assign references to shared prefs and method manager
        mFragmentActivity = requireActivity();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mFragmentActivity);
        mMethodManager = (InputMethodManager) mFragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);

        // Set controller orientation based on preference
        mControlsFlipped = mSharedPreferences.getBoolean(
                getString(R.string.sp_key_controlsflipped), false);
        if (mControlsFlipped) {
            ConstraintSet set = new ConstraintSet();
            set.clone(mRootView);
            set.connect(R.id.controller_wrapper, ConstraintSet.RIGHT, ConstraintSet.UNSET, ConstraintSet.RIGHT, 0);
            set.connect(R.id.controller_wrapper, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 32);
            set.applyTo(mRootView);
        }

        // Enable options menu
        setHasOptionsMenu(true);

        // Get page size shared pref and inflate/populate page size controller views
        mPageSize = mSharedPreferences.getInt(getString(R.string.sp_key_pagesize), 15);
        Button incrementButton = binding.incrementButton;
        Button decrementButton = binding.decrementButton;
        mEditText = binding.editText;
        incrementButton.setOnClickListener(this);
        decrementButton.setOnClickListener(this);
        mEditText.setOnEditorActionListener(this);
        mEditText.setText(String.format(Locale.getDefault(), "%d", mPageSize));

        // Instantiate amd format RecyclerView and attach ListAdapter to RecyclerView
        mRecyclerView = binding.rvList;
        mPagedListAdapter =
                new ElementListAdapter(new ElementComparator(), mFragmentActivity);
        mRecyclerView.setAdapter(mPagedListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragmentActivity));

        mTypeKey = mSharedPreferences.getInt(
                getString(R.string.sp_key_datasourcetype), 0);
        // Instantiate and load ViewModel
        mListViewModel = new ViewModelProvider(this).get(ElementListViewModel.class);
        mListViewModel.loadData(mFragmentActivity.getApplication(), mTypeKey);

        // Populate ListAdapter with observable Element LiveData generating callbacks on list updates
        mListViewModel.elementList(mPageSize).observe(mFragmentActivity, mPagedListAdapter::submitList);

        return mRootView;
    }

    // Handler for interactions with EditText view
    @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() != R.id.edit_text || actionId != EditorInfo.IME_ACTION_DONE) return false;
        mEditText.clearFocus();
        String inputStr = mEditText.getText().toString();
        try {
            int inputInt = Integer.parseInt(inputStr);
            if (inputInt > 1 && inputInt < PagedList.Config.MAX_SIZE_UNBOUNDED) mPageSize = inputInt;
        } catch (NumberFormatException e) { Log.e(LOG_TAG, e.getMessage()); }
        mEditText.setText(String.format(Locale.getDefault(), "%d", mPageSize));
        if (mMethodManager != null) mMethodManager.toggleSoftInput(0,0);
        mSharedPreferences.edit().putInt(getString(R.string.sp_key_pagesize), mPageSize).apply();

        // Populate ListAdapter with observable Element LiveData generating callbacks on list updates
        mListViewModel.elementList(mPageSize).observe(mFragmentActivity, mPagedListAdapter::submitList);
        return true;
    }

    // Handler for interactions with increment and decrement buttons
    @Override public void onClick(View v) {
        int id = v.getId();
        boolean pageSizeChanged = false;
        switch(id) {
            case R.id.increment_button:
                pageSizeChanged = true;
                mPageSize++;
                break;
            case R.id.decrement_button:
                pageSizeChanged = true;
                if (mPageSize > 1) mPageSize--;
                break;
            default:
        }
        if (pageSizeChanged) {
            mEditText.clearFocus();
            mEditText.setText(String.format(Locale.getDefault(), "%d", mPageSize));
            mSharedPreferences.edit().putInt(getString(R.string.sp_key_pagesize), mPageSize).apply();

            mPagedListAdapter = new ElementListAdapter(new ElementComparator(), mFragmentActivity);

            mRecyclerView.setAdapter(mPagedListAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragmentActivity));

            // Populate ListAdapter with observable Element LiveData generating callbacks on list updates
            mListViewModel = new ViewModelProvider(this).get(ElementListViewModel.class);
            mListViewModel.elementList(mPageSize).observe(mFragmentActivity, mPagedListAdapter::submitList);
        }
    }

    // Nullify view bindings on Fragment destruction
    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Set menu option icon based on preference
    @Override public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.list, menu);
        Drawable icon = menu.getItem(0).getIcon();
        icon.setAutoMirrored(true);
        icon.setLayoutDirection(mControlsFlipped ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Update preference, then set menu option icon and controller orientation based on preference
    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        final int id = item.getItemId();
        switch (id) {

            case R.id.action_controls:

            mControlsFlipped = !mControlsFlipped;
            mSharedPreferences.edit().putBoolean(getString(R.string.sp_key_controlsflipped), mControlsFlipped).apply();
            int iconDirection = mControlsFlipped ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR;
            int removedSide = mControlsFlipped ? ConstraintSet.RIGHT : ConstraintSet.LEFT;
            int addedSide = mControlsFlipped ? ConstraintSet.LEFT : ConstraintSet.RIGHT;

            Drawable icon = item.getIcon();
            icon.setAutoMirrored(true);
            icon.setLayoutDirection(iconDirection);

            int wrapperId = R.id.controller_wrapper;
            ConstraintSet set = new ConstraintSet();
            set.clone(mRootView);
            set.connect(wrapperId, removedSide, ConstraintSet.UNSET, removedSide, 0);
            set.connect(wrapperId, addedSide, ConstraintSet.PARENT_ID, addedSide, 32);
            set.applyTo(mRootView);
            break;

            case R.id.action_type:

                mTypeKey = mSharedPreferences.getInt(
                        getString(R.string.sp_key_datasourcetype), 2);
                // Instantiate and load ViewModel
                mListViewModel = new ViewModelProvider(this).get(ElementListViewModel.class);

                if (mTypeKey == 2) mTypeKey = 0; else ++mTypeKey;
                mSharedPreferences
                        .edit()
                        .putInt(getString(R.string.sp_key_datasourcetype), mTypeKey)
                        .apply();

                String type = String.format(
                        "Now paging %s keyed datasource",
                        ElementDataSourceFactory.DatasourceType.values()[mTypeKey]
                );
                Toast.makeText(getContext(), type, Toast.LENGTH_LONG).show();

                mPagedListAdapter = new ElementListAdapter(new ElementComparator(), mFragmentActivity);

                mRecyclerView.setAdapter(mPagedListAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragmentActivity));

                mListViewModel.loadData(mFragmentActivity.getApplication(), mTypeKey);

                // Populate ListAdapter with observable Element LiveData generating callbacks on list updates
                mListViewModel.elementList(mPageSize).observe(mFragmentActivity, mPagedListAdapter::submitList);

                LinearLayout controllerWrapper = mRootView.findViewById(R.id.controller_wrapper);
                if (mTypeKey == 0) controllerWrapper.setVisibility(View.VISIBLE);
                else controllerWrapper.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }
}
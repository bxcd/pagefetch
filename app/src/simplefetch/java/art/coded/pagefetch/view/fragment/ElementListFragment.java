package art.coded.pagefetch.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import art.coded.pagefetch.databinding.FragmentListBinding;
import art.coded.pagefetch.view.adapter.ElementListAdapter;
import art.coded.pagefetch.viewmodel.ElementListViewModel;

/**
 * Manages the UI visuals and interactions inside the content view of ListActivity
 */
public class ElementListFragment extends Fragment {

    private static final String LOG_TAG = ElementListFragment.class.getSimpleName();

    // Member variables
    private ElementListViewModel listViewModel;
    private FragmentListBinding binding;

    // Inflates Views and defines UI objects and their interactions
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate root view
        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get Activity reference for context
        Activity activity = getActivity();

        // Instantiate amd format RecyclerView and attach ListAdapter to RecyclerView
        final RecyclerView recyclerView = binding.rvList;
        ElementListAdapter listAdapter = new ElementListAdapter(activity);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        // Instantiate and load ViewModel
        listViewModel =
                new ViewModelProvider(this).get(ElementListViewModel.class);
        listViewModel.loadData(requireActivity().getApplication());

        // Populate ListAdapter with observable Element LiveData generating callbacks on list updates
        listViewModel.getData().observe(getViewLifecycleOwner(),
                elementList -> listAdapter.setElements(elementList));

        return root;
    }

    // Nullify view bindings on Fragment destruction
    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
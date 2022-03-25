package art.coded.pagefetch.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import art.coded.pagefetch.model.ElementRepository;

public class ElementListViewModelFactory implements ViewModelProvider.Factory {

    ElementRepository mRepository;

    public ElementListViewModelFactory(ElementRepository repository) {
        mRepository = repository;
    }

    @NonNull @Override public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new ElementListViewModel(mRepository);
    }
}
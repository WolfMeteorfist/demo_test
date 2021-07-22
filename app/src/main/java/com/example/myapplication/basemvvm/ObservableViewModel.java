package com.example.myapplication.basemvvm;

import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.ViewModel;

/**
 * @author yuechou.zhang
 * @since 2021/7/16
 */
public class ObservableViewModel extends ViewModel implements Observable {

    private final PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        propertyChangeRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        propertyChangeRegistry.remove(callback);
    }

    protected void notifyChanged() {
        propertyChangeRegistry.notifyCallbacks(this, 0, null);
    }

    protected void notifyPropertyChanged(int fieldId) {
        propertyChangeRegistry.notifyCallbacks(this, fieldId, null);
    }


}

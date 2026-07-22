package com.example.productivity.ui.screens.settingsscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productivity.datastore.DataStoreManager
import com.example.productivity.utils.ColorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var colorItemListState by mutableStateOf<List<ColorItem>>(emptyList())
    
    init {
        viewModelScope.launch {
            dataStoreManager.getStringPreference(
                key = DataStoreManager.TITLE_COLOR,
                defValue = "#FFB388FF"
            ).collect { selectedColor ->
                val tempColorList = arrayListOf<ColorItem>()
                ColorUtils.colorList.forEach { color ->
                    tempColorList.add(
                        ColorItem(
                            color = color,
                            isSelected = selectedColor == color
                        )
                    )
                }
                colorItemListState = tempColorList
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnItemClick -> {
                viewModelScope.launch {
                    dataStoreManager.saveStringPreference(
                        value = event.color,
                        key = DataStoreManager.TITLE_COLOR
                    )
                }
            }
        }
    }
}

data class ColorItem(
    val color: String,
    val isSelected: Boolean
)
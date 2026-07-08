package com.example.productivity.ui.screens.mainscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productivity.data.entities.ShoppingListItem
import com.example.productivity.data.repository.ShoppingListRepository
import com.example.productivity.dialog.DialogController
import com.example.productivity.dialog.DialogEvent
import com.example.productivity.utils.Routes
import com.example.productivity.utils.UiEvent
import com.example.productivity.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val shoppingListRepository: ShoppingListRepository
): ViewModel(), DialogController{

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    override var dialogTitle = mutableStateOf<String>("List Name")
        private set
    override var showEditableText = mutableStateOf<Boolean>(true)
        private set
    override var editableText = mutableStateOf<String>("")
        private set
    override var openDialog = mutableStateOf<Boolean>(false)
        private set

    var showFloatingButton = mutableStateOf(true)
        private set

    fun updateFloatingButtonVisibility (route: String) {
        showFloatingButton.value = !(route == Routes.ABOUT || route == Routes.SETTINGS)
    }


    fun onEvent(event: MainScreenEvent){
        when(event){
            is MainScreenEvent.OnItemSave -> {
                if (editableText.value.isBlank()) return
                viewModelScope.launch {
                    shoppingListRepository.insertItem(
                        ShoppingListItem(
                            id = null,
                            name = editableText.value,
                            time = getCurrentTime(),
                            allItemsCount = 0,
                            allSelectedItemsCount = 0
                        )
                    )
                }

            }
            is MainScreenEvent.OnNewItemClick -> {
                if (event.route == Routes.SHOPPING_LIST) {
                    openDialog.value = true
                }else {
                    sendUiEvent(UiEvent.NavigateMain(route = Routes.NEW_NOTE + "/-"))
                }
            }
            is MainScreenEvent.Navigate -> {
                sendUiEvent(UiEvent.Navigate(route = event.route))
            }
            is MainScreenEvent.NavigateMain -> {
                sendUiEvent(UiEvent.NavigateMain(route = event.route))
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnConfirm -> {
                if (showEditableText.value) {
                    onEvent(event = MainScreenEvent.OnItemSave)
                    openDialog.value = false
                    editableText.value = ""
                }
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text

            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


}
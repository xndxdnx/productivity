package com.example.productivity.ui.screens.shoppinglistscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productivity.data.entities.ShoppingListItem
import com.example.productivity.data.repository.ShoppingListRepository
import com.example.productivity.dialog.DialogController
import com.example.productivity.dialog.DialogEvent
import com.example.productivity.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel(), DialogController {
    val list = repository.getAllItems()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var listItem: ShoppingListItem? = null

    override var dialogTitle = mutableStateOf<String>("List Name")
        private set

    override var showEditableText = mutableStateOf<Boolean>(true)
        private set

    override var editableText = mutableStateOf<String>("")
        private set

    override var openDialog = mutableStateOf<Boolean>(false)
        private set

    fun onEvent(event: ShoppingListEvent) {
        when (event) { 
            is ShoppingListEvent.OnItemSave -> {
                if (editableText.value.isBlank()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            listItem?.id,
                            name = editableText.value,
                            time = "12.06.26 15:50",
                            allItemsCount = listItem?.allItemsCount ?: 0,
                            allSelectedItemsCount = listItem?.allSelectedItemsCount ?: 0
                        )
                    )
                }
            }

            is ShoppingListEvent.OnItemClick -> {
                sendUiEvent(event = UiEvent.Navigate(event.route))
            }

            is ShoppingListEvent.OnShowEditDialog -> {
                listItem = event.item
                openDialog.value = true
                editableText.value = listItem?.name ?: ""
                dialogTitle.value = "List name: "
                showEditableText.value = true
            }

            is ShoppingListEvent.OnShowDeleteDialog -> {
                listItem = event.item
                openDialog.value = true
                showEditableText.value = false
                dialogTitle.value = "Delete List?"
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }
            is DialogEvent.OnConfirm -> {
                if (showEditableText.value) {
                    onEvent(event = ShoppingListEvent.OnItemSave)
                } else {
                    viewModelScope.launch {
                        listItem?.let { item ->
                            repository.deleteItem(item)
                        }
                    }
                }
                openDialog.value = false
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
        }
    }

    private fun sendUiEvent(
        event: UiEvent.Navigate
    ) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
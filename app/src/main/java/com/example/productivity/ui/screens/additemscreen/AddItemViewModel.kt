package com.example.productivity.ui.screens.additemscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productivity.data.entities.AddItem
import com.example.productivity.data.entities.ShoppingListItem
import com.example.productivity.data.repository.AddItemRepository
import com.example.productivity.data.repository.ShoppingListRepository
import com.example.productivity.dialog.DialogController
import com.example.productivity.dialog.DialogEvent
import com.example.productivity.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val addItemRepository: AddItemRepository,
    private val shoppingListRepository: ShoppingListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), DialogController {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var itemList: Flow<List<AddItem>>? = null
    var addItem: AddItem? = null
    var shoppingListItem: ShoppingListItem? = null
    var listId: Int = -1

    init {
        listId = savedStateHandle.get<String>(
            "listId"
        )?.toInt()!!
        itemList = addItemRepository.getItemsByListId(listId)
        viewModelScope.launch {
            shoppingListItem = shoppingListRepository.getAllItemsById(listId)
        }
    }
    override var editableText = mutableStateOf("")
        private set
    override var dialogTitle = mutableStateOf("Edit Name")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(true)
        private set

    fun onEvent(event: AddItemEvent) {
        when(event){
            is AddItemEvent.OnItemSave -> {
                viewModelScope.launch {
                    if (listId == -1) return@launch

                    if (addItem != null) {
                        if (addItem!!.name.isBlank()){
                            sendUiEvent(
                                uiEvent = UiEvent.ShowSnackBar("Name mustn't be empty")
                            )
                            return@launch
                        }

                    }else {
                        if (editableText.value.isBlank()){
                            sendUiEvent(
                                uiEvent = UiEvent.ShowSnackBar("Name mustn't be empty")
                            )
                            return@launch
                        }
                    }

                    addItemRepository.insertItem(
                        AddItem(
                            id = addItem?.id,
                            name = addItem?.name ?: editableText.value,
                            isCheck = addItem?.isCheck ?: false,
                            listId = listId
                        )
                    )
                    editableText.value = ""
                    addItem = null
                }

                viewModelScope.launch {
                    updateShoppingListCount()
                }
            }
            is AddItemEvent.OnShowEditDialog -> {
                addItem = event.item
                openDialog.value = true
                editableText.value = addItem?.name ?: ""
            }

            is AddItemEvent.OnTextChange -> {
                editableText.value = event.text
            }
            is AddItemEvent.OnItemDelete -> {
                viewModelScope.launch {
                    addItemRepository.deleteItem(event.item)

                }
                viewModelScope.launch {
                    updateShoppingListCount()
                }
            }

            is AddItemEvent.OnCheckedChange -> {
                viewModelScope.launch {
                    updateShoppingListCount()
                }
                viewModelScope.launch {
                    addItemRepository.insertItem(
                        event.item
                    )
                }
            }

        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when(event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnConfirm -> {
                openDialog.value = false

                addItem = addItem?.copy(
                    name = editableText.value
                )
                editableText.value = ""
                onEvent(AddItemEvent.OnItemSave)
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
        }
    }

    private suspend fun updateShoppingListCount () {
        itemList?.let { flow ->
            val list = flow.first()
            var counter = 0
            list.forEach { item ->
                if (item.isCheck) counter++
            }
            val updateItem = shoppingListItem?.copy(
                allItemsCount = list.size,
                allSelectedItemsCount = counter
            )
            updateItem?.let { shpListItem ->
                shoppingListRepository.insertItem(shpListItem)
                shoppingListItem = shpListItem

            }
        }
    }
    private fun sendUiEvent(uiEvent : UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }



}
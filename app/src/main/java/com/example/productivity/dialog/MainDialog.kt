package com.example.productivity.dialog


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.productivity.ui.theme.GrayLight
import com.example.productivity.ui.theme.GrayLightSoft

@Composable
fun MainDialog(
    dialogController: DialogController
) {
    if (dialogController.openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                dialogController.onDialogEvent(DialogEvent.OnCancel)
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = dialogController.dialogTitle.value,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    if (dialogController.showEditableText.value) {
                        TextField(
                            value = dialogController.editableText.value,
                            onValueChange = { newValue ->
                                dialogController.editableText.value = newValue
                            },
                            label = {
                                Text(
                                    "List name:",
                                    color = GrayLight
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = GrayLightSoft,
                                unfocusedContainerColor = GrayLightSoft,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            },

            title = null,
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogController.onDialogEvent(DialogEvent.OnConfirm)
                    }
                ) {
                    Text(
                        text = "OK",
                        fontSize = 16.sp
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialogController.onDialogEvent(DialogEvent.OnCancel)
                    }
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 16.sp
                    )
                }
            }
        )
    }

}

private class FakeDialogController(
    titleInitial: String,
    editableTextInitial: String,
    openInitial: Boolean,
    showEditableInitial: Boolean
) : DialogController {

    override val dialogTitle = mutableStateOf(titleInitial)
    override val editableText = mutableStateOf(editableTextInitial)
    override val openDialog = mutableStateOf(openInitial)
    override val showEditableText = mutableStateOf(showEditableInitial)
    override fun onDialogEvent(event: DialogEvent) {

    }
}

@Preview
@Composable
fun MainDialogPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MainDialog(
            dialogController = FakeDialogController(
                titleInitial = "Delete",
                editableTextInitial = "Hello",
                openInitial = true,
                showEditableInitial = true
            )
        )
    }

}
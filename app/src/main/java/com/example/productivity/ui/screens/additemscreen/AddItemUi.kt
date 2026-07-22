package com.example.productivity.ui.screens.additemscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.productivity.R
import com.example.productivity.data.entities.AddItem

@Composable
fun AddItemUi (
    item: AddItem,
    onEvent: (AddItemEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable{
                onEvent(AddItemEvent.OnShowEditDialog(item))
            },
        colors = CardDefaults.cardColors(
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.name,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            )
            Checkbox(
                checked = item.isCheck,
                onCheckedChange = { isChecked ->
                    onEvent(AddItemEvent.OnCheckedChange(item.copy(
                        isCheck = isChecked
                    )))
                }
            )
            IconButton(
                onClick = {
                    onEvent(AddItemEvent.OnItemDelete(item))
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.delete_ic),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@Preview
fun AddItemUiPreview(){
    AddItemUi(
        item = AddItem(
            1,
            "Orange",
            isCheck = true,
            listId = 2
        )
    ) { }
}
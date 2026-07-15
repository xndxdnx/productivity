package com.example.productivity.ui.screens.notelistscreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.productivity.R
import com.example.productivity.data.entities.NoteItem
import com.example.productivity.ui.theme.DarkText
import com.example.productivity.ui.theme.LightText
import com.example.productivity.ui.theme.Pink80
import com.example.productivity.utils.Routes

@Composable
fun NoteUiItem (
    item : NoteItem,
    titleColor: String,
    onEvent: (NoteListEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 3.dp, end = 3.dp, top = 3.dp)
            .clickable{
                onEvent(NoteListEvent.OnNoteClick(Routes.NEW_NOTE + "/${item.id}"))
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    color = Color(android.graphics.Color.parseColor(titleColor)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 10.dp)
                        .weight(1f)

                )
                Text(
                    text = item.time,
                    fontSize = 12.sp,
                    color = DarkText,
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = item.description,
                    color = LightText,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 5.dp, start = 10.dp, bottom = 10.dp)
                        .weight(1f)

                )
                IconButton(
                  onClick = {
                      onEvent(NoteListEvent.OnShowDeleteDialog(item))
                  }
                ){
                    Icon(
                        painter = painterResource(R.drawable.delete_ic),
                        contentDescription = null,
                        tint = Pink80
                    )
                }

            }
        }
    }

}

@Composable
@Preview
fun NoteItemPreview(

){
    NoteUiItem (
        item = NoteItem(
            id = 1,
            title = "Заметка 1",
            time = "-------------",
            description = "Что-то непонятное"
        ),
        titleColor = "#FFB388FF",
        onEvent = {}
    )
}
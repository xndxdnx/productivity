package com.example.productivity.ui.screens.shoppinglistscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.productivity.R
import com.example.productivity.data.entities.ShoppingListItem
import com.example.productivity.ui.theme.BluePastel
import com.example.productivity.ui.theme.DarkText
import com.example.productivity.ui.theme.Lavender
import com.example.productivity.ui.theme.LightText
import com.example.productivity.ui.theme.PinkPastel
import com.example.productivity.utils.ColorUtils
import com.example.productivity.utils.ProgressHelper
import com.example.productivity.utils.Routes

@Composable
fun ShoppingListItem (
    item: ShoppingListItem,
    onEvent: (ShoppingListEvent) -> Unit 
) {
    
    val progress = ProgressHelper.getProgress(
        allItemsCount = item.allItemsCount,
        selectedItemsCount = item.allSelectedItemsCount
    )
    
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 3.dp, top = 18.dp, end = 3.dp)
    ) { 
        val (card, deleteButton, editButton, counter) = createRefs()
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable { onEvent(ShoppingListEvent.OnItemClick(route = Routes.ADD_ITEM + "${item.id}")) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                )
                Text(
                    text = item.time,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = LightText
                    )
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    progress = progress,
                    color = ColorUtils.getProgressColor(
                        progress = progress
                    )
                )
            }
        }
        IconButton(
            onClick = {
                onEvent(ShoppingListEvent.OnShowDeleteDialog(item = item))
            },
            modifier = Modifier
                .constrainAs (deleteButton) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(card.end)
                }
                .padding(end = 10.dp)
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.delete_ic),
                contentDescription = null,
                modifier = Modifier
                    .clip(
                        shape = CircleShape,
                    )
                    .background(
                        color = PinkPastel
                    )
                    .padding(all = 5.dp)
                ,
                tint = Color.White
            )
        }

        IconButton(
            onClick = {
                onEvent(ShoppingListEvent.OnShowEditDialog(item = item))
            },
            modifier = Modifier
                .constrainAs (editButton) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(deleteButton.start)
                }
                .padding(end = 5.dp)
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.edit_ic),
                contentDescription = null,
                modifier = Modifier
                    .clip(
                        shape = CircleShape,
                    )
                    .background(
                        color = BluePastel
                    )
                    .padding(all = 5.dp)
                ,
                tint = Color.White
            )
        }
        
        Card(
            modifier = Modifier
                .constrainAs (counter) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(editButton.start)
                }
                .padding(end = 5.dp)
            ,
            shape = RoundedCornerShape(5.dp)
        ) { 
            Text(
                text = "${item.allSelectedItemsCount} / ${item.allItemsCount}",
                color = Color.White,
                modifier = Modifier
                    .background(color = Lavender)
                    .padding(top = 3.dp, bottom = 3.dp, start = 5.dp, end = 5.dp)
            )
        }
        
        
    }
    
}

@Preview
@Composable
fun ShoppingListItemPreview (){
    ShoppingListItem(
        item = ShoppingListItem(
            id = 1,
            name = "Sample",
            time = "2026.02.02",
            allItemsCount = 150,
            allSelectedItemsCount = 99
        ),
        onEvent = {}
    )
}
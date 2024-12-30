package com.jop.marketjp.ui.screens.home.tabs.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jop.marketjp.R
import com.jop.marketjp.ui.composables.CustomText
import com.jop.marketjp.ui.composables.SimpleImage
import com.jop.marketjp.ui.navigation.Screens
import com.jop.marketjp.ui.screens.home.view.state.HomeViewState

@Composable
fun HomeTabView(
    state: HomeViewState,
    navController: NavController
){
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(state.listCategory){ item ->
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ){
                TitleCategory(
                    title = item.name,
                    onClick = {
                        navController.navigate(Screens.CATEGORY)
                    }
                )
                ItemCardCategory(
                    urlImage = item.image,
                    nameCategory = item.name,
                    onClick = {
                        navController.navigate(Screens.CATEGORY)
                    }
                )
            }
        }
    }
}

@Composable
private fun TitleCategory(
    title: String,
    onClick: () -> Unit
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        CustomText(text = title, fontSize = 18)
        IconButton(
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ItemCardCategory(
    urlImage: String,
    nameCategory: String,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier.padding(10.dp),
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        SimpleImage(
            modifier = Modifier.fillMaxSize(),
            imageUrl = urlImage
        )
        Card (
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(15.dp)
        ){
            CustomText(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                text = nameCategory,
                fontSize = 18
            )
        }
    }
}
package com.jop.marketjp.source.paging.utils


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.jop.marketjp.R
import com.jop.marketjp.ui.composables.CustomCircularProgress
import com.jop.marketjp.ui.composables.CustomText

@Composable
fun <T : Any>PagingLoadingInitial(
    statePage: LazyPagingItems<T>,
    modifier: Modifier = Modifier
){
    when {
        //Carga inicial
        statePage.loadState.refresh is LoadState.Loading && statePage.itemCount == 0 -> {
            CustomCircularProgress(
                sizeProgress = 40.dp,
                isLoading = true,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(25.dp)
            )
        }

        //Estado vacío
        statePage.loadState.refresh is LoadState.NotLoading && statePage.itemCount == 0 -> {
            CustomText(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                textAlign = TextAlign.Center,
                text = R.string.paging_msj_empty_data
            )
        }
        // Estado error
        statePage.loadState.hasError -> {
            CustomText(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                textAlign = TextAlign.Center,
                text = R.string.paging_msj_error_data
            )
        }
    }
}

@Composable
fun <T : Any>PagingLoadingMore(
    statePage: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    showDotAtEnd: Boolean = true
){
    if (statePage.loadState.append is LoadState.Loading) {
        CustomCircularProgress(
            sizeProgress = 20.dp,
            isLoading = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp))
    } else {
        if (!isPagingDataEmpty(statePage) && statePage.itemCount > 0 && showDotAtEnd){
            Row (
                modifier = Modifier.fillMaxWidth().height(60.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    modifier = Modifier.size(12.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.not_more_items),
                    contentDescription = ""
                )
            }
        }
    }
}

fun <T : Any>isPagingDataEmpty(
    statePage: LazyPagingItems<T>
): Boolean{
    return statePage.loadState.refresh is LoadState.NotLoading && statePage.itemCount == 0
}
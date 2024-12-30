package com.jop.marketjp.ui.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import com.google.accompanist.pager.HorizontalPager
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.jop.marketjp.R
import com.jop.marketjp.ui.composables.CustomLoading
import com.jop.marketjp.ui.composables.CustomText
import com.jop.marketjp.ui.composables.CustomToolBar
import com.jop.marketjp.ui.screens.home.data.ItemTabPage
import com.jop.marketjp.ui.screens.home.tabs.home.HomeTabView
import com.jop.marketjp.ui.screens.home.tabs.product.ProductTabView
import com.jop.marketjp.ui.screens.home.view.model.HomeViewModel
import com.jop.marketjp.ui.screens.home.view.state.HomeViewState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
){
    val state by viewModel.getState<HomeViewState>().collectAsState()
    CustomLoading(isLoading = state.isLoading)
    CustomToolBar(
        navController = navController,
        showIcoLauncher = true,
        title = stringResource(id = R.string.home_home_title)
    ){
        val pages = listOf(
            ItemTabPage(title = R.string.home_tab_home_title, composable = {
                HomeTabView(navController = navController, state = state)
            }),
            ItemTabPage(title = R.string.home_tab_product_title, composable = {
                ProductTabView(navController = navController, state = state)
            })
        )
        val coroutine = rememberCoroutineScope()
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = pages.size
        )
        val indexPage = remember { mutableIntStateOf(0) }

        TabRow(
            selectedTabIndex = indexPage.intValue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            pages.forEachIndexed { index, page ->
                Tab(
                    selected = indexPage.intValue == index,
                    onClick = {
                        coroutine.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = Color.Red,
                    text = {
                        CustomText(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = page.title,
                            fontSize = 14,
                            textColor = if (indexPage.intValue == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }

        LaunchedEffect(pagerState.currentPage) {
            indexPage.intValue = pagerState.currentPage
        }

        HorizontalPager(
            modifier = Modifier.heightIn(max = 9900.dp),
            state = pagerState,
            dragEnabled = true
        ) { page ->
            pages[page].composable()
        }
    }
}
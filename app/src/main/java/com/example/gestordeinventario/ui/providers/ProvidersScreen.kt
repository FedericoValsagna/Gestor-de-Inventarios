package com.example.gestordeinventario.ui.providers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.ui.common.LogoutButton
import com.example.gestordeinventario.ui.common.TableCell
import com.example.gestordeinventario.ui.common.TableClickableCell
import com.example.gestordeinventario.model.Provider

@Composable
fun ProvidersListScreen(viewModel: ProvidersViewModel, screensNavigation: ScreensNavigation){
    val providersList : List<Provider> by viewModel.providersList.collectAsState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp) ) {
        ProviderListHeader(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        Box(Modifier.height(screenHeight*0.8f)){
            ProviderList(providers = providersList, modifier = Modifier, screensNavigation = screensNavigation)
        }
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        LogoutButton(modifier = Modifier.align(Alignment.Start)){screensNavigation.restart()}
    }
}

@Composable
fun ProviderListHeader(modifier: Modifier) {
    Text(
        text = "Listado de Provedores",
        fontSize = 24.sp,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun ProviderList(providers: List<Provider>, modifier: Modifier, screensNavigation: ScreensNavigation) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Provedor", weight = 1f, modifier = modifier)
                TableCell(text = "Catalogo", weight = 1f, modifier = modifier)
            }
        }
        items(providers) { provider ->
            Row(modifier = modifier.fillMaxWidth()) {
                TableCell(text = provider.name, weight = 1f, modifier = modifier)
                TableClickableCell(
                    text = "Catalogo",
                    weight = 1f,
                    modifier = modifier,
                    navigateToScreen = {screensNavigation.navigateToReplenish(provider.name)}
                )
            }
        }
    }
}

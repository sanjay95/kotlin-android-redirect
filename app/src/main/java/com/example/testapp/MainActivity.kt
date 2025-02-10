package com.example.testapp

import ActionButton
import InputCard
import LoadingIndicator
import ResponseCard
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.ui.theme.TestAppTheme
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeepLink(intent)
        enableEdgeToEdge()
        setContent {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            TestAppTheme {
                Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            CustomTopAppBar(
                                    onBackPressed = { onBackPressedDispatcher.onBackPressed() },
                                    onMenuClicked = {
                                        // Open the drawer when the menu icon is clicked
                                        scope.launch { drawerState.open() }
                                    }
                            )
                        },
                ) { innerPadding ->
                    ModalNavigationDrawer(
                            drawerState = drawerState,
                            gesturesEnabled = true,
                            drawerContent = {
                                // Add a Box with a colored background
                                Box(
                                        modifier =
                                                Modifier.background(color = Color.LightGray)
                                                        .padding(vertical = 56.dp)
                                                        .requiredWidth(300.dp)
                                                        .requiredHeight(700.dp)
                                        // Set your desired background color
                                        ) {
                                    // Use a Column to organize the drawer contents
                                    Column(
                                            modifier = Modifier.padding(top = 36.dp).fillMaxWidth()
                                    ) {
                                        // Drawer content with separation
                                        var isSubMenuExpanded by remember { mutableStateOf(true) }

                                        Text(
                                                text = "+ Issuance details",
                                                modifier =
                                                        Modifier.padding(16.dp)
                                                                .fillMaxWidth()
                                                                .clickable {
                                                                    isSubMenuExpanded =
                                                                            !isSubMenuExpanded
                                                                },
                                                style =
                                                        TextStyle(
                                                                fontSize = 20.sp,
                                                                fontWeight = FontWeight.Bold
                                                        )
                                        )
                                        if (isSubMenuExpanded) {
                                            Column(modifier = Modifier.padding(start = 32.dp)) {
                                                Text(
                                                        text = "AHC Vitals Aggregate",
                                                        modifier =
                                                                Modifier.padding(8.dp)
                                                                        .fillMaxWidth()
                                                )
                                            }
                                        }
                                        Divider(
                                                color = Color.Gray,
                                                thickness = 1.dp
                                        ) // Add a divider for separation
                                        Text(
                                                text = "+ App Backend",
                                                modifier =
                                                        Modifier.padding(16.dp)
                                                                .fillMaxWidth()
                                                                .clickable {
                                                                    isSubMenuExpanded =
                                                                            !isSubMenuExpanded
                                                                },
                                                style =
                                                        TextStyle(
                                                                fontSize = 20.sp,
                                                                fontWeight = FontWeight.Bold
                                                        )
                                        )
                                        if (isSubMenuExpanded) {
                                            Column(modifier = Modifier.padding(start = 32.dp)) {
                                                Text(
                                                        text =
                                                                "https://aca-bank.vercel.app/api/issuance/start",
                                                        modifier =
                                                                Modifier.padding(8.dp)
                                                                        .fillMaxWidth()
                                                )
                                            }
                                        }
                                        Divider(color = Color.Gray, thickness = 1.dp)
                                        Text(
                                                text = "DID Needed: Yes",
                                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                                style =
                                                        TextStyle(
                                                                fontSize = 20.sp,
                                                                fontWeight = FontWeight.Bold
                                                        )
                                        )
                                    }
                                }
                            }
                    ) {
                        MainContent(
                                innerPadding = innerPadding,
                                service = IssuanceService(),
                                jsonData =
                                        IssuanceService()
                                                .getJsonDataFromRaw(this, "aggregate_data"),
                                context = this
                        )
                    }
                }
            }
            handleDeepLink(intent)
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        intent?.data?.let { uri ->
            println("Deep link URI: $uri")
            // Handle the deep link as needed
        }
    }
    private fun onMenuClicked() {
        // Handle menu click action
        println("Menu clicked")
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomTopAppBar(onBackPressed: () -> Unit, onMenuClicked: () -> Unit) {
    CenterAlignedTopAppBar(
            title = {
                Text(
                        text = "CIS Redirect Test",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Gray),
            navigationIcon = {
                Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.clickable { onBackPressed() }.padding(8.dp)
                )
            },
            actions = {
                Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White,
                        modifier = Modifier.clickable { onMenuClicked() }.padding(8.dp)
                )
            }
    )
}

@Composable
@Preview
fun MainContent(
        innerPadding: PaddingValues,
        service: IssuanceService,
        jsonData: String,
        context: Context,
) {
    val did = remember {
        mutableStateOf("did:key:zQ3shZ5XvgFEiuLeBofUKk3QzHpEMpcfHYnPKVyDSdkKrkwqX")
    }
    val apiResponse = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            contentAlignment = Alignment.TopCenter
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
        ) {
            // DID Input Section
            InputCard(
                    title = "Enter Target DID:",
                    value = did.value,
                    onValueChange = { did.value = it }
            )

            // Issue VC Button
            ActionButton(
                    text = "Issue VC",
                    onClick = {
                        isLoading.value = true
                        try {
                            service.startIssuance(did.value, jsonData) { response ->
                                println("Response from API: $response")
                                apiResponse.value = response
                                isLoading.value = false
                            }
                            println("Response in main: ${apiResponse.value.toString()}")
                        } catch (e: Exception) {
                            println("Error: ${e.message}")
                            isLoading.value = false
                        }
                    }
            )

            // Loading Indicator
            if (isLoading.value) {
                LoadingIndicator()
            }

            // API Response Section
            if (apiResponse.value.isNotEmpty() && apiResponse.value.contains("credentialOfferUri")
            ) {
                ResponseCard(
                        response = apiResponse.value,
                        onClaimClick = {
                            val claimUrl =
                                    "https://vault.affinidi.com/claim?credential_offer_uri=" +
                                            JSONObject(apiResponse.value)
                                                    .getString("credentialOfferUri")
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(claimUrl))
                            context.startActivity(intent)
                        }
                )
            }
        }
    }
}

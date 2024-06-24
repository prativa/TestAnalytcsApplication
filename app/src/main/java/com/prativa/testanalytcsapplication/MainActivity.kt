package com.prativa.testanalytcsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prativa.analyticssdk.AnalyticsDemoSDK
import com.prativa.testanalytcsapplication.ui.theme.TestAnalytcsApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val analytics = AnalyticsDemoSDK.getInstance(this, "1", true, "abc")

        setContent {
            TestAnalytcsApplicationTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                ) { innerPadding ->
                    TrackingButtons(
                        analyticsDemoSDK = analytics,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name! This is a test",
        modifier = modifier.fillMaxSize(),
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestAnalytcsApplicationTheme(dynamicColor = false) {
        Greeting("Android")
    }
}

@Composable
fun TrackingButtons(analyticsDemoSDK: AnalyticsDemoSDK, modifier: Modifier = Modifier) {
    var sessionEnabled by remember {
        mutableStateOf(false)
    }

    var showTrackedEvents by remember {
        mutableStateOf(false)
    }

    var buttonSessionStartColor by remember {
        mutableStateOf(Color.Red)
    }
    var buttonSessionStartText by remember {
        mutableStateOf("Start Session")
    }
    buttonSessionStartColor = if (sessionEnabled)
        Color.Green
    else Color.Red

    buttonSessionStartText = if (sessionEnabled)
        "Session Started"
    else "Start Session"


    val stateDisabled by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Set white background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally // Center buttons horizontally
        ) {
            Button(
                onClick = {
                    analyticsDemoSDK.startSession()
                    sessionEnabled = true
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Set button width to 80% of screen
                    .padding(8.dp)
                    ,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(contentColor = buttonSessionStartColor)

            ) {
                Text(text = buttonSessionStartText, color = Color.White) // Set white text color
            }

            Button(
                onClick = {
                    analyticsDemoSDK.trackEvent("Button Click")
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(contentColor = buttonSessionStartColor)
            ) {
                Text(text = "Track Event", color = Color.White)
            }

            val properties = mutableMapOf<String, Any>()
            properties["device"] = "Android"
            properties["version"] = "1.0.0"
            properties["name"] = "Demo User"
            Button(
                onClick = {
                    analyticsDemoSDK.trackEventWithProperties(
                        "Button Click with Properties",
                        properties
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(text = "Track Event with Properties", color = Color.White)
            }

            Button(
                onClick = {
                    sessionEnabled = false
                    analyticsDemoSDK.stopSession() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(contentColor = buttonSessionStartColor)
            ) {
                Text(text = "Stop Session", color = Color.White)
            }

            Button(
                onClick = {
                    analyticsDemoSDK.listEvent()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(text = "Show Tracked Events", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp)) // Add spacing


        }
    }
}


package com.example.lemonade

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

const val DEBUG = true

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}

@Composable
fun LemonApp() {

    var currentStep  by rememberSaveable { mutableIntStateOf(1) }
    var squeezeCount by rememberSaveable { mutableIntStateOf(0) }

    val stepUp:    () -> Unit = { currentStep++ }
    val countDown: () -> Unit = { squeezeCount-- }
    val restart:   () -> Unit = { currentStep = 1 }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        var actionID: Int = 0
        var descriptionID: Int = 0
        var imageID: Int = 0
        var click: () -> Unit = stepUp


        when (currentStep) {
            1 -> {  // Start
                actionID = R.string.tap_lemon_tree
                imageID = R.drawable.lemon_tree
                descriptionID = R.string.lemon_tree_string

                squeezeCount = (1..4).random()
            }
            2 -> {  // Squeeze
                actionID = R.string.tap_lemon
                imageID = R.drawable.lemon_squeeze
                descriptionID = R.string.lemon_string

                // Set click lambda
                click = if (squeezeCount == 1)  stepUp else countDown
            }
            3 -> {  // Serve
                actionID = R.string.glass_of_lemonade
                imageID = R.drawable.lemon_drink
                descriptionID = R.string.glass_of_lemonade
            }
            4 -> {  // Restart
                actionID = R.string.tap_empty_glass
                imageID = R.drawable.lemon_restart
                descriptionID = R.string.empty_glass

                // TODO - use a common stepUp w/ Mod 4?
                click =  restart
            }
            else -> {
                // Nothing to do here
                Log.e("when{} else{}", "Invalid currentStep = $currentStep")
            }
        }

        // Call the myColumn() function to display the step
        MyColumn(
            textID = actionID,
            imageID = imageID,
            descriptionID = descriptionID,
            currentStep = currentStep,
            squeezeCount = squeezeCount,
            click = click
        )

    }  // end of Surface
}  // end of LemonApp()


@Composable
fun MyColumn(
    textID: Int,
    imageID: Int,
    descriptionID: Int,
    currentStep: Int,
    squeezeCount: Int,
    click: () -> Unit)
{


    // "LEMONADE" title bar w/yellow background
    // optional "DEBUG" text
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        // "LEMONADE" title
        TextString(
            textID = R.string.app_name,
            modifier = Modifier.fillMaxWidth()
                               .background(MaterialTheme.colors.primary)
        )

        // DEBUG text
        if (DEBUG)
            TextStringDebug(step = currentStep, count = squeezeCount)
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){

        ImageDrawable(
            imageID = imageID,
            descriptionID = descriptionID,
            click =  click
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextString(textID = textID)

    }  // end of Column
}  // end of myColumn()


@Composable
fun TextString(textID: Int,
               modifier: Modifier = Modifier,
               alignment: TextAlign = TextAlign.Center) {

    Text(
        text = stringResource(textID),
        textAlign = alignment,
        modifier = modifier
    )
}

@Composable
fun TextStringDebug(step: Int, count: Int) {
    when (step) {
        2 -> Text(text = "currentStep = $step : squeezeCount = $count")
        else -> Text(text = "currentStep = $step")
    }
}

@Composable
fun ImageDrawable(imageID: Int,
                  descriptionID: Int,
                  click: () -> Unit)
{
    Image(
        painter = painterResource(imageID),
        contentDescription = stringResource(descriptionID),
        modifier = Modifier.wrapContentSize()
                           .background(MaterialTheme.colors.secondary)
                           .clickable { click() }
    )
}

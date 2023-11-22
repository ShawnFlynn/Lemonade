package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.R.*
import com.example.lemonade.ui.theme.LemonadeTheme

const val DEBUG = true

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Composable
fun LemonadeApp() {

    var currentStep  by rememberSaveable { mutableIntStateOf(1) }
    var squeezeCount by rememberSaveable { mutableIntStateOf(1) }

    val stepUp:    () -> Unit = { if (currentStep == 4) currentStep = 1 else currentStep++ }
    val countDown: () -> Unit = { squeezeCount-- }

    val onClick:   () -> Unit = {
        when (currentStep) {
            2 -> if (squeezeCount == 1) stepUp() else countDown()
            else -> stepUp()
        }
    }

    @StringRes val actionID: Int
    @StringRes val descriptionID: Int
    @DrawableRes val imageID: Int

    // Setup step specific data
    when (currentStep) {
        1 -> {  // Start
            actionID = string.tap_lemon_tree
            imageID = drawable.lemon_tree
            descriptionID = string.lemon_tree_string

            squeezeCount = (1..4).random()
        }
        3 -> {  // Serve
            actionID = string.glass_of_lemonade
            imageID = drawable.lemon_drink
            descriptionID = string.glass_of_lemonade
        }
        4 -> {  // Restart
            actionID = string.tap_empty_glass
            imageID = drawable.lemon_restart
            descriptionID = string.empty_glass
        }
        else -> {  // Squeeze (2)
            actionID = string.tap_lemon
            imageID = drawable.lemon_squeeze
            descriptionID = string.lemon_string
        }
    }

    // Execute composables
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        // MyColumn() function to display the step data
        MyColumn(
            textID = actionID,
            imageID = imageID,
            descriptionID = descriptionID,
            currentStep = currentStep,
            squeezeCount = squeezeCount,
            click = onClick
        )

    }  // end of Surface
}  // end of LemonApp()


@Composable
fun MyColumn(
    @StringRes textID: Int,
    @DrawableRes imageID: Int,
    @StringRes descriptionID: Int,
    currentStep: Int,
    squeezeCount: Int,
    click: () -> Unit
) {

    // "LEMONADE" title bar w/yellow background
    // optional "DEBUG" text
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        // "LEMONADE" title
        TextString(
            textID = string.app_name,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
        )

        // DEBUG text
        if (DEBUG)
            TextStringDebug(step = currentStep, count = squeezeCount)
    }

    // Step action command and clickable image
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){

        ImageDrawable(
            imageID = imageID,
            descriptionID = descriptionID,
            click = click
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextString(textID = textID)

    }  // end of Column
}  // end of myColumn()

@Composable
fun ImageDrawable(
    @DrawableRes imageID: Int,
    @StringRes descriptionID: Int,
    click: () -> Unit)
{
    Image(
        painter = painterResource(imageID),
        contentDescription = stringResource(descriptionID),
        modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colors.secondary)
            .clickable { click() }
    )
}

@Composable
fun TextString(modifier: Modifier = Modifier,
               @StringRes textID: Int = 0,
               textStr: String = "",
               textSize: TextUnit = 20.sp,
               alignment: TextAlign = TextAlign.Center) {

    val textRef: String = if (textStr == "")
        stringResource(id = textID)
    else
        textStr

    Text(
        text = textRef,
        fontSize = textSize,
        textAlign = alignment,
        modifier = modifier
    )
}

@Composable
fun TextStringDebug(step: Int, count: Int) {
    Spacer(modifier = Modifier.height(6.dp))
    TextString(textStr = "currentStep  = $step",
               textSize = 12.sp,
               modifier = Modifier.background(color = colorResource(id = color.currentStep))
    )
    if (step == 2) {
        Spacer(modifier = Modifier.height(6.dp))
        TextString(textStr = "squeezeCount = $count",
                   textSize = 12.sp,
                   modifier = Modifier.background(color = colorResource(id = color.squeezeCount))
        )
    }
}

package com.example.lemonade

import android.os.Bundle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

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

const val DEBUG = true

@Composable
fun LemonApp() {

    var currentStep by rememberSaveable { mutableIntStateOf(1) }
    var squeezeCount by rememberSaveable { mutableIntStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val fieldText: String
        val description: String
        val imagePainter: Painter
        val click: () -> Unit

        when (currentStep) {
            1 -> {  // Start
                fieldText = stringResource(R.string.tap_lemon_tree)
                imagePainter = painterResource(R.drawable.lemon_tree)
                description = stringResource(R.string.lemon_tree_string)
                click = { currentStep++ }
            }
            2 -> {
                squeezeCount = (1..6).random()
                fieldText = stringResource(R.string.tap_lemon)
                imagePainter = painterResource(R.drawable.lemon_squeeze)
                description = stringResource(R.string.lemon_string)
                click = { currentStep++ }
            }
            //TODO - 2+squeezeCount(=0) = 2
            (2+squeezeCount) -> {  // Serve
                fieldText = stringResource(R.string.tap_lemonade)
                imagePainter = painterResource(R.drawable.lemon_drink)
                description = stringResource(R.string.glass_of_lemonade)
                click = { currentStep++ }
            }
            (3+squeezeCount) -> {  // Restart
                fieldText = stringResource(R.string.tap_empty_glass)
                imagePainter = painterResource(R.drawable.lemon_restart)
                description = stringResource(R.string.empty_glass)
                click = { currentStep = 1 }
            }
            else -> {  // Squeeze x:squeezeCount
                fieldText = stringResource(R.string.tap_lemon)
                imagePainter = painterResource(R.drawable.lemon_squeeze)
                description = stringResource(R.string.lemon_string)
                click = { currentStep++ }
            }
        }

        // Call the myColumn() function to display the step
        MyColumn(
            fieldText,
            imagePainter,
            description,
            currentStep,
            squeezeCount,
            click
        )

    }  // end of Surface
}  // end of LemonApp()


@Composable
fun MyColumn(
    fieldText: String,
    imagePainter: Painter,
    description: String,
    currentStep: Int,
    squeezeCount: Int,
    click: () -> Unit)
{

    // "LEMONADE" title bar w/yellow background
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextString(
            fieldText = "LEMONADE",
            modifier = Modifier.fillMaxWidth()
                               .background(Color.Yellow)
        )
        if (DEBUG)
            TextStringDebug(step = currentStep, count = squeezeCount)
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){

        ImageDrawable(
            painter = imagePainter,
            description = description,
            click
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextString(fieldText = fieldText)

    }  // end of Column
}  // end of myColumn()


@Composable
fun TextString(fieldText: String,
               modifier: Modifier = Modifier,
               alignment: TextAlign = TextAlign.Center) {

    Text(
        text = fieldText,
        textAlign = alignment,
        modifier = modifier
    )
}

@Composable
fun TextStringDebug(step: Int, count: Int) {
    val squeezes = step - 2
    when (step) {
        in 2..(count+2) -> Text(text = "currentStep = $step : squeezeCount = $squeezes/$count")
        else -> Text(text = "currentStep = $step")
    }
}

@Composable
fun ImageDrawable(painter: Painter,
                  description: String,
                  click: () -> Unit) {
//TODO: why constant recompose?
    Image(
        painter = painter,
        contentDescription = description,
        modifier = Modifier.wrapContentSize()
                           .clickable { click() }
    )
}

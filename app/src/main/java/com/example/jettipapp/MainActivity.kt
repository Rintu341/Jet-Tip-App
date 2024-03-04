package com.example.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderPositions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettipapp.Widgets.RoundedIconCircle
import com.example.jettipapp.components.CreateSlider
import com.example.jettipapp.components.InputField
import com.example.jettipapp.ui.theme.JetTipAppTheme
import com.example.jettipapp.utils.calculatePerPersonBill
import com.example.jettipapp.utils.calculateTip

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                MyApp {
                        MainContent()
                }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit){ // This is Called Container function
    JetTipAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

//@Preview
@Composable
fun TopHeader(totalPrePerson:Double = 0.0)
{
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
//            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))) // another way to add corner
        color = Color(0xFFA5B7CF)
    ){
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement =  Arrangement.Center
        ){
            val total = "%.2f".format(totalPrePerson) // format up to 2 Decimal value
            Text(text = "Total per person",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = "₹$total",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
    }
}

@Preview
@Composable
fun MainContent()
{
    val totalBillState  = remember{
        mutableStateOf("")
    }
    val eachPersonTotal = remember{
        mutableStateOf(0.0)
    }
    val range = IntRange(start = 1, endInclusive = 10)
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        TopHeader(eachPersonTotal.value)
        Spacer(modifier = Modifier.height(16.dp))
        BillForm(
            totalBillState = totalBillState,
        ){
            eachPersonTotal.value = it
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    range :IntRange = 1..10,
    totalBillState :MutableState<String>,
    onValueChange:(Double) ->Unit ={}
)
{
    val splitValue = remember{
        mutableStateOf(1)
    }

    val validState = remember(totalBillState.value) // Check is inputField is empty  or not
    {
        totalBillState.value.trim().isNotBlank()
    }
    val sliderPosition = remember { mutableStateOf(0.0) }


    val tipState = remember{
        mutableStateOf(0.0)
    }
    val tipValue = "%.1f".format(tipState.value) // change into 1 decimal  digit
    val eachPersonState =  remember{
        mutableStateOf(0.0)
    }

    val keyboardController = LocalSoftwareKeyboardController.current


        Surface(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        )
        {
            Column()
            {
                InputField(valueState = totalBillState,
                    enabled = true,
                    labelId = "Enter Bill",
                    isSingleLine = true,
                    keyboardType = KeyboardType.Number,
                    onAction = KeyboardActions {
                        if (!validState) return@KeyboardActions
//                        onValueChange(totalBillState.value.trim())

                        // if on valueChange is  valid
                        keyboardController?.hide()
                    }
                )
                if (validState) {
                onValueChange(eachPersonState.value)
                    Row(
                        modifier = Modifier.padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "Split",
                            modifier = Modifier.align(
                                alignment = Alignment.CenterVertically
                            )
                        )
                        Spacer(modifier = Modifier.width(140.dp))
                        Row(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            RoundedIconCircle(imageVector = Icons.Filled.Remove) {
                                if (splitValue.value > 1)
                                    splitValue.value -= 1
                                else {
                                    splitValue.value = 1
                                }
                                eachPersonState.value = calculatePerPersonBill(totalBillState.value.toDouble(),splitValue.value.toDouble(),sliderPosition.value)
                            }
                            Text(
                                text = "${splitValue.value}", modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp)
                                    .align(alignment = Alignment.CenterVertically)
                            )
                            RoundedIconCircle(imageVector = Icons.Filled.Add) {
                                if (splitValue.value < range.last) {
                                    splitValue.value += 1
                                }
                                eachPersonState.value = calculatePerPersonBill(totalBillState.value.toDouble(),splitValue.value.toDouble(),sliderPosition.value)

                            }
                        }
                    }
//                Spacer(modifier = Modifier.height(8.dp))
                    //Row Tip
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Tip")
                        Spacer(modifier = Modifier.width(200.dp))

                        Text(text = "₹${tipValue}")
                    }
                    // slider and its percent
                    Column(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text("${sliderPosition.value.toInt()}%")
                        Spacer(modifier = Modifier.height(12.dp))
                        CreateSlider() {
                            sliderPosition.value = it
                            tipState.value = calculateTip(totalBillState.value.toDouble(),sliderPosition.value)
                            eachPersonState.value = calculatePerPersonBill(totalBillState.value.toDouble(),splitValue.value.toDouble(),sliderPosition.value)
                        }
                    }
                } else {
                    Box() {

                    }
                }
            }
        }
    }




@Preview(showBackground = true)
@Composable
fun MyAppPreview()
{
    RoundedIconCircle(){

    }
}


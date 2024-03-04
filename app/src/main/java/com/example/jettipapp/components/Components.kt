package com.example.jettipapp.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettipapp.R

@Composable
fun InputField(
    modifier:Modifier = Modifier,
    valueState: MutableState<String>,
    enabled : Boolean,
    labelId : String,
    isSingleLine:Boolean,
    imeAction: ImeAction = ImeAction.Next,
    onAction:KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Number

)
{
    OutlinedTextField(
        value = valueState.value ,
        onValueChange = {valueState.value = it},
        modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.rupee),
                contentDescription = "Rupee icon",
                modifier = Modifier.size(20.dp)
            )
        },
        label = {
//            Text(text = labelId, color = Color.LightGray)
            Text(text = labelId)
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        keyboardActions = onAction,
        singleLine = isSingleLine
    )
}



@Preview
@Composable
fun CreateSlider(onComplete:(Double) -> Unit = {}) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Slider(
        value = sliderPosition,
        onValueChange = { newValue ->
            sliderPosition = newValue
            onComplete(sliderPosition.toDouble())
        },
        valueRange = 0f..100f,
        steps = 100,
        onValueChangeFinished = {
            // You can use this to trigger some action when the sliding is finished
            onComplete(sliderPosition.toDouble())
        }
    )
}
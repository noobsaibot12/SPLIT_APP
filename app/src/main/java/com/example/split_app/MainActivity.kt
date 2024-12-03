package com.example.split_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.split_app.components.InputField
import com.example.split_app.ui.theme.SPLIT_APPTheme
import com.example.split_app.utils.calculateTotalBillState
import com.example.split_app.utils.calculateTotalTip
import com.example.split_app.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SPLIT_APPTheme {
                AppScaffoldContainer {
                    modifier ->

//                    TopHeader()
                    MainContent(modifier)

                }
            }
        }
    }
}

// Container function encapsulating Scaffold and Greeting composable
@Composable
fun AppScaffoldContainer( content: @Composable ( Modifier ) -> Unit ) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        content( Modifier.padding(innerPadding) )

    }

}

//@Preview
@Composable
fun TopHeader ( totalAmountPerPerson: Double = 0.0 , modifier: Modifier = Modifier ) {

    val color = Color(0xFF9482A7)

    Surface(

        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = color
//            .clip( shape = CircleShape.copy( all = CornerSize( 12.dp ) ) ),
//        shape = RoundedCornerShape( corner = CornerSize( 12.dp ) )


    ) {

        Column (

            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            val total = "%.2f".format( totalAmountPerPerson )

            Text (

                text = "Total per person!!",
                modifier = Modifier,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black

            )
            Spacer(

                modifier = Modifier
                    .height(5.dp)

            )
            Text (

                text = "$$total",
                modifier = Modifier,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color.Black

            )

        }

    }

}

@Preview
@Composable
fun MainContent ( modifier: Modifier = Modifier ) {

    Surface(

        modifier = Modifier
            .fillMaxSize(),

    ) {

        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BillForm() // Call your composable functions here
        }

    }

}

//@Preview
@Composable
fun BillForm ( modifier: Modifier = Modifier , onValChange: (String) -> Unit = {} ) {

    val totalBillState = remember {

        mutableStateOf( "" )

    }
    val validState = remember ( totalBillState.value ) {

        totalBillState.value.trim().isNotEmpty()

    }
    val splitByState = remember {

        mutableStateOf( 1 )

    }
    val sliderPositionState = remember {

        mutableStateOf( 0f )

    }
    var tipPercentage = ( sliderPositionState.value * 100 ).toInt()
    val keyboardController = LocalSoftwareKeyboardController.current
    val color = Color ( 0xFFf89cbc )
    val tipAmountState = remember {

        mutableStateOf(0.0)

    }
    val amountToDisplay = remember {

        mutableStateOf( 0.0 )

    }

    Surface (

        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding( 2.dp ),
        shape = RoundedCornerShape( corner = CornerSize( 2.dp ) ),

    ) {

        Column (

            modifier = Modifier
                .padding( horizontal = 20.dp , vertical = 20.dp ),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            TopHeader( totalAmountPerPerson = amountToDisplay.value )

            InputField(

                valueState = totalBillState,
                labelId = "Enter Bill!!",
                enabled = true,
                isSingleLine = true,
                keyboardActions = KeyboardActions {

                    if ( !validState ) return@KeyboardActions

                    onValChange ( totalBillState.value.trim() )         //Val Sent to onValChange and is hoisted in MainContent Function
                    keyboardController?.hide()

                },
                onValueChange = {

                    newValue ->
                    totalBillState.value = newValue

                    amountToDisplay.value = calculateTotalBillState(

                        totalBill = totalBillState.value.toDouble(),
                        tipPercentage = tipPercentage,
                        splitBy = splitByState.value
                    )

                }

            )

            Spacer( Modifier.height( 10.dp ) )

            if ( validState ) {

                Row (

                    modifier = Modifier
                        .padding( horizontal = 10.dp , vertical = 5.dp )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Text (

                        modifier = Modifier,
                        text = "Split",
                        style = TextStyle(

                            fontSize = 30.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium

                        ),
                        color = Color.White

                    )

                    Row (

                        modifier = Modifier
                            .padding( 2.dp )
                            .width( 200.dp ),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        RoundIconButton(

                            imageVector = Icons.Default.Remove,
                            onClick = {

                                splitByState.value--

                                if ( splitByState.value <= 1 ) splitByState.value = 1

                                amountToDisplay.value = calculateTotalBillState(

                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage,
                                    splitBy = splitByState.value
                                )

                            }

                        )

                        Text(

                            text = "${splitByState.value}",
                            fontSize = 15.sp,
                            color = Color.White

                        )

                        RoundIconButton(

                            imageVector = Icons.Default.Add,
                            onClick = {

                                splitByState.value++

                                amountToDisplay.value = calculateTotalBillState(

                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage,
                                    splitBy = splitByState.value
                                )

                            }

                        )

                    }

                }

                Row (

                    modifier = Modifier
                        .padding( vertical = 10.dp , horizontal = 10.dp )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Text (

                        modifier = Modifier,
                        text = "Tip",
                        style = TextStyle(

                            fontSize = 30.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium

                        ),
                        color = Color.White

                    )

                    Text(

                        modifier = Modifier
                            .width( 200.dp ),
                        textAlign = TextAlign.Center,
                        text = "$ ${tipAmountState.value}",
                        color = Color.White

                    )

                }

                Column (

                    modifier = Modifier
                        .padding( vertical = 10.dp )
                        .fillMaxWidth()
                        .height( 50.dp ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround

                ) {

                    Text(

                        text = "${tipPercentage} %",
                        color = Color.White

                    )

                    Slider(

                        modifier = Modifier
                            .padding( horizontal = 10.dp ),
                        value = sliderPositionState.value ,
                        onValueChange = {

                            newVal ->
                            sliderPositionState.value = newVal

                            tipAmountState.value = calculateTotalTip (

                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage

                            )

                            amountToDisplay.value = calculateTotalBillState(

                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage,
                                splitBy = splitByState.value
                            )

                        }

                    )

                }

            } else {

                amountToDisplay.value = 0.0
                splitByState.value = 1
                tipAmountState.value = 0.0
                sliderPositionState.value = 0F

                Box (



                ) {



                }

            }

        }

    }

}


//@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {
    SPLIT_APPTheme {
        AppScaffoldContainer {

//            TopHeader()
            MainContent()

        }
    }
}

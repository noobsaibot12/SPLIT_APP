package com.example.split_app.components

import android.adservices.adid.AdId
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputField (

    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit

) {

    OutlinedTextField(

        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        value = valueState.value,
        onValueChange = onValueChange,
        label = {

            Text( text = labelId )

        },
        leadingIcon = {

            Icon( imageVector = Icons.Rounded.AttachMoney , contentDescription = "Money Icon!!" )

        },
        singleLine = isSingleLine,
        textStyle = TextStyle(

            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground

        ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(

            keyboardType = keyboardType,
            imeAction = imeAction

        ),
        keyboardActions = keyboardActions,
        colors = OutlinedTextFieldDefaults.colors(

            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black

        )

    )

}
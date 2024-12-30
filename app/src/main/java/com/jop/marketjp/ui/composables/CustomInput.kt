package com.jop.marketjp.ui.composables


import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jop.marketjp.R
import com.jop.marketjp.ui.theme.MarketJPTheme


@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    colorText: Color = Color.Gray,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyleValue: TextStyle? = null,
    @StringRes placeholder: Int? = null,
    labelFontSize: TextUnit = 17.sp,
    @StringRes label: Int? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    suffix: @Composable () -> Unit = {},
    @StringRes supportingText: Int? = null,
    isError: Boolean = false,
    colorContentBackground: Color? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isTextArea: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
){
    Column (
        modifier = modifier
    ){
        if (label != null){
            Text(
                modifier = Modifier.padding(start = 7.dp, bottom = 0.dp),
                text = stringResource(id = label),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = labelFontSize,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        var isFocused by remember { mutableStateOf(false) }
        OutlinedTextField(
            modifier = modifier.onFocusChanged { isFocused = it.isFocused },
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyleValue ?: TextStyle(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorContentBackground ?: MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = colorContentBackground ?: MaterialTheme.colorScheme.surface,
                focusedBorderColor = Color.Gray,//MaterialTheme.colorScheme.background,
                unfocusedBorderColor = Color.LightGray,//MaterialTheme.colorScheme.background,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            ),
            shape = RoundedCornerShape(10.dp),
            enabled = enabled,
            readOnly = readOnly,
//            placeholder = {
//                placeholder?.let {
//                    Text(
//                        text = stringResource(id = placeholder),
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//            },
            trailingIcon = trailingIcon,
            supportingText = {
                supportingText?.let {
                    Text(
                        fontSize = 10.sp,
                        text = stringResource(id = supportingText),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            label = {
                placeholder?.let {
                    Text(
                        text = stringResource(id = placeholder),
                        color = if (isFocused) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground,
                        fontSize = if (isFocused) 12.sp else 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            suffix = suffix,
            isError = isError,
            visualTransformation =  visualTransformation,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            singleLine = !isTextArea,
            maxLines = maxLines,
            interactionSource = interactionSource,
            leadingIcon = leadingIcon
        )
        //CustomSpace(height = 6)
    }
}

@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyleValue: TextStyle? = null,
    placeholder: String? = null,
    labelFontSize: TextUnit = 17.sp,
    @StringRes label: Int? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    suffix: @Composable () -> Unit = {},
    @StringRes supportingText: Int? = null,
    isError: Boolean = false,
    colorContentBackground: Color? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isTextArea: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
){
    Column (
        modifier = modifier
    ){
        if (label != null){
            Text(
                modifier = modifier.padding(start = 7.dp, bottom = 0.dp),
                text = stringResource(id = label),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = labelFontSize,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        var isFocused by remember { mutableStateOf(false) }
        OutlinedTextField(
            modifier = modifier.onFocusChanged { isFocused = it.isFocused },
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyleValue ?: TextStyle(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorContentBackground ?: MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = colorContentBackground ?: MaterialTheme.colorScheme.surface,
                focusedBorderColor = Color.Gray,//MaterialTheme.colorScheme.background,
                unfocusedBorderColor = Color.LightGray,//MaterialTheme.colorScheme.background,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            ),
            shape = RoundedCornerShape(10.dp),
            enabled = enabled,
            readOnly = readOnly,
//            placeholder = {
//                placeholder?.let {
//                    Text(
//                        text = stringResource(id = placeholder),
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//            },
            trailingIcon = trailingIcon,
            supportingText = {
                supportingText?.let {
                    Text(
                        fontSize = 10.sp,
                        text = stringResource(id = supportingText),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            label = {
                placeholder?.let {
                    Text(
                        text = placeholder,
                        color = if (isFocused) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground,
                        fontSize = if (isFocused) 12.sp else 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            suffix = suffix,
            isError = isError,
            visualTransformation =  visualTransformation,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            singleLine = !isTextArea,
            maxLines = maxLines,
            interactionSource = interactionSource,
            leadingIcon = leadingIcon
        )
        //CustomSpace(height = 6)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    var text by remember { mutableStateOf("") }
    MarketJPTheme {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ){
            CustomInput(
                value = text,
                label = R.string.home_tab_product_title,
                placeholder = R.string.app_name,
                onValueChange = {newText -> text = newText })
        }
    }


}


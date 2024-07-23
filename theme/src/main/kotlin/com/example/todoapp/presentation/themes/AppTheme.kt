package com.example.todoapp.presentation.themes

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.presentation.themes.mainTheme.MainTheme

@Composable
fun AppTheme(
    theme: Theme,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val themeColors = if (darkTheme) {
        ColorNames(
            supportSeparator = theme.darkSupportSeparator,
            supportOverlay = theme.darkSupportOverlay,
            labelPrimary = theme.darkLabelPrimary,
            labelSecondary = theme.darkLabelSecondary,
            labelTertiary = theme.darkLabelTertiary,
            labelDisable = theme.darkLabelDisable,
            colorRed = theme.darkRed,
            colorGreen = theme.darkGreen,
            colorBlue = theme.darkBlue,
            colorGray = theme.darkGray,
            colorGreyLight = theme.darkGrayLight,
            colorWhite = theme.darkWhite,
            backPrimary = theme.darkBackPrimary,
            backSecondary = theme.darkBackSecondary,
            backElevated = theme.darkBackElevated,
        )
    } else {
        ColorNames(
            supportSeparator = theme.lightSupportSeparator,
            supportOverlay = theme.lightSupportOverlay,
            labelPrimary = theme.lightLabelPrimary,
            labelSecondary = theme.lightLabelSecondary,
            labelTertiary = theme.lightLabelTertiary,
            labelDisable = theme.lightLabelDisable,
            colorRed = theme.lightRed,
            colorGreen = theme.lightGreen,
            colorBlue = theme.lightBlue,
            colorGray = theme.lightGray,
            colorGreyLight = theme.lightGrayLight,
            colorWhite = theme.lightWhite,
            backPrimary = theme.lightBackPrimary,
            backSecondary = theme.lightBackSecondary,
            backElevated = theme.lightBackElevated,
        )
    }
    CompositionLocalProvider(LocalColorNames provides themeColors) {
        MaterialTheme(
            colors = if (isSystemInDarkTheme()) {
                darkColors(
                    primary = theme.darkLabelPrimary,
                    primaryVariant = theme.darkLabelPrimary,
                    secondary = theme.darkLabelSecondary,
                    secondaryVariant = theme.darkLabelSecondary,
                    background = theme.darkBackPrimary,
                    surface = theme.darkWhite,
                    error = theme.darkRed,
                    onPrimary = Color.Black,
                    onSecondary = Color.Black,
                    onBackground = theme.darkWhite,
                    onSurface = theme.darkWhite,
                    onError = Color.Black,
                )
            } else {
                lightColors(
                    primary = theme.lightLabelPrimary,
                    primaryVariant = theme.lightLabelPrimary,
                    secondary = theme.lightLabelSecondary,
                    secondaryVariant = theme.lightLabelSecondary,
                    background = theme.lightBackPrimary,
                    surface = theme.lightWhite,
                    error = theme.lightRed,
                    onPrimary = theme.lightWhite,
                    onSecondary = Color.Black,
                    onBackground = Color.Black,
                    onSurface = Color.Black,
                    onError = theme.lightWhite,
                )
            },
            content = content
        )
    }
}

val themeColors: ColorNames
    @Composable
    get() = LocalColorNames.current

@Preview(
    showBackground = true,
    widthDp = 2000,
    heightDp = 500
)
@Composable
private fun AppThemeLightPreview(
    theme: Theme = MainTheme
) {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            ColorPreviewElement(
                colorName = "Support [Light] / Separator",
                colorValue = theme.lightSupportSeparator,
                modifier = Modifier.padding(end = 8.dp)
            )
            ColorPreviewElement(
                colorName = "Support [Light] / Overlay",
                colorValue = theme.lightSupportOverlay,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            ColorPreviewElement(
                colorName = "Label [Light] / Primary",
                colorValue = theme.lightLabelPrimary,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Label [Light] / Secondary",
                colorValue = theme.lightLabelSecondary,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Label [Light] / Tertiary",
                colorValue = theme.lightLabelTertiary,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Label [Light] / Disable",
                colorValue = theme.lightLabelDisable,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            ColorPreviewElement(
                colorName = "Color [Light] / Red",
                colorValue = theme.lightRed,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Color [Light] / Green",
                colorValue = theme.lightGreen,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Color [Light] / Blue",
                colorValue = theme.lightBlue,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Color [Light] / Gray",
                colorValue = theme.lightGray,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Color [Light] / Red",
                colorValue = theme.lightGrayLight,
                modifier = Modifier.padding(end = 8.dp)
            )
            ColorPreviewElement(
                colorName = "Color [Light] / White",
                colorValue = theme.lightWhite,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            ColorPreviewElement(
                colorName = "Back [Light] / Primary",
                colorValue = theme.lightBackPrimary,
                modifier = Modifier.padding(end = 8.dp)
            )
            ColorPreviewElement(
                colorName = "Back [Light] / Secondary",
                colorValue = theme.lightBackSecondary,
                modifier = Modifier.padding(end = 8.dp)
            )
            ColorPreviewElement(
                colorName = "Back [Light] / Elevated",
                colorValue = theme.lightBackElevated,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 2000,
    heightDp = 500
)
@Composable
private fun AppThemeDarkPreview(
    theme: Theme = MainTheme
) {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            ColorPreviewElement(
                colorName = "Support [Dark] / Separator",
                colorValue = themeColors.supportSeparator,
                modifier = Modifier.padding(end = 8.dp)
            )
            ColorPreviewElement(
                colorName = "Support [Dark] / Overlay",
                colorValue = theme.darkSupportOverlay,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
        }
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            ColorPreviewElement(
                colorName = "Label [Dark] / Primary",
                colorValue = theme.darkLabelPrimary,
                modifier = Modifier.padding(end = 8.dp)
            )
            ColorPreviewElement(
                colorName = "Label [Dark] / Secondary",
                colorValue = theme.darkLabelSecondary,
                modifier = Modifier.padding(end = 8.dp)
            )
            ColorPreviewElement(
                colorName = "Label [Dark] / Tertiary",
                colorValue = theme.darkLabelTertiary,
                modifier = Modifier.padding(end = 8.dp)
            )
            ColorPreviewElement(
                colorName = "Label [Dark] / Disable",
                colorValue = theme.darkLabelDisable,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            ColorPreviewElement(
                colorName = "Color [Dark] / Red",
                colorValue = theme.darkRed,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Color [Dark] / Green",
                colorValue = theme.darkGreen,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Color [Dark] / Blue",
                colorValue = theme.darkBlue,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Color [Dark] / Gray",
                colorValue = theme.darkGray,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Color [Dark] / Red",
                colorValue = theme.darkGrayLight,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Color [Dark] / White",
                colorValue = theme.darkWhite,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            ColorPreviewElement(
                colorName = "Back [Dark] / Primary",
                colorValue = theme.darkBackPrimary,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Back [Dark] / Secondary",
                colorValue = theme.darkBackSecondary,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
            ColorPreviewElement(
                colorName = "Back [Dark] / Elevated",
                colorValue = theme.darkBackElevated,
                modifier = Modifier.padding(end = 8.dp),
                isTextColorDark = false
            )
        }
    }
}

@Composable
private fun ColorPreviewElement(
    colorName: String,
    colorValue: Color,
    modifier: Modifier = Modifier,
    isTextColorDark: Boolean = true
) {
    val textColor = if (isTextColorDark) {
        Color.Black
    } else {
        Color.White
    }
    Column(
        modifier = modifier
            .background(color = colorValue)
            .width(300.dp)
    ) {
        Text(
            text = colorName,
            color = textColor,
            modifier = Modifier.padding(start = 8.dp, top = 40.dp)
        )
        Text(
            text = colorToHex(colorValue),
            color = textColor,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
        )
    }
}

private fun colorToHex(color: Color): String {
    val alpha = (color.alpha * 255).toInt()
    val red = (color.red * 255).toInt()
    val green = (color.green * 255).toInt()
    val blue = (color.blue * 255).toInt()
    return String.format("#%02X%02X%02X%02X", alpha, red, green, blue)
}
package org.oneui.compose.layout.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha

/**
 * Stores default values for the button specs
 */
object DrawerItemDefaults {

    val margin = PaddingValues(
        horizontal = 12.dp
    )

    val padding = PaddingValues(
        vertical = 13.dp,
        horizontal = 18.dp
    )

    val iconTextSpacing = 15.dp

    const val labelMaxLines = 5

    const val labelEndMaxLines = 1

    val shape = RoundedCornerShape(8.dp)

}

/**
 * Stores the colors that are needed to define a drawer layout
 */
data class DrawerItemColors(

    val ripple: Color

)

/**
 * Constructs the default layout colors
 */
@Composable
fun drawerItemColors(
    ripple: Color = OneUITheme.colors.seslRippleColor
): DrawerItemColors = DrawerItemColors(
    ripple = ripple
)

/**
 * Composable for a oui-style drawer list item
 *
 * @param modifier The modifier to be applied to the item
 * @param icon The icon at the start of the item
 * @param label The title of the item
 * @param labelEnd The title at the end of the item
 * @param onClick The callback called when the tem is clicked
 * @param colors The colors to apply to the item
 * @param interactionSource The used [MutableInteractionSource]
 * @param textStyleLabel The [TextStyle] for the title
 * @param shape The overall shape of the item
 */
@Composable
fun DrawerItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    label: String,
    labelEnd: String? = null,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    colors: DrawerItemColors = drawerItemColors(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    textStyleLabel: TextStyle = with(OneUITheme.types) { if (selected) drawerItemLabelSelected else drawerItemLabel },
    shape: Shape = DrawerItemDefaults.shape
) = Box(
    modifier = modifier
        .fillMaxWidth()
        .clip(
            shape = shape
        )
        .clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(
                color = colors.ripple
            ),
            enabled = enabled,
            role = Role.Button,
            onClick = { onClick?.let { it() } }
        )
        .background(
            color = if (selected) colors.ripple else Color.Transparent //When selected, we add ripple color as background
        )
        .enabledAlpha(enabled)
        .padding(DrawerItemDefaults.margin),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DrawerItemDefaults.padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()

        Spacer(
            modifier = Modifier
                .width(DrawerItemDefaults.iconTextSpacing)
        )

        BasicText(
            text = label,
            style = textStyleLabel,
            overflow = TextOverflow.Ellipsis,
            maxLines = DrawerItemDefaults.labelMaxLines
        )

        Spacer(
            modifier = Modifier
                .weight(1F)
        )

        labelEnd?.let { labelEnd ->
            BasicText(
                text = labelEnd,
                style = OneUITheme.types.drawerItemEndLabel,
                overflow = TextOverflow.Ellipsis,
                maxLines = DrawerItemDefaults.labelEndMaxLines
            )
        }
    }
}
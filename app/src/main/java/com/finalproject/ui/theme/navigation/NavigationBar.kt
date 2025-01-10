package com.finalproject.ui.theme.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import com.finalproject.R


data class DropletButtonModel(
    val icon: Int,
    val contentDescription: String? = "Button",
    val text: String? = null,
    val iconColor: Int = R.color.white,
    val dropletColor: Int = com.finalproject.util.R.color.buttonColor,
    val size: Dp = 20.dp
)

@Composable
fun NavigationBarWithAnimation(
    modifier: Modifier = Modifier,
    containerColor: Color = colorResource(id = com.finalproject.util.R.color.prime),
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    selectedIndex: Int,
    dropletButtons: List<DropletButtonModel>,
    onItemSelected: (Int) -> Unit,
    context: Context
) {


    AnimatedNavigationBar(
        modifier = modifier
            .padding(bottom = 2.dp),
        selectedIndex = selectedIndex,
        barColor = containerColor,
        cornerRadius = shapeCornerRadius(180f),
        ballColor = Color(ContextCompat.getColor(context, com.finalproject.util.R.color.skyBlue)),
        ballAnimation = Parabolic(tween(600)),
        indentAnimation = Height(tween(124))
    ) {
        dropletButtons.forEachIndexed { index, dropletButton ->


            DropletButton(
                modifier = Modifier
                    .defaultMinSize(minWidth = 56.dp, minHeight = 56.dp),
                isSelected = selectedIndex == index,
                onClick = {
                    onItemSelected(index)
                },
                icon = dropletButton.icon,
                contentDescription = dropletButton.contentDescription,
                iconColor = Color(
                    ContextCompat.getColor(
                        context,
                        com.finalproject.util.R.color.skyBlue
                    )
                ),
                dropletColor = Color(
                    ContextCompat.getColor(
                        context,
                        com.finalproject.util.R.color.white
                    )
                ),
                size = dropletButton.size
            )
        }
    }
}


package com.parithidb.ljassignment.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.parithidb.ljassignment.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun LoadImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(R.drawable.ic_placeholder)
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoaded by remember { mutableStateOf(false) }

    // Load image asynchronously
    LaunchedEffect(imageUrl) {
        bitmap = withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        isLoaded = true
    }

    if (bitmap == null) {
        // Show placeholder while loading
        Image(
            painter = placeholder,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        // Fade-in animation for loaded image
        val alpha by animateFloatAsState(targetValue = if (isLoaded) 1f else 0f)
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = null,
            modifier = modifier.alpha(alpha),
            contentScale = ContentScale.Crop
        )
    }
}

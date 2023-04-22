@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package hashem.mousavi.composeslidebook3d

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hashem.mousavi.composeslidebook3d.model.books

@Composable
fun BookSliderScreen() {
    val pagerState = rememberPagerState()
    HorizontalPager(
        state = pagerState,
        pageCount = books.size,
        pageSpacing = 80.dp,
        contentPadding = PaddingValues(horizontal = 80.dp)
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Box(
                modifier = Modifier
                    .padding(all = 10.dp)
                    .fillMaxSize()
                    .shadow(elevation = 10.dp)
                    .background(Color.White)
                    .padding(all = 16.dp)
            ) {
                Text(
                    text = books[page].description,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify
                )
            }
            Image(
                modifier = Modifier
                    .graphicsLayer {
                        transformOrigin =
                            TransformOrigin(pivotFractionX = 0f, pivotFractionY = 0.5f)
                        rotationY = calcAngle(pagerState, page)
                        cameraDistance = 100f
                    }
                    .padding(horizontal = 10.dp)
                    .fillMaxSize(),
                painter = painterResource(id = books[page].imageResId),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun calcAngle(pagerState: PagerState, page: Int): Float {
    val fraction = pagerState.currentPageOffsetFraction
    if (fraction > 0 && page == pagerState.currentPage || (fraction < 0 && page < pagerState.currentPage))
        return -120f * (if (fraction > 0) fraction else 1 + fraction)
    return 0f
}

@Preview
@Composable
fun BookSliderScreenPreview() {
    BookSliderScreen()
}
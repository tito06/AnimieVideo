package com.prabal.animatorlearn.screens

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.prabal.animatorlearn.viewmodels.AnimieDetailViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimieDetail(navController: NavController,id: String, title: String) {
    val animieDetailViewModel: AnimieDetailViewModel = viewModel(
        factory = AnimieDetailViewModelFactory(id)
    )

    val animieDetail by animieDetailViewModel.animieDetail.collectAsState()
    val error by animieDetailViewModel.errorState.collectAsState()
    val loading by animieDetailViewModel.loadingState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent // Makes the TopAppBar transparent
                ),

                modifier = Modifier.background(Color.Transparent),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back",
                            tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        if (loading) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Text("Error: $error")
        } else {
            animieDetail?.let { animieDetail ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFF0D47A1), Color.Black)
                            )
                        )
                        .padding(16.dp)
                        .padding(paddingValues)
                ) {

                    // If trailer is available, show the trailer player first
                    if (!animieDetail.data.trailer.embed_url.isNullOrEmpty()) {
                        VideoPlayer(trailerUrl = animieDetail.data.trailer.embed_url)
                    } else {
                        // If no trailer, display the main image
                        Image(
                            painter = rememberAsyncImagePainter(animieDetail.data.images.jpg.image_url),
                            contentDescription = "Main Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(text = animieDetail.data.title, style = MaterialTheme.typography.titleMedium, color = Color.White)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Synopsis: ${animieDetail.data.synopsis}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Rating: ${animieDetail.data.rating}", style = MaterialTheme.typography.bodyMedium, color = Color.White)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Genres: ${animieDetail.data.genres.joinToString { it.name }}", color = Color.White)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Producers: ${animieDetail.data.producers.joinToString { it.name }}", color = Color.White)
                    }
                }
            } ?: run {
                Text("No details available.", color = Color.White)
            }
        }
    }
}



@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoPlayer(trailerUrl: String) {
    val context = LocalContext.current

    if (trailerUrl.contains("youtube.com") || trailerUrl.contains("youtu.be")) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true

                    val videoId = extractYouTubeVideoId(trailerUrl)
                    if (videoId != null) {
                        loadData(
                            """
                            <!DOCTYPE html>
                            <html>
                            <body style="margin:0;padding:0;height:100%;overflow:hidden;">
                                <iframe 
                                    width="100%" 
                                    height="550" 
                                    src="https://www.youtube.com/embed/$videoId?autoplay=1" 
                                    frame border="0" 
                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                                    allow fullscreen>
                                </iframe>
                            </body>
                            </html>
                            """.trimIndent(),
                            "text/html",
                            "utf-8"
                        )
                    } else {
                        Toast.makeText(ctx, "Invalid YouTube URL", Toast.LENGTH_SHORT).show()
                    }


                }
            }

        )
    } else {
        Text(
            "Invalid YouTube URL or unsupported video format.",
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// Helper function to extract YouTube video ID
fun extractYouTubeVideoId(url: String): String? {
    val regex = "(?<=v=|/videos/|embed/|youtu.be/|/v/|e/|u/\\w/|embed\\?|vi?=|vi/|shorts/)([a-zA-Z0-9_-]{11})"
    val matchResult = Regex(regex).find(url)
    return matchResult?.value
}

package com.finalproject.movie.detail.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import com.finalproject.domain.model.movie.Actor
import com.finalproject.movie.detail.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent



@Composable
fun CastComponent(
    movieName: String
) {
    val actors = getActorsForMovie(movieName)
    var selectedActor by remember { mutableStateOf<Actor?>(null) }
    val imageOptions = ImageOptions(
        contentScale = ContentScale.Crop,
    )

    if (actors.isNotEmpty()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(actors) { actor ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(110.dp)
                        .background(colorResource(id = com.finalproject.util.R.color.prime))
                        .clickable {
                            selectedActor = actor
                        }
                ) {
                    val imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                    )

                    CoilImage(
                        imageModel = { actor.imageUrl },
                        modifier = Modifier
                            .size(66.dp)
                            .padding(top = 2.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape),
                        imageOptions = imageOptions,
                        component = rememberImageComponent {
                            +CircularRevealPlugin(duration = 550)
                        },
                        failure = {
                            Text(text = "Image load failed.")
                        }
                    )


                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = actor.name,
                            fontWeight =FontWeight.Bold,
                            style = MaterialTheme.typography.body2,
                            color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }


                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = actor.roleInMovie,
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }
            }
        }

        selectedActor?.let { actor ->
            ActorDetailDialog(
                actor = actor,
                onDismiss = { selectedActor = null }
            )
        }
    } else {
        Text("No actors found for this movie.", style = MaterialTheme.typography.h6)
    }
}

@Composable
fun ActorDetailDialog(
    actor: Actor,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = colorResource(id = com.finalproject.util.R.color.prime2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoilImage(
                    imageModel = { actor.imageUrl },
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color.Gray, CircleShape),
                    component = rememberImageComponent {
                        +CircularRevealPlugin(duration = 550)
                    },
                    failure = {
                        Text(text = "Image load failed.")
                    }
                )


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = actor.name,
                    color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h5.copy(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rolü: ${actor.roleInMovie}",
                    style = MaterialTheme.typography.body1.copy(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun getActorsForMovie(movieName: String): List<Actor> {
    return when (movieName) {

        "Scarface" -> listOf(
            Actor(1, "Al Pacino", "Tony Montana", R.drawable.scarface1, "JTSoD4BBCJc"),
            Actor(2, "Michelle Pfeiffer", "Elvira", R.drawable.scarface2, "JTSoD4BBCJc"),
            Actor(3, "Steven Bauer", "Manny Ray", R.drawable.scarface3, "JTSoD4BBCJc"),
            Actor(4, "Mary Elizabeth Mastrantonio", "Gina", R.drawable.scarface4, "JTSoD4BBCJc"),
        )
        "12 Monkeys" -> listOf(
            Actor(1, "Bruce Willis", "James Cole", R.drawable.monkey_5, "cv276Wg3e7I"),
            Actor(2, "Brad Pitt", "Jeffrey Goines", R.drawable.monkey_6, "cv276Wg3e7I"),
            Actor(3, "Madeleine Stowe", "Kathryn Railly", R.drawable.monkey_7, "cv276Wg3e7I"),
            Actor(4, "Jon Seda", "Jose", R.drawable.monkey_8, "cv276Wg3e7I"),
        )

        "Batman" -> listOf(
            Actor(1, "Robert Pattinson", "Bruce Wayne", R.drawable.batman_1, "cv276Wg3e7I"),
            Actor(2, "Zoë Kravitz", "Selina Kyle", R.drawable.batman_2, "cv276Wg3e7I"),
            Actor(3, "Jeffrey Wright", "Lt. James Gordon", R.drawable.batman_3, "cv276Wg3e7I"),
            Actor(4, "Colin Farrell", "Oz", R.drawable.batman_4, "cv276Wg3e7I"),
        )

        "Hobbit" -> listOf(
            Actor(1, "Ian McKellen", "Gandalf", R.drawable.hobbit_9, "JTSoD4BBCJc"),
            Actor(2, "Martin Freeman", "Bilbo", R.drawable.hobbit_5, "JTSoD4BBCJc"),
            Actor(3, "Richard Armitage", "Thorin", R.drawable.hobbit_6, "JTSoD4BBCJc"),
            Actor(4, "Ken Stott", "Balin", R.drawable.hobbit_7, "JTSoD4BBCJc"),
        )

        else -> listOf(
            Actor(1, "Aaron Stanford", "James Cole", R.drawable.monkeys_1, "cv276Wg3e7I"),
            Actor(2, "Amanda Schull", "Dr. Cassandra Railly", R.drawable.monkeys_2, "cv276Wg3e7I"),
            Actor(3, "Noah Bean", "Aaron Marker", R.drawable.monkeys_3, "cv276Wg3e7I"),
            Actor(4, "Barbara Sukowa", "Katarina Jones", R.drawable.monkeys_4, "cv276Wg3e7I"),
        )

    }
}

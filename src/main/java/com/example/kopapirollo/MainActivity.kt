package com.example.kopapirollo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kopapirollo.ui.theme.KoPapirOlloTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            KoPapirOlloTheme {
                MaterialTheme {
                    KoPapirOllo()
                }
            }
        }
    }
}

fun iconOf(index: Int): String {
    return when(index) {
        0 -> "🪨"
        1 -> "📜"
        2 -> "✂️"
        else -> "❓"
    }
}

@Composable
fun KoPapirOllo() {
    val Ko = 0
    val Papir = 1
    val Ollo = 2
    var resultString: String by remember { mutableStateOf("") }
    var player by remember { mutableIntStateOf(-1) }
    var computer by remember { mutableIntStateOf(-1) }
    var winamount by remember { mutableIntStateOf(0) }
    var bg by remember { mutableStateOf(Color.White) }

    fun result(win: Boolean) {
        if (win) {
            winamount++
            resultString = "Nyertél!"
            bg = Color.Green
            return
        }
        resultString = "Vesztettél!"
        bg = Color.Red
    }

    fun draw() {
        resultString = "Döntetlen!"
        bg = Color.Gray
    }

    fun play(option: Int) {
        player = option
        computer = Random.nextInt(0, 3)

        if (player == computer) {
            draw()
            return
        }

        when(player) {
            Ko -> result(computer == Ollo)
            Papir -> result(computer == Ko)
            Ollo -> result(computer == Papir)
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(bg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Row {
            Text("Kő - Papír - Olló", fontSize = 24.sp)
        }

        // Buttons
        Row {
            Button(
                onClick = { play(Ko) },
                modifier = Modifier.padding(15.dp)
            ) {
                Text("🪨", fontSize = 24.sp)
            }
            Button(
                onClick = { play(Papir) },
                modifier = Modifier.padding(15.dp)
            ) {
                Text("📜", fontSize = 24.sp)
            }
            Button(
                onClick = { play(Ollo) },
                modifier = Modifier.padding(15.dp)
            ) {
                Text("✂️", fontSize = 24.sp)
            }
        }

        Spacer(Modifier.height(10.dp))

        // Arena
        Row (verticalAlignment = Alignment.CenterVertically) {
            Text(iconOf(player), fontSize = 32.sp, modifier = Modifier.padding(10.dp))
            Text("vs", modifier = Modifier.padding(10.dp))
            Text(iconOf(computer), fontSize = 32.sp, modifier = Modifier.padding(10.dp))
        }

        Spacer(Modifier.height(10.dp))

        // Win/Lose text
        Row {
            Text(resultString, fontSize = 24.sp)
        }

        // Win counter
        Row {
            Text("Nyert játékok: $winamount")
        }
    }
}
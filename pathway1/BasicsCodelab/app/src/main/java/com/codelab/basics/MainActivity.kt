package com.codelab.basics

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            messageCard(name = "Android")
//            BasicsCodelabTheme {
//                messageImageCard(msg = Message(author = "Android", body = "Jetpack Compose"))
//            }
            mutableListOf<Message>().apply {
                add(Message(author = "newJeans", body = "newJean member minji, newJean member minji"))
                add(Message(author = "newJeans", body = "newJean member hyerin, newJean member hyerin"))
                add(Message(author = "newJeans", body = "newJean member hani, newJean member hani"))
                add(Message(author = "newJeans", body = "newJean member daniel mash, newJean member daniel mash"))
                add(Message(author = "newJeans", body = "newJean member hyein, newJean member hyein"))
                conversation(msg = this)
            }
        }
    }
}

@Composable
fun messageCard(name : String) {
    Text(text = "Hello $name")
}

data class Message(val author : String, val body : String)

@Composable
fun messageCard(msg : Message){
    Column {
        Text(text = msg.author)
        Text(text = msg.body)
    }
}

@Composable
fun messageImageCard(msg : Message){

    Row {

        Image(
            painter = painterResource(id = R.drawable.newjeans),
            contentDescription = "newJeans wow",
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .clip(CircleShape)
                .border(1.dp, color = Color.Red, CircleShape),
            contentScale = ContentScale.Crop,
        )
        
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }

        Column (modifier = Modifier.clickable {
            isExpanded = !isExpanded
        }){
            Text(text = msg.author)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.body, maxLines = if (isExpanded) Int.MAX_VALUE else 1)
        }
    }
}

@Composable
fun conversation(msg : List<Message>) {
    LazyColumn {
        items(msg) {
            messageImageCard(msg = it)
        }
    }
}

@Preview(showBackground = true, name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
//    messageCard(name = "world")
//    messageCard(msg = Message(
//        "Lexi",
//        "hey, take a look...."
//    ))
//    messageImageCard(msg = Message(
//        "Lexi",
//        "hey, take a look...."
//    ))
//    BasicsCodelabTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            messageImageCard(msg = Message(author = "Android", body = "Jetpack Compose"))
//        }
//    }

    mutableListOf<Message>().apply {
        add(Message(author = "newJeans", body = "newJean member minji, newJean member minji"))
        add(Message(author = "newJeans", body = "newJean member hyerin, newJean member hyerin"))
        add(Message(author = "newJeans", body = "newJean member hani, newJean member hani"))
        add(Message(author = "newJeans", body = "newJean member daniel mash, newJean member daniel mash"))
        add(Message(author = "newJeans", body = "newJean member hyein, newJean member hyein"))
        conversation(msg = this)
    }
}
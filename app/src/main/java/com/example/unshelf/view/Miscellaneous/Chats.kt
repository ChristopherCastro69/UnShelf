package com.example.unshelf.view.Miscellaneous

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

data class Message(val content: String, val sender: String)

@Composable
fun ChatScreen(messages: List<Message>, onSend: (String) -> Unit) {
    Column {
        ChatList(messages = messages)
        UserInput(onSend = onSend)
    }
}

@Composable
fun ChatList(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            ChatMessage(message)
        }
    }
}

@Composable
fun ChatMessage(message: Message) {
    Text(text = "${message.sender}: ${message.content}", modifier = Modifier.padding(8.dp))
}

@Composable
fun UserInput(onSend: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    onSend(text)
                    text = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Send")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    val sampleMessages = listOf(
        Message("Hello!", "Alice"),
        Message("Hi!", "Bob")
    )
    ChatScreen(messages = sampleMessages, onSend = {})
}

package com.mnashit.lucy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mnashit.lucy.ui.theme.LucyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LucyTheme {
                LucyHomeScreen()
            }
        }
    }
}

@Composable
fun LucyHomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF080D1A)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "LUCY",
                color = Color(0xFF2196F3),
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your Personal AI Assistant",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .background(
                        Color(0xFF101A2C),
                        RoundedCornerShape(100.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "L",
                    color = Color(0xFF00BCD4),
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "Hello Boss!",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Lucy is ready to assist you.",
                color = Color.LightGray,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(35.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                )
            ) {
                Text("ACTIVATE LUCY")
            }
        }
    }
}

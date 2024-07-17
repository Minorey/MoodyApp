package com.example.moodyapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodyapp.R

@Composable
fun MoodyButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Color.White
        )
    }
}

@Composable
fun LoginButton(
    text: String,
    onClick: () -> Unit,
    contentColorValue: Color = Color.White,
    containerColorValue: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(OutlinedTextFieldDefaults.MinWidth)
            .height(46.dp)
            .padding(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColorValue,
            contentColor = contentColorValue,
        )
    ) {
        Text(
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            text = text,
            fontSize = 18.sp,
            color = contentColorValue,
        )
    }
}

@Composable
fun GoogleButton(
    text: String,
) {
    Button(
        onClick = {
        },
        modifier = Modifier
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
            .width(OutlinedTextFieldDefaults.MinWidth)
            .height(46.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
        )
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo_google),
            contentDescription = "GoogleLogo",
            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
        )
        Text(
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            text = text,
            modifier = Modifier.padding(0.dp)
        )
    }
}

@Composable
fun LinkButton(
    text: String,
    onClick: () -> Unit,
    contentColorValue: Color = MaterialTheme.colorScheme.secondary,
) {
    TextButton(
        onClick = onClick,
        Modifier.padding(0.dp)
    ) {
        Text(
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            text = text,
            color = contentColorValue,
        )
    }
}

@Composable
fun ProfileButton(
    text: String,
    onClick: () -> Unit,
    icon: ImageVector,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(size = 10.dp),
        modifier = Modifier
            .padding(20.dp, 15.dp)//Margin
            .fillMaxWidth()
            .height(70.dp)
            .border(3.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(size = 10.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "iconMyProfile",
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.tertiary
            )
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowRight,
                contentDescription = "ArrowRight",
                tint= MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

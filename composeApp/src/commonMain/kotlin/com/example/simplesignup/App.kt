package com.example.simplesignup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import simplesignup.composeapp.generated.resources.Res
import simplesignup.composeapp.generated.resources.ic_eye_closed
import simplesignup.composeapp.generated.resources.ic_eye_open
import simplesignup.composeapp.generated.resources.ic_checkbox_checked
import simplesignup.composeapp.generated.resources.ic_checkbox_unchecked
import simplesignup.composeapp.generated.resources.ic_emoji
import simplesignup.composeapp.generated.resources.ic_microphone
import simplesignup.composeapp.generated.resources.ic_signal_bars
import simplesignup.composeapp.generated.resources.ic_wifi
import simplesignup.composeapp.generated.resources.ic_battery

@Composable
@Preview
fun App() {
    MaterialTheme {
        SignUpScreenContent()
    }
}

@Composable
private fun IOSStatusBar(
    time: String = "9:41"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(Color.White)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Time display
        Text(
            text = time,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1F2024),
            letterSpacing = (-0.165).sp
        )
        
        // Status indicators
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Signal bars
            Image(
                painter = painterResource(Res.drawable.ic_signal_bars),
                contentDescription = "Signal strength",
                modifier = Modifier.size(17.dp, 11.dp)
            )
            
            // Wi-Fi
            Image(
                painter = painterResource(Res.drawable.ic_wifi),
                contentDescription = "Wi-Fi",
                modifier = Modifier.size(19.dp, 17.dp)
            )
            
            // Battery
            Image(
                painter = painterResource(Res.drawable.ic_battery),
                contentDescription = "Battery",
                modifier = Modifier.size(29.dp, 20.dp)
            )
        }
    }
}

@Composable
private fun FieldTitle(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF2F3036)
    )
}

@Composable
private fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    visible: Boolean,
    onToggleVisible: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        FieldTitle(label)
        Spacer(modifier = Modifier.height(8.dp)) // 8dp gap between title and field (Figma measurement)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, fontSize = 14.sp, color = Color(0xFF8F9098)) },
            singleLine = true,
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onToggleVisible() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(if (visible) Res.drawable.ic_eye_open else Res.drawable.ic_eye_closed),
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier.size(16.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF006FFD),
                unfocusedBorderColor = Color(0xFFC5C6CC)
            ),
            shape = RoundedCornerShape(12.dp),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = Color(0xFF1F2024)
            )
        )
    }
}

@Composable
private fun TextFieldBlock(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isFocused: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        FieldTitle(label)
        Spacer(modifier = Modifier.height(8.dp)) // 8dp gap between title and field (Figma measurement)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, fontSize = 14.sp, color = Color(0xFF8F9098)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isFocused || value.isNotEmpty()) Color(0xFF006FFD) else Color(0xFFC5C6CC),
                unfocusedBorderColor = if (isFocused || value.isNotEmpty()) Color(0xFF006FFD) else Color(0xFFC5C6CC)
            ),
            shape = RoundedCornerShape(12.dp),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = Color(0xFF1F2024)
            )
        )
    }
}

@Composable
private fun TermsRow(accepted: Boolean, onToggle: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Image(
            painter = painterResource(if (accepted) Res.drawable.ic_checkbox_checked else Res.drawable.ic_checkbox_unchecked),
            contentDescription = "Terms and conditions checkbox",
            modifier = Modifier
                .size(24.dp)
                .clickable { onToggle() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = Color(0xFF71727A))) {
                    append("I've read and agree with the ")
                }
                withStyle(SpanStyle(color = Color(0xFF006FFD), fontWeight = FontWeight.SemiBold)) {
                    append("Terms and Conditions")
                }
                withStyle(SpanStyle(color = Color(0xFF71727A))) {
                    append(" and the ")
                }
                withStyle(SpanStyle(color = Color(0xFF006FFD), fontWeight = FontWeight.SemiBold)) {
                    append("Privacy Policy")
                }
                withStyle(SpanStyle(color = Color(0xFF71727A))) {
                    append(".")
                }
            },
            fontSize = 12.sp,
            color = Color(0xFF71727A),
            lineHeight = 16.sp,
            modifier = Modifier.weight(1f)
        )
    }
}



@Composable
private fun KeyboardRow(
    keys: List<String>,
    keyWidth: Int,
    modifier: Modifier = Modifier,
    onKeyClick: (String) -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        keys.forEachIndexed { index, key ->
            KeyboardKey(
                text = key,
                modifier = Modifier
                    .width(keyWidth.dp)
                    .height(43.dp),
                onClick = { onKeyClick(key) }
            )
            if (index < keys.size - 1) {
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}

@Composable
private fun IOSKeyboard(
    onKeyClick: (String) -> Unit = {},
    onBackspace: () -> Unit = {},
    onReturn: () -> Unit = {},
    onSpace: () -> Unit = {},
    onEmojiClick: () -> Unit = {},
    onMicClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(293.dp)
            .background(Color(0xFFD4D6DD))
    ) {
        // Alphabet section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(169.dp)
                .padding(horizontal = 5.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            // Top row: QWERTYUIOP
            KeyboardRow(
                keys = listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
                keyWidth = 31,
                onKeyClick = onKeyClick
            )
            
            // Middle row: ASDFGHJKL
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                KeyboardRow(
                    keys = listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
                    keyWidth = 32,
                    onKeyClick = onKeyClick
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            
            // Bottom row: Shift + ZXCVBNM + Backspace
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Shift key
                KeyboardKey(
                    text = "⇧",
                    modifier = Modifier
                        .width(42.dp)
                        .height(43.dp),
                    backgroundColor = Color.White
                )
                
                Spacer(modifier = Modifier.width(13.dp))
                
                // Letters ZXCVBNM
                KeyboardRow(
                    keys = listOf("Z", "X", "C", "V", "B", "N", "M"),
                    keyWidth = 32,
                    onKeyClick = onKeyClick
                )
                
                Spacer(modifier = Modifier.width(13.dp))
                
                // Backspace key
                KeyboardKey(
                    text = "⌫",
                    modifier = Modifier
                        .width(42.dp)
                        .height(42.dp),
                    backgroundColor = Color(0xFFC5C6CC),
                    onClick = onBackspace
                )
            }
        }
        
        // Controller section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(124.dp)
                .padding(horizontal = 3.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Control buttons row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(43.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 123 key
                KeyboardKey(
                    text = "123",
                    modifier = Modifier
                        .width(87.dp)
                        .height(43.dp),
                    backgroundColor = Color(0xFFC5C6CC),
                    fontSize = 16
                )
                
                Spacer(modifier = Modifier.width(6.dp))
                
                // Space key
                KeyboardKey(
                    text = "space",
                    modifier = Modifier
                        .width(182.dp)
                        .height(43.dp),
                    backgroundColor = Color.White,
                    fontSize = 16,
                    onClick = onSpace
                )
                
                Spacer(modifier = Modifier.width(6.dp))
                
                // Return key
                KeyboardKey(
                    text = "return",
                    modifier = Modifier
                        .width(87.dp)
                        .height(43.dp),
                    backgroundColor = Color(0xFF006FFD),
                    textColor = Color.White,
                    fontSize = 16,
                    onClick = onReturn
                )
            }
            
            Spacer(modifier = Modifier.height(18.dp))
            
            // Emoji and Mic buttons row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Emoji button
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable { onEmojiClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_emoji),
                        contentDescription = "Emojis",
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                // Mic button
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable { onMicClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_microphone),
                        contentDescription = "Microphone",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Home indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(133.dp)
                        .height(6.dp)
                        .background(
                            color = Color(0xFF1F2024),
                            shape = RoundedCornerShape(3.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun KeyboardKey(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    textColor: Color = Color(0xFF1F2024),
    fontSize: Int = 24,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            fontWeight = if (fontSize == 24) FontWeight.Light else FontWeight.Normal,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SignUpScreenContent() {
    var name by remember { mutableStateOf("Luc") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var termsAccepted by remember { mutableStateOf(false) }
    var currentField by remember { mutableStateOf("name") } // Track which field is focused

    // Keyboard input handlers
    val handleKeyClick: (String) -> Unit = { key ->
        when (currentField) {
            "name" -> name += key.lowercase()
            "email" -> email += key.lowercase()
            "password" -> password += key.lowercase()
            "confirmPassword" -> confirmPassword += key.lowercase()
        }
    }
    
    val handleBackspace: () -> Unit = {
        when (currentField) {
            "name" -> if (name.isNotEmpty()) name = name.dropLast(1)
            "email" -> if (email.isNotEmpty()) email = email.dropLast(1)
            "password" -> if (password.isNotEmpty()) password = password.dropLast(1)
            "confirmPassword" -> if (confirmPassword.isNotEmpty()) confirmPassword = confirmPassword.dropLast(1)
        }
    }
    
    val handleSpace: () -> Unit = {
        when (currentField) {
            "name" -> name += " "
            "email" -> email += " "
            "password" -> password += " "
            "confirmPassword" -> confirmPassword += " "
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // iOS Status Bar
        IOSStatusBar()
        
        // Main content with padding (starts at y: 44 + 24 = 68dp from top)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Header text section (y: 68dp from screen top)
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Sign up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1F2024),
                    letterSpacing = 0.08.sp
                )
                Spacer(modifier = Modifier.height(8.dp)) // 8dp gap (y: 95 - 87 = 8dp)
                Text(
                    text = "Create an account to get started ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF71727A),
                    letterSpacing = 0.12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp)) // 24dp gap (y: 135 - 111 = 24dp)

            // Form fields (y: 135dp from screen top)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp) // 16dp between each field (87-71=16)
            ) {
                Box(modifier = Modifier.clickable { currentField = "name" }) {
                    TextFieldBlock(
                        label = "Name",
                        value = name,
                        onValueChange = { name = it },
                        placeholder = "Enter your name",
                        isFocused = currentField == "name"
                    )
                }

                Box(modifier = Modifier.clickable { currentField = "email" }) {
                    TextFieldBlock(
                        label = "Email Address",
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "name@email.com",
                        isFocused = currentField == "email"
                    )
                }

                Box(modifier = Modifier.clickable { currentField = "password" }) {
                    PasswordField(
                        label = "Password",
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Create a passaword",
                        visible = passwordVisible,
                        onToggleVisible = { passwordVisible = !passwordVisible }
                    )
                }

                Box(modifier = Modifier.clickable { currentField = "confirmPassword" }) {
                    PasswordField(
                        label = "Confirm Password",
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = "Confirm password",
                        visible = confirmPasswordVisible,
                        onToggleVisible = { confirmPasswordVisible = !confirmPasswordVisible }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp)) // 24dp gap to terms (y: 468 - 444 = 24dp)

            TermsRow(accepted = termsAccepted) { termsAccepted = !termsAccepted }
        }
        
        Spacer(modifier = Modifier.height(19.dp)) // 19dp gap to keyboard (y: 519 - 500 = 19dp)
        
        // iOS Keyboard (y: 519dp from screen top)
        IOSKeyboard(
            onKeyClick = handleKeyClick,
            onBackspace = handleBackspace,
            onSpace = handleSpace,
            onReturn = {
                // Move to next field or submit
                currentField = when (currentField) {
                    "name" -> "email"
                    "email" -> "password"
                    "password" -> "confirmPassword"
                    else -> "name"
                }
            },
            onEmojiClick = {
                // Add emoji functionality here
                when (currentField) {
                    "name" -> name += "😊"
                    "email" -> email += "😊"
                    "password" -> password += "😊"
                    "confirmPassword" -> confirmPassword += "😊"
                }
            },
            onMicClick = {
                // Add microphone functionality here
                // This could trigger voice input or other mic-related features
                when (currentField) {
                    "name" -> name += "🎤"
                    "email" -> email += "🎤"
                    "password" -> password += "🎤"
                    "confirmPassword" -> confirmPassword += "🎤"
                }
            }
        )
    }
}
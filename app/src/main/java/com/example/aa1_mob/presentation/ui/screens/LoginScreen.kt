package com.example.aa1_mob.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aa1_mob.R
import com.example.aa1_mob.viewmodel.AppViewModelProvider
import com.example.aa1_mob.viewmodel.LoginViewModel
import com.example.aa1_mob.ui.theme.Aa1mobTheme
import com.example.aa1_mob.ui.theme.VoeBlueLight // Importe as cores que você definiu
import com.example.aa1_mob.ui.theme.VoeYellow
import com.example.aa1_mob.ui.theme.VoeTextBlack
import com.example.aa1_mob.ui.theme.VoeTextGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            onLoginSuccess()
            viewModel.resetLoginState()
        }
    }

    // A cor de fundo principal será o branco puro, e o gradiente estará em uma caixa sobre ele
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fundo branco puro
    ) {
        // Topo com gradiente azul e forma orgânica
        // Para a forma orgânica, precisaríamos de um custom shape ou uma imagem SVG.
        // Por enquanto, vamos simular com um gradiente e uma forma simples.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f) // Ocupa 40% da altura para o topo
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(VoeBlueLight, Color.White), // Gradiente do azul claro para o branco
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
            // Para a forma curva, é mais complexo. Você pode usar um Path ou uma imagem SVG.
            // Como alternativa, podemos fazer uma borda inferior curva se você tiver uma imagem
            // de fundo para o topo que já contenha a curva.
            // Por simplicidade, vamos fazer uma forma retangular com gradiente por agora.
            // Se a curva for crucial, avise para explorarmos CustomPainter ou Modifier.clip(CustomShape).
        )

        // Conteúdo centralizado
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Ajustado para Top
        ) {
            Spacer(modifier = Modifier.height(100.dp)) // Espaço para o gradiente de fundo

            Text(
                text = "JobTree",
                style = MaterialTheme.typography.headlineLarge.copy( // Usando headlineLarge para título
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp // Aumente o tamanho da fonte para se parecer com o Figma
                ),
                color = VoeTextBlack,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.jobtree_description), // Nova string no strings.xml
                style = MaterialTheme.typography.bodyLarge,
                color = VoeTextGrey,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(64.dp)) // Mais espaço antes dos campos

            // Campos de texto com fundo branco, cantos arredondados e elevação/sombra
            // Use Card ou Surface para dar a elevação e a forma arredondada
            Card(
                shape = RoundedCornerShape(16.dp), // Cantos arredondados
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Sombra
                colors = CardDefaults.cardColors(containerColor = Color.White), // Fundo branco
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // Espaçamento lateral do Card
            ) {
                Column(modifier = Modifier.padding(16.dp)) { // Padding dentro do Card
                    OutlinedTextField(
                        value = email,
                        onValueChange = viewModel::onEmailChange,
                        label = { Text(stringResource(id = R.string.email_label)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = errorMessage != null && email.isBlank(),
                        colors = OutlinedTextFieldDefaults.colors( // Remover bordas e cores padrão
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedBorderColor = Color.Transparent, // Sem borda
                            unfocusedBorderColor = Color.Transparent, // Sem borda
                            errorBorderColor = MaterialTheme.colorScheme.error // Manter borda de erro
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = viewModel::onPasswordChange,
                        label = { Text(stringResource(id = R.string.password_label)) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = errorMessage != null && password.isBlank(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff
                            val description = if (passwordVisible) stringResource(id = R.string.hide_password) else stringResource(id = R.string.show_password)

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, description)
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors( // Remover bordas e cores padrão
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedBorderColor = Color.Transparent, // Sem borda
                            unfocusedBorderColor = Color.Transparent, // Sem borda
                            errorBorderColor = MaterialTheme.colorScheme.error // Manter borda de erro
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Botão Login
            Button(
                onClick = viewModel::login,
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = VoeYellow), // Cor amarela do Figma
                shape = RoundedCornerShape(16.dp), // Cantos arredondados
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // Altura fixa para o botão
                    .padding(horizontal = 16.dp) // Para alinhar com o Card acima
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = VoeTextBlack // Cor do spinner sobre o botão amarelo
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.login_button),
                        color = VoeTextBlack, // Cor do texto do botão
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Texto "Não tem uma conta?"
            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = stringResource(id = R.string.dont_have_account_register),
                    color = MaterialTheme.colorScheme.secondary // Ou outra cor que combine
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Aa1mobTheme {
        LoginScreen(onLoginSuccess = {}, onNavigateToRegister = {})
    }
}
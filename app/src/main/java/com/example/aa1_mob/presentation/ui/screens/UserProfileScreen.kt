package com.example.aa1_mob.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aa1_mob.R // Certifique-se de que R está correto
import com.example.aa1_mob.ui.theme.Aa1mobTheme
import com.example.aa1_mob.ui.theme.VoeBlueLight // Para o gradiente
import com.example.aa1_mob.ui.theme.VoeTextBlack
import com.example.aa1_mob.ui.theme.VoeTextGrey
import com.example.aa1_mob.viewmodel.AppViewModelProvider
import com.example.aa1_mob.viewmodel.UserProfileViewModel // Importe o UserProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onBack: () -> Unit,
    viewModel: UserProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.profile_title)) }, // Nova string
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.back_button_desc))
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Fundo do topo (similar ao LoginScreen)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Altura para o topo
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(VoeBlueLight, Color.White),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (errorMessage != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center                    )
                }
            } else if (userProfile != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(60.dp)) // Espaço para o topo

                    // Card do Perfil do Usuário
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Ícone/Avatar do usuário
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = stringResource(id = R.string.profile_picture_desc), // Nova string
                                modifier = Modifier
                                    .size(96.dp)
                                    .clip(CircleShape)
                                    .background(VoeBlueLight.copy(alpha = 0.3f))
                                    .padding(16.dp),
                                tint = VoeTextBlack
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = userProfile!!.user.nome,
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = VoeTextBlack
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = userProfile!!.user.email,
                                style = MaterialTheme.typography.bodyLarge,
                                color = VoeTextGrey
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Título das Vagas Aplicadas
                    Text(
                        text = stringResource(id = R.string.applied_jobs_title), // Nova string
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = VoeTextBlack,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    // Lista de Vagas Aplicadas
                    if (userProfile!!.jobs.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.no_applied_jobs), // Nova string
                            style = MaterialTheme.typography.bodyMedium,
                            color = VoeTextGrey,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(userProfile!!.jobs) { job ->
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = job.titulo,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = VoeTextBlack
                                        )
                                        Text(
                                            text = job.empresa,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = VoeTextGrey
                                        )
                                        Text(
                                            text = job.localizacao,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = VoeTextGrey
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Caso não haja dados ou algum erro não capturado
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(id = R.string.profile_load_error), // Nova string
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    Aa1mobTheme {
        UserProfileScreen(onBack = {})
    }
}
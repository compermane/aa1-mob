package com.example.aa1_mob.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aa1_mob.repository.room.models.Job
import com.example.aa1_mob.viewmodel.AppViewModelProvider
import com.example.aa1_mob.viewmodel.JobViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.aa1_mob.ui.theme.VoeTextBlack
import com.example.aa1_mob.ui.theme.VoeYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    jobId: Int,
    onBack: () -> Unit,
    jobViewModel: JobViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var job by remember { mutableStateOf<Job?>(null)}

    LaunchedEffect(jobId) {
        job = jobViewModel.findById(jobId)
    }

    if (job == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.align(Alignment.Start)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = job!!.titulo,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = job!!.empresa,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Descrição da Vaga",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = job!!.descricao,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Localização",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = job!!.localizacao,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            // implementar ação de aplicar para a vaga
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = VoeYellow)
                    ) {
                        Text(
                            text  = "Aplicar",
                            color = VoeTextBlack
                            )
                    }
                }
            }
        }
    }
}
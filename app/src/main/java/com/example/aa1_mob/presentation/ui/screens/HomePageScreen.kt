package com.example.aa1_mob.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person // <--- IMPORTANTE: Adicione esta importação!
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton // <--- IMPORTANTE: Adicione esta importação!
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold // <--- IMPORTANTE: Adicione esta importação!
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar // <--- IMPORTANTE: Adicione esta importação!
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource // <--- Importe para stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.aa1_mob.R // <--- Certifique-se de que R está importado
import com.example.aa1_mob.repository.room.models.Job
import com.example.aa1_mob.ui.theme.Aa1mobTheme
import com.example.aa1_mob.ui.theme.VoeBlueLight
import com.example.aa1_mob.ui.theme.VoeTextBlack
import com.example.aa1_mob.ui.theme.VoeTextGrey
import com.example.aa1_mob.viewmodel.AppViewModelProvider
import com.example.aa1_mob.viewmodel.JobViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    jobViewModel: JobViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    //LaunchedEffect(Unit) {
      //  jobViewModel.insertSampleJobs()
    //}

    // <--- INÍCIO DA REESTRUTURAÇÃO COM SCAFFOLD ---
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "JobTree", // Ou stringResource(id = R.string.app_name)
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = VoeTextBlack
                    )
                },
                actions = {
                    // <--- Botão de perfil aqui
                    IconButton(onClick = { navController.navigate("userProfile") }) {
                        Icon(
                            imageVector = Icons.Default.Person, // Ícone de pessoa
                            contentDescription = stringResource(id = R.string.profile_title), // Use a string resource
                            tint = VoeTextBlack // Cor do ícone
                        )
                    }
                },
                // Cores do TopAppBar para combinar com o tema
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VoeBlueLight, // Cor de fundo do TopAppBar
                    titleContentColor = VoeTextBlack,
                    actionIconContentColor = VoeTextBlack
                )
            )
        }
    ) { innerPadding -> // <--- innerPadding é crucial aqui
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding) // Aplica o padding do Scaffold
        ) {
            // O gradiente de fundo agora pode ser menor ou estilizado para complementar o TopAppBar
            // Você pode ajustar a altura ou remover se o TopAppBar já tiver o visual desejado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f) // Reduzido um pouco a altura do gradiente
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(VoeBlueLight, Color.White),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp), // Padding horizontal para o conteúdo
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Removido Spacer(modifier = Modifier.height(64.dp)) pois o TopAppBar já ocupa espaço

                // O título "JobTree" já está na TopAppBar, você pode removê-lo daqui
                // ou ajustá-lo se quiser um título secundário aqui.
                // Vou manter a descrição, mas você pode mudar o texto para algo como "Vagas Populares"

                Text(
                    text = "Encontre sua próxima oportunidade!", // Ou stringResource(id = R.string.homepage_tagline)
                    style = MaterialTheme.typography.bodyLarge,
                    color = VoeTextGrey,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp) // Ajustado padding
                )

                // Spacer(modifier = Modifier.height(32.dp)) // Este spacer pode ser ajustado ou removido

                JobList(jobViewModel, navController)
            }
        }
    }
    // <--- FIM DA REESTRUTURAÇÃO COM SCAFFOLD ---
}

// ... (Restante do código de SearchBar, JobCard, JobList permanece o mesmo) ...

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onSearch(it)
            Log.i("SearchBar", "Searching for $it")
        },
        placeholder = {
            Text(
                text = "Buscar vagas",
                color = Color.Gray
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(21.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor   = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor  = Color.White,
            errorContainerColor     = Color.White,

            focusedLabelColor   = Color.Black,
            unfocusedLabelColor = Color.Gray,
            disabledLabelColor  = Color.LightGray,
            errorLabelColor     = Color.Red,

            focusedPlaceholderColor   = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            disabledPlaceholderColor  = Color.LightGray,
            errorPlaceholderColor     = Color.Red,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        }
    )
}

@Composable
fun JobCard(job : Job, onClick : () -> Unit) {
    Card(
        shape     = RoundedCornerShape(16.dp),
        modifier  = Modifier
            .fillMaxWidth()
            .clickable{ onClick() },
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = job.titulo, style = MaterialTheme.typography.titleLarge)
            Text(text = job.empresa, style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = job.descricao, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun JobList(viewModel: JobViewModel, navController: NavController) {
    val jobList by viewModel.filteredJobs.collectAsState()

    Spacer(
        modifier = Modifier.padding(0.dp, 21.dp)
    )
    Column {
        SearchBar { query ->
            viewModel.onSearchQueryChanged(query)
        }
    }
    Spacer(
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 21.dp)
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(jobList) { job ->
            JobCard(job = job, onClick = {
                Log.i("JobList", "clicked")
                navController.navigate("jobdetail/${job.idJob}")
            })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePageScreenPreview() {
    Aa1mobTheme {
        val navController = rememberNavController()
        HomePageScreen(navController = navController)
    }
}
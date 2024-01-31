package th.ac.kku.cis.lab05_api

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import th.ac.kku.cis.lab05_api.ui.theme.Lab05apiTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab05apiTheme {
                PokemonApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonApp(pokemonViewModel: PokemonViewModel = viewModel()) {

    val pokemonList by pokemonViewModel.pokemonList.observeAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Pokemon")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    ){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            items(pokemonList){
                item: Pokemon ->  PokemonItem(item, onClick = {})
            }

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonItem(
    pokemon:Pokemon,
    onClick:() -> Unit
){
    var context = LocalContext.current
    var imageUrl:String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    var pokemonId:List<String> = pokemon.url.split('/')
    var pokemonImage:String = imageUrl + pokemonId[pokemonId.size - 2] + ".png"
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        onClick = {
                  Toast.makeText(context, pokemon.name, Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .size(width = 100.dp, height = 100.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(14.dp).fillMaxWidth(),
        ) {
            AsyncImage(
                model = pokemonImage,
                contentDescription = "Translated description of what the image contains"
            )
            Text(text = pokemon.name)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Lab05apiTheme {
        PokemonApp()
    }
}
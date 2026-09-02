package com.example.tallerlayoutsylistas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.tallerlayoutsylistas.R
import com.example.tallerlayoutsylistas.data.remote.model.User
import com.example.tallerlayoutsylistas.util.launchDialer

@Composable
fun DetailScreen(
    user: User,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = user.image,
            contentDescription = stringResource(R.string.foto_de, user.firstName),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
        )

        Text(
            text = stringResource(
                R.string.nombre_completo,
                user.firstName,
                user.lastName
            ),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)
        )

        DetailRow(label = stringResource(R.string.empresa), value = user.company.name)
        DetailRow(label = stringResource(R.string.cargo), value = user.role)
        DetailRow(label = stringResource(R.string.email), value = user.email)
        DetailRow(
            label = stringResource(R.string.telefono),
            value = user.phone,
            onClick = { launchDialer(context, user.phone) }
        )
        DetailRow(label = stringResource(R.string.genero), value = user.gender)
        DetailRow(label = stringResource(R.string.nacimiento), value = user.birthDate)
        DetailRow(label = stringResource(R.string.color_de_ojos), value = user.eyeColor)
        DetailRow(label = stringResource(R.string.universidad), value = user.university)

    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick)
                else Modifier
            )
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    HorizontalDivider()
}

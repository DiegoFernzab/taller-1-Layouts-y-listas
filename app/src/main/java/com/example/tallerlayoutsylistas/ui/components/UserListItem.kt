package com.example.tallerlayoutsylistas.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.tallerlayoutsylistas.data.remote.model.User

@Composable
fun UserListItem(
    user: User,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier.clickable(onClick = onClick),
        leadingContent = {
            AsyncImage(
                model = user.image,
                contentDescription = "Foto de ${user.firstName}",
                modifier = Modifier.size(48.dp)
            )
        },
        headlineContent = {
            Text(
                text = "${user.firstName} ${user.lastName}",
                color = MaterialTheme.colorScheme.primary
            )
        },
        supportingContent = {
            Text(text = user.company.name)
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }
    )
}

package com.roamguard.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToWhitelist: () -> Unit,
    onNavigateToNetworkScan: () -> Unit,
    onNavigateToSettings: () -> Unit = {}
) {
    val homeCountry by viewModel.homeCountry.collectAsState()
    val isServiceEnabled by viewModel.isServiceEnabled.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()
    val dialogCountry by viewModel.dialogCountry.collectAsState()
    val dialogNetworkMcc by viewModel.dialogNetworkMcc.collectAsState()

    if (showDialog) {
        RoamingConfirmationDialog(
            country = dialogCountry,
            onAllow = { alwaysAllow ->
                viewModel.allowNetwork(alwaysAllow, dialogNetworkMcc)
            },
            onDeny = viewModel::denyNetwork,
            onDismiss = viewModel::dismissDialog
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Home, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Home Country",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = homeCountry?.let { "${it.countryName} (MCC: ${it.mcc})" }
                        ?: "Not set",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Shield, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Roaming Protection",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Switch(
                        checked = isServiceEnabled,
                        onCheckedChange = { viewModel.toggleService() }
                    )
                }
            }
        }

        Button(
            onClick = viewModel::checkRoaming,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check Roaming Status")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onNavigateToWhitelist,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)
                Text("  Whitelist")
            }

            OutlinedButton(
                onClick = onNavigateToNetworkScan,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Language, contentDescription = null)
                Text("  Networks")
            }
        }

        Button(
            onClick = onNavigateToSettings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Settings, contentDescription = null)
            Text("  Settings")
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "System Access",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                val rootAvailable = try {
                    Class.forName("com.roamguard.root.RootHelper")
                    true
                } catch (e: Exception) { false }
                Text(
                    text = "Root: ${if (rootAvailable) "Available" else "Not checked"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun RoamingConfirmationDialog(
    country: String,
    onAllow: (alwaysAllow: Boolean) -> Unit,
    onDeny: () -> Unit,
    onDismiss: () -> Unit
) {
    var alwaysAllow by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Roaming Detected") },
        text = {
            Column {
                Text("Connect to network in $country?")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = alwaysAllow,
                        onCheckedChange = { alwaysAllow = it }
                    )
                    Text("Always allow for this country")
                }
            }
        },
        confirmButton = {
            Button(onClick = { onAllow(alwaysAllow) }) {
                Text("Allow")
            }
        },
        dismissButton = {
            TextButton(onClick = onDeny) {
                Text("Deny")
            }
        }
    )
}

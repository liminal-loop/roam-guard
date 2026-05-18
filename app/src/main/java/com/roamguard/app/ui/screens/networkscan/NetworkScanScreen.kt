package com.roamguard.app.ui.screens.networkscan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SignalWifi4Bar
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkScanScreen(
    viewModel: NetworkScanViewModel,
    onBack: () -> Unit
) {
    val networks by viewModel.networks.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()
    val connectResult by viewModel.connectResult.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(connectResult) {
        connectResult?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearConnectResult()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Available Networks") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = viewModel::scan,
                        enabled = !isScanning
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Scan")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (isScanning) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (networks.isEmpty()) {
                Text(
                    text = "No networks found.\nTap refresh to scan.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(networks) { network ->
                        NetworkItem(
                            network = network,
                            onClick = { viewModel.connectToNetwork(network.plmn) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NetworkItem(
    network: com.roamguard.domain.model.NetworkInfo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.SignalWifi4Bar,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = network.operatorName.ifEmpty { network.plmn },
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${network.countryName} (MCC: ${network.mcc})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (network.isHomeNetwork) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            if (network.isWhitelisted) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Whitelisted",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

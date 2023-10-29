//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.ExpenseTrackerTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Expense
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.Expense.formatedPrice
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.ExpenseTrackerTheme

object HomeDestination : NavigationDestination {

    override val route = "home"
    override val titleRes = R.string.app_name

}

/**
 * Entry route for Home Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(

    navigateToExpenseEntry: () -> Unit,
    navigateToExpenseUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {

    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ExpenseTrackerTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToExpenseEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.expense_entry_title)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            expenseList = homeUiState.expenseList,
            onExpenseClick = navigateToExpenseUpdate,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun HomeBody(

    expenseList: List<Expense>, onExpenseClick: (Int) -> Unit, modifier: Modifier = Modifier

) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        if (expenseList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_expenses_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

        } else {
            ExpenseList(
                expenseList = expenseList,
                onExpenseClick = { onExpenseClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun ExpenseList(

    expenseList: List<Expense>, onExpenseClick: (Expense) -> Unit, modifier: Modifier = Modifier

) {
    LazyColumn(modifier = modifier) {
        items(items = expenseList, key = { it.id }) { item ->
            ExpenseTrackerExpense(expense = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onExpenseClick(item) })
        }
    }
}

@Composable
private fun ExpenseTrackerExpense(

    expense: Expense, modifier: Modifier = Modifier

) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = expense.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))

                Text(
                    text = expense.formatedPrice(),
                    style = MaterialTheme.typography.titleLarge
                )
            }


            Text(
                text = expense.description,
                style = MaterialTheme.typography.titleLarge,
            )



        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {

    ExpenseTrackerTheme {

        HomeBody(listOf(

            Expense(1, "Game", 100.0,  "blah blah blah"),
            Expense(2, "Pen", 200.0,  "blah blah blah"),
            Expense(3, "TV", 300.0,  "blah blah blah")

        ), onExpenseClick = {})

    }

}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {

    ExpenseTrackerTheme {
        HomeBody(listOf(), onExpenseClick = {})
    }

}

@Preview(showBackground = true)
@Composable
fun ExpenseTrackerExpensePreview() {

    ExpenseTrackerTheme {
        ExpenseTrackerExpense(
            Expense(1, "Game", 100.0,  "blah blah blah"),
        )
    }

}


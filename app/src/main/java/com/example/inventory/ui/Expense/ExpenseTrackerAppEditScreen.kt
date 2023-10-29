//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.ui.Expense

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.ExpenseTrackerTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.Expense.ExpenseDetailsDestination.expenseIdArg
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.ExpenseTrackerTheme
import kotlinx.coroutines.launch

object ExpenseEditDestination : NavigationDestination {

    override val route = "expense_edit"
    override val titleRes = R.string.edit_expense_title
    const val expenseAppIdArg = "expenseId"
    val routeWithArgs = "$route/{$expenseIdArg}"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEditScreen(

    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExpenseEditViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ExpenseTrackerTopAppBar(
                title = stringResource(ExpenseEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ExpenseEntryBody(
            expenseUiState = viewModel.expenseUiState,
            onExpenseValueChange = viewModel::updateUiState,
            onSaveClick = {

                coroutineScope.launch {
                    viewModel.updateExpense()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseEditScreenPreview() {

    ExpenseTrackerTheme {

        ExpenseEditScreen(navigateBack = {  }, onNavigateUp = {  })

    }
}

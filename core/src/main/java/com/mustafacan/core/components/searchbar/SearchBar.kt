package com.mustafacan.core.components.searchbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mustafacan.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalSearch(
    onSearch: (query: String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp)
    ) {
        val (searchBar, textViewCancel) = createRefs()

        SearchBar(
            query = text,
            onQueryChange = {
                println("onQueryChange " + it)
                text = it
                onSearch(it)
            },
            onSearch = {},
            modifier = Modifier.constrainAs(searchBar) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start, margin = 15.dp)
                end.linkTo(
                    anchor = parent.end,
                    margin = 20.dp
                )
                width = Dimension.fillToConstraints
            },
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.search_placeholder_local
                    )
                )
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    Icon(
                        Icons.Default.Close, contentDescription = "Close Icon",
                        modifier = Modifier.clickable {
                            text = ""
                            onSearch("")
                        })
                }


            },
            active = false,
            onActiveChange = { }
        ) {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoteSearch(
    onSearch: (query: String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp)
    ) {
        val (searchBar, textViewCancel) = createRefs()

        androidx.compose.material3.SearchBar(
            query = text,
            onQueryChange = {
                println("onQueryChange " + it)
                text = it
            },
            onSearch = {
                onSearch(text)
            },
            modifier = Modifier.constrainAs(searchBar) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start, margin = 15.dp)
                end.linkTo(
                    anchor = if (text.isNotEmpty()) textViewCancel.start else parent.end,
                    margin = if (text.isNotEmpty()) 5.dp else 20.dp
                )
                width = Dimension.fillToConstraints
            },
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.search_placeholder_remote
                    )
                )
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Search Apply Icon",
                        modifier = Modifier.clickable { onSearch(text) })
                }


            },
            active = false,
            onActiveChange = { }
        ) {}

        Text(
            text = "cancel",
            modifier = Modifier
                .wrapContentWidth()
                .constrainAs(textViewCancel) {
                    centerVerticallyTo(parent)
                    end.linkTo(parent.end, margin = 20.dp)
                }
                .clickable {
                    text = ""
                    onSearch("")
                })

    }

}
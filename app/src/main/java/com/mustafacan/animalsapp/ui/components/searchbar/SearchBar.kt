package com.mustafacan.animalsapp.ui.components.searchbar

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
import com.mustafacan.animalsapp.R
import com.mustafacan.animalsapp.ui.model.enums.SearchType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    searchType: SearchType,
    onLocalSearch: (query: String) -> Unit,
    onRemoteSearch: (query: String) -> Unit
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
                if (searchType == SearchType.LOCAL_SEARCH)
                    onLocalSearch(it)
            },
            onSearch = {
                if (searchType == SearchType.REMOTE_SEARCH) {
                    onRemoteSearch(text)
                }
            },
            modifier = Modifier.constrainAs(searchBar) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start, margin = 15.dp)
                end.linkTo(
                    anchor = if (searchType == SearchType.REMOTE_SEARCH && text.isNotEmpty()) textViewCancel.start else parent.end,
                    margin = if (searchType == SearchType.REMOTE_SEARCH && text.isNotEmpty()) 5.dp else 20.dp
                )
                width = Dimension.fillToConstraints
            },
            placeholder = {
                Text(
                    text = if (searchType == SearchType.REMOTE_SEARCH) stringResource(
                        id = R.string.search_placeholder_remote
                    ) else stringResource(
                        id = R.string.search_placeholder_local
                    )
                )
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    if (searchType == SearchType.LOCAL_SEARCH) {
                        Icon(
                            Icons.Default.Close, contentDescription = "Close Icon",
                            modifier = Modifier.clickable {
                                text = ""
                                onLocalSearch("")
                            })
                    } else {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Search Apply Icon",
                            modifier = Modifier.clickable { onRemoteSearch(text) })
                    }
                }


            },
            active = false,
            onActiveChange = { }
        ) {}

        if (searchType == SearchType.REMOTE_SEARCH && text.isNotEmpty()) {
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
                        onRemoteSearch("")
                    })
        }

    }

}
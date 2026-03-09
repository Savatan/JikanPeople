package com.example.swapiapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapiapp.data.repository.PersonRepository
import com.example.swapiapp.domain.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PeopleListUiState {
    data object Loading : PeopleListUiState()
    data class Content(val people: List<Person>) : PeopleListUiState()
    data class Error(val message: String) : PeopleListUiState()
}

@HiltViewModel
class PeopleListViewModel @Inject constructor(
    private val repository: PersonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PeopleListUiState>(PeopleListUiState.Loading)
    val uiState: StateFlow<PeopleListUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadPeople()
    }

    fun loadPeople() {
        viewModelScope.launch {
            _uiState.value = PeopleListUiState.Loading
            try {
                val query = _searchQuery.value
                val people = if (query.isBlank()) {
                    repository.getPeople()
                } else {
                    repository.searchPeople(query)
                }
                _uiState.value = PeopleListUiState.Content(people)
            } catch (e: Exception) {
                _uiState.value = PeopleListUiState.Error(
                    e.localizedMessage ?: "Unknown error occurred"
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onSearch() {
        loadPeople()
    }
}

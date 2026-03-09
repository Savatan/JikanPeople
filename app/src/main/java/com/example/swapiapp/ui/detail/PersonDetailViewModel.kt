package com.example.swapiapp.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapiapp.domain.model.Person
import com.example.swapiapp.data.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PersonDetailUiState {
    data object Loading : PersonDetailUiState()
    data class Content(val person: Person) : PersonDetailUiState()
    data class Error(val message: String) : PersonDetailUiState()
}

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PersonRepository
) : ViewModel() {

    private val personId: Int = checkNotNull(savedStateHandle["personId"])

    private val _uiState = MutableStateFlow<PersonDetailUiState>(PersonDetailUiState.Loading)
    val uiState: StateFlow<PersonDetailUiState> = _uiState.asStateFlow()

    init {
        loadPersonDetail()
    }

    fun loadPersonDetail() {
        viewModelScope.launch {
            _uiState.value = PersonDetailUiState.Loading
            try {
                val person = repository.getPersonById(personId)
                _uiState.value = PersonDetailUiState.Content(person)
            } catch (e: Exception) {
                _uiState.value = PersonDetailUiState.Error(
                    e.localizedMessage ?: "Unknown error occurred"
                )
            }
        }
    }
}

package com.example.wellnessway.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wellnessway.WellnessWayApplication
import com.example.wellnessway.data.local.schema.History
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(

): ViewModel() {

    private val realm = WellnessWayApplication.realm
    val histories = realm
        .query<History>()
        .asFlow()
        .map { results ->
            results.list.toList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    fun deleteHistory(history: History) {
        viewModelScope.launch {
            realm.write {
                val item = findLatest(history)?: return@write
                delete(item)
            }
        }
    }
}
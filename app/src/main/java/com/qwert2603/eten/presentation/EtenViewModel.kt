package com.qwert2603.eten.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.EtenRepoStub
import com.qwert2603.eten.domain.repo.EtenRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class EtenViewModel(
    private val etenRepo: EtenRepo = EtenRepoStub
) : ViewModel() {

    val productsUpdates = etenRepo.productsUpdates()
        .shareIn(
            scope = viewModelScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
        )
}
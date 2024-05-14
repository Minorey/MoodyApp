package com.example.moodyapp.domain.usecases.app_entry

import com.example.moodyapp.domain.manger.LocalUserManger
import kotlinx.coroutines.flow.Flow

data class ReadAppEntry(
    private val localUserManger: LocalUserManger
) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManger.readAppEntry()
    }
}


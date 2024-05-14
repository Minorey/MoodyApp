package com.example.moodyapp.domain.usecases.app_entry

import com.example.moodyapp.domain.manger.LocalUserManger

data class SaveAppEntry(
    private val localUserManger: LocalUserManger
) {
    suspend operator fun invoke(){
        localUserManger.saveAppEntry()
    }
}

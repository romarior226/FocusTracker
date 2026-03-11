package com.example.focustracker.data

import com.example.focustracker.data.database.dao.HistoryDao
import com.example.focustracker.data.entity.HistoryEntityDbModel
import com.example.focustracker.data.mapper.toHistoryEntityDbModel
import com.example.focustracker.data.mapper.toTask
import com.example.focustracker.domain.repo.HistoryRepositoryInterface
import com.example.focustracker.domain.entity.Task
import javax.inject.Inject

class HistoryRepository @Inject constructor(private val historyDao: HistoryDao) :
    HistoryRepositoryInterface {
    suspend fun getAllHistoryEntityDbModelList(): List<HistoryEntityDbModel> {
        return historyDao.getAllHistory()
    }

    override suspend fun getAllHistoryList(): List<Task> {
        return getAllHistoryEntityDbModelList()
            .map {
                it.toTask()
            }
    }

    override suspend fun insertHistory(history: Task): Long {
        return historyDao.insertHistory(history.toHistoryEntityDbModel())
    }

    override suspend fun deleteHistory(history: Task) {
        historyDao.deleteHistory(history.toHistoryEntityDbModel())
    }


}
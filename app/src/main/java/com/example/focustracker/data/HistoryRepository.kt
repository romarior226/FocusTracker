package com.example.focustracker.data

import com.example.focustracker.data.dao.HistoryDao
import com.example.focustracker.data.entity.HistoryEntityDbModel
import com.example.focustracker.data.mapper.toTask
import com.example.focustracker.domain.Task

class HistoryRepository(private val historyDao: HistoryDao) {
        suspend fun getAllHistoryEntityDbModelList(): List<HistoryEntityDbModel> {
            return historyDao.getAllHistory()
        }

        suspend fun getAllHistoryList(): List<Task> {
            return getAllHistoryEntityDbModelList()
                .map {
                    it.toTask()
                }
        }
        suspend fun insertHistory(historyEntityDbModel: HistoryEntityDbModel): Long {
            return  historyDao.insertHistory(historyEntityDbModel)
        }

        suspend fun deleteHistory(historyEntityDbModel: HistoryEntityDbModel) {
            historyDao.deleteHistory(historyEntityDbModel)
        }

    }
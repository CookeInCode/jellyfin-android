package org.jellyfin.mobilec.data

import androidx.room.Database
import androidx.room.RoomDatabase
import org.jellyfin.mobilec.data.dao.ServerDao
import org.jellyfin.mobilec.data.dao.UserDao
import org.jellyfin.mobilec.data.entity.ServerEntity
import org.jellyfin.mobilec.data.entity.UserEntity

@Database(entities = [ServerEntity::class, UserEntity::class], version = 2)
abstract class JellyfinDatabase : RoomDatabase() {
    abstract val serverDao: ServerDao
    abstract val userDao: UserDao
}

package org.jellyfin.mobile.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jellyfin.mobile.AppPreferences
import org.jellyfin.mobile.model.sql.dao.ServerDao
import org.jellyfin.mobile.model.sql.dao.UserDao
import org.jellyfin.mobile.model.sql.entity.ServerEntity
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.model.DeviceInfo
import org.jellyfin.sdk.model.serializer.toUUID
import java.util.*

class ApiController(
    private val appPreferences: AppPreferences,
    private val jellyfin: Jellyfin,
    private val apiClient: ApiClient,
    private val serverDao: ServerDao,
    private val userDao: UserDao,
) {
    private val baseDeviceInfo: DeviceInfo
        get() = jellyfin.options.deviceInfo!!

    /**
     * Migrate from preferences if necessary
     */
    @Suppress("DEPRECATION")
    suspend fun migrateFromPreferences() {
        appPreferences.instanceUrl?.let { url ->
            setupServer(url)
            appPreferences.instanceUrl = null
        }
    }

    suspend fun setupServer(hostname: String) {
        appPreferences.currentServerId = withContext(Dispatchers.IO) {
            serverDao.getServerByHostname(hostname)?.id ?: serverDao.insert(hostname)
        }
        apiClient.baseUrl = hostname
    }

    suspend fun setupUser(serverId: Long, userId: String, accessToken: String) {
        appPreferences.currentUserId = withContext(Dispatchers.IO) {
            userDao.upsert(serverId, userId, accessToken)
        }
        configureApiClientUser(userId, accessToken)
    }

    suspend fun loadSavedServer(): ServerEntity? {
        val server = withContext(Dispatchers.IO) {
            val serverId = appPreferences.currentServerId ?: return@withContext null
            serverDao.getServer(serverId)
        }
        configureApiClientServer(server)
        return server
    }

    suspend fun loadSavedServerUser() {
        val serverUser = withContext(Dispatchers.IO) {
            val serverId = appPreferences.currentServerId ?: return@withContext null
            val userId = appPreferences.currentUserId ?: return@withContext null
            userDao.getServerUser(serverId, userId)
        }

        configureApiClientServer(serverUser?.server)

        if (serverUser?.user?.accessToken != null) {
            configureApiClientUser(serverUser.user.userId, serverUser.user.accessToken)
        } else {
            resetApiClientUser()
        }
    }

    private fun configureApiClientServer(server: ServerEntity?) {
        apiClient.baseUrl = server?.hostname
    }

    private fun configureApiClientUser(userId: String, accessToken: String) {
        apiClient.userId = userId.toUUID()
        apiClient.accessToken = accessToken
        // Append user id to device id to ensure uniqueness across sessions
        apiClient.deviceInfo = baseDeviceInfo.copy(id = baseDeviceInfo.id + userId)
    }

    private fun resetApiClientUser() {
        apiClient.userId = null
        apiClient.accessToken = null
        apiClient.deviceInfo = baseDeviceInfo
    }
}

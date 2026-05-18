package com.roamguard.data.repository

import com.roamguard.data.local.dao.HomeCountryDao
import com.roamguard.data.local.dao.WhitelistDao
import com.roamguard.data.local.entity.HomeCountryEntity
import com.roamguard.data.local.entity.WhitelistCountryEntity
import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.model.WhitelistCountry
import com.roamguard.domain.repository.WhitelistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WhitelistRepositoryImpl @Inject constructor(
    private val homeCountryDao: HomeCountryDao,
    private val whitelistDao: WhitelistDao
) : WhitelistRepository {

    override val homeCountry: Flow<HomeCountry?> = homeCountryDao.getHomeCountry().map { entity ->
        entity?.let { HomeCountry(id = it.id, mcc = it.mcc, countryName = it.countryName, countryCode = it.countryCode) }
    }

    override val whitelist: Flow<List<WhitelistCountry>> = whitelistDao.getAll().map { entities ->
        entities.map { WhitelistCountry(id = it.id, mcc = it.mcc, countryName = it.countryName, countryCode = it.countryCode) }
    }

    override suspend fun setHomeCountry(country: HomeCountry) {
        homeCountryDao.setHomeCountry(
            HomeCountryEntity(
                mcc = country.mcc,
                countryName = country.countryName,
                countryCode = country.countryCode
            )
        )
    }

    override suspend fun addToWhitelist(country: WhitelistCountry) {
        whitelistDao.insert(
            WhitelistCountryEntity(
                mcc = country.mcc,
                countryName = country.countryName,
                countryCode = country.countryCode
            )
        )
    }

    override suspend fun removeFromWhitelist(mcc: Int) {
        whitelistDao.deleteByMcc(mcc)
    }

    override suspend fun isWhitelisted(mcc: Int): Boolean {
        return whitelistDao.exists(mcc) > 0
    }
}

//package com.plum.superheroapp.presentation
//
//import com.plum.superheroapp.data.cache.AppDatabase
//import com.plum.superheroapp.data.mapper.toData
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import kotlinx.coroutines.runBlocking
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import javax.inject.Inject
//
//@HiltAndroidTest
//abstract class SuperHeroAndroidTest {
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @Inject
//    lateinit var db: AppDatabase
//
//    @Before
//    open fun setUp() = runBlocking {
//        db.clearAllTables()
//        populateDb()
//
//    }
//
//
//    @After
//    open fun tearDown() {
//        db.close()
//    }
//
//    private suspend fun populateDb() {
//        val heroes = (0..11)
//            .map {
//                DummyEntities.hero.copy(
//                    id = it,
//                    isSquadMember = it%2==0
//                ) }
//            .map { it.toData() }
//
//        db.getHeroDao().put(heroes)
//    }
//
//}
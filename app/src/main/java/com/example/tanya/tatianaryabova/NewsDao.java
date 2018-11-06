package com.example.tanya.tatianaryabova;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Observable;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM NewsEntity")
    List<NewsEntity> getAll();

    @Query("SELECT * FROM NewsEntity WHERE id = :id")
    NewsEntity getNewsById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NewsEntity... newsEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsEntity newsEntity);

    @Delete
    void delete(NewsEntity newsEntity);

    @Query("DELETE FROM NewsEntity")
    void deleteAll();
}

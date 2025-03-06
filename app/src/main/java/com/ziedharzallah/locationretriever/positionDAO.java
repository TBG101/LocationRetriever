package com.ziedharzallah.locationretriever;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface positionDAO
{
    @Insert
    void insert ( Position position ) ;

    @Query("SELECT * FROM position")
    List<Position> getALlPositions();

}

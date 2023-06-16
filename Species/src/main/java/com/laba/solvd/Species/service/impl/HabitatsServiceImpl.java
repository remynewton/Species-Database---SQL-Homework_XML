package com.laba.solvd.Species.service.impl;

import com.laba.solvd.Species.domain.Habitats;
import com.laba.solvd.Species.persistence.HabitatsRepository;
import com.laba.solvd.Species.persistence.impl.HabitatsRepositoryImpl;
import com.laba.solvd.Species.service.HabitatsService;

public class HabitatsServiceImpl implements HabitatsService {
    private final HabitatsRepository habitatsRepository;

    public HabitatsServiceImpl(HabitatsRepository habitatsRepository) {
        this.habitatsRepository = new HabitatsRepositoryImpl();
    }

    @Override
    public void create(Habitats habitats) {
        habitatsRepository.create(habitats);
    }
}
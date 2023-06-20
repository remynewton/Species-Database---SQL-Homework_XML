package com.laba.solvd.Species.service.impl;

import com.laba.solvd.Species.domain.Kingdoms;
import com.laba.solvd.Species.persistence.KingdomsRepository;
import com.laba.solvd.Species.persistence.impl.KingdomsRepositoryImpl;
import com.laba.solvd.Species.service.KingdomsService;

public class KingdomsServiceImpl implements KingdomsService {
    private final KingdomsRepository kingdomsRepository;

    public KingdomsServiceImpl() {
        this.kingdomsRepository = new KingdomsRepositoryImpl();
    }

    @Override
    public void create(Kingdoms kingdom) {
        kingdom.setId(null);
        kingdomsRepository.create(kingdom);
    }
}

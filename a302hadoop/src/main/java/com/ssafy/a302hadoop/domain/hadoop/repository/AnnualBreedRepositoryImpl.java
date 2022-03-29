package com.ssafy.a302hadoop.domain.hadoop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.ssafy.a302hadoop.domain.hadoop.entity.QAnnualBreed.annualBreed;

public class AnnualBreedRepositoryImpl implements AnnualBreedRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AnnualBreedRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public int getTotalFiveBreeds(int year, String species) {
        return queryFactory
                .select(
                        annualBreed.count.sum()
                ).from(annualBreed)
                .where(annualBreed.year.eq(year).and(annualBreed.species.eq(species)))
                .fetch().get(0);
    }
}

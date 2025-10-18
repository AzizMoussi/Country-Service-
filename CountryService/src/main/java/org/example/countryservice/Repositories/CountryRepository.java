package org.example.countryservice.Repositories;

import org.example.countryservice.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Integer> {

}
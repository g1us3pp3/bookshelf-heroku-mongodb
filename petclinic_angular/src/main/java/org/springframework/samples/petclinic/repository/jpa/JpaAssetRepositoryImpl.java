/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository.jpa;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;
//import org.springframework.samples.petclinic.model.Pet;
//import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Asset;
import org.springframework.samples.petclinic.repository.AssetRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of the {@link AssetRepository} interface.
 *

 * @author Giuseppe Marsico
 * @since 27.12.2015
 */
@Repository
public class JpaAssetRepositoryImpl implements AssetRepository {

    @PersistenceContext
    private EntityManager em;

    /*
    @Override
    @SuppressWarnings("unchecked")
    public List<PetType> findPetTypes() {
        return this.em.createQuery("SELECT ptype FROM PetType ptype ORDER BY ptype.name").getResultList();
    }
	*/
    @Override
    public Asset findById(int id) {
        return this.em.find(Asset.class, id);
    }

    @Override
    public void save(Asset asset) {
    	if (asset.getId() == null) {
    		this.em.persist(asset);     		
    	}
    	else {
    		this.em.merge(asset);    
    	}
    }

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Asset> findAll() throws DataAccessException {
		return this.em.createQuery("SELECT assets FROM Asset assets").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Asset> findByName(String query) throws DataAccessException {
		return this.em.createQuery("SELECT assets FROM Asset assets WHERE UPPER(asset.name) like UPPER('%" + query + "%')").getResultList();
	}

}

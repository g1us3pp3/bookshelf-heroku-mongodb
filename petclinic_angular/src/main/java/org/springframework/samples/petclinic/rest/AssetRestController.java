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
package org.springframework.samples.petclinic.rest;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Asset;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lim.han
 */
@RestController
@RequestMapping("/api")
public class AssetRestController {

    private final ClinicService clinicService;


    @Autowired
    public AssetRestController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }
    
    /*
    @RequestMapping(value = "/pets/types", method = RequestMethod.GET)
    public Collection<PetType> populatePetTypes() {
        return this.clinicService.findPetTypes();
    }
    
    @RequestMapping(value = "/owners/{ownerId}/pets", method = RequestMethod.POST)
    public Pet addPet(@PathVariable("ownerId") int ownerId, @RequestBody Pet pet) {
        Owner owner = this.clinicService.findOwnerById(ownerId);
        owner.addPet(pet);
        this.clinicService.savePet(pet);
        return pet;
    }
	*/
    @RequestMapping(value = "/assets", method = RequestMethod.POST)
    public @ResponseBody Asset create(@RequestBody Asset asset) {
    	if (asset.getId()!=null && asset.getId()>0){
    		Asset existingAsset = clinicService.findAssetById(asset.getId());
    		BeanUtils.copyProperties(asset, existingAsset, "pets", "id");
    		clinicService.saveAsset(existingAsset);
    	}
    	else {
    		this.clinicService.saveAsset(asset);
    	}
    	
    	return asset;
    }
    
    @RequestMapping(value = "/assets", method = RequestMethod.PUT)
    public @ResponseBody Collection<Asset> create(@RequestBody Collection<Asset> assets) {
    	for(Asset asset : assets) {
    		this.clinicService.saveAsset(asset);
    	}
    	
    	return assets;
    }
    
    
    @RequestMapping(value = "/assets/{id}", method = RequestMethod.GET)
    public @ResponseBody Asset find(@PathVariable Integer id) {
        return this.clinicService.findAssetById(id);
    }
    
    @RequestMapping(value = "/assets", method = RequestMethod.GET)
    public Collection<Asset> findAllAssets() {
    	return this.clinicService.findAssets();	
    }

    @RequestMapping(value = "/assets/search", method = RequestMethod.GET)
    public Collection<Asset> search(@RequestParam String q) {
    	return this.clinicService.findAssetByName(q);
    }
}

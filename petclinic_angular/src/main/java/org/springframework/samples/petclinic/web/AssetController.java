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
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Asset;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**

 * @author Giuseppe Marsico
 */
@Controller
@SessionAttributes(types = Asset.class)
public class AssetController {

    private final ClinicService clinicService;

    @Autowired
    public AssetController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/assets/new", method = RequestMethod.GET)
    public String initCreationForm(Map<String, Object> model) {
        Asset asset = new Asset();
        model.put("asset", asset);
        return "assets/createOrUpdateAssetForm";
    }

    @RequestMapping(value = "/assets/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Asset asset, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "assets/createOrUpdateAssetForm";
        } else {
            this.clinicService.saveAsset(asset);
            status.setComplete();
            return "redirect:/assets/" + asset.getId();
        }
    }

    @RequestMapping(value = "/assets/find", method = RequestMethod.GET)
    public String initFindForm(Map<String, Object> model) {
        model.put("asset", new Asset());
        return "assets/findAssets";
    }

    @RequestMapping(value = "/assets", method = RequestMethod.GET)
    public String processFindForm(Asset asset, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (asset.getName() == null) {
            asset.setName(""); // empty string signifies broadest possible search
        }

        // find assets by last name
        Collection<Asset> results = this.clinicService.findAssetByName(asset.getName());
        if (results.size() < 1) {
            // no owners found
            result.rejectValue("Name", "notFound", "not found");
            return "assets/findAssets";
        }
        if (results.size() > 1) {
            // multiple owners found
            model.put("selections", results);
            return "assets/assetsList";
        } else {
            // 1 owner found
            asset = results.iterator().next();
            return "redirect:/assets/" + asset.getId();
        }
    }

    @RequestMapping(value = "/assets/{assetId}/edit", method = RequestMethod.GET)
    public String initUpdateOwnerForm(@PathVariable("assetId") int assetId, Model model) {
        Asset asset = this.clinicService.findAssetById(assetId);
        model.addAttribute(asset);
        return "assets/createOrUpdateAssetForm";
    }

    @RequestMapping(value = "/assets/{assetId}/edit", method = RequestMethod.PUT)
    public String processUpdateOwnerForm(@Valid Asset asset, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "assets/createOrUpdateAssetForm";
        } else {
            this.clinicService.saveAsset(asset);
            status.setComplete();
            return "redirect:/assets/{assetId}";
        }
    }

    /**
     * Custom handler for displaying an asset.
     *
     * @param ownerId the ID of the asset to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/assets/{assetId}")
    public ModelAndView showAsset(@PathVariable("assetId") int assetId) {
        ModelAndView mav = new ModelAndView("assets/assetDetails");
        mav.addObject(this.clinicService.findAssetById(assetId));
        return mav;
    }

}

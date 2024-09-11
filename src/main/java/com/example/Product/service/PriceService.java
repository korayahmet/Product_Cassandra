package com.example.Product.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.Product.Exceptions.ExceptionsWithMessageConflict;
import com.example.Product.Exceptions.ExceptionsWithMessageNotFound;
import com.example.Product.model.Price;
import com.example.Product.repo.PriceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;

    public void savePrice(Price price) {
        Optional<Price> PricesByProductId = priceRepository.findAllByProductId(price.getProductId());
        if(PricesByProductId.isPresent()){
            System.out.println("Conflict check: Entry with productId " + price.getProductId() + " already exists.");        //debug
            throw new ExceptionsWithMessageConflict("This entry with productId: " + price.getProductId() + " already exists.");
        }
        else {System.out.println("NoConflict" + price.getProductId());}         //debug

        // Set a UUID if not provided
        if (price.getId() == null) {
            price.setId(Uuids.timeBased());         //new - set a time based uuid to id before saving so it is unique
        }
        priceRepository.save(price);
    }

    public Price getPricesByProductId(String productId) {
        return priceRepository.findByProductId(productId);
    }

    public void deletePricesByProductId(String productId) {
        Price priceToDelete = priceRepository.findByProductId(productId);
        priceRepository.delete(priceToDelete);
    }

    public void updatePricesByProductId(Price updatedPrice){
        Optional<Price> priceToUpdateOptional = priceRepository.findAllByProductId(updatedPrice.getProductId());
        if (priceToUpdateOptional.isPresent()){
            Price priceToUpdate = priceToUpdateOptional.get();

            priceToUpdate.setValues(updatedPrice.getValues());
            priceToUpdate.setDates(updatedPrice.getDates());

            priceRepository.save(priceToUpdate);
        } else {
            throw new ExceptionsWithMessageNotFound("User with the id \"" + updatedPrice.getProductId() + "\" does not exist.");
        }
    }
}

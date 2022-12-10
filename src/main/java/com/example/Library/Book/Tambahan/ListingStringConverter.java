package com.example.Library.Book.Tambahan;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
public class ListingStringConverter {
    public Set<Long> convertListInStringToSetInLong(String listInString){

        Set<Long> converted = new LinkedHashSet<Long>();

        if (listInString.length() <= 2) {
            return converted;
        } else {
            String idsInString = listInString.substring(1, listInString.length() -1);
            List<String> idsStringArrayList = Arrays.asList(idsInString.split(", "));
            for (String id : idsStringArrayList) converted.add(Long.parseLong(id));
            return converted;
        }
    }
}

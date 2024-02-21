package rw.dyna.ecommerce.v1.utils;

import org.hibernate.collection.internal.PersistentBag;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.ArrayList;
import java.util.List;

public class PersistentBagToListConverter implements Converter<PersistentBag, List<?>> {
    @Override
    public List<?> convert(MappingContext<PersistentBag, List<?>> context) {
        PersistentBag persistentBag = context.getSource();
        List<?> list = new ArrayList<>(persistentBag);
        return list;
    }
}
package ru.otus.finalproject.rest.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.rest.dto.ProductDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);

    @InheritInverseConfiguration
    Product toProduct(ProductDto productDto);

    List<ProductDto> toProductDtoList(List<Product> productList);

    List<Product> toProductList(List<ProductDto> productDtoList);
}

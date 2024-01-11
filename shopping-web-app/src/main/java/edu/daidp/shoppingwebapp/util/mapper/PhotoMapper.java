package edu.daidp.shoppingwebapp.util.mapper;

import edu.daidp.shoppingwebapp.dto.PhotoDto;
import edu.daidp.shoppingwebapp.entity.Photo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapper {

    private final ModelMapper modelMapper;


    public PhotoMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PhotoDto toDto(Photo photo) {
        return modelMapper.map(photo, PhotoDto.class);
    }

    public Photo toEntity(PhotoDto photoDto) {
        return modelMapper.map(photoDto, Photo.class);
    }
}

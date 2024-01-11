package edu.daidp.shoppingwebapp.util.mapper;

import edu.daidp.shoppingwebapp.dto.VideoDto;
import edu.daidp.shoppingwebapp.entity.Video;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    private final ModelMapper modelMapper;


    public VideoMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VideoDto toDto(Video video) {
        return modelMapper.map(video, VideoDto.class);
    }

    public Video toEntity(VideoDto videoDto) {
        return modelMapper.map(videoDto, Video.class);
    }
}

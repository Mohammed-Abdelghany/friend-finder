package com.example.friendfinder.mapper;

import com.example.friendfinder.dto.PostDto;
import com.example.friendfinder.model.Post;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto postToPostDto(Post post);
    Post postDtoToPost(PostDto postDto);

}

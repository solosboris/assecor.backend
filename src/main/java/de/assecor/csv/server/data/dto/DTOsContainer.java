package de.assecor.csv.server.data.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class DTOsContainer {

    private final List<PersonDTO> dtos;

}
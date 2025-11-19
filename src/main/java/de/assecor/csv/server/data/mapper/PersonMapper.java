package de.assecor.csv.server.data.mapper;

import de.assecor.csv.server.data.dto.PersonDTO;
import de.assecor.csv.server.data.enums.ColorEnum;
import de.assecor.csv.server.data.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class PersonMapper {

    public static final PersonMapper INSTANCE =
            Mappers.getMapper(PersonMapper.class);

    public PersonDTO model2Dto(
        Person model
    ) {
        if ( model == null ) {
            return null;
        }
        return new PersonDTO(
                model.getId(),
                notNull(model.getName()),
                notNull(model.getLastName()),
                notNull(model.getZip()),
                notNull(model.getCity()),
                model.getColor().getColor()
        );
    }

    public Person dto2Model(
        PersonDTO dto
    ) {
        if (dto == null ) {
            return null;
        }
        return new Person(
                dto.getId(),
                dto.getName(),
                dto.getLastName(),
                dto.getZip(),
                dto.getCity(),
                ColorEnum.getByColor(
                    dto.getColor()
                )
        );
    }

    private static String notNull(String param) {
        return param == null ? "" : param;
    }

}
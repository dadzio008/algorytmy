package com.example.algorytmy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceAlgorytmy {

    public List<Person> getAllPersonsFromFile(MultipartFile file, Model model, String sortBy, String order) throws IOException {
        Path tempFile = Files.createTempFile("upload", ".json");
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Person> persons = objectMapper.readValue(tempFile.toFile(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
        LinkedList<Person> personsRetVal = null;
        switch(sortBy) {
            case "age":
                personsRetVal = getPeople(order, persons, Comparator.comparing(Person::getAge));
                break;
            case "name":
                personsRetVal = getPeople(order, persons, Comparator.comparing(Person::getName));
                break;
            case "email":
                personsRetVal = getPeople(order, persons, Comparator.comparing(Person::getEmail));
                break;
            case "address" :
                personsRetVal = getPeople(order, persons, Comparator.comparing(Person::getAddress));
                break;
            case "phone" :
                personsRetVal = getPeople(order, persons, Comparator.comparing(Person::getPhone));
                break;
            }
        return personsRetVal;
    }

    private LinkedList<Person> getPeople(String order, List<Person> persons, Comparator<Person> comparing) {
        LinkedList<Person> personsRetVal;
        if ("ascent".equals(order)) {
            personsRetVal = persons.stream().sorted(comparing).collect(Collectors.toCollection(LinkedList::new));
        }
        else {
            personsRetVal = persons.stream().sorted(comparing.reversed()).collect(Collectors.toCollection(LinkedList::new));
        }
        return personsRetVal;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.Helper;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */
@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @NonNull
    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @NonNull
    @Column(name = "Instructor", nullable = false, length = 50)
    private String instructor;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Student> students;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }
}
